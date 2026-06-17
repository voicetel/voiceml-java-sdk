package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.models.CreateSipCredentialListMappingRequest;
import com.voicetel.voiceml.models.CreateSipCredentialListRequest;
import com.voicetel.voiceml.models.CreateSipCredentialRequest;
import com.voicetel.voiceml.models.CreateSipDomainRequest;
import com.voicetel.voiceml.models.CreateSipIpAccessControlListMappingRequest;
import com.voicetel.voiceml.models.CreateSipIpAccessControlListRequest;
import com.voicetel.voiceml.models.CreateSipIpAddressRequest;
import com.voicetel.voiceml.models.UpdateSipCredentialListRequest;
import com.voicetel.voiceml.models.UpdateSipCredentialRequest;
import com.voicetel.voiceml.models.UpdateSipDomainRequest;
import com.voicetel.voiceml.models.UpdateSipIpAccessControlListRequest;
import com.voicetel.voiceml.models.UpdateSipIpAddressRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Wire-shape tests for the SIP Trunking surface — voiceml.sip().
 *
 * <p>Spins up a JDK HttpServer, records every request, and feeds back canned
 * responses. No network. No external dependencies.
 */
class SipTest {

    private static final String DOMAIN_SID = "SD" + "1".repeat(32);
    private static final String CL_SID = "CL" + "2".repeat(32);
    private static final String CR_SID = "CR" + "3".repeat(32);
    private static final String ACL_SID = "AL" + "4".repeat(32);
    private static final String IP_SID = "IP" + "5".repeat(32);
    private static final String MAPPING_SID = "CL" + "9".repeat(32);
    private static final String ACCOUNT = "AC" + "f".repeat(32);

    private HttpServer server;
    private int port;
    private Deque<Recorded> recorded;

