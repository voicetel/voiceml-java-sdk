package com.voicetel.voiceml.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.VoicemlClient;
import com.voicetel.voiceml.exceptions.ValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spec-driven contract test (C15).
 *
 * <p>Walks every public method of every resource reachable from {@link VoicemlClient}, invokes it
 * against three local mock hosts (base, Messaging Service, Conversations) that answer with
 * samples generated from the spec's response schemas, and asserts for every request:
 * <ul>
 *   <li>(method, path including the {@code .json} / {@code .wav} / no-suffix rules) matches an
 *       operation of the vendored spec;</li>
 *   <li>the host is the product host for the op's tag and for the client group the method
 *       hangs off;</li>
 *   <li>{@code Authorization} is present, or absent for {@code security: []} ops;</li>
 *   <li>query keys and form / JSON body keys are declared by the op, with the declared content
 *       type, required keys present and repeated keys only for array parameters.</li>
 * </ul>
 * It also asserts every spec operation is reached (on both hosts for the double-tagged
 * {@code /v1/Services} ops), every declared query parameter and body property is sendable, and
 * that no SDK method fails against spec-shaped responses: once with typed values, unknown enum
 * values and extra properties, once with every property {@code null}.
 */
class SpecContractTest {

    /**
     * Properties the SDK sends that the vendored spec does not declare yet. Each entry must name the
     * op and the reason; an entry the spec already declares fails the test so it gets removed.
     * Intentionally empty: the spec now declares every property the SDK sends.
     */
    private static final Map<String, Set<String>> PENDING_SPEC_PROPS = Map.of();

    /** Spec ops the SDK cannot reach. Intentionally empty. */
    private static final Set<String> UNCOVERABLE_OPS = Set.of();

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static SpecModel spec;
    private static final Map<String, HttpServer> SERVERS = new LinkedHashMap<>();
    private static final List<Captured> CAPTURED = new CopyOnWriteArrayList<>();
    private static volatile boolean nullResponses;

    private static final List<String> failures = new ArrayList<>();
    private static final Map<String, Set<String>> coveredHosts = new TreeMap<>();
    private static final Map<String, Set<String>> sentKeys = new HashMap<>();
    private static final Map<String, Map<Type, String>> returnTypes = new TreeMap<>();
    private static int counter;

    static final class Captured {
        final String host;
        final String method;
        final String rawPath;
        final String rawQuery;
        final String contentType;
        final boolean auth;
        final byte[] body;

        Captured(String host, String method, String rawPath, String rawQuery, String contentType,
                 boolean auth, byte[] body) {
            this.host = host;
            this.method = method;
            this.rawPath = rawPath;
            this.rawQuery = rawQuery;
            this.contentType = contentType;
            this.auth = auth;
            this.body = body;
        }
    }

    @BeforeAll
    static void startServers() throws IOException {
        spec = new SpecModel();
        for (String host : new String[] {"base", "messaging", "conversations"}) {
            HttpServer srv = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            srv.createContext("/", ex -> {
                byte[] body = ex.getRequestBody().readAllBytes();
                String method = ex.getRequestMethod();
                String rawPath = ex.getRequestURI().getRawPath();
                CAPTURED.add(new Captured(host, method, rawPath, ex.getRequestURI().getRawQuery(),
                        ex.getRequestHeaders().getFirst("Content-Type"),
                        ex.getRequestHeaders().getFirst("Authorization") != null, body));
                SpecModel.Op op = spec.match(method, rawPath);
                int status = op == null ? 404 : op.successStatus();
                byte[] resp = new byte[0];
                String ct = "application/json";
                if (op != null) {
                    JsonNode schema = op.successJsonSchema(spec);
                    if (schema != null) {
                        resp = MAPPER.writeValueAsBytes(spec.sample(schema, nullResponses, 0));
                    } else if (op.successContentType() != null) {
                        ct = op.successContentType();
                        resp = (ct.contains("json") ? "{\"openapi\":\"3.1.0\"}" : "payload")
                                .getBytes(StandardCharsets.UTF_8);
                    }
                    if (status == 302) {
                        status = 200;
                        ct = "application/octet-stream";
                        resp = "payload".getBytes(StandardCharsets.UTF_8);
                    }
                }
                ex.getResponseHeaders().add("Content-Type", ct);
                if (status == 204 || resp.length == 0) {
                    ex.sendResponseHeaders(status, -1);
                    ex.close();
                    return;
                }
                ex.sendResponseHeaders(status, resp.length);
                try (OutputStream os = ex.getResponseBody()) {
                    os.write(resp);
                }
            });
            srv.start();
            SERVERS.put(host, srv);
        }
        walkAll(false);
        walkAll(true);
    }

