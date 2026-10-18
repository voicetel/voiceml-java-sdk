package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** One usage record ({@code /Usage/Records} and its time windows). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsageRecord {

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("as_of")
    private String asOf;

    @JsonProperty("category")
    private String category;

    @JsonProperty("count")
    private String count;

    @JsonProperty("count_unit")
    private String countUnit;

    @JsonProperty("description")
    private String description;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_unit")
    private String priceUnit;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("subresource_uris")
    private SubresourceUris subresourceUris;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("usage")
    private String usage;

    @JsonProperty("usage_unit")
    private String usageUnit;

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code api_version} field */
    public String getApiVersion() {
        return apiVersion;
    }

    /** @param apiVersion the {@code api_version} field */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /** @return the {@code as_of} field */
    public String getAsOf() {
        return asOf;
    }

    /** @param asOf the {@code as_of} field */
    public void setAsOf(String asOf) {
        this.asOf = asOf;
    }

    /** @return the {@code category} field */
    public String getCategory() {
        return category;
    }

    /** @param category the {@code category} field */
    public void setCategory(String category) {
        this.category = category;
    }

    /** @return the {@code count} field */
    public String getCount() {
        return count;
    }

    /** @param count the {@code count} field */
    public void setCount(String count) {
        this.count = count;
    }

    /** @return the {@code count_unit} field */
    public String getCountUnit() {
        return countUnit;
    }

    /** @param countUnit the {@code count_unit} field */
    public void setCountUnit(String countUnit) {
        this.countUnit = countUnit;
    }

    /** @return the {@code description} field */
    public String getDescription() {
        return description;
    }

    /** @param description the {@code description} field */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return the {@code end_date} field */
    public String getEndDate() {
        return endDate;
    }

    /** @param endDate the {@code end_date} field */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /** @return the {@code price} field */
    public String getPrice() {
        return price;
    }

    /** @param price the {@code price} field */
    public void setPrice(String price) {
        this.price = price;
    }

    /** @return the {@code price_unit} field */
    public String getPriceUnit() {
        return priceUnit;
    }

    /** @param priceUnit the {@code price_unit} field */
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    /** @return the {@code start_date} field */
    public String getStartDate() {
        return startDate;
    }

    /** @param startDate the {@code start_date} field */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /** @return the {@code subresource_uris} field */
    public SubresourceUris getSubresourceUris() {
        return subresourceUris;
    }

    /** @param subresourceUris the {@code subresource_uris} field */
    public void setSubresourceUris(SubresourceUris subresourceUris) {
        this.subresourceUris = subresourceUris;
    }

    /** @return the {@code uri} field */
    public String getUri() {
        return uri;
    }

    /** @param uri the {@code uri} field */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /** @return the {@code usage} field */
    public String getUsage() {
        return usage;
    }

    /** @param usage the {@code usage} field */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    /** @return the {@code usage_unit} field */
    public String getUsageUnit() {
        return usageUnit;
    }

    /** @param usageUnit the {@code usage_unit} field */
    public void setUsageUnit(String usageUnit) {
        this.usageUnit = usageUnit;
    }

    /** The {@code SubresourceUris} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubresourceUris {

        @JsonProperty("all_time")
        private String allTime;

        @JsonProperty("daily")
        private String daily;

        @JsonProperty("last_month")
        private String lastMonth;

        @JsonProperty("monthly")
        private String monthly;

        @JsonProperty("this_month")
        private String thisMonth;

        @JsonProperty("today")
        private String today;

        @JsonProperty("yearly")
        private String yearly;

        @JsonProperty("yesterday")
        private String yesterday;

        /** @return the {@code all_time} field */
        public String getAllTime() {
            return allTime;
        }

        /** @param allTime the {@code all_time} field */
        public void setAllTime(String allTime) {
            this.allTime = allTime;
        }

        /** @return the {@code daily} field */
        public String getDaily() {
            return daily;
        }

        /** @param daily the {@code daily} field */
        public void setDaily(String daily) {
            this.daily = daily;
        }

        /** @return the {@code last_month} field */
        public String getLastMonth() {
            return lastMonth;
        }

        /** @param lastMonth the {@code last_month} field */
        public void setLastMonth(String lastMonth) {
            this.lastMonth = lastMonth;
        }

        /** @return the {@code monthly} field */
        public String getMonthly() {
            return monthly;
        }

        /** @param monthly the {@code monthly} field */
        public void setMonthly(String monthly) {
            this.monthly = monthly;
        }

        /** @return the {@code this_month} field */
        public String getThisMonth() {
            return thisMonth;
        }

        /** @param thisMonth the {@code this_month} field */
        public void setThisMonth(String thisMonth) {
            this.thisMonth = thisMonth;
        }

        /** @return the {@code today} field */
        public String getToday() {
            return today;
        }

        /** @param today the {@code today} field */
        public void setToday(String today) {
            this.today = today;
        }

        /** @return the {@code yearly} field */
        public String getYearly() {
            return yearly;
        }

        /** @param yearly the {@code yearly} field */
        public void setYearly(String yearly) {
            this.yearly = yearly;
        }

        /** @return the {@code yesterday} field */
        public String getYesterday() {
            return yesterday;
        }

        /** @param yesterday the {@code yesterday} field */
        public void setYesterday(String yesterday) {
            this.yesterday = yesterday;
        }
    }
}
