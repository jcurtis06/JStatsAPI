/*
 * JStatProvider.java
 * Created in a corn field by Jonathan Curtis on 11/29/2023
 *
 * Find me @ https://jcurtis.io or https://github.com/jcurtis06
 */

package io.jcurtis.jstatsapi.api.provider;

import org.bukkit.plugin.Plugin;

/**
 * Used to provide JStatsAPI with information
 * about the plugin that is using it
 */
public class JStatProvider {
    private final String name;

    /**
     * Creates a new JStatProvider
     * @param plugin The plugin that is using JStatsAPI
     * @param isProtected Whether other plugins can modify the statistics provided by this plugin
     */
    public JStatProvider(Plugin plugin, boolean isProtected) {
        this.name = plugin.getName();
    }

    /**
     * @return The name of the plugin that is using JStatsAPI
     */
    public String getName() {
        return name;
    }
}
