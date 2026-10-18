package com.voicetel.voiceml.models;

import com.voicetel.voiceml.exceptions.ValidationException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shared {@code Page} / {@code PageSize} / {@code PageToken} query parameters for list endpoints.
 *
 * <p>Most {@code /v1} lists accept only {@code PageSize}; their resources send
 * {@link #toPageSizeQuery()}, which rejects {@code Page} / {@code PageToken} instead of sending keys
 * the endpoint does not declare. Follow {@code meta.next_page_url} with
 * {@code VoicemlClient.nextPage(...)} to move between their pages.
 */
public final class ListPageParams {

    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListPageParams(Builder b) {
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    /** @return {@code Page}, {@code PageSize} and {@code PageToken}, when set */
    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        if (pageToken != null) q.put("PageToken", pageToken);
        return q;
    }

    /**
     * The query for an endpoint that accepts only {@code PageSize}.
     *
     * @return {@code PageSize}, when set
     * @throws ValidationException when {@code Page} or {@code PageToken} is set
     */
    public Map<String, Object> toPageSizeQuery() {
        if (page != null || pageToken != null) {
            throw new ValidationException("this list accepts only PageSize; follow meta.next_page_url "
                    + "with VoicemlClient.nextPage(...) instead of Page/PageToken");
        }
        Map<String, Object> q = new LinkedHashMap<>();
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    /** @return the {@code Page} value, or {@code null} */
    public Integer getPage() {
        return page;
    }

    /** @return the {@code PageSize} value, or {@code null} */
    public Integer getPageSize() {
        return pageSize;
    }

    /** @return the {@code PageToken} value, or {@code null} */
    public String getPageToken() {
        return pageToken;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ListPageParams}. */
    public static final class Builder {
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        /** Create an empty builder; prefer {@link ListPageParams#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v zero-based {@code Page}
         * @return this builder
         */
        public Builder page(Integer v) { this.page = v; return this; }

        /**
         * @param v {@code PageSize} (1-1000)
         * @return this builder
         */
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        /**
         * @param s {@code PageToken} from a previous page's {@code next_page_uri}
         * @return this builder
         */
        public Builder pageToken(String s) { this.pageToken = s; return this; }

        /** @return the parameters */
        public ListPageParams build() {
            return new ListPageParams(this);
        }
    }
}
