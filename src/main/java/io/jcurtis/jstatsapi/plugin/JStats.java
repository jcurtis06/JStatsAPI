package io.jcurtis.jstatsapi.plugin;

import io.jcurtis.jstatsapi.api.JStatsAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class JStats extends JavaPlugin {
    private static JStats instance;
    private static JStatsAPI jStatsAPI;

    @Override
    public void onEnable() {
        initializeClasses();
        getServer().getPluginManager().registerEvents(new EventHandlers(), this);

        for (Player p : getServer().getOnlinePlayers()) {
            JStatsAPI.getInstance().getLocalDataManager().loadPlayerData(p.getUniqueId());
        }

        this.getLogger().info("Enabled JStats!");
    }

    @Override
    public void onDisable() {
        for (Player p : getServer().getOnlinePlayers()) {
            JStatsAPI.getInstance().getLocalDataManager().savePlayerData(p.getUniqueId());
        }

        this.getLogger().info("Disabled JStats!");
    }

    /**
     * Gets the plugin instance
     * @return The plugin instance
     * @throws IllegalStateException If the plugin is not loaded
     */
    public static @NotNull JStats getInstance() throws IllegalStateException {
        if (instance == null) {
            throw new IllegalStateException("JStats is not loaded!");
        }

        return instance;
    }

    private void initializeClasses() {
        instance = this;
        jStatsAPI = JStatsAPI.getInstance();
    }
}
