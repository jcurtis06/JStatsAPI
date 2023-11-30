/*
 * JStat.java
 * Created in a corn field by Jonathan Curtis on 11/29/2023
 *
 * Find me @ https://jcurtis.io or https://github.com/jcurtis06
 */

package io.jcurtis.jstatsapi.api.stat;

import io.jcurtis.jstatsapi.api.provider.JStatProvider;
import io.jcurtis.jstatsapi.plugin.JStats;

import java.util.HashMap;
import java.util.UUID;

public class JStat<T extends Number> {
    private final HashMap<UUID, Number> values = new HashMap<>();

    private final String displayName;
    private final JStatProvider provider;
    private final String id;

    public T value;

    public JStat(String displayName, JStatProvider provider, String id, T defaultValue) {
        this.displayName = displayName;
        this.provider = provider;
        this.value = defaultValue;
        this.id = provider.getName() + ":" + id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public JStatProvider getProvider() {
        return provider;
    }

    public Number getValueFor(UUID uuid) {
        return values.get(uuid);
    }

    public void setValueFor(UUID uuid, Number value) {
        values.put(uuid, value);
    }

    public void addValueFor(UUID uuid, Number value) {
        if (getValueFor(uuid) == null) {
            setValueFor(uuid, value);
            return;
        }
        values.put(uuid, value.doubleValue() + getValueFor(uuid).doubleValue());
    }

    public void subtractValueFor(UUID uuid, Number value) {
        if (getValueFor(uuid) == null) {
            setValueFor(uuid, value);
            return;
        }
        values.put(uuid, getValueFor(uuid).doubleValue() - value.doubleValue());
    }

    public String getID() {
        return id;
    }

    public void removePlayer(UUID uuid) {
        values.remove(uuid);
        JStats.getInstance().getLogger().info("Removed player " + uuid + " from " + id);
    }
}
