package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /Usage/Records} (or time-window) response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsageRecordList extends Page {

    @JsonProperty("usage_records")
    private List<UsageRecord> usageRecords = new ArrayList<>();

    /** @return the {@code usage_records} field */
    public List<UsageRecord> getUsageRecords() {
        return usageRecords;
    }

    /** @param usageRecords the {@code usage_records} field */
    public void setUsageRecords(List<UsageRecord> usageRecords) {
        this.usageRecords = usageRecords;
    }
}
