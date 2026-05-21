package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Application;
import com.voicetel.voiceml.models.ApplicationBody;
import com.voicetel.voiceml.models.ApplicationList;
import com.voicetel.voiceml.models.ListApplicationsParams;

import java.util.Map;

/** {@code /Applications} resource. */
public final class ApplicationsResource extends BaseResource {

    public ApplicationsResource(Transport transport) {
        super(transport);
    }

    public Application create(ApplicationBody body) {
        return decode(
                transport.request("POST", accountPath("Applications"), null, body.toForm()),
                Application.class);
    }

    public ApplicationList list(ListApplicationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Applications"), q, null),
                ApplicationList.class);
    }

    public ApplicationList list() {
        return list(null);
    }

    public Application get(String applicationSid) {
        return decode(
                transport.request(
                        "GET", accountPath("Applications", applicationSid), null, null),
                Application.class);
    }

    public Application update(String applicationSid, ApplicationBody body) {
        return decode(
                transport.request(
                        "POST", accountPath("Applications", applicationSid), null, body.toForm()),
                Application.class);
    }

    public void delete(String applicationSid) {
        transport.request("DELETE", accountPath("Applications", applicationSid), null, null);
    }
}