    @BeforeEach
    void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        port = server.getAddress().getPort();
        recorded = new ArrayDeque<>();
        server.start();
    }

    @AfterEach
    void stop() {
        if (server != null) server.stop(0);
    }

    private VoicemlClient client() {
        return VoicemlClient.builder()
                .accountSid(ACCOUNT)
                .apiKey("secret")
                .baseUrl("http://127.0.0.1:" + port)
                .maxRetries(0)
                .build();
    }

    private void handle(String path, int status, String body) {
        server.createContext(path, exchange -> {
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            recorded.addLast(new Recorded(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    exchange.getRequestURI().getRawQuery() == null ? "" : exchange.getRequestURI().getRawQuery(),
                    new String(reqBody, StandardCharsets.UTF_8)));
            byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        });
    }

    private static Map<String, String> form(String body) {
        Map<String, String> out = new HashMap<>();
        if (body == null || body.isEmpty()) return out;
        for (String pair : body.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) continue;
            String k = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
            String v = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
            out.put(k, v);
        }
        return out;
    }

    private static final class Recorded {
        private final String method;
        private final String path;
        private final String query;
        private final String body;
        Recorded(String method, String path, String query, String body) {
            this.method = method; this.path = path; this.query = query; this.body = body;
        }
        String method() { return method; }
        String path() { return path; }
        String query() { return query; }
        String body() { return body; }
    }

    private static String domainBody() {
        return "{\"sid\":\"" + DOMAIN_SID + "\",\"account_sid\":\"" + ACCOUNT + "\",\"domain_name\":\"ingress.example.com\","
                + "\"api_version\":\"2010-04-01\",\"friendly_name\":\"ingress\",\"secure\":true,"
                + "\"date_created\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"date_updated\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + ".json\"}";
    }

    private static String clBody() {
        return "{\"sid\":\"" + CL_SID + "\",\"account_sid\":\"" + ACCOUNT + "\",\"friendly_name\":\"office-handsets\","
                + "\"date_created\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"date_updated\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/SIP/CredentialLists/" + CL_SID + ".json\"}";
    }

    private static String crBody() {
        return "{\"sid\":\"" + CR_SID + "\",\"account_sid\":\"" + ACCOUNT + "\",\"credential_list_sid\":\"" + CL_SID + "\","
                + "\"username\":\"alice\",\"date_created\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"date_updated\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/SIP/CredentialLists/" + CL_SID
                + "/Credentials/" + CR_SID + ".json\"}";
    }

    private static String aclBody() {
        return "{\"sid\":\"" + ACL_SID + "\",\"account_sid\":\"" + ACCOUNT + "\",\"friendly_name\":\"carrier-allowlist\","
                + "\"date_created\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"date_updated\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/SIP/IpAccessControlLists/" + ACL_SID + ".json\"}";
    }

    private static String ipBody() {
        return "{\"sid\":\"" + IP_SID + "\",\"account_sid\":\"" + ACCOUNT + "\",\"ip_access_control_list_sid\":\"" + ACL_SID + "\","
                + "\"friendly_name\":\"carrier-edge-1\",\"ip_address\":\"203.0.113.10\",\"cidr_prefix_length\":32,"
                + "\"date_created\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"date_updated\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/SIP/IpAccessControlLists/" + ACL_SID
                + "/IpAddresses/" + IP_SID + ".json\"}";
    }

    private static String mapBody() {
        return "{\"sid\":\"" + MAPPING_SID + "\",\"account_sid\":\"" + ACCOUNT + "\",\"domain_sid\":\"" + DOMAIN_SID + "\","
                + "\"date_created\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"date_updated\":\"Mon, 17 Jun 2026 12:00:00 +0000\","
                + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID
                + "/CredentialListMappings/" + MAPPING_SID + ".json\"}";
    }

    @Test
    void sipResourceWiredOnClient() {
        VoicemlClient c = client();
        assertThat(c.sip()).isNotNull();
        assertThat(c.sip().domains()).isNotNull();
        assertThat(c.sip().credentialLists()).isNotNull();
        assertThat(c.sip().ipAccessControlLists()).isNotNull();
    }

    @Test
    void domainsCreateSendsForm() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains.json", 200, domainBody());
        var domain = client().sip().domains().create(CreateSipDomainRequest.builder()
                .domainName("ingress.example.com")
                .friendlyName("ingress")
                .voiceUrl("https://hooks/voice")
                .voiceMethod("POST")
                .secure(true)
                .build());
        assertThat(domain.getSid()).isEqualTo(DOMAIN_SID);
        var body = form(recorded.peekFirst().body());
        assertThat(body).containsEntry("DomainName", "ingress.example.com");
        assertThat(body).containsEntry("VoiceUrl", "https://hooks/voice");
        assertThat(body).containsEntry("VoiceMethod", "POST");
        assertThat(body).containsEntry("Secure", "true");
    }

    @Test
    void domainsListFetchUpdateDelete() {
        String listBody = "{\"domains\":[" + domainBody() + "],\"page\":0,\"page_size\":50,\"total\":1,\"uri\":\"\"}";
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains.json", 200, listBody);
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + ".json", 200, domainBody());
        var c = client();
        var out = c.sip().domains().list();
        assertThat(out.getDomains()).hasSize(1);
        assertThat(c.sip().domains().fetch(DOMAIN_SID).getSid()).isEqualTo(DOMAIN_SID);
        assertThat(c.sip().domains().update(DOMAIN_SID, UpdateSipDomainRequest.builder().friendlyName("renamed").build()).getSid()).isEqualTo(DOMAIN_SID);
        c.sip().domains().delete(DOMAIN_SID);
        assertThat(recorded).hasSizeGreaterThanOrEqualTo(4);
        boolean sawDelete = recorded.stream().anyMatch(r -> r.method().equals("DELETE"));
        assertThat(sawDelete).isTrue();
    }

    @Test
    void credentialListsCRUD() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/CredentialLists.json", 200, clBody());
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/CredentialLists/" + CL_SID + ".json", 200, clBody());
        var c = client();
        var cl = c.sip().credentialLists().create(CreateSipCredentialListRequest.builder().friendlyName("office-handsets").build());
        assertThat(cl.getSid()).isEqualTo(CL_SID);
        assertThat(form(recorded.peekFirst().body())).containsEntry("FriendlyName", "office-handsets");
        c.sip().credentialLists().fetch(CL_SID);
        c.sip().credentialLists().update(CL_SID, UpdateSipCredentialListRequest.builder().friendlyName("renamed").build());
        c.sip().credentialLists().delete(CL_SID);
    }

    @Test
    void credentialsNestedCRUD() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/CredentialLists/" + CL_SID + "/Credentials.json", 200, crBody());
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/CredentialLists/" + CL_SID + "/Credentials/" + CR_SID + ".json", 200, crBody());
        var c = client();
        var cred = c.sip().credentialLists().createCredential(CL_SID,
                CreateSipCredentialRequest.builder().username("alice").password("hunter2").build());
        assertThat(cred.getUsername()).isEqualTo("alice");
        var body = form(recorded.peekFirst().body());
        assertThat(body).containsEntry("Username", "alice").containsEntry("Password", "hunter2");

        c.sip().credentialLists().fetchCredential(CL_SID, CR_SID);
        c.sip().credentialLists().updateCredential(CL_SID, CR_SID,
                UpdateSipCredentialRequest.builder().password("newpwd").build());
        c.sip().credentialLists().deleteCredential(CL_SID, CR_SID);
    }

    @Test
    void ipAccessControlListsCRUD() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/IpAccessControlLists.json", 200, aclBody());
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/IpAccessControlLists/" + ACL_SID + ".json", 200, aclBody());
        var c = client();
        c.sip().ipAccessControlLists().create(CreateSipIpAccessControlListRequest.builder().friendlyName("carrier-allowlist").build());
        assertThat(form(recorded.peekFirst().body())).containsEntry("FriendlyName", "carrier-allowlist");
        c.sip().ipAccessControlLists().fetch(ACL_SID);
        c.sip().ipAccessControlLists().update(ACL_SID, UpdateSipIpAccessControlListRequest.builder().friendlyName("renamed").build());
        c.sip().ipAccessControlLists().delete(ACL_SID);
    }

    @Test
    void ipAddressesNestedCRUD() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/IpAccessControlLists/" + ACL_SID + "/IpAddresses.json", 200, ipBody());
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/IpAccessControlLists/" + ACL_SID + "/IpAddresses/" + IP_SID + ".json", 200, ipBody());
        var c = client();
        c.sip().ipAccessControlLists().createIpAddress(ACL_SID,
                CreateSipIpAddressRequest.builder().friendlyName("carrier-edge-1").ipAddress("203.0.113.10").cidrPrefixLength(32).build());
        var body = form(recorded.peekFirst().body());
        assertThat(body).containsEntry("FriendlyName", "carrier-edge-1")
                .containsEntry("IpAddress", "203.0.113.10")
                .containsEntry("CidrPrefixLength", "32");
        c.sip().ipAccessControlLists().fetchIpAddress(ACL_SID, IP_SID);
        c.sip().ipAccessControlLists().updateIpAddress(ACL_SID, IP_SID,
                UpdateSipIpAddressRequest.builder().ipAddress("203.0.113.11").build());
        c.sip().ipAccessControlLists().deleteIpAddress(ACL_SID, IP_SID);
    }

    @Test
    void domainHistoricalMappingsRouting() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/CredentialListMappings.json", 200, mapBody());
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/IpAccessControlListMappings.json", 200, mapBody());
        var c = client();
        c.sip().domains().createCredentialListMapping(DOMAIN_SID,
                CreateSipCredentialListMappingRequest.builder().credentialListSid(CL_SID).build());
        assertThat(form(recorded.peekFirst().body())).containsEntry("CredentialListSid", CL_SID);
        c.sip().domains().createIpAccessControlListMapping(DOMAIN_SID,
                CreateSipIpAccessControlListMappingRequest.builder().ipAccessControlListSid(ACL_SID).build());
        assertThat(form(recorded.peekLast().body())).containsEntry("IpAccessControlListSid", ACL_SID);
    }

    @Test
    void domainAuthCallsCredentialListMappingsRouting() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/Auth/Calls/CredentialListMappings.json", 200, mapBody());
        client().sip().domains().createAuthCallsCredentialListMapping(DOMAIN_SID,
                CreateSipCredentialListMappingRequest.builder().credentialListSid(CL_SID).build());
        assertThat(recorded.peekFirst().path()).isEqualTo(
                "/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/Auth/Calls/CredentialListMappings.json");
    }

    @Test
    void domainAuthCallsIpAccessControlListMappingsRouting() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/Auth/Calls/IpAccessControlListMappings.json", 200, mapBody());
        client().sip().domains().createAuthCallsIpAccessControlListMapping(DOMAIN_SID,
                CreateSipIpAccessControlListMappingRequest.builder().ipAccessControlListSid(ACL_SID).build());
        assertThat(recorded.peekFirst().path()).isEqualTo(
                "/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/Auth/Calls/IpAccessControlListMappings.json");
    }

    @Test
    void domainAuthRegistrationsCredentialListMappingsRouting() {
        handle("/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/Auth/Registrations/CredentialListMappings.json", 200, mapBody());
        client().sip().domains().createAuthRegistrationsCredentialListMapping(DOMAIN_SID,
                CreateSipCredentialListMappingRequest.builder().credentialListSid(CL_SID).build());
        assertThat(recorded.peekFirst().path()).isEqualTo(
                "/2010-04-01/Accounts/" + ACCOUNT + "/SIP/Domains/" + DOMAIN_SID + "/Auth/Registrations/CredentialListMappings.json");
    }
}