    @AfterAll
    static void stopServers() {
        SERVERS.values().forEach(s -> s.stop(0));
    }

    private static String url(String host) {
        return "http://127.0.0.1:" + SERVERS.get(host).getAddress().getPort();
    }

    private static void walkAll(boolean secondPass) {
        nullResponses = secondPass;
        VoicemlClient client = VoicemlClient.builder()
                .accountSid("ACzzAccountZZ")
                .apiKey("contract-test-key")
                .baseUrl(url("base"))
                .messagingBaseUrl(url("messaging"))
                .conversationsBaseUrl(url("conversations"))
                .maxRetries(0)
                .build();
        Method[] roots = VoicemlClient.class.getMethods();
        Arrays.sort(roots, Comparator.comparing(Method::getName));
        for (Method m : roots) {
            if (m.getParameterCount() == 0 && isResource(m.getReturnType())) {
                String group = m.getName();
                String host = group.equals("conversationsV1") ? "conversations"
                        : group.equals("messagingV1") ? "messaging" : "base";
                try {
                    walk(m.invoke(client), "client." + group + "()", host, !secondPass, 0, new LinkedHashSet<>());
                } catch (ReflectiveOperationException ex) {
                    failures.add("cannot open group " + group + ": " + ex);
                }
            }
        }
    }

    private static boolean isResource(Class<?> c) {
        return c.getPackageName().equals("com.voicetel.voiceml.resources");
    }

    private static void walk(Object res, String chain, String expectedHost, boolean bools, int depth,
                             Set<String> visiting) {
        if (res == null || depth > 6 || !visiting.add(res.getClass().getName())) {
            return;
        }
        Method[] methods = res.getClass().getMethods();
        Arrays.sort(methods, Comparator.comparing(Method::toGenericString));
        for (Method m : methods) {
            if (m.getDeclaringClass() == Object.class || Modifier.isStatic(m.getModifiers())) {
                continue;
            }
            String label = chain + "." + m.getName() + Arrays.toString(m.getParameterTypes());
            if (isResource(m.getReturnType())) {
                try {
                    Object sub = m.invoke(res, dummyArgs(m, bools, false));
                    walk(sub, chain + "." + m.getName() + "(…)", expectedHost, bools, depth + 1, visiting);
                } catch (ReflectiveOperationException ex) {
                    failures.add(label + ": could not open sub-resource: " + ex);
                }
                continue;
            }
            invoke(res, m, label, expectedHost, bools);
        }
        visiting.remove(res.getClass().getName());
    }

    private static void invoke(Object res, Method m, String label, String expectedHost, boolean bools) {
        for (boolean reduced : new boolean[] {false, true}) {
            CAPTURED.clear();
            Throwable error = null;
            try {
                Object out = m.invoke(res, dummyArgs(m, bools, reduced));
                if (out instanceof Pager<?>) {
                    for (Object ignored : (Pager<?>) out) {
                        // drain the (single-page) mock list
                    }
                }
            } catch (InvocationTargetException ex) {
                error = ex.getCause();
            } catch (ReflectiveOperationException ex) {
                error = ex;
            }
            if (error instanceof ValidationException && !reduced) {
                continue;
            }
            if (error != null) {
                failures.add(label + (nullResponses ? " [null responses]" : "") + " threw " + error);
            }
            if (CAPTURED.isEmpty() && error == null) {
                failures.add(label + ": sent no request");
            }
            for (Captured c : CAPTURED) {
                SpecModel.Op op = check(label, expectedHost, c);
                Type rt = m.getGenericReturnType();
                if (op != null && error == null && rt != void.class && !(rt instanceof ParameterizedType
                        && ((ParameterizedType) rt).getRawType() == Pager.class)) {
                    returnTypes.computeIfAbsent(op.id(), k -> new LinkedHashMap<>()).putIfAbsent(rt, label);
                }
            }
            return;
        }
    }

