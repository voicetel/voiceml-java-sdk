package com.voicetel.voiceml.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.exceptions.ApiException;

import java.io.IOException;

/**
 * Shared helpers for every resource group.
 *
 * <p>Holds the {@link Transport}, builds {@code /2010-04-01/Accounts/{AccountSid}/…} paths, and
 * deserializes JSON nodes into model classes via the transport's shared {@code ObjectMapper}.
 */
abstract class BaseResource {

    protected final Transport transport;

    BaseResource(Transport transport) {
        this.transport = transport;
    }

    /** Build a URL under {@code /2010-04-01/Accounts/{AccountSid}/…}. Empty segments are dropped. */
    protected String accountPath(String... parts) {
        StringBuilder sb = new StringBuilder("/2010-04-01/Accounts/")
                .append(transport.accountSid());
        for (String p : parts) {
            if (p == null || p.isEmpty()) {
                continue;
            }
            sb.append('/').append(p);
        }
        return sb.toString();
    }

    /** Decode a {@link JsonNode} into the requested model type. */
    protected <T> T decode(JsonNode node, Class<T> type) {
        if (node == null) {
            return null;
        }
        try {
            return transport.mapper().treeToValue(node, type);
        } catch (IOException ex) {
            throw new ApiException("failed to decode response: " + ex.getMessage(), 0, null, null, ex);
        }
    }

    /** Decode a {@link JsonNode} into a generic type (used for {@code List<…>}). */
    @SuppressWarnings("unused")
    protected <T> T decode(JsonNode node, TypeReference<T> type) {
        if (node == null) {
            return null;
        }
        try {
            return transport.mapper().readValue(transport.mapper().treeAsTokens(node), type);
        } catch (IOException ex) {
            throw new ApiException("failed to decode response: " + ex.getMessage(), 0, null, null, ex);
        }
    }
}
