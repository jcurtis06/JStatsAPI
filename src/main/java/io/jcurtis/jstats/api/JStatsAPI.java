/*
 * StatManager.java
 * Created in a corn field by Jonathan Curtis on 11/29/2023
 *
 * Find me @ https://jcurtis.io or https://github.com/jcurtis06
 */

package io.jcurtis.jstats.api;

import io.jcurtis.jstats.api.data.LocalDataManager;
import io.jcurtis.jstats.api.provider.JStatProvider;
import io.jcurtis.jstats.api.stat.JStat;
import io.jcurtis.jstats.api.stat.StatRequest;
import io.jcurtis.jstats.plugin.JStats;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public class JStatsAPI {
    private static JStatsAPI instance;

    private final HashMap<JStatProvider, ArrayList<JStat<? extends Number>>> stats = new HashMap<>();
    private final LocalDataManager localDataManager;

    private JStatsAPI() {
        localDataManager = new LocalDataManager();
    }

    public static synchronized JStatsAPI getInstance() {
        if (instance == null) {
            instance = new JStatsAPI();
        }
        return instance;
    }

    public LocalDataManager getLocalDataManager() {
        return localDataManager;
    }

    public void registerStat(JStat<? extends Number> stat) {
        ArrayList<JStat<? extends Number>> providerStats =
                stats.computeIfAbsent(stat.getProvider(), k -> new ArrayList<>());

        if (providerStats.contains(stat)) {
            JStats.getInstance().getLogger().warning("Attempted to register duplicate stat: " + stat.getID());
            return;
        }

        providerStats.add(stat);
        JStats.getInstance().getLogger().info("Registered Stat: " + stat.getID());
    }

    public @Nullable JStat<? extends Number> makeRequest(StatRequest request) {
        ArrayList<JStat<? extends Number>> providerStats = stats.get(request.getProvider());

        if (providerStats == null) {
            return null;
        }

        for (JStat<? extends Number> stat : providerStats) {
            if (stat.getID().equals(request.getStatID())) {
                return stat;
            }
        }

        return null;
    }

    public @NotNull HashMap<JStatProvider, ArrayList<JStat<? extends Number>>> getStats() {
        return stats;
    }
}
