package com.s1survival.getlit.util.functions;

import com.s1survival.getlit.GetLit;
import com.s1survival.getlit.torch.PlacedTorch;
import com.s1survival.getlit.torch.PlacedTorchResult;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WorldFunctions {
    GetLit plugin;
    public WorldFunctions(GetLit getLit) {
        // Take the instance of our main class and save in plugin
        plugin = getLit;
    }

    /**
     * This function takes a CommandSender, radius, spacing, and upper-height
     * and attempts to place torches
     * @param player CommandSender that issued the command
     * @param radius Radius
     * @param spacing Spacing
     * @param topheight Upper-height
     */
    public PlacedTorchResult placeTorches(Player player, int radius, int spacing, int topheight) {
        List<Location> locations = new ArrayList<>();
        try {
            // Check if player is null
            if (player != null) {
                Location playerLocation = player.getLocation(); // Get the player location (once) - player COULD move before task is complete
                UUID key = UUID.randomUUID();  // A change key for logging purposes
                List<PlacedTorch> torchList = new ArrayList<>();

                for (int y = topheight; y > -63; y--) {
                    int x;
                    for (x = playerLocation.getBlockX() - radius; x < playerLocation.getBlockX() + radius; x += spacing) {
                        int z;
                        for (z = playerLocation.getBlockZ() - radius; z < playerLocation.getBlockZ() + radius; z += spacing) {
                            Block block = Objects.requireNonNull(playerLocation.getWorld()).getBlockAt(x, y, z); // Require a non-null block (which it will be)

                            if (block.getRelative(BlockFace.UP, 1).getState().update(true)) {
                                // plugin.log.toConsole(String.valueOf(block.getRelative(BlockFace.UP, 1).getState().update(true)));
                                if (block.canPlace(Material.TORCH.createBlockData())) {
                                    // plugin.log.toConsole("Torch can be placed on this type: " + block.canPlace(Material.TORCH.createBlockData()),");
                                    if (block.getRelative(BlockFace.UP, 1).getLightFromBlocks() <= 4
                                            && block.getRelative(BlockFace.UP, 1).getLightLevel() <= 4
                                            && block.getRelative(BlockFace.UP, 1).getLightFromSky() == 0) {
                                        // plugin.log.toConsole("::::  LIGHT ::::");
                                        // plugin.log.toConsole("getLightFromBlocks: " + String.valueOf(block.getRelative(BlockFace.UP, 1).getLightFromBlocks()) + " at pos: " + block.getLocation(),");
                                        // plugin.log.toConsole("getLightLevel: " + String.valueOf(block.getRelative(BlockFace.UP, 1).getLightLevel()) + " at pos: " + block.getLocation(),");
                                        // plugin.log.toConsole("getLightFromSky: " + String.valueOf(block.getRelative(BlockFace.UP, 1).getLightFromSky()) + " at pos: " + block.getLocation(),");
                                        // plugin.log.toConsole("::::  LIGHT ::::");
                                        if (block.getType().isSolid()
                                                && block.getType() != Material.POINTED_DRIPSTONE
                                                && block.getRelative(BlockFace.UP, 1).getType().isAir()) {
                                            // plugin.log.toConsole("::::  BLOCKS ::::");
                                            // plugin.log.toConsole("Placing on Type: " + block.getType().name(),");
                                            // plugin.log.toConsole("Relative Block (UP) is AIR: " + block.getRelative(BlockFace.UP, 1).getLocation(),");
                                            // plugin.log.toConsole("::::  BLOCKS ::::");

                                            // Place the torch
                                            block.getRelative(BlockFace.UP, 1).setType(Material.TORCH);
                                            // plugin.log.toConsole("Torch has been placed at pos: " + block.getRelative(BlockFace.UP, 1).getLocation(),");
                                            // plugin.log.toConsole("");
                                            // plugin.log.toConsole("");

                                            // Create a new PlacedTorch class which records our block, person who placed it
                                            // and then save it into the list
                                            locations.add(block.getLocation());
                                            torchList.add(new PlacedTorch(player.getUniqueId(), block));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // Now we're done, add that list to our map in the Data class which we reference from our main instance
                // We'll use the key we created as the key for the map, and we can always undo an individual change now
                // by referencing the key which would give us the list of blocks and people back.
                // This list will clear on restart unless you save it to file
                plugin.data.torches.put(key, torchList);

                PlacedTorchResult result = new PlacedTorchResult(
                        true,
                        torchList.size(),
                        player.getUniqueId(),
                        player.getName(),
                        topheight,
                        radius,
                        spacing,
                        locations
                );
                return result;
            } else {

                return new PlacedTorchResult(false);
            }
        } catch (Exception torch) {
            plugin.log.toConsole("placing torch");
            plugin.log.toConsole(torch.getMessage());
        }
        return new PlacedTorchResult(true);
    }
}
