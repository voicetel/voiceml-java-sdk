package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Twilio Voice v1 SourceIpMapping — pins an {@link VoiceV1IpRecord} to a
 * SIP Domain so inbound calls from that source IP route to the domain. SID
 * is {@code IB…}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1SourceIpMapping {

    @JsonProperty("sid") private String sid;
    @JsonProperty("ip_record_sid") private String ipRecordSid;
    @JsonProperty("sip_domain_sid") private String sipDomainSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;

    public String getSid() { return sid; }
    public String getIpRecordSid() { return ipRecordSid; }
    public String getSipDomainSid() { return sipDomainSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }

    public void setSid(String v) { this.sid = v; }
    public void setIpRecordSid(String v) { this.ipRecordSid = v; }
    public void setSipDomainSid(String v) { this.sipDomainSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
}
