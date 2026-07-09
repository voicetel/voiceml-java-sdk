package com.voicetel.voiceml;

import java.net.URI;

/**
 * Per-product host resolution for the VoiceML API.
 *
 * <p>Twilio splits its products across dedicated subdomains ({@code api.twilio.com},
 * {@code conversations.twilio.com}, {@code messaging.twilio.com}, …). VoiceML mirrors that shape
 * on {@code voicetel.com}: the Conversations product answers on {@code conversations.voicetel.com}
 * and the Messaging Service product on {@code messaging.voicetel.com}, while everything else stays
 * on the default {@code voiceml.voicetel.com} host. Conversation Service and Messaging Service
 * share the identical {@code /v1/Services} path shape, so the <em>host</em> is what disambiguates
 * them on the wire.
 *
 * <p>Given the configured base URL this helper derives the two product hosts by swapping the
 * leftmost {@code voiceml} label — but only for recognised {@code *.voicetel.com} hosts. For any
 * other base URL (a self-hosted callBroadcast instance, a test stub, a regional override) the
 * product hosts fall back to the configured host unchanged, so a single-host deployment keeps
 * working. A caller who needs Messaging Service against a custom host points
 * {@code messagingBaseUrl} (or {@code conversationsBaseUrl}) at their own subdomain explicitly.
 */
final class Hosts {

    private Hosts() {}

    static String stripTrailingSlash(String url) {
        if (url == null) {
            return null;
        }
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    /**
     * Swap the leftmost {@code voiceml} label of a {@code *.voicetel.com} host for {@code product}.
     *
     * <p>Returns {@code baseUrl} unchanged when the host is not a {@code voiceml.*.voicetel.com}
     * style host (e.g. a self-hosted instance), so single-host deployments keep working without
     * special-casing.
     */
    static String deriveProductHost(String baseUrl, String product) {
        URI uri;
        try {
            uri = URI.create(baseUrl);
        } catch (IllegalArgumentException ex) {
            return baseUrl;
        }
        String host = uri.getHost();
        if (host == null || !host.endsWith(".voicetel.com")) {
            return baseUrl;
        }
        String[] labels = host.split("\\.");
        int idx = -1;
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].equals("voiceml")) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            return baseUrl;
        }
        labels[idx] = product;
        String newHost = String.join(".", labels);
        int port = uri.getPort();
        String authority = port < 0 ? newHost : newHost + ":" + port;
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getScheme()).append("://").append(authority);
        String path = uri.getRawPath();
        if (path != null && !path.isEmpty()) {
            sb.append(path);
        }
        return sb.toString();
    }
}
