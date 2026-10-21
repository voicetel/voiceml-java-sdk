package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Transcriptions}. */
public final class StartTranscriptionRequest {

    private final String name;
    private final String track;
    private final String languageCode;
    private final String transcriptionEngine;
    private final Boolean profanityFilter;
    private final Boolean partialResults;
    private final String hints;
    private final String speechModel;
    private final Boolean enableAutomaticPunctuation;
    private final String intelligenceService;
    private final String statusCallbackUrl;
    private final String statusCallbackMethod;
    private final String statusCallbackEvents;

    private StartTranscriptionRequest(Builder b) {
        this.name = b.name;
        this.track = b.track;
        this.languageCode = b.languageCode;
        this.transcriptionEngine = b.transcriptionEngine;
        this.profanityFilter = b.profanityFilter;
        this.partialResults = b.partialResults;
        this.hints = b.hints;
        this.speechModel = b.speechModel;
        this.enableAutomaticPunctuation = b.enableAutomaticPunctuation;
        this.intelligenceService = b.intelligenceService;
        this.statusCallbackUrl = b.statusCallbackUrl;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.statusCallbackEvents = b.statusCallbackEvents;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (name != null) form.put("Name", name);
        if (track != null) form.put("Track", track);
        if (languageCode != null) form.put("LanguageCode", languageCode);
        if (transcriptionEngine != null) form.put("TranscriptionEngine", transcriptionEngine);
        if (profanityFilter != null) form.put("ProfanityFilter", profanityFilter);
        if (partialResults != null) form.put("PartialResults", partialResults);
        if (hints != null) form.put("Hints", hints);
        if (speechModel != null) form.put("SpeechModel", speechModel);
        if (enableAutomaticPunctuation != null) form.put("EnableAutomaticPunctuation", enableAutomaticPunctuation);
        if (intelligenceService != null) form.put("IntelligenceService", intelligenceService);
        if (statusCallbackUrl != null) form.put("StatusCallbackUrl", statusCallbackUrl);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        if (statusCallbackEvents != null) form.put("StatusCallbackEvents", statusCallbackEvents);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String track;
        private String languageCode;
        private String transcriptionEngine;
        private Boolean profanityFilter;
        private Boolean partialResults;
        private String hints;
        private String speechModel;
        private Boolean enableAutomaticPunctuation;
        private String intelligenceService;
        private String statusCallbackUrl;
        private String statusCallbackMethod;
        private String statusCallbackEvents;

        public Builder name(String s) { this.name = s; return this; }
        public Builder track(String s) { this.track = s; return this; }
        public Builder languageCode(String s) { this.languageCode = s; return this; }
        public Builder transcriptionEngine(String s) { this.transcriptionEngine = s; return this; }
        public Builder profanityFilter(Boolean v) { this.profanityFilter = v; return this; }
        public Builder partialResults(Boolean v) { this.partialResults = v; return this; }
        public Builder hints(String s) { this.hints = s; return this; }
        /** {@code SpeechModel}: engine-specific model hint (e.g. Deepgram's {@code nova-3}) */
        public Builder speechModel(String s) { this.speechModel = s; return this; }
        /** {@code EnableAutomaticPunctuation} */
        public Builder enableAutomaticPunctuation(Boolean v) { this.enableAutomaticPunctuation = v; return this; }
        /** {@code IntelligenceService}: accepted for compatibility; transcription proceeds unchanged */
        public Builder intelligenceService(String s) { this.intelligenceService = s; return this; }
        /** {@code StatusCallbackUrl}: transcription lifecycle webhook */
        public Builder statusCallbackUrl(String s) { this.statusCallbackUrl = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }
        public Builder statusCallbackEvents(String s) { this.statusCallbackEvents = s; return this; }

        public StartTranscriptionRequest build() {
            return new StartTranscriptionRequest(this);
        }
    }
}
