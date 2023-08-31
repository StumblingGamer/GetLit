package com.s1survival.getlit.handlers;

import com.s1survival.getlit.GetLit;
import com.s1survival.getlit.torch.PlacedTorch;
import com.s1survival.getlit.torch.PlacedTorchResult;
import com.s1survival.getlit.util.Send;
import com.s1survival.getlit.util.functions.WorldFunctions;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;
import java.util.*;

public class GetLitCmdHandler {
    GetLit GetLit;
    public GetLitCmdHandler(GetLit getLit) {
        // Take the instance of our main class and save in plugin
        GetLit = getLit;
    }

    public int longToIntCast(long number) {
        return (int) number;
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

                Send.playerMessage("Attempting to light your world, please stand by...", player);

                UUID key = UUID.randomUUID();  // A change key for logging purposes
                List<PlacedTorch> torchList = new ArrayList<>();
                List<Location> locations = new ArrayList<>();

                // Build a list of all blocks in the specified radius
                new BukkitRunnable() {
                    public void run() {
                        if (GetLit.data.spacing == 0) {
                            GetLit.data.torches.put(key, torchList);

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

                            if (level.equalsIgnoreCase("water")){
                                Send.playerMessage("Your world has been lit with " + result.get_numTorches() + " Glowstone Blocks!", player);
                            } else {
                                Send.playerMessage("Your world has been lit with " + result.get_numTorches() + " torches!", player);
                            }


                            super.cancel();

                        } else {

                            //message to player was here

                            // for each eligible block place torch
                            int timestamp = (int) (System.currentTimeMillis() / 1000L);
                            for (Block block : Objects.requireNonNull(WorldFunctions.getBlocksInRegion(player, radius, topheight, GetLit.data.spacing))) {
                                block.getState().update(true);
                                GetLit.data.skyLight = false;

                                switch (level) {
                                    case "surface":
                                        // Assumption = if block gets light from sky, it is on the surface.
                                        if (block.getRelative(BlockFace.UP, 1).getLightFromSky() >= 1
                                                && (!block.getType().equals(Material.WATER)
                                                && !block.getType().equals(Material.SEAGRASS))) {
                                            GetLit.data.skyLight = true;
                                        }
                                        break;
                                    case "caves":
                                        // Assumption = if block gets no light from sky, it is in a cave.
                                        if (block.getRelative(BlockFace.UP, 1).getLightFromSky() == 0
                                                && (!block.getType().equals(Material.WATER)
                                                && !block.getType().equals(Material.SEAGRASS))) {
                                            GetLit.data.skyLight = true;
                                        }
                                        break;
                                    case "water":
                                        // Assumption = if block gets no light from sky, it is in a cave.
                                        if (block.getRelative(BlockFace.UP, 1).getLightFromSky() >= 0
                                                && (block.getType().equals(Material.WATER)
                                                || block.getType().equals(Material.SEAGRASS))) {
                                            GetLit.data.skyLight = true;
                                        }
                                        break;
                                }

                                block.getState().update(true);

                                if (GetLit.data.skyLight && block.getLightFromBlocks() == 0) {
                                    CoreProtectAPI coreProtect = GetLit.getCoreProtect(); //instantiate the API
                                    if (coreProtect != null){ // Ensure we have access to the API

                                        // added to check if block was player placed
                                        boolean isModified = coreProtect.blockLookup(block.getRelative(BlockFace.DOWN, 1), timestamp).size() > 0;
                                        //List<String[]> lookup = coreProtect.blockLookup(block.getRelative(BlockFace.DOWN, 1), 12000);

                                        if (!isModified){
                                            if (level.equalsIgnoreCase("water")){
                                                block.getRelative(BlockFace.DOWN, 1).setType(Material.GLOWSTONE, true);
                                                if (coreProtect.logPlacement("GetLit", block.getRelative(BlockFace.DOWN, 1).getLocation(), block.getType(), block.getData())) {
                                                    block.getState().update(true);
                                                    // record our torch setting activities
                                                    locations.add(block.getLocation());
                                                    torchList.add(new PlacedTorch(player.getUniqueId(), block));
                                                }
                                            } else {
                                                block.setType(Material.TORCH, true);
                                                if (coreProtect.logPlacement("GetLit", block.getLocation(), block.getType(), block.getData())) {
                                                    block.getState().update(true);
                                                    // record our torch setting activities
                                                    locations.add(block.getLocation());
                                                    torchList.add(new PlacedTorch(player.getUniqueId(), block));
                                                }
                                            }
                                        } // If block is not modified   [If]
                                    } // Is API Null?                   [If]
                                } // Should we place block?             [If]
                            }  // For each block                        [for loop]

                            // reduce the spacing of torches by 2 for next iteration
                            if (GetLit.data.spacing <= 2) {
                                GetLit.data.spacing--;
                            } else {
                                GetLit.data.spacing = GetLit.data.spacing - 2;
                            }
                        }
                    }
                }.runTaskTimer(GetLit, 0, 40L); // repeat every half a tick (was 10L) | 20L = 1 tick

                // Now we're done, add that list to our map in the Data class which we reference from our main instance
                // We'll use the key we created as the key for the map, and we can always undo an individual change now
                // by referencing the key which would give us the list of blocks and people back.
                // This list will clear on restart unless you save it to file

                return new PlacedTorchResult(true);
            } else {

                return new PlacedTorchResult(false);
            }
        } catch (Exception torch) {
            GetLit.log.toConsole("placing torch");
            GetLit.log.toConsole(torch.getMessage());
            return new PlacedTorchResult(false);
        }
        // return new PlacedTorchResult(true);
    }
}

