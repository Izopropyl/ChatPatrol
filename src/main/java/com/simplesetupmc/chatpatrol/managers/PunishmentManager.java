package com.simplesetupmc.chatpatrol.managers;

import com.simplesetupmc.chatpatrol.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishmentManager {

    private final MainClass plugin;

    public PunishmentManager(MainClass plugin) {
        this.plugin = plugin;
    }

    public void executeCommand(String command, Player player) {
        if (command == null || command.isBlank()) {
            return;
        }

        String finalCommand = command.replace("{player}", player.getName());

        ScheduleManager.run(plugin, () ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand));
    }
}