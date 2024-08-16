package io.github.eltankesitoarceus.dailyDig;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import java.util.Arrays;
import java.util.Map;

public class Listeners implements Listener {
    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        System.out.println(Arrays.toString(Statistic.values()));
        Map<String, Integer> stats =  StatisticsHandler.getPlayerStat(Statistic.LEAVE_GAME);
        Bukkit.getLogger().info(String.valueOf(stats.size()));
        for (String p : stats.keySet()) {
            Bukkit.getLogger().info(p + " stat:" + stats.get(p));
        }
        Bukkit.getLogger().info("DayliDig Started");
    }
}
