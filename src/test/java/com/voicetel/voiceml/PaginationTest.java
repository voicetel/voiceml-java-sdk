package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.PaginationException;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.ConversationsV1Conversation;
import com.voicetel.voiceml.models.ConversationsV1ConversationList;
import com.voicetel.voiceml.models.ListCallsParams;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** C1: iterators follow the server's links verbatim, lazily, and stop. */
class PaginationTest {

    private static final String ACCOUNT = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String CALLS = "/2010-04-01/Accounts/" + ACCOUNT + "/Calls.json";

    private static String page(int page, String next, String... sids) {
        StringBuilder items = new StringBuilder();
        for (String sid : sids) {
            if (items.length() > 0) {
                items.append(',');
            }
            items.append("{\"sid\":\"").append(sid).append("\",\"status\":\"completed\"}");
        }
        return "{\"calls\":[" + items + "],\"page\":" + page + ",\"page_size\":2,\"start\":" + (page * 2)
                + ",\"end\":" + (page * 2 + sids.length - 1) + ",\"uri\":\"" + CALLS + "\","
                + "\"first_page_uri\":\"" + CALLS + "?PageSize=2&Page=0\","
                + "\"previous_page_uri\":null,"
                + "\"next_page_uri\":" + (next == null ? "null" : "\"" + next + "\"") + "}";
    }

    @Test
    void followsNextPageUriVerbatimWithPageTokenAndTerminates() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, page(0, CALLS + "?PageSize=2&Page=1&PageToken=PACA02", "CA01", "CA02"))
                .respond(200, page(1, CALLS + "?PageSize=2&Page=2&PageToken=PACA04", "CA03", "CA04"))
                .respond(200, page(2, null, "CA05"));
            List<String> sids = mock.client(0).calls()
                    .iterate(ListCallsParams.builder().pageSize(2).build())
                    .stream().map(Call::getSid).collect(Collectors.toList());
            assertThat(sids).containsExactly("CA01", "CA02", "CA03", "CA04", "CA05");
            assertThat(mock.requests).hasSize(3);
            assertThat(mock.requests.get(0).rawPath).isEqualTo(CALLS);
            assertThat(mock.requests.get(0).rawQuery).isEqualTo("PageSize=2");
            assertThat(mock.requests.get(1).rawPath).isEqualTo(CALLS);
            assertThat(mock.requests.get(1).rawQuery).isEqualTo("PageSize=2&Page=1&PageToken=PACA02");
            assertThat(mock.requests.get(2).rawQuery).isEqualTo("PageSize=2&Page=2&PageToken=PACA04");
            assertThat(mock.requests.get(1).header("Authorization")).startsWith("Basic ");
        }
    }

    @Test
    void isLazyAndStopsWhenTheConsumerStops() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, page(0, CALLS + "?Page=1&PageToken=PA1", "CA01", "CA02"));
            Pager<Call> pager = mock.client(0).calls().iterate();
            assertThat(mock.requests).isEmpty();
            Iterator<Call> it = pager.iterator();
            assertThat(it.next().getSid()).isEqualTo("CA01");
            assertThat(it.next().getSid()).isEqualTo("CA02");
            assertThat(mock.requests).hasSize(1);
            assertThat(pager.stream().limit(1).count()).isEqualTo(1);
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void stopsOnAnEmptyPageEvenIfItLinksOnward() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, page(0, CALLS + "?Page=1&PageToken=PA1", "CA01"))
                .respond(200, page(1, CALLS + "?Page=2&PageToken=PA2"));
            List<Call> all = new ArrayList<>();
            mock.client(0).calls().iterate().forEach(all::add);
            assertThat(all).hasSize(1);
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void aLinkThatDoesNotAdvanceRaisesInsteadOfLooping() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            String self = CALLS + "?Page=0";
            mock.respond(200, page(0, self, "CA01")).respond(200, page(0, self, "CA01"));
            Iterator<Call> it = mock.client(0).calls().iterate().iterator();
            it.next();
            it.next();
            assertThatThrownBy(it::hasNext).isInstanceOf(PaginationException.class);
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void anAbsoluteLinkToAnotherHostIsRefusedWithoutSendingCredentials() throws IOException {
        try (MockHttp mock = new MockHttp(); MockHttp foreign = new MockHttp()) {
            mock.respond(200, page(0, foreign.url() + CALLS + "?Page=1", "CA01"));
            Iterator<Call> it = mock.client(0).calls().iterate().iterator();
            it.next();
            assertThatThrownBy(it::hasNext).isInstanceOf(PaginationException.class)
                    .hasMessageContaining("not the configured host");
            assertThat(foreign.requests).isEmpty();
        }
    }

    @Test
    void nextPageHelperFollowsTheLinkAndReturnsNullAtTheEnd() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, page(0, CALLS + "?Page=1&PageToken=PA1", "CA01"))
                .respond(200, page(1, null, "CA02"));
            VoicemlClient client = mock.client(0);
            CallList first = client.calls().list();
            CallList second = client.nextPage(first, CallList.class);
            assertThat(second.getCalls()).extracting(Call::getSid).containsExactly("CA02");
            assertThat(mock.requests.get(1).rawQuery).isEqualTo("Page=1&PageToken=PA1");
            assertThat(client.nextPage(second, CallList.class)).isNull();
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void v1ListsFollowMetaNextPageUrlOnTheirOwnHostOnly() throws IOException {
        try (MockHttp base = new MockHttp(); MockHttp conv = new MockHttp(); MockHttp foreign = new MockHttp()) {
            String next = conv.url() + "/v1/Conversations?PageSize=1&Page=1&PageToken=PTx";
            conv.respond(200, "{\"conversations\":[{\"sid\":\"CH1\"}],\"meta\":{\"page\":0,\"page_size\":1,"
                    + "\"next_page_url\":\"" + next + "\",\"key\":\"conversations\"}}")
                .respond(200, "{\"conversations\":[{\"sid\":\"CH2\"}],\"meta\":{\"page\":1,\"page_size\":1,"
                    + "\"next_page_url\":\"" + foreign.url() + "/v1/Conversations?Page=2\",\"key\":\"conversations\"}}");
            VoicemlClient client = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("k")
                    .baseUrl(base.url()).conversationsBaseUrl(conv.url()).maxRetries(0).build();
            ConversationsV1ConversationList first = client.conversationsV1().conversations()
                    .list(ListPageParams.builder().pageSize(1).build());
            assertThat(conv.requests.get(0).rawQuery).isEqualTo("PageSize=1");
            ConversationsV1ConversationList second = client.nextPage(first, ConversationsV1ConversationList.class);
            assertThat(second.getConversations()).extracting(ConversationsV1Conversation::getSid).containsExactly("CH2");
            assertThat(conv.requests.get(1).rawQuery).isEqualTo("PageSize=1&Page=1&PageToken=PTx");
            assertThatThrownBy(() -> client.nextPage(second, ConversationsV1ConversationList.class))
                    .isInstanceOf(PaginationException.class);
            assertThat(foreign.requests).isEmpty();
            assertThat(base.requests).isEmpty();
        }
    }

    @Test
    void pageSizeOnlyListsRejectPageAndPageToken() {
        VoicemlClient client = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("k")
                .baseUrl("http://127.0.0.1:9").build();
        assertThatThrownBy(() -> client.conversationsV1().conversations()
                .list(ListPageParams.builder().page(2).build()))
                .isInstanceOf(ValidationException.class);
    }
}
