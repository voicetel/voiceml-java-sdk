package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/IpRecords} response — {@code ip_records} + {@code meta}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1IpRecordList {

    @JsonProperty("ip_records")
    private List<VoiceV1IpRecord> ipRecords = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<VoiceV1IpRecord> getIpRecords() { return ipRecords; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setIpRecords(List<VoiceV1IpRecord> v) { this.ipRecords = v != null ? v : new ArrayList<>(); }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
