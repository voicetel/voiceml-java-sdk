package com.voicetel.voiceml.models;

/**
 * A list envelope of the {@code /v1} and {@code /v2} APIs (Conversations, Messaging Service,
 * Voice, Assistants, Pricing), which carry pagination links in a {@code meta} object.
 *
 * <p>Pass a page to {@code VoicemlClient.nextPage(...)} or {@code VoicemlClient.paginate(...)} to
 * follow {@code meta.next_page_url}; the link is followed only when it points at the host that
 * served the page.
 */
public interface MetaPage {

    /**
     * The pagination metadata.
     *
     * @return the {@code meta} object, or {@code null} when the server omitted it
     */
    VoiceV1Meta getMeta();
}
