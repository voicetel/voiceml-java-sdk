package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateVoiceV1SourceIpMappingRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateVoiceV1SourceIpMappingRequest;
import com.voicetel.voiceml.models.VoiceV1SourceIpMapping;
import com.voicetel.voiceml.models.VoiceV1SourceIpMappingList;

import java.util.Map;

/** Operations on {@code /v1/SourceIpMappings[/{Sid}]}. */
public final class VoiceV1SourceIpMappingsResource extends BaseResource {

    public VoiceV1SourceIpMappingsResource(Transport transport) {
        super(transport);
    }

    public VoiceV1SourceIpMapping create(CreateVoiceV1SourceIpMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/SourceIpMappings", null, form),
                VoiceV1SourceIpMapping.class);
    }

    public VoiceV1SourceIpMappingList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/SourceIpMappings", q, null),
                VoiceV1SourceIpMappingList.class);
    }

    public VoiceV1SourceIpMappingList list() { return list(null); }

    public VoiceV1SourceIpMapping fetch(String sid) {
        return decode(transport.request("GET", "/v1/SourceIpMappings/" + sid, null, null),
                VoiceV1SourceIpMapping.class);
    }

    public VoiceV1SourceIpMapping update(String sid, UpdateVoiceV1SourceIpMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/SourceIpMappings/" + sid, null, form),
                VoiceV1SourceIpMapping.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/SourceIpMappings/" + sid, null, null);
    }
}
