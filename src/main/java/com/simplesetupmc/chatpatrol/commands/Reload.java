package com.simplesetupmc.chatpatrol.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.managers.ConfigManager;

public class Reload implements CommandExecutor {

    private final MainClass plugin;

    public Reload(MainClass plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigManager configManager = plugin.getConfigManager();

        if (args.length == 0) {
            sender.sendMessage(ChatColor.AQUA + "ChatPatrol Plugin is active!");
            sender.sendMessage(ChatColor.GOLD + "Word Filter: "
                    + (configManager.isWordFilterEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
            sender.sendMessage(ChatColor.GOLD + "Spam Filter: "
                    + (configManager.isSpamFilterEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
            sender.sendMessage(ChatColor.GOLD + "Caps Filter: "
                    + (configManager.isCapsFilterEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("chatpatrol.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }

            plugin.reloadChatPatrolConfig();
            sender.sendMessage(ChatColor.GREEN + "ChatPatrol config reloaded!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " [reload]");
        return true;
    }
}