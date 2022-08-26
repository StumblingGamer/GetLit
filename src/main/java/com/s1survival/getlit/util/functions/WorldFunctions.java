package com.s1survival.getlit.util.functions;

import com.s1survival.getlit.GetLit;
import com.s1survival.getlit.torch.PlacedTorch;
import com.s1survival.getlit.torch.PlacedTorchResult;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WorldFunctions {
    GetLit plugin;
    boolean skyLight;
    public WorldFunctions(GetLit getLit) {
        // Take the instance of our main class and save in plugin
        plugin = getLit;
    }

    public static @NotNull Integer worldHeight(Player player) {
        int wh = 320;
        if (player == null) {
            return wh;
        }

        // Get the world that the player is currently in
        World world = player.getLocation().getWorld();
        int worldHeight = world.getMaxHeight();
        wh = world.getMaxHeight();
        return wh;
    }

    /**
     * This function takes a CommandSender, radius, spacing, and upper-height
     * and attempts to place torches
     * @param player CommandSender that issued the command
     * @param radius Radius
     * @param spacing Spacing
     * @param topheight Upper-height
     */
    public PlacedTorchResult placeTorches(Player player, String level, int radius, int spacing, int topheight) {

        try {
            // Check if player is null
            if (player != null) {
                Location playerLocation = player.getLocation(); // Get the player location (once) - player COULD move before task is complete
                UUID key = UUID.randomUUID();  // A change key for logging purposes
                List<PlacedTorch> torchList = new ArrayList<>();
                List<Location> locations = new ArrayList<>();
                for (int y = topheight; y > -63; y--) {
                    int x;
                    for (x = playerLocation.getBlockX() - radius; x < playerLocation.getBlockX() + radius; x += spacing) {
                        int z;
                        for (z = playerLocation.getBlockZ() - radius; z < playerLocation.getBlockZ() + radius; z += spacing) {
                            Block block = Objects.requireNonNull(playerLocation.getWorld()).getBlockAt(x, y, z); // Require a non-null block (which it will be)
                            skyLight = false;

                            if (block.canPlace(Material.TORCH.createBlockData())
                                    && block.getType().isSolid()
                                    && block.getRelative(BlockFace.UP, 1).getType().isAir()
                                    && block.getType() != Material.POINTED_DRIPSTONE
                                    && block.getRelative(BlockFace.UP, 1).getLightFromBlocks() <= 4
                                    && block.getRelative(BlockFace.UP, 1).getLightLevel() <= 4
                            ) {

                                switch(level) {
                                    case "surface":
                                        // Assumption = if block gets light from sky, it is on the surface.
                                        if (block.getRelative(BlockFace.UP, 1).getLightFromSky() >= 1) {
                                            skyLight = true;
                                        }
                                        break;
                                    case "caves":
                                        // Assumption = if block gets no light from sky, it is not in a cave.
                                        if (block.getRelative(BlockFace.UP, 1).getLightFromSky() == 0) {
                                            skyLight = true;
                                        }
                                        break;
                                }

                                if (skyLight) {
                                    block.getRelative(BlockFace.UP, 1).setType(Material.TORCH);

                                    // record our torch setting activities
                                    locations.add(block.getLocation());
                                    torchList.add(new PlacedTorch(player.getUniqueId(), block));
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
                        level,
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


