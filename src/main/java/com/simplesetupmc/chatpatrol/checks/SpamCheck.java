package com.simplesetupmc.chatpatrol.checks;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.managers.ConfigManager;
import com.simplesetupmc.chatpatrol.managers.LogManager;
import com.simplesetupmc.chatpatrol.managers.PunishmentManager;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpamCheck {

    private final ConfigManager configManager;
    private final LogManager logManager;
    private final PunishmentManager punishmentManager;

    private final Map<UUID, Long> lastMessageTime = new HashMap<>();
    private final Map<UUID, Integer> messageCount = new HashMap<>();

    public SpamCheck(MainClass plugin) {
        this.configManager = plugin.getConfigManager();
        this.logManager = new LogManager(plugin);
        this.punishmentManager = new PunishmentManager(plugin);
    }

    public boolean handleChat(AsyncPlayerChatEvent event, Player player) {
        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        int threshold = configManager.getSpamThreshold();
        int windowMillis = configManager.getSpamTimeWindowSeconds() * 1000;

        if (lastMessageTime.containsKey(uuid) && (currentTime - lastMessageTime.get(uuid)) < windowMillis) {
            messageCount.put(uuid, messageCount.getOrDefault(uuid, 0) + 1);
        } else {
            messageCount.put(uuid, 1);
        }

        lastMessageTime.put(uuid, currentTime);

        if (messageCount.get(uuid) > threshold) {
            event.setCancelled(true);
            logManager.logPunishment(player, "Spam", event.getMessage());
            punishmentManager.executeCommand(configManager.getSpamCommand(), player);
            messageCount.put(uuid, 0);
            return true;
        }

        return false;
    }
}