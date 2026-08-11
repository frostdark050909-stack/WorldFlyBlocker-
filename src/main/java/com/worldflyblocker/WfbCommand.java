package com.worldflyblocker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class WfbCommand implements CommandExecutor, TabCompleter {

    private final WorldFlyBlocker plugin;

    public WfbCommand(WorldFlyBlocker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("worldflyblocker.admin")) {
                sender.sendMessage(ChatColor.RED + "Kamu tidak punya izin untuk melakukan ini.");
                return true;
            }
            plugin.loadSettings();
            sender.sendMessage(ChatColor.GREEN + "Config WorldFlyBlocker berhasil di-reload.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Penggunaan: /wfb reload");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
