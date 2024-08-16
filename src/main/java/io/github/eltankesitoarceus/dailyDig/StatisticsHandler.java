package io.github.eltankesitoarceus.dailyDig;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.HashMap;
import java.util.Map;

public class StatisticsHandler {

    static Map<String, Integer> getPlayerStat(Statistic s) {
        Map<String, Integer> stats = new HashMap<>();
        Bukkit.getServer().getOnlinePlayers().forEach((n) -> stats.put(n.getName(), n.getStatistic(s)));
        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
            stats.put(p.getName(), p.getStatistic(s));
        }
        return stats;
    }
}
