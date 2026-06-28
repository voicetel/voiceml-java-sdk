package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateVoiceV1ConnectionPolicyTargetRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateVoiceV1ConnectionPolicyTargetRequest;
import com.voicetel.voiceml.models.VoiceV1ConnectionPolicyTarget;
import com.voicetel.voiceml.models.VoiceV1ConnectionPolicyTargetList;

import java.util.Map;

/** Operations on {@code /v1/ConnectionPolicies/{ConnectionPolicySid}/Targets[/{Sid}]}. */
public final class VoiceV1ConnectionPolicyTargetsResource extends BaseResource {

    private final String connectionPolicySid;

    public VoiceV1ConnectionPolicyTargetsResource(Transport transport, String connectionPolicySid) {
        super(transport);
        this.connectionPolicySid = connectionPolicySid;
    }

    private String base() {
        return "/v1/ConnectionPolicies/" + connectionPolicySid + "/Targets";
    }

    public VoiceV1ConnectionPolicyTarget create(CreateVoiceV1ConnectionPolicyTargetRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form), VoiceV1ConnectionPolicyTarget.class);
    }

    public VoiceV1ConnectionPolicyTargetList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null), VoiceV1ConnectionPolicyTargetList.class);
    }

    public VoiceV1ConnectionPolicyTargetList list() { return list(null); }

    public VoiceV1ConnectionPolicyTarget fetch(String sid) {
        return decode(transport.request("GET", base() + "/" + sid, null, null),
                VoiceV1ConnectionPolicyTarget.class);
    }

    public VoiceV1ConnectionPolicyTarget update(String sid, UpdateVoiceV1ConnectionPolicyTargetRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + sid, null, form),
                VoiceV1ConnectionPolicyTarget.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", base() + "/" + sid, null, null);
    }
}
