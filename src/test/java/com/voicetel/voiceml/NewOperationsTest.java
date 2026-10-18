package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ConflictException;
import com.voicetel.voiceml.exceptions.ValidationException;
import com.voicetel.voiceml.models.Account;
import com.voicetel.voiceml.models.AccountWithKey;
import com.voicetel.voiceml.models.ApplicationBody;
import com.voicetel.voiceml.models.CallEvent;
import com.voicetel.voiceml.models.CreateIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.CreateMessageFeedbackRequest;
import com.voicetel.voiceml.models.CreateMessageRequest;
import com.voicetel.voiceml.models.CreateMessagingServicePhoneNumberRequest;
import com.voicetel.voiceml.models.CreateOutgoingCallerIdRequest;
import com.voicetel.voiceml.models.CreateTypedIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.ListIncomingPhoneNumbersParams;
import com.voicetel.voiceml.models.ListMonitorAlertsParams;
import com.voicetel.voiceml.models.ListSipParams;
import com.voicetel.voiceml.models.ListUsageRecordsParams;
import com.voicetel.voiceml.models.MessageFeedback;
import com.voicetel.voiceml.models.MonitorV1Alert;
import com.voicetel.voiceml.models.Notification;
import com.voicetel.voiceml.models.StartConferenceRecordingRequest;
import com.voicetel.voiceml.models.StartRecordingRequest;
import com.voicetel.voiceml.models.UpdateAccountRequest;
import com.voicetel.voiceml.models.UpdateOutgoingCallerIdRequest;
import com.voicetel.voiceml.models.UsageRecordList;
import com.voicetel.voiceml.models.ValidationRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Exact wire bytes for the operations and parameters added in this release. */
class NewOperationsTest {

    private static final String ACCOUNT = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String A = "/2010-04-01/Accounts/" + ACCOUNT;

    @Test
    void mmsSendsRepeatedMediaUrlKeysAndTheNewFields() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(201, "{\"sid\":\"MM1\",\"status\":\"queued\",\"num_media\":\"2\"}");
            mock.client(0).messages().create(CreateMessageRequest.builder()
                    .to("+14155550100").from("+14155550101")
                    .mediaUrl(List.of("https://example.com/a.png", "https://example.com/b.png"))
                    .validityPeriod(600).statusCallback("https://example.com/dlr").statusCallbackMethod("POST")
                    .build());
            MockHttp.Req r = mock.only();
            assertThat(r.method).isEqualTo("POST");
            assertThat(r.rawPath).isEqualTo(A + "/Messages.json");
            assertThat(r.header("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
            assertThat(r.body).isEqualTo("To=%2B14155550100&From=%2B14155550101"
                    + "&MediaUrl=https%3A%2F%2Fexample.com%2Fa.png&MediaUrl=https%3A%2F%2Fexample.com%2Fb.png"
                    + "&ValidityPeriod=600&StatusCallback=https%3A%2F%2Fexample.com%2Fdlr&StatusCallbackMethod=POST");
        }
    }

    @Test
    void aMessageNeedsBodyOrMediaUrl() {
        assertThatThrownBy(() -> CreateMessageRequest.builder().to("+14155550100").build())
                .isInstanceOf(ValidationException.class);
        assertThat(CreateMessageRequest.builder().to("+14155550100").body("hi").build().toForm())
                .containsEntry("Body", "hi");
    }

