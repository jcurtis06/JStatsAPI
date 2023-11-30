/*
 * EventHandlers.java
 * Created in a corn field by Jonathan Curtis on 11/29/2023
 *
 * Find me @ https://jcurtis.io or https://github.com/jcurtis06
 */

package io.jcurtis.jstats.plugin;

import io.jcurtis.jstats.api.JStatsAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventHandlers implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        JStatsAPI.getInstance().getLocalDataManager().loadPlayerData(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        JStatsAPI.getInstance().getLocalDataManager().savePlayerData(e.getPlayer().getUniqueId());
    }
}
