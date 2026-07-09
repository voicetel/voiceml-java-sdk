package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingCountriesList;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A Pricing {@code .../Countries} group: a paginated {@link #list()} plus a per-country
 * {@link #fetch(String)} whose body type varies by product (hence the {@code T} parameter and the
 * {@code Class<T>} passed at construction).
 *
 * @param <T> the product-specific country body returned by {@link #fetch(String)}.
 */
public final class PricingCountriesResource<T> extends BaseResource {

    private final String basePath;
    private final Class<T> model;

    PricingCountriesResource(Transport transport, String basePath, Class<T> model) {
        super(transport);
        this.basePath = basePath;
        this.model = model;
    }

    /** List the countries VoiceML publishes pricing for (NANP-only: exactly the tenant country). */
    public PricingCountriesList list(Integer pageSize) {
        Map<String, Object> q = null;
        if (pageSize != null) {
            q = new LinkedHashMap<>();
            q.put("PageSize", pageSize);
        }
        return decode(transport.request("GET", basePath, q, null), PricingCountriesList.class);
    }

    public PricingCountriesList list() {
        return list(null);
    }

    /** Fetch the pricing body for one ISO country. */
    public T fetch(String isoCountry) {
        return decode(transport.request("GET", basePath + "/" + isoCountry, null, null), model);
    }
}
