package com.simplesetupmc.chatpatrol.listeners;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.checks.BlacklistCheck;
import com.simplesetupmc.chatpatrol.checks.CapsCheck;
import com.simplesetupmc.chatpatrol.checks.SpamCheck;
import com.simplesetupmc.chatpatrol.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final ConfigManager configManager;
    private final BlacklistCheck blacklistCheck;
    private final SpamCheck spamCheck;
    private final CapsCheck capsCheck;

    public ChatListener(MainClass plugin) {
        this.configManager = plugin.getConfigManager();
        this.blacklistCheck = new BlacklistCheck(plugin);
        this.spamCheck = new SpamCheck(plugin);
        this.capsCheck = new CapsCheck(plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String originalMessage = event.getMessage();
        String loweredMessage = originalMessage.toLowerCase();

        if (configManager.isWordFilterEnabled()) {
            if (blacklistCheck.handleChat(event, player, loweredMessage)) {
                return;
            }
        }

        if (configManager.isSpamFilterEnabled()) {
            if (spamCheck.handleChat(event, player)) {
                return;
            }
        }

        if (configManager.isCapsFilterEnabled()) {
            capsCheck.handleChat(event, player, originalMessage);
        }
    }
}