package com.worldflyblocker;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public final class WorldFlyBlocker extends JavaPlugin {

    private List<String> disabledWorlds;
    private String denyMessage;
    private boolean forceDisableAllowFlight;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSettings();

        getServer().getPluginManager().registerEvents(new FlightListener(this), this);

        WfbCommand executor = new WfbCommand(this);
        getCommand("wfb").setExecutor(executor);
        getCommand("wfb").setTabCompleter(executor);

        getLogger().info("WorldFlyBlocker aktif. World yang di-blok: " + disabledWorlds);
    }

    public void loadSettings() {
        reloadConfig();
        disabledWorlds = getConfig().getStringList("disabled-worlds")
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        denyMessage = getConfig().getString("deny-message", "&cFly dinonaktifkan di world ini!");
        forceDisableAllowFlight = getConfig().getBoolean("force-disable-allow-flight", true);
    }

    public boolean isWorldDisabled(World world) {
        if (world == null) return false;
        return disabledWorlds.contains(world.getName().toLowerCase());
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    public boolean isForceDisableAllowFlight() {
        return forceDisableAllowFlight;
    }
}
