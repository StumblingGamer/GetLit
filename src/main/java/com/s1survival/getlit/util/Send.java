package com.s1survival.getlit.util;

import com.s1survival.getlit.util.functions.StringFunctions;

import com.s1survival.getlit.util.functions.WorldFunctions;
import org.bukkit.entity.Player;


public class Send {

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
    /**
     * Sends a message to an online player
     * @param player Player to send message to
     */
    public static void pluginUsage(Player player) {
        if (player == null) {
            return;
        }
        playerMessage("&lLight Caves:", player);
        playerMessage("&5/getlit&r &b<radius>&r, &b<torch spacing>&r, &b<max height>", player);
        playerMessage("Max radius=128,Min torch spacing=4,Max height=" + WorldFunctions.worldHeight(player), player);

        playerMessage("&lLight Surface:", player);
        playerMessage("&5/getlit&r &6surface &b<radius>&r, &b<torch spacing>&r, &b<max height>", player);
        playerMessage("Max radius=128,Min torch spacing=4,Max height=" + WorldFunctions.worldHeight(player), player);

        playerMessage("&lLight Caves:", player);
        playerMessage("&5/getlit&r &6caves &b<radius>&r, &b<torch spacing>&r, &b<max height>", player);
        playerMessage("Max radius=128,Min torch spacing=4,Max height=" + WorldFunctions.worldHeight(player), player);

        playerMessage("&lUndo Changes:", player);
        playerMessage("&5/getlit&r &b&6undo", player);
        playerMessage("This will undo the placement of torches", player);
    }
}
