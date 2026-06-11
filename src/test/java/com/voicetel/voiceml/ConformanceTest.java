package com.voicetel.voiceml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicetel.voiceml.models.Application;
import com.voicetel.voiceml.models.ApplicationList;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.CallTranscription;
import com.voicetel.voiceml.models.Conference;
import com.voicetel.voiceml.models.ConferenceList;
import com.voicetel.voiceml.models.IncomingPhoneNumber;
import com.voicetel.voiceml.models.IncomingPhoneNumberList;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageList;
import com.voicetel.voiceml.models.NotificationsList;
import com.voicetel.voiceml.models.Participant;
import com.voicetel.voiceml.models.ParticipantList;
import com.voicetel.voiceml.models.Queue;
import com.voicetel.voiceml.models.QueueList;
import com.voicetel.voiceml.models.QueueMember;
import com.voicetel.voiceml.models.QueueMemberList;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingList;
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
            default:
                throw new AssertionError("conformance harness: no mapping for operation_id=" + opId
                        + " (fixture=" + fixturePath + "). Add a case or extend SKIP_OPS.");
        }
    }
}
