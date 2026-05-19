package com.voicetel.voiceml.models;

/**
 * Result of fetching {@code GET /Recordings/{sid}.wav}.
 *
 * <p>Three server delivery shapes are flattened into one result by following any 302 redirect:
 * 200 (local file), 302→presigned-S3 URL, 410 (gone — raises {@code GoneException}).
 */
public final class RecordingAudio {

    private final String sid;
    private final byte[] content;
    private final String contentType;
    private final boolean viaRedirect;

    public RecordingAudio(String sid, byte[] content, String contentType, boolean viaRedirect) {
        this.sid = sid;
        this.content = content;
        this.contentType = contentType;
        this.viaRedirect = viaRedirect;
    }

    public String getSid() {
        return sid;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isViaRedirect() {
        return viaRedirect;
    }
}
