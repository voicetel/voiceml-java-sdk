package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateSipIpAccessControlListRequest;
import com.voicetel.voiceml.models.CreateSipIpAddressRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.SipIpAccessControlList;
import com.voicetel.voiceml.models.SipIpAccessControlListList;
import com.voicetel.voiceml.models.SipIpAddress;
import com.voicetel.voiceml.models.SipIpAddressList;
import com.voicetel.voiceml.models.UpdateSipIpAccessControlListRequest;
import com.voicetel.voiceml.models.UpdateSipIpAddressRequest;

import java.util.Map;

/** {@code /SIP/IpAccessControlLists} plus per-list /IpAddresses sub-resource. */
public final class SipIpAccessControlListsResource extends BaseResource {

    public SipIpAccessControlListsResource(Transport transport) {
        super(transport);
    }

    // --- /SIP/IpAccessControlLists CRUD ------------------------------------

    public SipIpAccessControlListList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("SIP", "IpAccessControlLists"), q, null),
                SipIpAccessControlListList.class);
    }

    public SipIpAccessControlListList list() { return list(null); }

    public SipIpAccessControlList create(CreateSipIpAccessControlListRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", accountPath("SIP", "IpAccessControlLists"), null, form),
                SipIpAccessControlList.class);
    }

    public SipIpAccessControlList fetch(String aclSid) {
        return decode(transport.request("GET", accountPath("SIP", "IpAccessControlLists", aclSid), null, null),
                SipIpAccessControlList.class);
    }

    public SipIpAccessControlList update(String aclSid, UpdateSipIpAccessControlListRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", accountPath("SIP", "IpAccessControlLists", aclSid), null, form),
                SipIpAccessControlList.class);
    }

    public void delete(String aclSid) {
        transport.request("DELETE", accountPath("SIP", "IpAccessControlLists", aclSid), null, null);
    }

    // --- /SIP/IpAccessControlLists/{Sid}/IpAddresses sub-resource ----------

    public SipIpAddressList listIpAddresses(String aclSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "IpAccessControlLists", aclSid, "IpAddresses"), q, null),
                SipIpAddressList.class);
    }

    public SipIpAddress createIpAddress(String aclSid, CreateSipIpAddressRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "IpAccessControlLists", aclSid, "IpAddresses"), null, form),
                SipIpAddress.class);
    }

    public SipIpAddress fetchIpAddress(String aclSid, String ipAddressSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "IpAccessControlLists", aclSid, "IpAddresses", ipAddressSid), null, null),
                SipIpAddress.class);
    }

    public SipIpAddress updateIpAddress(String aclSid, String ipAddressSid, UpdateSipIpAddressRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "IpAccessControlLists", aclSid, "IpAddresses", ipAddressSid), null, form),
                SipIpAddress.class);
    }

    public void deleteIpAddress(String aclSid, String ipAddressSid) {
        transport.request("DELETE",
                accountPath("SIP", "IpAccessControlLists", aclSid, "IpAddresses", ipAddressSid), null, null);
    }
}
