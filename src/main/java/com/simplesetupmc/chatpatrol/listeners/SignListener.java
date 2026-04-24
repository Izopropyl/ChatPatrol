package com.simplesetupmc.chatpatrol.listeners;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.checks.BlacklistCheck;
import com.simplesetupmc.chatpatrol.managers.LogManager;
import com.simplesetupmc.chatpatrol.managers.PunishmentManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    private final MainClass plugin;
    private final BlacklistCheck blacklistCheck;
    private final LogManager logManager;
    private final PunishmentManager punishmentManager;

    public SignListener(MainClass plugin) {
        this.plugin = plugin;
        this.blacklistCheck = new BlacklistCheck(plugin);
        this.logManager = new LogManager(plugin);
        this.punishmentManager = new PunishmentManager(plugin);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        for (String line : event.getLines()) {
            if (line != null && blacklistCheck.containsBlacklistedWord(line)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Your sign contains inappropriate language and was not placed.");
                logManager.logPunishment(player, "Sign Text", line);
                punishmentManager.executeCommand(
                        plugin.getConfigManager().getBlacklistedWordsCommand(),
                        player
                );
                return;
            }
        }
    }
}