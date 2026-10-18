package com.voicetel.voiceml.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The vendored public OpenAPI document ({@code src/test/resources/spec/voiceml-openapi.json}),
 * reduced to what the contract tests assert against.
 */
final class SpecModel {

    static final String RESOURCE = "/spec/voiceml-openapi.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonNodeFactory F = JsonNodeFactory.instance;

    final JsonNode root;
    final List<Op> ops = new ArrayList<>();

    /** One operation of the spec. */
    static final class Op {
        final String method;
        final String path;
        final String operationId;
        final Set<String> tags;
        final boolean auth;
        final Pattern regex;
        final int literalChars;
        final Map<String, Boolean> query = new LinkedHashMap<>();
        final Map<String, JsonNode> querySchemas = new LinkedHashMap<>();
        /** content type → (prop → required). */
        final Map<String, Map<String, Boolean>> bodies = new LinkedHashMap<>();
        final boolean bodyRequired;
        final JsonNode responses;

        Op(String method, String path, JsonNode pathItem, JsonNode op, boolean globalAuth, SpecModel spec) {
            this.method = method;
            this.path = path;
            this.operationId = op.path("operationId").asText();
            this.tags = new LinkedHashSet<>();
            op.path("tags").forEach(t -> tags.add(t.asText()));
            JsonNode sec = op.get("security");
            this.auth = sec == null ? globalAuth : sec.size() > 0;
            StringBuilder re = new StringBuilder("^");
            int literal = 0;
            int i = 0;
            while (i < path.length()) {
                int open = path.indexOf('{', i);
                if (open < 0) {
                    re.append(Pattern.quote(path.substring(i)));
                    literal += path.length() - i;
                    break;
                }
                re.append(Pattern.quote(path.substring(i, open))).append("[^/]+");
                literal += open - i;
                i = path.indexOf('}', open) + 1;
            }
            this.regex = Pattern.compile(re.append('$').toString());
            this.literalChars = literal;
            List<JsonNode> params = new ArrayList<>();
            pathItem.path("parameters").forEach(params::add);
            op.path("parameters").forEach(params::add);
            for (JsonNode p : params) {
                JsonNode prm = spec.deref(p);
                if ("query".equals(prm.path("in").asText())) {
                    query.put(prm.path("name").asText(), prm.path("required").asBoolean(false));
                    querySchemas.put(prm.path("name").asText(), spec.deref(prm.path("schema")));
                }
            }
            JsonNode rb = op.get("requestBody");
            this.bodyRequired = rb != null && rb.path("required").asBoolean(false);
            if (rb != null) {
                for (Map.Entry<String, JsonNode> e : rb.path("content").properties()) {
                    JsonNode schema = spec.deref(e.getValue().path("schema"));
                    Set<String> required = new LinkedHashSet<>();
                    schema.path("required").forEach(r -> required.add(r.asText()));
                    Map<String, Boolean> props = new LinkedHashMap<>();
                    schema.path("properties").fieldNames().forEachRemaining(k -> props.put(k, required.contains(k)));
                    bodies.put(e.getKey(), props);
                }
            }
            this.responses = op.path("responses");
        }

        String id() {
            return method + " " + path + " (" + operationId + ")";
        }

        /** Hosts the op may be served on, per its tags. */
        Set<String> allowedHosts() {
            Set<String> hosts = new LinkedHashSet<>();
            for (String t : tags) {
                if (t.startsWith("ConversationsV1")) {
                    hosts.add("conversations");
                } else if (t.equals("MessagingV1Service")) {
                    hosts.add("messaging");
                } else {
                    hosts.add("base");
                }
            }
            return hosts;
        }

        /** The first 2xx response code and its JSON schema (or {@code null}). */
        int successStatus() {
            Iterator<String> it = responses.fieldNames();
            while (it.hasNext()) {
                String code = it.next();
                if (code.startsWith("2")) {
                    return Integer.parseInt(code);
                }
            }
            return 200;
        }

        JsonNode successJsonSchema(SpecModel spec) {
            JsonNode r = responses.path(String.valueOf(successStatus()));
            JsonNode schema = r.path("content").path("application/json").get("schema");
            return schema == null ? null : spec.deref(schema);
        }

        String successContentType() {
            JsonNode content = responses.path(String.valueOf(successStatus())).path("content");
            Iterator<String> it = content.fieldNames();
            return it.hasNext() ? it.next() : null;
        }
    }