    private static SpecModel.Op check(String label, String expectedHost, Captured c) {
        SpecModel.Op op = spec.match(c.method, c.rawPath);
        if (op == null) {
            failures.add(label + ": " + c.method + " " + c.rawPath + " matches no spec operation");
            return null;
        }
        coveredHosts.computeIfAbsent(op.id(), k -> new TreeSet<>()).add(c.host);
        String where = label + " → " + op.id();
        if (!op.allowedHosts().contains(c.host) || !c.host.equals(expectedHost)) {
            failures.add(where + ": sent to " + c.host + " host; spec tags allow " + op.allowedHosts()
                    + ", client group expects " + expectedHost);
        }
        if (c.auth != op.auth) {
            failures.add(where + ": Authorization " + (c.auth ? "sent" : "missing") + " but spec security is "
                    + (op.auth ? "basicAuth" : "[]"));
        }
        Set<String> sent = sentKeys.computeIfAbsent(op.id(), k -> new LinkedHashSet<>());
        Map<String, Integer> query = keyCounts(c.rawQuery);
        for (Map.Entry<String, Integer> e : query.entrySet()) {
            sent.add("query:" + e.getKey());
            if (!op.query.containsKey(e.getKey())) {
                failures.add(where + ": query key '" + e.getKey() + "' is not declared");
            } else if (e.getValue() > 1 && !SpecModel.types(op.querySchemas.get(e.getKey())).contains("array")) {
                failures.add(where + ": query key '" + e.getKey() + "' repeated but not an array");
            }
        }
        op.query.forEach((k, required) -> {
            if (required && !query.containsKey(k)) {
                failures.add(where + ": required query key '" + k + "' missing");
            }
        });
        if (c.body.length == 0) {
            if (op.bodyRequired && !op.bodies.isEmpty()) {
                // A required body with no required props may be sent empty.
                boolean anyRequired = op.bodies.values().stream().anyMatch(p -> p.containsValue(true));
                if (anyRequired) {
                    failures.add(where + ": required body missing");
                }
            }
            return op;
        }
        String ct = c.contentType == null ? "" : c.contentType.split(";")[0].trim();
        Map<String, Boolean> props = op.bodies.get(ct);
        if (props == null) {
            failures.add(where + ": body content type '" + ct + "' not declared (" + op.bodies.keySet() + ")");
            return op;
        }
        Map<String, Integer> keys;
        if (ct.equals("application/json")) {
            keys = new LinkedHashMap<>();
            try {
                MAPPER.readTree(c.body).fieldNames().forEachRemaining(k -> keys.put(k, 1));
            } catch (IOException ex) {
                failures.add(where + ": JSON body does not parse: " + ex);
                return op;
            }
        } else {
            keys = keyCounts(new String(c.body, StandardCharsets.UTF_8));
        }
        Set<String> pending = PENDING_SPEC_PROPS.getOrDefault(op.operationId, Set.of());
        for (Map.Entry<String, Integer> e : keys.entrySet()) {
            sent.add("body:" + e.getKey());
            if (!props.containsKey(e.getKey()) && !pending.contains(e.getKey())) {
                failures.add(where + ": body key '" + e.getKey() + "' is not declared for " + ct);
            }
        }
        props.forEach((k, required) -> {
            if (required && !keys.containsKey(k)) {
                failures.add(where + ": required body key '" + k + "' missing");
            }
        });
        return op;
    }

    private static Map<String, Integer> keyCounts(String encoded) {
        Map<String, Integer> out = new LinkedHashMap<>();
        if (encoded == null || encoded.isEmpty()) {
            return out;
        }
        for (String pair : encoded.split("&")) {
            String k = URLDecoder.decode(pair.split("=", 2)[0], StandardCharsets.UTF_8);
            out.merge(k, 1, Integer::sum);
        }
        return out;
    }

    // --- dummy arguments -----------------------------------------------------------------------

