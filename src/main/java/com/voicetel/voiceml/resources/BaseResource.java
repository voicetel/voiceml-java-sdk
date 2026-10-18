package com.voicetel.voiceml.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.exceptions.ValidationException;
import com.voicetel.voiceml.models.MetaPage;
import com.voicetel.voiceml.models.Page;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Shared helpers for every resource group.
 *
 * <p>Holds the {@link Transport}, builds {@code /2010-04-01/Accounts/{AccountSid}/…} paths with
 * every segment percent-encoded, and deserializes JSON nodes into model classes via the
 * transport's decoder.
 */
abstract class BaseResource {

    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    protected final Transport transport;

    BaseResource(Transport transport) {
        this.transport = transport;
    }

    /**
     * Percent-encode one path segment (RFC 3986): the unreserved set {@code A-Z a-z 0-9 - . _ ~}
     * is kept, every other UTF-8 byte becomes {@code %XX}, so {@code +} is sent as {@code %2B},
     * a space as {@code %20}, {@code /} as {@code %2F} and {@code ?} as {@code %3F}.
     *
     * @throws ValidationException for a {@code null}, empty, {@code .} or {@code ..} segment,
     *     which would change the request's path
     */
    static String seg(String value) {
        if (value == null || value.isEmpty()) {
            throw new ValidationException("path identifier must not be null or empty");
        }
        if (value.equals(".") || value.equals("..")) {
            throw new ValidationException("path identifier must not be '" + value + "'");
        }
        StringBuilder sb = new StringBuilder(value.length() + 8);
        for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
            int c = b & 0xFF;
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                    || c == '-' || c == '.' || c == '_' || c == '~') {
                sb.append((char) c);
            } else {
                sb.append('%').append(HEX[c >> 4]).append(HEX[c & 0x0F]);
            }
        }
        return sb.toString();
    }

    /**
     * Build {@code /2010-04-01/Accounts/{AccountSid}/{parts…}.json}. Every part is encoded with
     * {@link #seg(String)}; the {@code .json} suffix is part of every such route in the spec.
     */
    protected String accountPath(String... parts) {
        return accountPathWithSuffix(".json", parts);
    }

    /**
     * Build {@code /2010-04-01/Accounts/{AccountSid}/{parts…}{suffix}} for the routes that do not
     * end in {@code .json} ({@code .wav} audio, {@code Exports/{Date}}).
     */
    protected String accountPathWithSuffix(String suffix, String... parts) {
        StringBuilder sb = new StringBuilder("/2010-04-01/Accounts/").append(seg(transport.accountSid()));
        for (String p : parts) {
            sb.append('/').append(seg(p));
        }
        return sb.append(suffix).toString();
    }

    /** Decode a {@link JsonNode} into the requested model type. */
    protected <T> T decode(JsonNode node, Class<T> type) {
        return transport.decode(node, type);
    }

    /** Decode a {@link JsonNode} into a generic type (used for {@code List<…>}). */
    protected <T> T decode(JsonNode node, TypeReference<T> type) {
        return transport.decode(node, type);
    }

    /** A lazy pager over a {@code /2010-04-01} list that follows {@code next_page_uri}. */
    protected <P extends Page, T> Pager<T> pager(Supplier<P> firstPage, Class<P> type,
                                                 Function<? super P, List<T>> items) {
        return Pager.create(firstPage, items, Page::getNextPageUri,
                link -> decode(transport.getLink(link), type));
    }

    /** A lazy pager over a {@code /v1} list that follows {@code meta.next_page_url}. */
    protected <P extends MetaPage, T> Pager<T> metaPager(Supplier<P> firstPage, Class<P> type,
                                                         Function<? super P, List<T>> items) {
        return Pager.create(firstPage, items,
                p -> p.getMeta() == null ? null : p.getMeta().getNextPageUrl(),
                link -> decode(transport.getLink(link), type));
    }
}
