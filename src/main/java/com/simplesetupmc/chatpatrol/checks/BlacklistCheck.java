package com.simplesetupmc.chatpatrol.checks;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.managers.ConfigManager;
import com.simplesetupmc.chatpatrol.managers.LogManager;
import com.simplesetupmc.chatpatrol.managers.PunishmentManager;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BlacklistCheck {

    private final ConfigManager configManager;
    private final LogManager logManager;
    private final PunishmentManager punishmentManager;

    public BlacklistCheck(MainClass plugin) {
        this.configManager = plugin.getConfigManager();
        this.logManager = new LogManager(plugin);
        this.punishmentManager = new PunishmentManager(plugin);
    }

    public boolean handleChat(AsyncPlayerChatEvent event, Player player, String message) {
        for (String word : configManager.getBlacklistedWords()) {
            if (message.contains(word.toLowerCase())) {
                event.setCancelled(true);
                logManager.logPunishment(player, "Blacklisted Word", message);
                punishmentManager.executeCommand(configManager.getBlacklistedWordsCommand(), player);
                return true;
            }
        }
        return false;
    }

    public boolean containsBlacklistedWord(String text) {
        String lowered = text.toLowerCase();

        for (String word : configManager.getBlacklistedWords()) {
            if (lowered.contains(word.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}