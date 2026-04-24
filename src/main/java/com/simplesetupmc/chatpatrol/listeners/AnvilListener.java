package com.simplesetupmc.chatpatrol.listeners;

import com.simplesetupmc.chatpatrol.MainClass;
import com.simplesetupmc.chatpatrol.checks.BlacklistCheck;
import com.simplesetupmc.chatpatrol.managers.LogManager;
import com.simplesetupmc.chatpatrol.managers.PunishmentManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilListener implements Listener {

    private final MainClass plugin;
    private final BlacklistCheck blacklistCheck;
    private final LogManager logManager;
    private final PunishmentManager punishmentManager;

    public AnvilListener(MainClass plugin) {
        this.plugin = plugin;
        this.blacklistCheck = new BlacklistCheck(plugin);
        this.logManager = new LogManager(plugin);
        this.punishmentManager = new PunishmentManager(plugin);
    }

    @EventHandler
    public void onAnvilRename(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) return;
        if (event.getSlotType() != InventoryType.SlotType.RESULT) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack result = event.getCurrentItem();
        if (result == null || !result.hasItemMeta() || !result.getItemMeta().hasDisplayName()) return;

        String displayName = result.getItemMeta().getDisplayName();

        if (blacklistCheck.containsBlacklistedWord(displayName)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot name items with inappropriate words.");
            logManager.logPunishment(player, "Anvil Rename", displayName);
            punishmentManager.executeCommand(
                    plugin.getConfigManager().getBlacklistedWordsCommand(),
                    player
            );
        }
    }
}