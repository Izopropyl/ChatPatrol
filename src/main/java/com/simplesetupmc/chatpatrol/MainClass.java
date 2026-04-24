package com.simplesetupmc.chatpatrol;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import com.simplesetupmc.chatpatrol.commands.Reload;
import com.simplesetupmc.chatpatrol.listeners.AnvilListener;
import com.simplesetupmc.chatpatrol.listeners.ChatListener;
import com.simplesetupmc.chatpatrol.listeners.SignListener;
import com.simplesetupmc.chatpatrol.managers.ConfigManager;

public class MainClass extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.load();

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new SignListener(this), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(this), this);

        PluginCommand command = getCommand("chatpatrol");
        if (command != null) {
            command.setExecutor(new Reload(this));
        } else {
            getLogger().warning("Command 'chatpatrol' is not defined in plugin.yml");
        }

        getLogger().info("ChatPatrol enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatPatrol disabled.");
    }

    public void reloadChatPatrolConfig() {
        configManager.reload();
        getLogger().info("ChatPatrol config reloaded.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}