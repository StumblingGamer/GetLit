package com.s1survival.getlit.commands;

import com.s1survival.getlit.GetLit;

import com.s1survival.getlit.torch.PlacedTorch;
import com.s1survival.getlit.torch.PlacedTorchResult;

import com.s1survival.getlit.util.Send;
import com.s1survival.getlit.util.functions.WorldFunctions;
import com.s1survival.getlit.util.functions.PlayerFunctions;
import com.s1survival.getlit.util.functions.StringFunctions;

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

        // Check if CommandSender has the command permission
        if (!PlayerFunctions.hasPermission(sentBy, "getlit.getlit")) {
            Send.playerMessage("&cYou do not have permission to run that command", sentBy);
            Send.playerMessage("&cPermission required for command:&5 getlit.getlit", sentBy);
            return true;
        }

        // Convert command args to a data map
        Map<Integer, String> arguments = StringFunctions.getArguments(args);
        // plugin.log.toConsole(String.valueOf(arguments.size()));

        // Find out what type of command they are trying to do
        switch(arguments.size()) {

            // There is only 1 argument in the command
            case 1:
                switch (arguments.get(0).toLowerCase()) {

                    // List command - lists players recent activity (needs work)
                    case "list":
                        // Loop through all the entries in torches
                        for (Map.Entry<UUID, List<PlacedTorch>> entry : plugin.data.torches.entrySet()) {
                            plugin.log.toConsole("Unique key = " + entry.getKey() + " - Number of torches = " + entry.getValue().size(), true);
                            // Loop through all the PlacedTorch records and get the location
                            for (PlacedTorch pt : entry.getValue()) {
                                plugin.log.toConsole("Location = " + pt.getBlock().getLocation(), true);
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

                    // Undo Command - Coming in the future
                    case "surface":
                    case "undo":
                        Send.playerMessage(
                                "This functionality will be coming soon",
                                sentBy
                        );
                        break;

                    // Help - Shows proper plugin usage
                    case "?":
                    case "help":
                        // If the argument passed is ? or help, then display usage
                        Send.pluginUsage(sentBy);
                        break;
                }
                break;

            // There are 4 arguments in the command (ie: /getlit surface 1 2 3)
            case 4:
                switch (arguments.get(0).toLowerCase()) {

                    // Command to light up only blocks on the surface or caves
                    case "surface": case "caves":

                        // Set subcommand string
                        String level = arguments.get(0).toLowerCase();

                        // Check if last 3 args are numeric
                        if (StringFunctions.isNumeric(arguments.get(1))
                                && StringFunctions.isNumeric(arguments.get(2))
                                && StringFunctions.isNumeric(arguments.get(3))) {

                            // Get the radius, spacing, and topheight to variables
                            final int radius = StringFunctions.safeToInt(args[0]);
                            final int spacing = StringFunctions.safeToInt(args[1]);
                            final int topheight = StringFunctions.safeToInt(args[2]);  // May remove this, and just make it world height

                            // Set boundaries for the args
                            if (radius > 128 || spacing < 4 || topheight > WorldFunctions.worldHeight(sentBy)) {
                                Send.pluginUsage(sentBy);
                                return true;
                            }

                            // Create a new instance of WorldFunctions and pass the instance of our main class to it
                            WorldFunctions worldFunctions = new WorldFunctions(plugin);

                            // Call place torch
                            PlacedTorchResult result = worldFunctions.placeTorches(sentBy, level, radius, spacing, topheight);

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
                            Send.pluginUsage(sentBy);
                        }
                        break;
                }
        }
        return true;
    }
}