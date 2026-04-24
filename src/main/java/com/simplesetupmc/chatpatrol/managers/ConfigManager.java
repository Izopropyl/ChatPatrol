package com.simplesetupmc.chatpatrol.managers;

import com.simplesetupmc.chatpatrol.MainClass;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final MainClass plugin;

    public ConfigManager(MainClass plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveDefaultConfig();
        migrateAndUpdateConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        migrateAndUpdateConfig();
    }

    private void migrateAndUpdateConfig() {
        FileConfiguration config = plugin.getConfig();

        // WORD FILTER
        migrateBoolean(config, "enable-word-filter", "ENABLE-WORD-FILTER", true);
        migrateStringList(config, "blacklisted-words", "BLACKLISTED-WORDS", defaultBlacklistedWords());

        // SPAM FILTER
        migrateBoolean(config, "enable-spam-filter", "ENABLE-SPAM-FILTER", true);
        migrateInt(config, "spam-threshold", "SPAM-THRESHOLD", 3);
        migrateInt(config, "spam-time-window", "SPAM-TIME-WINDOW", 10);

        // CAPS FILTER
        migrateBoolean(config, "enable-caps-filter", "ENABLE-CAPS-FILTER", true);
        migrateInt(config, "caps-threshold", "CAPS-THRESHOLD", 3);
        migrateBoolean(config, "caps-notification", "CAPS-NOTIFICATION", true);

        // PUNISHMENTS
        migrateString(config,
                "punishments.blacklisted-words-command",
                "PUNISHMENTS.BLACKLISTED-WORDS-COMMAND",
                "ban {player} You were banned for using inappropriate language!");

        migrateString(config,
                "punishments.spam-command",
                "PUNISHMENTS.SPAM-COMMAND",
                "kick {player} You were kicked for spamming!");

        // Remove old help paths if they existed from older versions
        if (config.contains("enable-help-reply")) {
            config.set("enable-help-reply", null);
        }
        if (config.contains("help-response")) {
            config.set("help-response", null);
        }
        if (config.contains("help-words")) {
            config.set("help-words", null);
        }
        if (config.contains("ENABLE-HELP-REPLY")) {
            config.set("ENABLE-HELP-REPLY", null);
        }
        if (config.contains("HELP-RESPONSE")) {
            config.set("HELP-RESPONSE", null);
        }
        if (config.contains("HELP-WORDS")) {
            config.set("HELP-WORDS", null);
        }

        plugin.saveConfig();
    }

    private void migrateBoolean(FileConfiguration config, String oldPath, String newPath, boolean defaultValue) {
        if (!config.contains(newPath)) {
            if (config.contains(oldPath)) {
                config.set(newPath, config.getBoolean(oldPath));
            } else {
                config.set(newPath, defaultValue);
            }
        }
        if (config.contains(oldPath)) {
            config.set(oldPath, null);
        }
    }

    private void migrateInt(FileConfiguration config, String oldPath, String newPath, int defaultValue) {
        if (!config.contains(newPath)) {
            if (config.contains(oldPath)) {
                config.set(newPath, config.getInt(oldPath));
            } else {
                config.set(newPath, defaultValue);
            }
        }
        if (config.contains(oldPath)) {
            config.set(oldPath, null);
        }
    }

    private void migrateString(FileConfiguration config, String oldPath, String newPath, String defaultValue) {
        if (!config.contains(newPath)) {
            if (config.contains(oldPath)) {
                config.set(newPath, config.getString(oldPath));
            } else {
                config.set(newPath, defaultValue);
            }
        }
        if (config.contains(oldPath)) {
            config.set(oldPath, null);
        }
    }

    private void migrateStringList(FileConfiguration config, String oldPath, String newPath, List<String> defaultValue) {
        if (!config.contains(newPath)) {
            if (config.contains(oldPath)) {
                config.set(newPath, config.getStringList(oldPath));
            } else {
                config.set(newPath, defaultValue);
            }
        }
        if (config.contains(oldPath)) {
            config.set(oldPath, null);
        }
    }

    private List<String> defaultBlacklistedWords() {
        List<String> list = new ArrayList<>();
        list.add("badword1");
        list.add("badword2");
        return list;
    }

    public boolean isWordFilterEnabled() {
        return plugin.getConfig().getBoolean("ENABLE-WORD-FILTER", true);
    }

    public List<String> getBlacklistedWords() {
        return plugin.getConfig().getStringList("BLACKLISTED-WORDS");
    }

    public boolean isSpamFilterEnabled() {
        return plugin.getConfig().getBoolean("ENABLE-SPAM-FILTER", true);
    }

    public int getSpamThreshold() {
        return plugin.getConfig().getInt("SPAM-THRESHOLD", 3);
    }

    public int getSpamTimeWindowSeconds() {
        return plugin.getConfig().getInt("SPAM-TIME-WINDOW", 10);
    }

    public boolean isCapsFilterEnabled() {
        return plugin.getConfig().getBoolean("ENABLE-CAPS-FILTER", true);
    }

    public int getCapsThreshold() {
        return plugin.getConfig().getInt("CAPS-THRESHOLD", 3);
    }

    public boolean isCapsNotificationEnabled() {
        return plugin.getConfig().getBoolean("CAPS-NOTIFICATION", true);
    }

    public String getBlacklistedWordsCommand() {
        return plugin.getConfig().getString(
                "PUNISHMENTS.BLACKLISTED-WORDS-COMMAND",
                "ban {player} You were banned for using inappropriate language!"
        );
    }

    public String getSpamCommand() {
        return plugin.getConfig().getString(
                "PUNISHMENTS.SPAM-COMMAND",
                "kick {player} You were kicked for spamming!"
        );
    }
}