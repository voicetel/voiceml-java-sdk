package com.voicetel.voiceml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicetel.voiceml.models.Application;
import com.voicetel.voiceml.models.ApplicationList;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.CallPayment;
import com.voicetel.voiceml.models.CallTranscription;
import com.voicetel.voiceml.models.Conference;
import com.voicetel.voiceml.models.ConferenceList;
import com.voicetel.voiceml.models.IncomingPhoneNumber;
import com.voicetel.voiceml.models.IncomingPhoneNumberList;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageList;
import com.voicetel.voiceml.models.Participant;
import com.voicetel.voiceml.models.ParticipantList;
import com.voicetel.voiceml.models.Queue;
import com.voicetel.voiceml.models.QueueList;
import com.voicetel.voiceml.models.QueueMember;
import com.voicetel.voiceml.models.QueueMemberList;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.SipCredential;
import com.voicetel.voiceml.models.SipCredentialList;
import com.voicetel.voiceml.models.SipCredentialListList;
import com.voicetel.voiceml.models.SipCredentialListMappingList;
import com.voicetel.voiceml.models.SipCredentialListPage;
import com.voicetel.voiceml.models.SipDomain;
import com.voicetel.voiceml.models.SipDomainList;
import com.voicetel.voiceml.models.SipDomainMapping;
import com.voicetel.voiceml.models.SipIpAccessControlList;
import com.voicetel.voiceml.models.SipIpAccessControlListList;
import com.voicetel.voiceml.models.SipIpAccessControlListMappingList;
import com.voicetel.voiceml.models.SipIpAddress;
import com.voicetel.voiceml.models.SipIpAddressList;
import com.voicetel.voiceml.models.SiprecSession;
import com.voicetel.voiceml.models.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Twilio response-shape conformance tests for the Java SDK (#330 Phase C,
 * mirrors the Go (#329) and Python harnesses, and the TS harness shipped
 * at voiceml-node-sdk@a11b0a1).
 *
 * <p>SKIPPED unless {@code VOICEML_CONFORMANCE_FIXTURES} points at a fixture
 * corpus emitted by callBroadcast's {@code cmd/twilio-conformance-fixtures}.
 * The harness loads each canonical Twilio response example from the corpus
 * and deserialises it into the matching SDK model via Jackson. If
 * deserialisation fails (wrong primitive type, unknown enum value), or if
 * the post-decode key-field asserts trip, the SDK's model has drifted from
 * Twilio's documented shape — fix the SDK, not the fixture.
 *
 * <p>For Twilio resources the Java SDK does not yet model as a Java POJO
 * (Account, Balance, Media, OutgoingCallerId, classic Transcription,
 * ValidationRequest), the harness decodes into a generic {@link JsonNode}
 * and asserts presence of the Twilio-documented top-level fields. This
 * mirrors the Go harness's {@code &map[string]any{}} dispatch — it still
 * catches malformed JSON and missing key fields without requiring a
 * full POJO surface for resources the SDK doesn't expose to callers.
 *
 * <p>Run:
 * <pre>
 *   VOICEML_CONFORMANCE_FIXTURES=/path/to/callBroadcast/cmd/twilio-conformance-fixtures/fixtures \
 *     ./mvnw test -Dtest=ConformanceTest
 * </pre>
 */
class ConformanceTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String FIXTURES_ENV = "VOICEML_CONFORMANCE_FIXTURES";

    /**
     * Unmodelled operation IDs — same skip set as the Go/Python/TS harnesses.
     * These operations are compat stubs that decode permissively elsewhere
     * (Notifications/Events/UserDefinedMessage).
     */
    private static final Set<String> SKIP_OPS = new HashSet<>(Arrays.asList(
            "ListCallEvent",
            "ListCallNotification",
            "FetchCallNotification",
            "ListNotification",
            "FetchNotification",
            "CreateUserDefinedMessage"
    ));

    @TestFactory
    Iterable<DynamicTest> twilioFixtureConformance() throws Exception {
        String root = System.getenv(FIXTURES_ENV);
        if (root == null || root.isEmpty()) {
            return Arrays.asList(DynamicTest.dynamicTest(
                    "skipped: " + FIXTURES_ENV + " not set",
                    () -> {}));
        }
        Path indexPath = Paths.get(root, "index.json");
        if (!Files.exists(indexPath)) {
            return Arrays.asList(DynamicTest.dynamicTest(
                    "skipped: index.json missing at " + indexPath,
                    () -> {}));
        }

        JsonNode entries = MAPPER.readTree(indexPath.toFile());
        List<DynamicTest> tests = new ArrayList<>();
        for (JsonNode entry : entries) {
            String resource = entry.path("resource").asText();
            String opId = entry.path("operation_id").asText();
            String example = entry.path("example_name").asText();
            String relFile = entry.path("file").asText();
            String name = resource + "/" + opId + "/" + example;
            Path fixturePath = Paths.get(root, relFile);

            tests.add(DynamicTest.dynamicTest(name, () -> runOne(opId, fixturePath)));
        }
        return tests;
    }

    private void runOne(String opId, Path fixturePath) throws Exception {
        if (SKIP_OPS.contains(opId)) {
            return;
        }
        byte[] body = Files.readAllBytes(fixturePath);
        switch (opId) {
            case "CreateCall":
            case "FetchCall":
            case "UpdateCall": {
                Call v = MAPPER.readValue(body, Call.class);
                assertThat(v.getSid()).as("Call.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Call.account_sid").isNotEmpty();
                break;
            }
            case "ListCall": {
                CallList v = MAPPER.readValue(body, CallList.class);
                assertThat(v.getUri()).as("CallList.uri (Twilio sets on every list)").isNotEmpty();
                break;
            }
            case "FetchConference":
            case "UpdateConference": {
                Conference v = MAPPER.readValue(body, Conference.class);
                assertThat(v.getSid()).as("Conference.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Conference.account_sid").isNotEmpty();
                break;
            }
            case "ListConference": {
                ConferenceList v = MAPPER.readValue(body, ConferenceList.class);
                assertThat(v.getUri()).as("ConferenceList.uri").isNotEmpty();
                break;
            }
            case "CreateParticipant":
            case "FetchParticipant":
            case "UpdateParticipant": {
                Participant v = MAPPER.readValue(body, Participant.class);
                assertThat(v.getCallSid()).as("Participant.call_sid").isNotEmpty();
                assertThat(v.getConferenceSid()).as("Participant.conference_sid").isNotEmpty();
                break;
            }
            case "ListParticipant": {
                ParticipantList v = MAPPER.readValue(body, ParticipantList.class);
                assertThat(v.getUri()).as("ParticipantList.uri").isNotEmpty();
                break;
            }
            case "CreateQueue":
            case "FetchQueue":
            case "UpdateQueue": {
                Queue v = MAPPER.readValue(body, Queue.class);
                assertThat(v.getSid()).as("Queue.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Queue.account_sid").isNotEmpty();
                break;
            }
            case "ListQueue": {
                QueueList v = MAPPER.readValue(body, QueueList.class);
                assertThat(v.getUri()).as("QueueList.uri").isNotEmpty();
                break;
            }
            case "FetchMember":
            case "UpdateMember": {
                QueueMember v = MAPPER.readValue(body, QueueMember.class);
                assertThat(v.getCallSid()).as("QueueMember.call_sid").isNotEmpty();
                break;
            }
            case "ListMember": {
                QueueMemberList v = MAPPER.readValue(body, QueueMemberList.class);
                assertThat(v.getUri()).as("QueueMemberList.uri").isNotEmpty();
                break;
            }
            case "CreateApplication":
            case "FetchApplication":
            case "UpdateApplication": {
                Application v = MAPPER.readValue(body, Application.class);
                assertThat(v.getSid()).as("Application.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Application.account_sid").isNotEmpty();
                break;
            }
            case "ListApplication": {
                ApplicationList v = MAPPER.readValue(body, ApplicationList.class);
                assertThat(v.getUri()).as("ApplicationList.uri").isNotEmpty();
                break;
            }
            case "CreateCallRecording":
            case "FetchCallRecording":
            case "UpdateCallRecording":
            case "FetchRecording":
            case "FetchConferenceRecording":
            case "UpdateConferenceRecording": {
                Recording v = MAPPER.readValue(body, Recording.class);
                assertThat(v.getSid()).as("Recording.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Recording.account_sid").isNotEmpty();
                break;
            }
            case "ListCallRecording":
            case "ListRecording":
            case "ListConferenceRecording": {
                RecordingList v = MAPPER.readValue(body, RecordingList.class);
                assertThat(v.getUri()).as("RecordingList.uri").isNotEmpty();
                break;
            }
            case "CreateIncomingPhoneNumber":
            case "CreateIncomingPhoneNumberLocal":
            case "CreateIncomingPhoneNumberMobile":
            case "CreateIncomingPhoneNumberTollFree":
            case "FetchIncomingPhoneNumber":
            case "UpdateIncomingPhoneNumber": {
                IncomingPhoneNumber v = MAPPER.readValue(body, IncomingPhoneNumber.class);
                assertThat(v.getSid()).as("IncomingPhoneNumber.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("IncomingPhoneNumber.account_sid").isNotEmpty();
                break;
            }
            case "ListIncomingPhoneNumber":
            case "ListIncomingPhoneNumberLocal":
            case "ListIncomingPhoneNumberMobile":
            case "ListIncomingPhoneNumberTollFree": {
                IncomingPhoneNumberList v = MAPPER.readValue(body, IncomingPhoneNumberList.class);
                assertThat(v.getUri()).as("IncomingPhoneNumberList.uri").isNotEmpty();
                break;
            }
            case "CreateStream":
            case "UpdateStream": {
                // api_version is optional on the Update/Create response (Twilio's
                // documented examples omit it); only sid/account_sid/call_sid asserted.
                Stream v = MAPPER.readValue(body, Stream.class);
                assertThat(v.getSid()).as("Stream.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Stream.account_sid").isNotEmpty();
                assertThat(v.getCallSid()).as("Stream.call_sid").isNotEmpty();
                break;
            }
            case "CreateSiprec":
            case "UpdateSiprec": {
                SiprecSession v = MAPPER.readValue(body, SiprecSession.class);
                assertThat(v.getSid()).as("SiprecSession.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SiprecSession.account_sid").isNotEmpty();
                assertThat(v.getCallSid()).as("SiprecSession.call_sid").isNotEmpty();
                break;
            }
            case "CreateMessage":
            case "FetchMessage":
            case "UpdateMessage": {
                Message v = MAPPER.readValue(body, Message.class);
                assertThat(v.getSid()).as("Message.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("Message.account_sid").isNotEmpty();
                break;
            }
            case "ListMessage": {
                MessageList v = MAPPER.readValue(body, MessageList.class);
                assertThat(v.getUri()).as("MessageList.uri").isNotEmpty();
                break;
            }
            case "CreateRealtimeTranscription":
            case "UpdateRealtimeTranscription": {
                CallTranscription v = MAPPER.readValue(body, CallTranscription.class);
                assertThat(v.getSid()).as("CallTranscription.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("CallTranscription.account_sid").isNotEmpty();
                assertThat(v.getCallSid()).as("CallTranscription.call_sid").isNotEmpty();
                break;
            }
            case "CreatePayments":
            case "UpdatePayments": {
                CallPayment v = MAPPER.readValue(body, CallPayment.class);
                assertThat(v.getSid()).as("CallPayment.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("CallPayment.account_sid").isNotEmpty();
                assertThat(v.getCallSid()).as("CallPayment.call_sid").isNotEmpty();
                break;
            }

            // ---------- SIP Domains ----------
            case "CreateSipDomain":
            case "FetchSipDomain":
            case "UpdateSipDomain": {
                SipDomain v = MAPPER.readValue(body, SipDomain.class);
                assertThat(v.getSid()).as("SipDomain.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipDomain.account_sid").isNotEmpty();
                assertThat(v.getDomainName()).as("SipDomain.domain_name").isNotEmpty();
                break;
            }
            case "ListSipDomain": {
                SipDomainList v = MAPPER.readValue(body, SipDomainList.class);
                assertThat(v.getUri()).as("SipDomainList.uri").isNotEmpty();
                break;
            }

            // ---------- SIP CredentialLists ----------
            case "CreateSipCredentialList":
            case "FetchSipCredentialList":
            case "UpdateSipCredentialList": {
                SipCredentialList v = MAPPER.readValue(body, SipCredentialList.class);
                assertThat(v.getSid()).as("SipCredentialList.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipCredentialList.account_sid").isNotEmpty();
                break;
            }
            case "ListSipCredentialList": {
                SipCredentialListList v = MAPPER.readValue(body, SipCredentialListList.class);
                assertThat(v.getUri()).as("SipCredentialListList.uri").isNotEmpty();
                break;
            }

            // ---------- SIP Credentials (inside a CredentialList) ----------
            case "CreateSipCredential":
            case "FetchSipCredential":
            case "UpdateSipCredential": {
                SipCredential v = MAPPER.readValue(body, SipCredential.class);
                assertThat(v.getSid()).as("SipCredential.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipCredential.account_sid").isNotEmpty();
                assertThat(v.getCredentialListSid()).as("SipCredential.credential_list_sid").isNotEmpty();
                break;
            }
            case "ListSipCredential": {
                SipCredentialListPage v = MAPPER.readValue(body, SipCredentialListPage.class);
                assertThat(v.getUri()).as("SipCredentialListPage.uri").isNotEmpty();
                break;
            }

            // ---------- SIP IpAccessControlLists ----------
            case "CreateSipIpAccessControlList":
            case "FetchSipIpAccessControlList":
            case "UpdateSipIpAccessControlList": {
                SipIpAccessControlList v = MAPPER.readValue(body, SipIpAccessControlList.class);
                assertThat(v.getSid()).as("SipIpAccessControlList.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipIpAccessControlList.account_sid").isNotEmpty();
                break;
            }
            case "ListSipIpAccessControlList": {
                SipIpAccessControlListList v = MAPPER.readValue(body, SipIpAccessControlListList.class);
                assertThat(v.getUri()).as("SipIpAccessControlListList.uri").isNotEmpty();
                break;
            }

            // ---------- SIP IpAddresses (inside an IpAccessControlList) ----------
            case "CreateSipIpAddress":
            case "FetchSipIpAddress":
            case "UpdateSipIpAddress": {
                SipIpAddress v = MAPPER.readValue(body, SipIpAddress.class);
                assertThat(v.getSid()).as("SipIpAddress.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipIpAddress.account_sid").isNotEmpty();
                assertThat(v.getIpAddress()).as("SipIpAddress.ip_address").isNotEmpty();
                break;
            }
            case "ListSipIpAddress": {
                SipIpAddressList v = MAPPER.readValue(body, SipIpAddressList.class);
                assertThat(v.getUri()).as("SipIpAddressList.uri").isNotEmpty();
                break;
            }

            // ---------- SIP CredentialListMappings (historical /Domains/{SD}/CredentialListMappings) ----------
            case "CreateSipCredentialListMapping":
            case "FetchSipCredentialListMapping": {
                SipDomainMapping v = MAPPER.readValue(body, SipDomainMapping.class);
                assertThat(v.getSid()).as("SipDomainMapping.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipDomainMapping.account_sid").isNotEmpty();
                break;
            }
            case "ListSipCredentialListMapping": {
                SipCredentialListMappingList v = MAPPER.readValue(body, SipCredentialListMappingList.class);
                assertThat(v.getUri()).as("SipCredentialListMappingList.uri").isNotEmpty();
                break;
            }

            // ---------- SIP IpAccessControlListMappings (historical /Domains/{SD}/IpAccessControlListMappings) ----------
            case "CreateSipIpAccessControlListMapping":
            case "FetchSipIpAccessControlListMapping": {
                SipDomainMapping v = MAPPER.readValue(body, SipDomainMapping.class);
                assertThat(v.getSid()).as("SipDomainMapping.sid").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipDomainMapping.account_sid").isNotEmpty();
                break;
            }
            case "ListSipIpAccessControlListMapping": {
                SipIpAccessControlListMappingList v = MAPPER.readValue(body, SipIpAccessControlListMappingList.class);
                assertThat(v.getUri()).as("SipIpAccessControlListMappingList.uri").isNotEmpty();
                break;
            }

            // ---------- SIP Auth/Calls + Auth/Registrations mappings (v0.8 split surfaces) ----------
            // The Twilio Auth-namespaced mapping fixtures omit domain_sid on the
            // Create/Fetch response (the binding is implicit in the URL path);
            // assert only the universally-present sid + account_sid.
            case "CreateSipAuthCallsCredentialListMapping":
            case "FetchSipAuthCallsCredentialListMapping":
            case "CreateSipAuthCallsIpAccessControlListMapping":
            case "FetchSipAuthCallsIpAccessControlListMapping":
            case "CreateSipAuthRegistrationsCredentialListMapping":
            case "FetchSipAuthRegistrationsCredentialListMapping": {
                SipDomainMapping v = MAPPER.readValue(body, SipDomainMapping.class);
                assertThat(v.getSid()).as("SipDomainMapping.sid (" + opId + ")").isNotEmpty();
                assertThat(v.getAccountSid()).as("SipDomainMapping.account_sid (" + opId + ")").isNotEmpty();
                break;
            }
            case "ListSipAuthCallsCredentialListMapping":
            case "ListSipAuthRegistrationsCredentialListMapping": {
                SipCredentialListMappingList v = MAPPER.readValue(body, SipCredentialListMappingList.class);
                assertThat(v.getUri()).as("SipCredentialListMappingList.uri (" + opId + ")").isNotEmpty();
                break;
            }
            case "ListSipAuthCallsIpAccessControlListMapping": {
                SipIpAccessControlListMappingList v = MAPPER.readValue(body, SipIpAccessControlListMappingList.class);
                assertThat(v.getUri()).as("SipIpAccessControlListMappingList.uri (" + opId + ")").isNotEmpty();
                break;
            }

            // ---------- Resources not (yet) modelled as Java POJOs ----------
            // Mirrors the Go harness's `&map[string]any{}` dispatch: decode into
            // a generic JsonNode and assert presence of the Twilio-documented
            // top-level fields. Catches malformed responses + shape drift on
            // the key fields without forcing a full POJO surface for resources
            // the Java SDK doesn't expose to callers (the SDK is intentionally
            // a subset of the underlying Twilio-compatible surface).

            case "FetchAccount":
            case "UpdateAccount": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("sid").asText()).as("Account.sid").isNotEmpty();
                assertThat(v.path("status").asText()).as("Account.status").isNotEmpty();
                assertThat(v.path("uri").asText()).as("Account.uri").isNotEmpty();
                break;
            }
            case "FetchBalance": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("account_sid").asText()).as("Balance.account_sid").isNotEmpty();
                assertThat(v.path("balance").asText()).as("Balance.balance").isNotEmpty();
                assertThat(v.path("currency").asText()).as("Balance.currency").isNotEmpty();
                break;
            }
            case "FetchMedia": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("sid").asText()).as("Media.sid").isNotEmpty();
                assertThat(v.path("account_sid").asText()).as("Media.account_sid").isNotEmpty();
                assertThat(v.path("parent_sid").asText()).as("Media.parent_sid").isNotEmpty();
                assertThat(v.path("content_type").asText()).as("Media.content_type").isNotEmpty();
                break;
            }
            case "ListMedia": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("uri").asText()).as("MediaList.uri").isNotEmpty();
                assertThat(v.has("media_list")).as("MediaList.media_list (envelope key)").isTrue();
                break;
            }
            case "FetchOutgoingCallerId":
            case "UpdateOutgoingCallerId": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("sid").asText()).as("OutgoingCallerId.sid").isNotEmpty();
                assertThat(v.path("account_sid").asText()).as("OutgoingCallerId.account_sid").isNotEmpty();
                assertThat(v.path("phone_number").asText()).as("OutgoingCallerId.phone_number").isNotEmpty();
                break;
            }
            case "ListOutgoingCallerId": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("uri").asText()).as("OutgoingCallerIdList.uri").isNotEmpty();
                assertThat(v.has("outgoing_caller_ids"))
                        .as("OutgoingCallerIdList.outgoing_caller_ids (envelope key)").isTrue();
                break;
            }
            case "CreateValidationRequest": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("account_sid").asText()).as("ValidationRequest.account_sid").isNotEmpty();
                assertThat(v.path("phone_number").asText()).as("ValidationRequest.phone_number").isNotEmpty();
                assertThat(v.path("validation_code").asText()).as("ValidationRequest.validation_code").isNotEmpty();
                break;
            }
            case "FetchTranscription":
            case "FetchRecordingTranscription": {
                // Classic /Transcriptions resource (NOT the realtime CallTranscription).
                // The Java SDK doesn't currently expose this as a POJO; assert the
                // documented top-level fields via JsonNode.
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("sid").asText()).as("Transcription.sid").isNotEmpty();
                assertThat(v.path("account_sid").asText()).as("Transcription.account_sid").isNotEmpty();
                assertThat(v.path("recording_sid").asText()).as("Transcription.recording_sid").isNotEmpty();
                break;
            }
            case "ListTranscription":
            case "ListRecordingTranscription": {
                JsonNode v = MAPPER.readTree(body);
                assertThat(v.path("uri").asText()).as("TranscriptionList.uri (" + opId + ")").isNotEmpty();
                assertThat(v.has("transcriptions"))
                        .as("TranscriptionList.transcriptions (envelope key)").isTrue();
                break;
            }

            default:
                throw new AssertionError("conformance harness: no mapping for operation_id=" + opId
                        + " (fixture=" + fixturePath + "). Add a case or extend SKIP_OPS.");
        }
    }
}
