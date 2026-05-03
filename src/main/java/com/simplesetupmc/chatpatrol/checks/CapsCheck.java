package com.simplesetupmc.chatpatrol.checks;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.managers.ConfigManager;
import com.simplesetupmc.chatpatrol.managers.ScheduleManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CapsCheck {

    private final MainClass plugin;
    private final ConfigManager configManager;

    public CapsCheck(MainClass plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public void handleChat(AsyncPlayerChatEvent event, Player player, String message) {
        int maxCapsAllowed = configManager.getCapsThreshold();
        long capsCount = message.chars().filter(Character::isUpperCase).count();

        if (capsCount > maxCapsAllowed) {
            event.setMessage(message.toLowerCase());

            if (configManager.isCapsNotificationEnabled()) {
                ScheduleManager.run(plugin, () -> player.sendMessage(ChatColor.YELLOW + "Your message contained too many capital letters and was modified."));
            }
        }
    }
}