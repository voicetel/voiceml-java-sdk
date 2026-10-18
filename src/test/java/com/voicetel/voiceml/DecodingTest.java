package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.BadRequestException;
import com.voicetel.voiceml.exceptions.ConflictException;
import com.voicetel.voiceml.exceptions.MethodNotAllowedException;
import com.voicetel.voiceml.exceptions.ServerException;
import com.voicetel.voiceml.exceptions.ValidationException;
import com.voicetel.voiceml.models.AnsweredBy;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallStatus;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageStatus;
import com.voicetel.voiceml.models.PaymentCapture;
import com.voicetel.voiceml.models.UpdatePaymentRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** C9: response decoding is tolerant; error envelopes are parsed; decode failures keep the cause. */
class DecodingTest {

    @Test
    void unknownEnumValuesDecodeToUnknownAndExtraFieldsAreIgnored() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"CA1\",\"status\":\"scheduled\",\"answered_by\":\"machine_end_voicemail\","
                    + "\"brand_new_field\":{\"nested\":[1,2,3]},\"direction\":\"outbound-api\"}");
            Call call = mock.client(0).calls().get("CA1");
            assertThat(call.getStatus()).isEqualTo(CallStatus.UNKNOWN);
            assertThat(call.getAnsweredBy()).isEqualTo(AnsweredBy.UNKNOWN);
            assertThat(call.getSid()).isEqualTo("CA1");
        }
    }

    @Test
    void knownEnumValuesStillDecode() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"SM1\",\"status\":\"delivered\"}");
            Message m = mock.client(0).messages().fetch("SM1");
            assertThat(m.getStatus()).isEqualTo(MessageStatus.DELIVERED);
            assertThat(CallStatus.fromString("in-progress")).isEqualTo(CallStatus.IN_PROGRESS);
            assertThat(CallStatus.fromString("later")).isEqualTo(CallStatus.UNKNOWN);
            assertThat(CallStatus.fromString(null)).isNull();
        }
    }

    @Test
    void nullsAndFalseSurviveDecoding() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":null,\"status\":null,\"price\":null,\"subresource_uris\":null}");
            Call call = mock.client(0).calls().get("CA1");
            assertThat(call.getSid()).isNull();
            assertThat(call.getStatus()).isNull();
        }
    }

    @Test
    void unknownIsNeverSent() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            assertThatThrownBy(() -> mock.client(0).calls().updatePayment("CA1", "PK1",
                    UpdatePaymentRequest.builder().capture(PaymentCapture.UNKNOWN).build()))
                    .isInstanceOf(ValidationException.class);
            assertThat(mock.requests).isEmpty();
        }
    }

    @Test
    void errorEnvelopeIsParsed() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(400, "{\"code\":21211,\"message\":\"Invalid To\",\"more_info\":\"https://voicetel.com/docs\","
                    + "\"status\":400}");
            assertThatThrownBy(() -> mock.client(0).calls().list())
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("Invalid To")
                    .satisfies(e -> {
                        ApiException api = (ApiException) e;
                        assertThat(api.getCode()).isEqualTo(21211);
                        assertThat(api.getMoreInfo()).isEqualTo("https://voicetel.com/docs");
                        assertThat(api.getStatusCode()).isEqualTo(400);
                    });
        }
    }

    @Test
    void nonJsonErrorBodyStillYieldsAnSdkError() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(502, "<html>bad gateway</html>").header("Content-Type", "text/html"));
            assertThatThrownBy(() -> mock.client(0).calls().list())
                    .isInstanceOf(ServerException.class)
                    .hasMessage("HTTP 502")
                    .satisfies(e -> assertThat(((ApiException) e).getBody()).isEqualTo("<html>bad gateway</html>"));
        }
    }

    @Test
    void removedEndpointsMapTo405() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(405, "{\"code\":20004,\"message\":\"Method not allowed\"}");
            assertThatThrownBy(() -> mock.client(0).calls().delete("CA1"))
                    .isInstanceOf(MethodNotAllowedException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(20004));
        }
    }

    @Test
    void accountUpdateConflictMapsToConflictException() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(409, "{\"code\":20409,\"message\":\"account was modified concurrently\"}");
            assertThatThrownBy(() -> mock.client(0).accounts().update(
                    com.voicetel.voiceml.models.UpdateAccountRequest.builder().friendlyName("x").build()))
                    .isInstanceOf(ConflictException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getCode()).isEqualTo(20409));
        }
    }

    @Test
    void undecodableSuccessBodyKeepsTheCauseAndSaysCouldNotDecode() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{not json");
            assertThatThrownBy(() -> mock.client(0).calls().get("CA1"))
                    .isInstanceOf(ApiException.class)
                    .hasMessageStartingWith("could not decode HTTP 200 response")
                    .hasCauseInstanceOf(com.fasterxml.jackson.core.JsonProcessingException.class);
        }
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"calls\":\"not-a-list\"}");
            assertThatThrownBy(() -> mock.client(0).calls().list())
                    .isInstanceOf(ApiException.class)
                    .hasMessageStartingWith("could not decode response into CallList")
                    .hasCauseInstanceOf(com.fasterxml.jackson.core.JsonProcessingException.class);
        }
    }
}