    SpecModel() {
        try (InputStream in = SpecModel.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("vendored spec missing: " + RESOURCE);
            }
            root = MAPPER.readTree(in);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        boolean globalAuth = root.path("security").size() > 0;
        for (Map.Entry<String, JsonNode> p : root.path("paths").properties()) {
            for (Map.Entry<String, JsonNode> m : p.getValue().properties()) {
                String method = m.getKey().toUpperCase();
                if (Set.of("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD").contains(method)) {
                    ops.add(new Op(method, p.getKey(), p.getValue(), m.getValue(), globalAuth, this));
                }
            }
        }
    }

    JsonNode deref(JsonNode node) {
        JsonNode n = node;
        int guard = 0;
        while (n != null && n.has("$ref") && guard++ < 20) {
            String ref = n.get("$ref").asText();
            String[] parts = ref.substring(2).split("/");
            JsonNode target = root;
            for (String part : parts) {
                target = target.path(part);
            }
            n = target;
        }
        return n;
    }

    /** The most specific op whose method and path template match a raw request path. */
    Op match(String method, String rawPath) {
        Op best = null;
        for (Op op : ops) {
            if (op.method.equals(method) && op.regex.matcher(rawPath).matches()) {
                if (best == null || op.literalChars > best.literalChars) {
                    best = op;
                }
            }
        }
        return best;
    }

    /** Non-null JSON types of a schema ({@code type} may be a 3.1 list including "null"). */
    static List<String> types(JsonNode schema) {
        List<String> out = new ArrayList<>();
        JsonNode t = schema.get("type");
        if (t == null) {
            return out;
        }
        if (t.isArray()) {
            t.forEach(v -> {
                if (!"null".equals(v.asText())) {
                    out.add(v.asText());
                }
            });
        } else {
            out.add(t.asText());
        }
        return out;
    }

    /** Merge {@code allOf} and resolve the first {@code oneOf}/{@code anyOf} branch. */
    JsonNode flatten(JsonNode schema) {
        JsonNode s = deref(schema);
        if (s == null) {
            return F.objectNode();
        }
        if (s.has("allOf")) {
            ObjectNode merged = F.objectNode();
            ObjectNode props = F.objectNode();
            for (JsonNode part : s.get("allOf")) {
                JsonNode p = flatten(part);
                if (p.has("properties")) {
                    props.setAll((ObjectNode) p.get("properties"));
                }
                if (p.has("type")) {
                    merged.set("type", p.get("type"));
                }
            }
            if (s.has("properties")) {
                props.setAll((ObjectNode) s.get("properties"));
            }
            merged.set("properties", props);
            if (!merged.has("type")) {
                merged.put("type", "object");
            }
            return merged;
        }
        for (String key : new String[] {"oneOf", "anyOf"}) {
            if (s.has(key) && s.get(key).size() > 0) {
                return flatten(s.get(key).get(0));
            }
        }
        return s;
    }

    /**
     * A sample instance of a schema.
     *
     * @param nulls every property {@code null} (nullable tolerance) instead of typed values
     *     with unknown enum values and an extra unknown property at every level
     */
    JsonNode sample(JsonNode schema, boolean nulls, int depth) {
        JsonNode s = flatten(schema);
        List<String> types = types(s);
        String type = types.isEmpty() ? (s.has("properties") ? "object" : "string") : types.get(0);
        switch (type) {
            case "object": {
                ObjectNode o = F.objectNode();
                if (depth > 8) {
                    return o;
                }
                for (Map.Entry<String, JsonNode> e : s.path("properties").properties()) {
                    String k = e.getKey();
                    if (nulls || k.equals("next_page_uri") || k.equals("next_page_url")) {
                        o.putNull(k);
                    } else {
                        o.set(k, sample(e.getValue(), false, depth + 1));
                    }
                }
                if (!nulls) {
                    o.put("zz_unknown_extra_property", 1);
                    JsonNode ap = s.get("additionalProperties");
                    if (ap != null && ap.isObject() && !s.has("properties")) {
                        o.set("zz_key", sample(ap, false, depth + 1));
                    }
                }
                return o;
            }
            case "array": {
                ArrayNode a = F.arrayNode();
                if (!nulls && depth <= 8) {
                    a.add(sample(s.path("items"), false, depth + 1));
                }
                return a;
            }
            case "integer":
                return F.numberNode(7);
            case "number":
                return F.numberNode(1.5);
            case "boolean":
                return F.booleanNode(true);
            default:
                if (s.has("enum")) {
                    return F.textNode("zz-unknown-enum-value");
                }
                return F.textNode("v");
        }
    }
}
