package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Account;
import com.voicetel.voiceml.models.AccountWithKey;
import com.voicetel.voiceml.models.UpdateAccountRequest;

/** {@code /2010-04-01/Accounts/{Sid}}: fetch and update your account. */
public final class AccountsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.accounts()}.
     *
     * @param transport the transport
     */
    public AccountsResource(Transport transport) {
        super(transport);
    }

    private static String path(String accountSid) {
        return "/2010-04-01/Accounts/" + seg(accountSid) + ".json";
    }

    /** @return the account the client authenticates as */
    public Account fetch() {
        return fetch(transport.accountSid());
    }

    /**
     * {@code GET /2010-04-01/Accounts/{Sid}}.
     *
     * @param accountSid the account SID
     * @return the account
     */
    public Account fetch(String accountSid) {
        return decode(transport.request("GET", path(accountSid), null, null), Account.class);
    }

    /**
     * Update the account the client authenticates as.
     *
     * @param body the properties to change
     * @return the updated account
     */
    public Account update(UpdateAccountRequest body) {
        return update(transport.accountSid(), body);
    }

    /**
     * {@code POST /2010-04-01/Accounts/{Sid}} with a JSON body. Raises {@code ConflictException}
     * (code {@code 20409}) when a concurrent update won the optimistic lock; fetch and retry.
     *
     * @param accountSid the account SID
     * @param body the properties to change
     * @return the updated account
     */
    public Account update(String accountSid, UpdateAccountRequest body) {
        return decode(transport.requestJson("POST", path(accountSid), null, body.toJsonBody()), Account.class);
    }

    /**
     * Rotate the API key of the account the client authenticates as. The previous key stops
     * working immediately; the response carries the new one on {@code getApiKey()} — no read
     * endpoint exposes it, so store it right away. Its {@code toString()} never includes the key.
     *
     * @return the account view plus the freshly generated {@code api_key}
     */
    public AccountWithKey rotateKey() {
        return rotateKey(transport.accountSid());
    }

    /**
     * {@code POST /2010-04-01/Accounts/{AccountSid}/RotateKey.json}. Raises
     * {@code ConflictException} (code {@code 20409}) when the account's optimistic lock was taken
     * by a concurrent update; fetch the account and retry.
     *
     * @param accountSid the account SID
     * @return the account view plus the freshly generated {@code api_key}
     */
    public AccountWithKey rotateKey(String accountSid) {
        return decode(transport.request(
                "POST", "/2010-04-01/Accounts/" + seg(accountSid) + "/RotateKey.json", null, null),
                AccountWithKey.class);
    }
}
