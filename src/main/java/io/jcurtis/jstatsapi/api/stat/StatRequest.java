/*
 * StatRequest.java
 * Created in a corn field by Jonathan Curtis on 11/29/2023
 *
 * Find me @ https://jcurtis.io or https://github.com/jcurtis06
 */

package io.jcurtis.jstatsapi.api.stat;

import io.jcurtis.jstatsapi.api.provider.JStatProvider;

public class StatRequest {
    private final JStatProvider provider;
    private final String statID;

    public StatRequest(JStatProvider provider, String statID) {
        this.provider = provider;
        this.statID = provider.getName() + ":" + statID;
    }

    public JStatProvider getProvider() {
        return provider;
    }

    public String getStatID() {
        return statID;
    }
}
