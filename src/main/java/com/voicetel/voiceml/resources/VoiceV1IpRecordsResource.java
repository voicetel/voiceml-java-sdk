package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateVoiceV1IpRecordRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateVoiceV1IpRecordRequest;
import com.voicetel.voiceml.models.VoiceV1IpRecord;
import com.voicetel.voiceml.models.VoiceV1IpRecordList;

import java.util.Map;

/** Operations on {@code /v1/IpRecords[/{Sid}]}. */
public final class VoiceV1IpRecordsResource extends BaseResource {

    public VoiceV1IpRecordsResource(Transport transport) {
        super(transport);
    }

    public VoiceV1IpRecord create(CreateVoiceV1IpRecordRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/IpRecords", null, form), VoiceV1IpRecord.class);
    }

    public VoiceV1IpRecordList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/IpRecords", q, null), VoiceV1IpRecordList.class);
    }

    public VoiceV1IpRecordList list() { return list(null); }

    public VoiceV1IpRecord fetch(String sid) {
        return decode(transport.request("GET", "/v1/IpRecords/" + sid, null, null), VoiceV1IpRecord.class);
    }

    public VoiceV1IpRecord update(String sid, UpdateVoiceV1IpRecordRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/IpRecords/" + sid, null, form), VoiceV1IpRecord.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/IpRecords/" + sid, null, null);
    }
}
