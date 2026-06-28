package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level {@code /v1/*} surface for Twilio voice.twilio.com/v1 (#420).
 * Sub-resources:
 *
 * <ul>
 *   <li>{@link #ipRecords()} — {@code /v1/IpRecords}.
 *   <li>{@link #sourceIpMappings()} — {@code /v1/SourceIpMappings}.
 *   <li>{@link #byocTrunks()} — {@code /v1/ByocTrunks}.
 *   <li>{@link #connectionPolicies()} — {@code /v1/ConnectionPolicies} + nested Targets.
 *   <li>{@link #settings()} — {@code /v1/Settings} (DialingPermissions).
 * </ul>
 */
public final class VoiceV1Resource {

    private final VoiceV1IpRecordsResource ipRecords;
    private final VoiceV1SourceIpMappingsResource sourceIpMappings;
    private final VoiceV1ByocTrunksResource byocTrunks;
    private final VoiceV1ConnectionPoliciesResource connectionPolicies;
    private final VoiceV1SettingsResource settings;

    public VoiceV1Resource(Transport transport) {
        this.ipRecords = new VoiceV1IpRecordsResource(transport);
        this.sourceIpMappings = new VoiceV1SourceIpMappingsResource(transport);
        this.byocTrunks = new VoiceV1ByocTrunksResource(transport);
        this.connectionPolicies = new VoiceV1ConnectionPoliciesResource(transport);
        this.settings = new VoiceV1SettingsResource(transport);
    }

    public VoiceV1IpRecordsResource ipRecords() { return ipRecords; }
    public VoiceV1SourceIpMappingsResource sourceIpMappings() { return sourceIpMappings; }
    public VoiceV1ByocTrunksResource byocTrunks() { return byocTrunks; }
    public VoiceV1ConnectionPoliciesResource connectionPolicies() { return connectionPolicies; }
    public VoiceV1SettingsResource settings() { return settings; }
}
