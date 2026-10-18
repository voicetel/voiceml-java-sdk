package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** One webhook request/response pair from {@code GET /Calls/{CallSid}/Events}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallEvent {

    @JsonProperty("request")
    private Request request;

    @JsonProperty("response")
    private Response response;

    /** @return the {@code request} field */
    public Request getRequest() {
        return request;
    }

    /** @param request the {@code request} field */
    public void setRequest(Request request) {
        this.request = request;
    }

    /** @return the {@code response} field */
    public Response getResponse() {
        return response;
    }

    /** @param response the {@code response} field */
    public void setResponse(Response response) {
        this.response = response;
    }

    /** The {@code Request} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Request {

        @JsonProperty("method")
        private String method;

        @JsonProperty("parameters")
        private Map<String, String> parameters;

        @JsonProperty("url")
        private String url;

        /** @return the {@code method} field */
        public String getMethod() {
            return method;
        }

        /** @param method the {@code method} field */
        public void setMethod(String method) {
            this.method = method;
        }

        /** @return the {@code parameters} field */
        public Map<String, String> getParameters() {
            return parameters;
        }

        /** @param parameters the {@code parameters} field */
        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        /** @return the {@code url} field */
        public String getUrl() {
            return url;
        }

        /** @param url the {@code url} field */
        public void setUrl(String url) {
            this.url = url;
        }
    }

    /** The {@code Response} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {

        @JsonProperty("content_type")
        private String contentType;

        @JsonProperty("date_created")
        private String dateCreated;

        @JsonProperty("request_duration")
        private Integer requestDuration;

        @JsonProperty("response_body")
        private String responseBody;

        @JsonProperty("response_code")
        private Integer responseCode;

        /** @return the {@code content_type} field */
        public String getContentType() {
            return contentType;
        }

        /** @param contentType the {@code content_type} field */
        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        /** @return the {@code date_created} field */
        public String getDateCreated() {
            return dateCreated;
        }

        /** @param dateCreated the {@code date_created} field */
        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        /** @return the {@code request_duration} field */
        public Integer getRequestDuration() {
            return requestDuration;
        }

        /** @param requestDuration the {@code request_duration} field */
        public void setRequestDuration(Integer requestDuration) {
            this.requestDuration = requestDuration;
        }

        /** @return the {@code response_body} field */
        public String getResponseBody() {
            return responseBody;
        }

        /** @param responseBody the {@code response_body} field */
        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }

        /** @return the {@code response_code} field */
        public Integer getResponseCode() {
            return responseCode;
        }

        /** @param responseCode the {@code response_code} field */
        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }
    }
}
