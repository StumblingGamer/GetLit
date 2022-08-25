package com.s1survival.getlit.commands;

import com.s1survival.getlit.GetLit;

import com.s1survival.getlit.torch.PlacedTorch;
import com.s1survival.getlit.torch.PlacedTorchResult;

import com.s1survival.getlit.util.Send;
import com.s1survival.getlit.util.functions.WorldFunctions;
import com.s1survival.getlit.util.functions.PlayerFunctions;
import com.s1survival.getlit.util.functions.StringFunctions;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.List;
import java.util.UUID;

public class GetLitCommand implements CommandExecutor {
    GetLit plugin;
    public GetLitCommand(GetLit getLit) {
        plugin = getLit;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        // Check if CommandSender is an online player
        if (!(sender instanceof Player)) {
            plugin.log.toConsole("&cThis command can not be run from the console");
            return true;
        }

        // Create sentBy instance of sender
        Player sentBy = (Player) sender;
        // Get the world that the player currently in
        World world = sentBy.getLocation().getWorld();
        int worldHeight = world.getMaxHeight();

        // Check if CommandSender has the command permission
        if (!PlayerFunctions.hasPermission(sentBy, "getlit.getlit")) {
            Send.playerMessage("&cYou do not have permission to run that command", sentBy);
            Send.playerMessage("&cPermission required for command:&5 getlit.getlit", sentBy);
            return true;
        }

        // Convert command args to a data map
        Map<Integer, String> arguments = StringFunctions.getArguments(args);

        if (arguments.containsKey(0)) {
            switch(arguments.get(0).toLowerCase()) {
                case "list":
                    // Loop through all the entries in torches
                    for (Map.Entry<UUID, List<PlacedTorch>> entry : plugin.data.torches.entrySet()) {
                        plugin.log.toConsole("Unique key = " + entry.getKey() + " - Number of torches = " + entry.getValue().size(),true);
                        // Loop through all the PlacedTorch records and get the location
                        for (PlacedTorch pt : entry.getValue()) {
                            plugin.log.toConsole("Location = " + pt.getBlock().getLocation(),true);
                        }
                    }
                    // // Alternately, if we know the key
                    // UUID key = UUID.fromString("WHATEVER MY KEY IS");
                    // List<PlacedTorch> placedTorches = plugin.data.torches.get(key);
                    // // Loop through all the PlacedTorch records and get the location
                    // for (PlacedTorch pt : placedTorches) {
                    //     OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(pt.getPlayerUUID());
                    //     plugin.log.toConsole("Player = " + offlinePlayer.getName() + " placed a torch at = " + pt.getBlock().getLocation().toString(),true);
                    // }
                    break;
                case "surface": case "undo":
                    Send.playerMessage(
                            "This functionality will be coming soon",
                            sentBy
                    );
                    break;

                case "?": case "help":
                    // If the argument passed is ? or help, then display usage
                    Send.playerMessage("&lLight Caves:",sentBy);
                    Send.playerMessage("&5/getlit&r &b<radius>&r, &b<torch spacing>&r, &b<max height>",sentBy);
                    Send.playerMessage("Max radius=128,Min torch spacing=4,Max height=" + worldHeight,sentBy);
                    Send.playerMessage("&lLight Surface:",sentBy);
                    Send.playerMessage("&5/getlit&r &bsurface &b<radius>&r, &b<torch spacing>&r, &b<max height>",sentBy);
                    Send.playerMessage("Max radius=128,Min torch spacing=4,Max height=" + worldHeight,sentBy);
                    Send.playerMessage("&lUndo Changes:",sentBy);
                    Send.playerMessage("&5/getlit&r &bundo",sentBy);
                    Send.playerMessage("This will undo the placement of torches",sentBy);
                    break;
            }
        } else {
            Send.playerMessage(
                    "&cInvalid command",
                    sentBy
            );
        }

        // There are 4 parameters passed through as args (0,1,2)
        if (arguments.containsKey(2)) {
            // Check if all 3 args are numeric
            if (StringFunctions.isNumeric(arguments.get(0)) && StringFunctions.isNumeric(arguments.get(1)) && StringFunctions.isNumeric(arguments.get(2))) {

                // Get the radius, spacing, and topheight to variables
                final int radius = StringFunctions.safeToInt(args[0]);
                final int spacing = StringFunctions.safeToInt(args[1]);
                final int topheight = StringFunctions.safeToInt(args[2]);

                // Set boundaries for the args
                if (radius > 128 || spacing < 4 || topheight > world.getMaxHeight()) {
                    Send.playerMessage("&cCan't exceed a radius of &r128&c blocks",sentBy);
                    Send.playerMessage("&cMinimum torch spacing has to be at least &r4&c blocks",sentBy);
                    Send.playerMessage("&cTorches can't be placed above the world build limit",sentBy);
                    Send.playerMessage("&cBuild height for this world:&r " + world.getMaxHeight(), sentBy);
                    return true;
                }

                // plugin.log.toConsole("radius: " + radius + " | spacing: " + spacing + " | topheight: " + topheight, true);
                // Send.playerMessage(
                //         "Attempting to light up your world!",
                //         sentBy
                // );

                // Create a new instance of WorldFunctions and pass the instance of our main class to it
                WorldFunctions worldFunctions = new WorldFunctions(plugin);

                // Call place torch
                PlacedTorchResult result = worldFunctions.placeTorches(sentBy, radius, spacing, topheight);

                // Return the number of torches placed to the player
                if (result.is_success()) {
                    Send.playerMessage(
                            "Lit up your world with " + result.get_numTorches() + " new torches!",
                            sentBy
                    );
                } else {
                    Send.playerMessage(
                            "Sad Face - The darkness is closing in around you!",
                            sentBy
                    );
                }
                return true;
            } else {
                // One or all of the arguments were not numbers
                Send.playerMessage(
                        "Well that didn't work...",
                        sentBy
                );
            }
        }
        return true;
    }
}