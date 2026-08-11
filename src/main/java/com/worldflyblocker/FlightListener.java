package com.worldflyblocker;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlightListener implements Listener {

    private static final String BYPASS_PERMISSION = "worldflyblocker.bypass";

    private final WorldFlyBlocker plugin;

    public FlightListener(WorldFlyBlocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (!event.isFlying()) return;
        if (player.hasPermission(BYPASS_PERMISSION)) return;
        if (!plugin.isWorldDisabled(player.getWorld())) return;

        event.setCancelled(true);
        applyRestriction(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        checkAndRestrict(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        checkAndRestrict(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRespawn(PlayerRespawnEvent event) {
        checkAndRestrict(event.getPlayer());
    }

    private void checkAndRestrict(Player player) {
        if (player.hasPermission(BYPASS_PERMISSION)) return;
        if (!plugin.isWorldDisabled(player.getWorld())) return;
        applyRestriction(player);
    }

    private void applyRestriction(Player player) {
        boolean wasFlying = player.isFlying();

        if (wasFlying) {
            player.setFlying(false);
        }

        GameMode mode = player.getGameMode();
        boolean isNaturalFlightMode = mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR;

        if (plugin.isForceDisableAllowFlight() && !isNaturalFlightMode && player.getAllowFlight()) {
            player.setAllowFlight(false);
        }

        if (wasFlying) {
            String msg = ChatColor.translateAlternateColorCodes('&', plugin.getDenyMessage());
            player.sendMessage(msg);
        }
    }
}
