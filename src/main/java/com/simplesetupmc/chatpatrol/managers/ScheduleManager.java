package com.simplesetupmc.chatpatrol.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ScheduleManager {
    private final static boolean FOLIA;

    static {
        boolean folia;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (ClassNotFoundException e) {
            folia = false;
        }
        FOLIA = folia;
    }



    public static void run(Plugin plugin, Runnable task) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().run(plugin, st -> task.run());
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }


}
