package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.models.AccountWithKey;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** C11: the API key never appears in default string output. */
class RedactionTest {

    private static final String KEY = "sk-super-secret-api-key-0123456789";

    @Test
    void secretsDoNotAppearInToStringOfClientOptionsTransportOrResources() throws Exception {
        ClientOptions options = ClientOptions.builder()
                .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").apiKey(KEY).build();
        VoicemlClient client = VoicemlClient.builder().options(options).build();
        List<String> rendered = new ArrayList<>();
        rendered.add(String.valueOf(options));
        rendered.add(String.valueOf(client));
        rendered.add(String.valueOf(client.transport()));
        for (Method m : VoicemlClient.class.getMethods()) {
            if (m.getParameterCount() == 0 && m.getReturnType().getPackageName().endsWith(".resources")) {
                rendered.add(String.valueOf(m.invoke(client)));
            }
        }
        rendered.add(String.valueOf(new RequestValidator(KEY)));
        for (String s : rendered) {
            assertThat(s).doesNotContain(KEY);
        }
        assertThat(options.toString()).contains("[redacted]").contains("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

    @Test
    void errorsDoNotEchoTheAuthorizationHeader() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(401, "{\"code\":20003,\"message\":\"Authenticate\"}");
            VoicemlClient client = VoicemlClient.builder().accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                    .apiKey(KEY).baseUrl(mock.url()).maxRetries(0).build();
            try {
                client.calls().list();
                throw new AssertionError("expected an ApiException");
            } catch (ApiException ex) {
                assertThat(ex.toString()).doesNotContain(KEY);
                assertThat(String.valueOf(ex.getMessage())).doesNotContain(KEY);
                assertThat(String.valueOf(ex.getBody())).doesNotContain(KEY);
            }
        }
    }

    @Test
    void rotatedApiKeyNeverAppearsInToString() throws IOException {
        String rotated = "rotated-secret-key-0123456789abcdef";
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\"api_key\":\"" + rotated
                    + "\",\"name\":\"Ops\",\"allow_cidr\":[],\"disabled\":false}");
            VoicemlClient client = mock.client(0);
            AccountWithKey account = client.accounts().rotateKey();
            assertThat(account.getApiKey()).isEqualTo(rotated);
            assertThat(account.toString()).contains("[redacted]").doesNotContain(rotated);
            assertThat(String.valueOf(account)).doesNotContain(rotated);
            assertThat(client.accounts().toString()).doesNotContain(rotated);
        }
    }
}
