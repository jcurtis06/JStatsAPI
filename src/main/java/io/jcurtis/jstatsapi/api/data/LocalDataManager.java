/*
 * ConfigManager.java
 * Created in a corn field by Jonathan Curtis on 11/29/2023
 *
 * Find me @ https://jcurtis.io or https://github.com/jcurtis06
 */

package io.jcurtis.jstatsapi.api.data;

import io.jcurtis.jstatsapi.api.JStatsAPI;
import io.jcurtis.jstatsapi.api.provider.JStatProvider;
import io.jcurtis.jstatsapi.api.stat.JStat;
import io.jcurtis.jstatsapi.plugin.JStats;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Handles loading and saving of data locally
 */
public class LocalDataManager {
    private JavaPlugin plugin = JStats.getInstance();

    public LocalDataManager() {
        createPlayersDirectory();
    }

    private void createPlayersDirectory() {
        File playersDir = new File(plugin.getDataFolder(), "players");
        if (!playersDir.exists()) {
            playersDir.mkdirs();
        }
    }

    public void savePlayerData(UUID playerId) {
        HashMap<JStatProvider, ArrayList<JStat<? extends Number>>> stats = JStatsAPI.getInstance().getStats();
        File playerFile = new File(plugin.getDataFolder() + File.separator + "players", playerId + ".yml");
        FileConfiguration playerYaml = new YamlConfiguration();

        for (JStatProvider provider : stats.keySet()) {
            ArrayList<JStat<? extends Number>> providerStats = stats.get(provider);

            for (JStat<? extends Number> stat : providerStats) {
                Number value = stat.getValueFor(playerId);
                if (value != null) {
                    playerYaml.set(stat.getID(), value);
                }
            }
        }

        try {
            playerYaml.save(playerFile);

            for (JStatProvider provider : stats.keySet()) {
                ArrayList<JStat<? extends Number>> providerStats = stats.get(provider);

                for (JStat<? extends Number> stat : providerStats) {
                    stat.removePlayer(playerId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerData(UUID playerId) {
        File playerFile = new File(plugin.getDataFolder() + File.separator + "players", playerId + ".yml");
        if (!playerFile.exists()) {
            return;
        }

        FileConfiguration playerYaml = YamlConfiguration.loadConfiguration(playerFile);
        HashMap<JStatProvider, ArrayList<JStat<? extends Number>>> stats = JStatsAPI.getInstance().getStats();

        for (JStatProvider provider : stats.keySet()) {
            ArrayList<JStat<? extends Number>> providerStats = stats.get(provider);

            for (JStat<? extends Number> stat : providerStats) {
                if (playerYaml.contains(stat.getID())) {
                    Number value = (Number) playerYaml.get(stat.getID());
                    if (value != null) {
                        stat.setValueFor(playerId, value);
                        System.out.println("Loaded " + stat.getID() + " for " + playerId + " with value " + value);
                    }
                }
            }
        }
    }
}
