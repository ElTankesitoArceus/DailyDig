package io.github.eltankesitoarceus.dailyDig;

import io.github.eltankesitoarceus.dailyDig.web.RouteHandler;
import io.undertow.Undertow;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class DailyDig extends JavaPlugin implements Listener {

    public static final boolean isPapiFound = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

    private static Undertow webServer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        // Plugin startup logic
        Bukkit.getLogger().info("Starting DayliDig");
        if (isPapiFound) {
            Bukkit.getLogger().info("Found PlaceholderAPI!.");
            Bukkit.getPluginManager().registerEvents(this, this);
            Bukkit.getLogger().info(PlaceholderAPI.getRegisteredIdentifiers().toString());
        } else {
            Bukkit.getLogger().info("Could not find PlaceholderAPI! This plugin is optional.");
        }
        Bukkit.getLogger().info("Starting web server on port " + config.getInt("server_port"));
        webServer = Undertow.builder().addHttpListener(config.getInt("server_port"), config.getString("server_host")).setHandler(RouteHandler.createHandler()).build();
        webServer.start();
        Bukkit.getLogger().info(STR."Server available at http://\{config.getString("server_host")}:\{config.getInt("server_port")}");
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        webServer.stop();
        Bukkit.getLogger().info("stopping DayliDig");
    }

//    public static Undertow getWebServer() {
//        return webServer;
//    }
//
//    public static void setWebServer(Undertow webServer) {
//        if (DailyDig.webServer != null) {
//            DailyDig.webServer.stop();
//        }
//        DailyDig.webServer = webServer;
//    }
}
