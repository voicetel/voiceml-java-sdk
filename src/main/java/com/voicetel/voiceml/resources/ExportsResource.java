package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ExportList;

import java.time.LocalDate;

/** {@code /Exports}: daily archives of the account's records. */
public final class ExportsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.exports()}.
     *
     * @param transport the transport
     */
    public ExportsResource(Transport transport) {
        super(transport);
    }

    /** @return {@code GET /Exports}: the archives available for download */
    public ExportList list() {
        return decode(transport.request("GET", accountPath("Exports"), null, null), ExportList.class);
    }

    /**
     * {@code GET /Exports/{Date}}: download one day's archive. The server redirects to a presigned
     * storage URL, which is followed without the {@code Authorization} header (an https-to-http
     * redirect is refused).
     *
     * @param date the archive day, {@code YYYY-MM-DD}
     * @return the archive bytes
     */
    public Transport.BinaryResponse download(String date) {
        return transport.fetchBinary(accountPathWithSuffix("", "Exports", date), "*/*");
    }

    /**
     * @param date the archive day
     * @return the archive bytes; see {@link #download(String)}
     */
    public Transport.BinaryResponse download(LocalDate date) {
        return download(date.toString());
    }
}