    private static Object[] dummyArgs(Method m, boolean bools, boolean reduced) {
        Class<?>[] types = m.getParameterTypes();
        Type[] generic = m.getGenericParameterTypes();
        Object[] out = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            out[i] = dummy(types[i], generic[i], bools, reduced);
        }
        return out;
    }

    private static Object dummy(Class<?> t, Type gt, boolean bools, boolean reduced) {
        if (t == String.class) {
            return "Zp" + (++counter) + "Z";
        }
        if (t == int.class || t == Integer.class) {
            return 3;
        }
        if (t == long.class || t == Long.class) {
            return 3L;
        }
        if (t == boolean.class || t == Boolean.class) {
            return bools;
        }
        if (t == double.class || t == Double.class) {
            return 1.5d;
        }
        if (t == float.class || t == Float.class) {
            return 2.5f;
        }
        if (t == LocalDate.class) {
            return LocalDate.of(2026, 1, 2);
        }
        if (List.class.isAssignableFrom(t)) {
            return new ArrayList<>(List.of("a", "b"));
        }
        if (Map.class.isAssignableFrom(t)) {
            Map<String, Object> mm = new LinkedHashMap<>();
            mm.put("k", "v");
            return mm;
        }
        if (t.isEnum()) {
            return t.getEnumConstants()[0];
        }
        if (t == Class.class && gt instanceof ParameterizedType) {
            return null;
        }
        if (t.getPackageName().equals("com.voicetel.voiceml.models")) {
            return populate(t, bools, reduced);
        }
        return null;
    }

    private static Object populate(Class<?> t, boolean bools, boolean reduced) {
        try {
            Method b = t.getMethod("builder");
            Object builder = b.invoke(null);
            Method[] setters = builder.getClass().getMethods();
            Arrays.sort(setters, Comparator.comparing(Method::toGenericString));
            for (Method s : setters) {
                if (s.getDeclaringClass() == Object.class || s.getName().equals("build")
                        || s.getParameterCount() != 1 || s.getReturnType() != builder.getClass()) {
                    continue;
                }
                if (reduced && (s.getName().equals("page") || s.getName().equals("pageToken"))) {
                    continue;
                }
                if (s.getParameterTypes()[0] == LocalDate.class) {
                    continue; // String overload carries the value
                }
                Object v = dummy(s.getParameterTypes()[0], s.getGenericParameterTypes()[0], bools, reduced);
                if (v != null) {
                    s.invoke(builder, v);
                }
            }
            return builder.getClass().getMethod("build").invoke(builder);
        } catch (NoSuchMethodException noBuilder) {
            Constructor<?>[] cs = t.getConstructors();
            if (cs.length == 0) {
                return null;
            }
            Arrays.sort(cs, Comparator.comparingInt(Constructor::getParameterCount));
            Constructor<?> c = cs[cs.length - 1];
            Object[] args = new Object[c.getParameterCount()];
            for (int i = 0; i < args.length; i++) {
                args[i] = dummy(c.getParameterTypes()[i], c.getGenericParameterTypes()[i], bools, reduced);
            }
            try {
                return c.newInstance(args);
            } catch (ReflectiveOperationException ex) {
                throw new IllegalStateException("cannot construct " + t, ex);
            }
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("cannot populate " + t, ex);
        }
    }

    // --- assertions ----------------------------------------------------------------------------

    @Test
    void everyRequestMatchesTheSpec() {
        assertThat(failures).as("contract violations").isEmpty();
    }

    @Test
    void everySpecOperationIsCovered() {
        List<String> missing = new ArrayList<>();
        for (SpecModel.Op op : spec.ops) {
            if (UNCOVERABLE_OPS.contains(op.operationId)) {
                continue;
            }
            Set<String> hit = coveredHosts.getOrDefault(op.id(), Set.of());
            for (String host : op.allowedHosts()) {
                if (!hit.contains(host)) {
                    missing.add(op.id() + " on " + host + " host");
                }
            }
        }
        int covered = (int) spec.ops.stream().filter(o -> coveredHosts.containsKey(o.id())).count();
        System.out.println("[contract] coverage: " + covered + "/" + spec.ops.size() + " operations");
        assertThat(spec.ops).hasSize(333);
        assertThat(missing).as("spec operations no SDK method reaches").isEmpty();
        assertThat(covered).isEqualTo(spec.ops.size());
    }

    @Test
    void everyDeclaredParameterIsSendable() {
        List<String> unsent = new ArrayList<>();
        for (SpecModel.Op op : spec.ops) {
            Set<String> sent = sentKeys.getOrDefault(op.id(), Set.of());
            for (String q : op.query.keySet()) {
                if (!sent.contains("query:" + q)) {
                    unsent.add(op.id() + " query " + q);
                }
            }
            Set<String> props = new LinkedHashSet<>();
            op.bodies.values().forEach(p -> props.addAll(p.keySet()));
            for (String p : props) {
                if (!sent.contains("body:" + p)) {
                    unsent.add(op.id() + " body " + p);
                }
            }
        }
        assertThat(unsent).as("declared parameters the SDK cannot send").isEmpty();
    }

    @Test
    void pendingAllowlistIsNotStale() {
        List<String> stale = new ArrayList<>();
        for (SpecModel.Op op : spec.ops) {
            for (String p : PENDING_SPEC_PROPS.getOrDefault(op.operationId, Set.of())) {
                if (op.bodies.values().stream().anyMatch(b -> b.containsKey(p))) {
                    stale.add(op.operationId + " now declares " + p + ": remove it from PENDING_SPEC_PROPS");
                }
            }
        }
        assertThat(stale).isEmpty();
    }

    /**
     * Response fields the SDK models differently from the spec, each with its reason. Keyed by
     * {@code "<Java class>.<json property>"}.
     */
    private static final Map<String, String> MODEL_ALLOWLIST = Map.of(
            "HealthStatus.failures", "one class models both the 200 and the 503 /health body");

    @Test
    void responseModelsMatchTheSpec() {
        List<String> drift = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        for (SpecModel.Op op : spec.ops) {
            JsonNode schema = op.successJsonSchema(spec);
            Map<Type, String> types = returnTypes.get(op.id());
            if (schema == null || types == null) {
                continue;
            }
            for (Type t : types.keySet()) {
                compare(schema, t, op.operationId + " → " + typeName(t), drift, seen, 0);
            }
        }
        assertThat(drift).as("response fields missing from, or not in, the SDK models").isEmpty();
    }

    private static String typeName(Type t) {
        return t instanceof Class<?> ? ((Class<?>) t).getSimpleName() : t.getTypeName();
    }

    private static void compare(JsonNode rawSchema, Type javaType, String where, List<String> drift,
                                Set<String> seen, int depth) {
        if (depth > 8) {
            return;
        }
        Class<?> raw = javaType instanceof Class<?> ? (Class<?>) javaType
                : javaType instanceof ParameterizedType ? (Class<?>) ((ParameterizedType) javaType).getRawType()
                : Object.class;
        if (Map.class.isAssignableFrom(raw) || raw == Object.class || JsonNode.class.isAssignableFrom(raw)) {
            return;
        }
        JsonNode schema = bestBranch(rawSchema, raw);
        List<String> types = SpecModel.types(schema);
        if (types.contains("array") || List.class.isAssignableFrom(raw)) {
            if (javaType instanceof ParameterizedType && schema.has("items")) {
                compare(schema.get("items"), ((ParameterizedType) javaType).getActualTypeArguments()[0],
                        where + "[]", drift, seen, depth + 1);
            }
            return;
        }
        if (!schema.has("properties") || !raw.getPackageName().startsWith("com.voicetel.voiceml.models")) {
            return;
        }
        if (!seen.add(raw.getName() + "@" + schema.path("properties").size() + ":" + where.split(" ")[0])) {
            return;
        }
        Map<String, Type> fields = jsonFields(raw);
        Set<String> declared = new LinkedHashSet<>();
        schema.path("properties").fieldNames().forEachRemaining(declared::add);
        for (String prop : declared) {
            String key = raw.getSimpleName() + "." + prop;
            if (!fields.containsKey(prop)) {
                if (!MODEL_ALLOWLIST.containsKey(key)) {
                    drift.add(where + ": missing field '" + prop + "' in " + raw.getSimpleName());
                }
                continue;
            }
            compare(schema.path("properties").get(prop), fields.get(prop), where + "." + prop, drift, seen, depth + 1);
        }
        for (String f : fields.keySet()) {
            if (!declared.contains(f) && !MODEL_ALLOWLIST.containsKey(raw.getSimpleName() + "." + f)) {
                drift.add(where + ": " + raw.getSimpleName() + "." + f + " is not in the spec schema");
            }
        }
    }

    /** For {@code oneOf}/{@code anyOf}, the branch whose properties best match the Java class. */
    private static JsonNode bestBranch(JsonNode rawSchema, Class<?> raw) {
        JsonNode s = spec.deref(rawSchema);
        for (String key : new String[] {"oneOf", "anyOf"}) {
            if (s != null && s.has(key)) {
                Set<String> fields = jsonFields(raw).keySet();
                JsonNode best = null;
                long bestScore = -1;
                for (JsonNode branch : s.get(key)) {
                    JsonNode b = spec.flatten(branch);
                    long score = 0;
                    Iterator<String> names = b.path("properties").fieldNames();
                    while (names.hasNext()) {
                        if (fields.contains(names.next())) {
                            score++;
                        }
                    }
                    if (score > bestScore) {
                        bestScore = score;
                        best = b;
                    }
                }
                return best;
            }
        }
        return spec.flatten(s);
    }

    private static Map<String, Type> jsonFields(Class<?> c) {
        Map<String, Type> out = new LinkedHashMap<>();
        for (Class<?> k = c; k != null && k != Object.class; k = k.getSuperclass()) {
            for (java.lang.reflect.Field f : k.getDeclaredFields()) {
                com.fasterxml.jackson.annotation.JsonProperty jp =
                        f.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class);
                if (jp != null) {
                    out.putIfAbsent(jp.value(), f.getGenericType());
                }
            }
        }
        return out;
    }
}
