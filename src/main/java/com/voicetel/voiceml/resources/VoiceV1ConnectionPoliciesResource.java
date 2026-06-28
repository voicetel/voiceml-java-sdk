package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateVoiceV1ConnectionPolicyRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateVoiceV1ConnectionPolicyRequest;
import com.voicetel.voiceml.models.VoiceV1ConnectionPolicy;
import com.voicetel.voiceml.models.VoiceV1ConnectionPolicyList;

import java.util.Map;

/** Operations on {@code /v1/ConnectionPolicies[/{Sid}]} and its nested Targets. */
public final class VoiceV1ConnectionPoliciesResource extends BaseResource {

    public VoiceV1ConnectionPoliciesResource(Transport transport) {
        super(transport);
    }

    public VoiceV1ConnectionPolicy create(CreateVoiceV1ConnectionPolicyRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/ConnectionPolicies", null, form),
                VoiceV1ConnectionPolicy.class);
    }

    public VoiceV1ConnectionPolicyList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/ConnectionPolicies", q, null),
                VoiceV1ConnectionPolicyList.class);
    }

    public VoiceV1ConnectionPolicyList list() { return list(null); }

    public VoiceV1ConnectionPolicy fetch(String connectionPolicySid) {
        return decode(transport.request("GET", "/v1/ConnectionPolicies/" + connectionPolicySid, null, null),
                VoiceV1ConnectionPolicy.class);
    }

    public VoiceV1ConnectionPolicy update(String connectionPolicySid, UpdateVoiceV1ConnectionPolicyRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/ConnectionPolicies/" + connectionPolicySid, null, form),
                VoiceV1ConnectionPolicy.class);
    }

    public void delete(String connectionPolicySid) {
        transport.request("DELETE", "/v1/ConnectionPolicies/" + connectionPolicySid, null, null);
    }

    /** Sub-resource handle for {@code /v1/ConnectionPolicies/{ConnectionPolicySid}/Targets}. */
    public VoiceV1ConnectionPolicyTargetsResource targets(String connectionPolicySid) {
        return new VoiceV1ConnectionPolicyTargetsResource(transport, connectionPolicySid);
    }
}
