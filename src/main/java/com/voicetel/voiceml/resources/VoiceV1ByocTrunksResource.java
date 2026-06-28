package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateVoiceV1ByocTrunkRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateVoiceV1ByocTrunkRequest;
import com.voicetel.voiceml.models.VoiceV1ByocTrunk;
import com.voicetel.voiceml.models.VoiceV1ByocTrunkList;

import java.util.Map;

/** Operations on {@code /v1/ByocTrunks[/{Sid}]}. */
public final class VoiceV1ByocTrunksResource extends BaseResource {

    public VoiceV1ByocTrunksResource(Transport transport) {
        super(transport);
    }

    public VoiceV1ByocTrunk create(CreateVoiceV1ByocTrunkRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/ByocTrunks", null, form), VoiceV1ByocTrunk.class);
    }

    public VoiceV1ByocTrunkList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/ByocTrunks", q, null), VoiceV1ByocTrunkList.class);
    }

    public VoiceV1ByocTrunkList list() { return list(null); }

    public VoiceV1ByocTrunk fetch(String sid) {
        return decode(transport.request("GET", "/v1/ByocTrunks/" + sid, null, null),
                VoiceV1ByocTrunk.class);
    }

    public VoiceV1ByocTrunk update(String sid, UpdateVoiceV1ByocTrunkRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/ByocTrunks/" + sid, null, form),
                VoiceV1ByocTrunk.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/ByocTrunks/" + sid, null, null);
    }
}
