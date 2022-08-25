package com.s1survival.getlit.util;

import com.s1survival.getlit.util.functions.StringFunctions;

import org.bukkit.entity.Player;

public class Send {
    // GetLit plugin;
    // String internalName;
    // public Send(GetLit plugin) {
    //     this.plugin = plugin;
    // }
    /**
     * Sends a message to an online player
     * @param message Message to send
     * @param player Player to send message to
     */
    public static void playerMessage(String message, Player player) {
        if (player == null) {
            return;
        }
        if (StringFunctions.isNullOrEmpty(message)) {
            return;
        }
        player.sendMessage(
                StringFunctions.color(
                        PluginInfo.prefix() +
                                message
                )
        );
    }
}
