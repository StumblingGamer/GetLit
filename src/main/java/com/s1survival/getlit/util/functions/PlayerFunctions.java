package com.s1survival.getlit.util.functions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerFunctions {
    public static Player getPlayerFromName(String name) {
        Bukkit.getOnlinePlayers();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
    /**
     * Checks if the player has a particular permission
     * @param player Player to check
     * @param permission Permission to check
     * @return If true, player has permission
     */
    public static boolean hasPermission(Player player, String permission) {
        return player.isOp() || player.hasPermission("getlit.admin") || player.hasPermission(permission);
    }
}
