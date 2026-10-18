package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Balance;

/** {@code /Balance}. */
public final class BalanceResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.balance()}.
     *
     * @param transport the transport
     */
    public BalanceResource(Transport transport) {
        super(transport);
    }

    /** @return {@code GET /Balance} for the account */
    public Balance fetch() {
        return decode(transport.request("GET", accountPath("Balance"), null, null), Balance.class);
    }
}