    @Test
    void incomingPhoneNumberSmsRoutingAndFilters() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(201, "{\"sid\":\"PN1\",\"sms_url\":\"https://example.com/sms\",\"subresource_uris\":{\"a\":\"/b\"}}");
            VoicemlClient c = mock.client(0);
            c.incomingPhoneNumbers().create(CreateIncomingPhoneNumberRequest.builder()
                    .phoneNumber("+14155550100").voiceUrl("https://example.com/voice")
                    .smsUrl("https://example.com/sms").smsMethod("POST")
                    .smsFallbackUrl("https://example.com/fb").smsFallbackMethod("GET")
                    .smsApplicationSid("AP0123456789abcdef0123456789abcdef").build());
            c.incomingPhoneNumbers().createLocal(CreateTypedIncomingPhoneNumberRequest.builder()
                    .phoneNumber("+14155550100").voiceUrl("https://example.com/voice").voiceMethod("POST").build());
            c.incomingPhoneNumbers().list(ListIncomingPhoneNumbersParams.builder()
                    .friendlyName("main line").beta(false).origin("hosted").pageSize(20).build());
            assertThat(mock.requests.get(0).body).isEqualTo("PhoneNumber=%2B14155550100"
                    + "&VoiceUrl=https%3A%2F%2Fexample.com%2Fvoice&SmsUrl=https%3A%2F%2Fexample.com%2Fsms&SmsMethod=POST"
                    + "&SmsFallbackUrl=https%3A%2F%2Fexample.com%2Ffb&SmsFallbackMethod=GET"
                    + "&SmsApplicationSid=AP0123456789abcdef0123456789abcdef");
            assertThat(mock.requests.get(1).rawPath).isEqualTo(A + "/IncomingPhoneNumbers/Local.json");
            assertThat(mock.requests.get(1).body)
                    .isEqualTo("PhoneNumber=%2B14155550100&VoiceUrl=https%3A%2F%2Fexample.com%2Fvoice&VoiceMethod=POST");
            assertThat(mock.requests.get(2).rawQuery).isEqualTo("PageSize=20&FriendlyName=main+line&Beta=false&Origin=hosted");
        }
    }

    @Test
    void applicationAndRecordingFields() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(201, "{\"sid\":\"AP1\",\"message_status_callback\":\"https://example.com/m\"}");
            VoicemlClient c = mock.client(0);
            assertThat(c.applications().create(ApplicationBody.builder().friendlyName("app")
                    .messageStatusCallback("https://example.com/m").smsStatusCallback("https://example.com/s").build())
                    .getMessageStatusCallback()).isEqualTo("https://example.com/m");
            c.calls().startRecording("CA1", StartRecordingRequest.builder().recordingTrack("inbound").playBeep(false).build());
            c.conferences().startRecording("CF1", StartConferenceRecordingRequest.builder()
                    .recordingStatusCallback("https://example.com/r").recordingStatusCallbackEvent("completed").build());
            assertThat(mock.requests.get(0).body).isEqualTo(
                    "FriendlyName=app&MessageStatusCallback=https%3A%2F%2Fexample.com%2Fm&SmsStatusCallback=https%3A%2F%2Fexample.com%2Fs");
            assertThat(mock.requests.get(1).body).isEqualTo("RecordingTrack=inbound&PlayBeep=false");
            assertThat(mock.requests.get(2).rawPath).isEqualTo(A + "/Conferences/CF1/Recordings.json");
            assertThat(mock.requests.get(2).body).isEqualTo(
                    "RecordingStatusCallback=https%3A%2F%2Fexample.com%2Fr&RecordingStatusCallbackEvent=completed");
        }
    }

    @Test
    void accountsAndBalance() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"" + ACCOUNT + "\",\"status\":\"active\",\"type\":\"Full\"}");
            VoicemlClient c = mock.client(0);
            Account a = c.accounts().fetch();
            c.accounts().update(UpdateAccountRequest.builder().friendlyName("Ops").allowCidr(List.of()).build());
            c.balance().fetch();
            assertThat(a.getStatus()).isEqualTo("active");
            assertThat(mock.requests.get(0).rawPath).isEqualTo("/2010-04-01/Accounts/" + ACCOUNT + ".json");
            assertThat(mock.requests.get(1).method).isEqualTo("POST");
            assertThat(mock.requests.get(1).header("Content-Type")).isEqualTo("application/json");
            assertThat(mock.requests.get(1).body).isEqualTo("{\"FriendlyName\":\"Ops\",\"AllowCidr\":[]}");
            assertThat(mock.requests.get(2).rawPath).isEqualTo(A + "/Balance.json");
        }
    }

    @Test
    void outgoingCallerIdsMediaAndTranscriptions() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"phone_number\":\"+14155550100\",\"validation_code\":\"123456\"}");
            VoicemlClient c = mock.client(0);
            ValidationRequest v = c.outgoingCallerIds().create(CreateOutgoingCallerIdRequest.builder()
                    .phoneNumber("+14155550100").friendlyName("desk").build());
            c.outgoingCallerIds().update("PN1", UpdateOutgoingCallerIdRequest.builder().friendlyName("desk 2").build());
            c.outgoingCallerIds().delete("PN1");
            c.messages().fetchMedia("MM1", "ME1");
            c.messages().deleteMedia("MM1", "ME1");
            c.transcriptions().fetch("TR1");
            c.recordings().deleteTranscription("RE1", "TR1");
            assertThat(v.getValidationCode()).isEqualTo("123456");
            assertThat(mock.requests).extracting(r -> r.method + " " + r.rawPath).containsExactly(
                    "POST " + A + "/OutgoingCallerIds.json",
                    "POST " + A + "/OutgoingCallerIds/PN1.json",
                    "DELETE " + A + "/OutgoingCallerIds/PN1.json",
                    "GET " + A + "/Messages/MM1/Media/ME1.json",
                    "DELETE " + A + "/Messages/MM1/Media/ME1.json",
                    "GET " + A + "/Transcriptions/TR1.json",
                    "DELETE " + A + "/Recordings/RE1/Transcriptions/TR1.json");
            assertThat(mock.requests.get(0).body).isEqualTo("PhoneNumber=%2B14155550100&FriendlyName=desk");
        }
    }

    @Test
    void usageRecordsSendDateOnlyFilters() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"usage_records\":[{\"category\":\"calls\",\"count\":\"3\",\"subresource_uris\":"
                    + "{\"daily\":\"/x\"}}],\"page\":0,\"page_size\":50,\"next_page_uri\":null}");
            VoicemlClient c = mock.client(0);
            ListUsageRecordsParams p = ListUsageRecordsParams.builder().category("calls")
                    .startDate(LocalDate.of(2026, 10, 1)).endDate("2026-10-31").includeSubaccounts(false).build();
            UsageRecordList list = c.usageRecords().list(p);
            c.usageRecords().listThisMonth(p);
            c.usageRecords().listAllTime();
            assertThat(list.getUsageRecords().get(0).getCount()).isEqualTo("3");
            assertThat(mock.requests.get(0).rawPath).isEqualTo(A + "/Usage/Records.json");
            assertThat(mock.requests.get(0).rawQuery)
                    .isEqualTo("Category=calls&StartDate=2026-10-01&EndDate=2026-10-31&IncludeSubaccounts=false");
            assertThat(mock.requests.get(1).rawPath).isEqualTo(A + "/Usage/Records/ThisMonth.json");
            assertThat(mock.requests.get(2).rawPath).isEqualTo(A + "/Usage/Records/AllTime.json");
        }
    }

    @Test
    void sipRegistrationsAndFriendlyNameFilters() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"registrations\":[{\"aor\":\"sip:a@b\",\"status\":\"registered\",\"contacts\":"
                    + "[{\"contact\":\"sip:a@1.2.3.4\",\"registered\":true}]}],\"page\":0,\"page_size\":50}");
            VoicemlClient c = mock.client(0);
            assertThat(c.sip().registrations().list().getRegistrations().get(0).getContacts().get(0).getRegistered())
                    .isTrue();
            c.sip().domains().list(ListSipParams.builder().friendlyName("edge").build());
            c.sip().ipAccessControlLists().listIpAddresses("AL1", ListSipParams.builder().friendlyName("office").build());
            assertThat(mock.requests.get(0).rawPath).isEqualTo(A + "/SIP/Registrations.json");
            assertThat(mock.requests.get(1).rawQuery).isEqualTo("FriendlyName=edge");
            assertThat(mock.requests.get(2).rawPath).isEqualTo(A + "/SIP/IpAccessControlLists/AL1/IpAddresses.json");
        }
    }

    @Test
    void messagingSenderPoolRidesTheMessagingHost() throws IOException {
        try (MockHttp base = new MockHttp(); MockHttp messaging = new MockHttp()) {
            messaging.respond(200, "{\"sid\":\"PN1\",\"capabilities\":[\"SMS\"]}");
            VoicemlClient c = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("k").baseUrl(base.url())
                    .messagingBaseUrl(messaging.url()).maxRetries(0).build();
            c.messagingV1().services().phoneNumbers("MG1").create(
                    CreateMessagingServicePhoneNumberRequest.builder().phoneNumberSid("PN1").build());
            c.messagingV1().services().phoneNumbers("MG1").fetch("PN1");
            c.messagingV1().services().phoneNumbers("MG1").delete("PN1");
            assertThat(messaging.requests).extracting(r -> r.method + " " + r.rawPath).containsExactly(
                    "POST /v1/Services/MG1/PhoneNumbers",
                    "GET /v1/Services/MG1/PhoneNumbers/PN1",
                    "DELETE /v1/Services/MG1/PhoneNumbers/PN1");
            assertThat(messaging.requests.get(0).body).isEqualTo("PhoneNumberSid=PN1");
            assertThat(base.requests).isEmpty();
        }
    }

    @Test
    void notificationsAndEventsAreTyped() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"NO1\",\"error_code\":\"11200\",\"log\":\"0\",\"call_sid\":\"CA1\"}")
                .respond(200, "{\"events\":[{\"request\":{\"method\":\"POST\",\"url\":\"https://example.com\","
                    + "\"parameters\":{\"CallSid\":\"CA1\"}},\"response\":{\"response_code\":200}}],"
                    + "\"page\":0,\"page_size\":50,\"uri\":\"/x\"}");
            VoicemlClient c = mock.client(0);
            Notification n = c.calls().getNotification("CA1", "NO1");
            CallEvent e = c.calls().listEvents("CA1").getEvents().get(0);
            assertThat(n.getErrorCode()).isEqualTo("11200");
            assertThat(e.getRequest().getParameters()).containsEntry("CallSid", "CA1");
            assertThat(e.getResponse().getResponseCode()).isEqualTo(200);
        }
    }

    @Test
    void monitorAlertsListAndFetch() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"alerts\":[{\"sid\":\"NO1\",\"account_sid\":\"" + ACCOUNT + "\","
                    + "\"alert_text\":\"HTTP retrieval failed\",\"error_code\":\"11200\",\"log_level\":\"error\","
                    + "\"more_info\":\"https://example.com/errors/11200\",\"request_method\":\"POST\","
                    + "\"request_url\":\"https://example.com/hook\",\"url\":\"/v1/Alerts/NO1\","
                    + "\"date_created\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                    + "\"date_generated\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                    + "\"date_updated\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                    + "\"resource_sid\":\"SM1\",\"service_sid\":null,\"api_version\":null}],"
                    + "\"meta\":{\"page\":0,\"page_size\":25,\"url\":\"/v1/Alerts\",\"first_page_url\":\"/v1/Alerts\","
                    + "\"next_page_url\":null,\"previous_page_url\":null,\"key\":\"alerts\"}}")
                .respond(200, "{\"sid\":\"NO1\",\"account_sid\":\"" + ACCOUNT + "\",\"alert_text\":\"detail\","
                        + "\"error_code\":\"11200\",\"log_level\":\"warning\",\"more_info\":\"https://example.com\","
                        + "\"request_method\":\"POST\",\"request_url\":\"https://example.com/hook\","
                        + "\"url\":\"/v1/Alerts/NO1\",\"date_created\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                        + "\"date_generated\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                        + "\"date_updated\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                        + "\"request_headers\":\"X-Test: 1\",\"request_variables\":null,"
                        + "\"response_body\":\"boom\",\"response_headers\":\"X-Test: 1\"}");
            VoicemlClient c = mock.client(0);
            MonitorV1Alert first = c.monitorV1().alerts().list(ListMonitorAlertsParams.builder()
                    .logLevel("error").startDate(LocalDate.of(2026, 10, 1)).endDate("2026-10-17").pageSize(25)
                    .build()).getAlerts().get(0);
            MonitorV1Alert fetched = c.monitorV1().alerts().fetch("NO1");
            for (MonitorV1Alert ignored : c.monitorV1().alerts().iterate()) {
                // single page, then the pager stops on the null meta.next_page_url
            }
            assertThat(first.getErrorCode()).isEqualTo("11200");
            assertThat(first.getServiceSid()).isNull();
            assertThat(fetched.getLogLevel()).isEqualTo("warning");
            assertThat(fetched.getResponseBody()).isEqualTo("boom");
            assertThat(mock.requests).extracting(r -> r.method + " " + r.rawPath
                    + (r.rawQuery.isEmpty() ? "" : "?" + r.rawQuery)).containsExactly(
                    "GET /v1/Alerts?LogLevel=error&StartDate=2026-10-01&EndDate=2026-10-17&PageSize=25",
                    "GET /v1/Alerts/NO1",
                    "GET /v1/Alerts");
        }
    }

    @Test
    void messageFeedbackCreate() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(201, "{\"account_sid\":\"" + ACCOUNT + "\",\"message_sid\":\"SM1\","
                    + "\"outcome\":\"confirmed\",\"date_created\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                    + "\"date_updated\":\"Sat, 17 Oct 2026 10:00:00 +0000\","
                    + "\"uri\":\"/2010-04-01/Accounts/" + ACCOUNT + "/Messages/SM1/Feedback.json\"}");
            VoicemlClient c = mock.client(0);
            MessageFeedback fb = c.messages().createFeedback("SM1", CreateMessageFeedbackRequest.builder()
                    .outcome("confirmed").build());
            c.messages().createFeedback("SM1");
            assertThat(fb.getOutcome()).isEqualTo("confirmed");
            assertThat(fb.getMessageSid()).isEqualTo("SM1");
            assertThat(mock.requests.get(0).method).isEqualTo("POST");
            assertThat(mock.requests.get(0).rawPath).isEqualTo(A + "/Messages/SM1/Feedback.json");
            assertThat(mock.requests.get(0).header("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
            assertThat(mock.requests.get(0).body).isEqualTo("Outcome=confirmed");
            assertThat(mock.requests.get(1).body).isEmpty();
            assertThat(mock.requests.get(1).header("Content-Type")).isNull();
        }
    }

    @Test
    void accountsRotateKey() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"" + ACCOUNT + "\",\"api_key\":\"rotated-secret-key-0123456789\","
                    + "\"sip_username\":\"ACuser\",\"phone_regions\":[\"US\"],\"allow_cidr\":[],"
                    + "\"disabled\":false,\"stt_http_token_set\":false,\"tts_http_token_set\":false,"
                    + "\"stripe_secret_key_set\":false,\"pay_enabled\":false,\"sms_allowed_providers\":[],"
                    + "\"stt_allowed_providers\":[],\"aws_secret_access_key_set\":false,"
                    + "\"created_at\":\"2026-10-17T10:00:00Z\",\"updated_at\":\"2026-10-17T10:00:00Z\"}")
                .respond(409, "{\"code\":20409,\"message\":\"rotated concurrently\"}");
            VoicemlClient c = mock.client(0);
            AccountWithKey rotated = c.accounts().rotateKey();
            assertThat(rotated.getApiKey()).isEqualTo("rotated-secret-key-0123456789");
            assertThat(rotated.getPhoneRegions()).containsExactly("US");
            assertThat(rotated.getAllowCidr()).isEmpty();
            assertThat(mock.only().method).isEqualTo("POST");
            assertThat(mock.only().rawPath).isEqualTo("/2010-04-01/Accounts/" + ACCOUNT + "/RotateKey.json");
            assertThat(mock.only().body).isEmpty();
            assertThat(mock.only().header("Content-Type")).isNull();
            try {
                c.accounts().rotateKey();
                throw new AssertionError("expected ConflictException");
            } catch (ConflictException expected) {
                assertThat(expected.getCode()).isEqualTo(20409);
            }
        }
    }

    @Test
    void valueSerialisation() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{}");
            Map<String, Object> form = new LinkedHashMap<>();
            form.put("Flag", Boolean.FALSE);
            form.put("When", Instant.parse("2026-10-17T12:30:00Z"));
            form.put("Day", LocalDate.of(2026, 10, 17));
            form.put("Event", List.of("initiated", "answered"));
            Map<String, Object> attrs = new LinkedHashMap<>();
            attrs.put("a", 1);
            attrs.put("b", List.of("x"));
            form.put("Attributes", attrs);
            form.put("Skipped", null);
            mock.client(0).transport().request("POST", "/v1/Test", null, form);
            assertThat(mock.only().body).isEqualTo("Flag=false&When=2026-10-17T12%3A30%3A00Z&Day=2026-10-17"
                    + "&Event=initiated&Event=answered&Attributes=%7B%22a%22%3A1%2C%22b%22%3A%5B%22x%22%5D%7D");
        }
    }
}
