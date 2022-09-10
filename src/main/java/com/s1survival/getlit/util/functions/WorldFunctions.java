package com.s1survival.getlit.util.functions;

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

public class WorldFunctions {

    /**
     * Returns the players current world's max build height
     * @param player Instance of player
     * @return Player.getWorld().getMaxHeight()
     */
    public static @NotNull Integer worldHeight(Player player) {
        int wh = 320;
        if (player == null) {
            return wh;
        }

        // Get the world that the player is currently in
        World world = player.getLocation().getWorld();
        wh = world.getMaxHeight();
        return wh;
    }

    /**
     * Gets the arguments into a Map from a string array
     * @param player Instance of player
     * @param sY This is the topheight arg from the initial command
     * @param radius This is the radius arg from the initial command
     * @return Object List of blocks within the defined radius
     */
    public static List<Block> getBlocksInRegion(Player player, Integer radius, Integer sY, Integer spacing) {
        List<Block> blocks = new ArrayList<>();

        Location playerLocation = player.getLocation();
        int sX = playerLocation.getBlockX() - radius;
        int sZ = playerLocation.getBlockZ() - radius;
        int eX = playerLocation.getBlockX() + radius;
        int eZ = playerLocation.getBlockZ() + radius;

        Location start = new Location(player.getWorld(),sX, sY, sZ);
        Location end = new Location(player.getWorld(),eX, -63, eZ);

        int topBlockX = (Math.max(start.getBlockX(), end.getBlockX()));
        int bottomBlockX = (Math.min(start.getBlockX(), end.getBlockX()));
        int topBlockY = (Math.max(start.getBlockY(), end.getBlockY()));
        int bottomBlockY = (Math.min(start.getBlockY(), end.getBlockY()));
        int topBlockZ = (Math.max(start.getBlockZ(), end.getBlockZ()));
        int bottomBlockZ = (Math.min(start.getBlockZ(), end.getBlockZ()));
        for(int x = bottomBlockX; x <= topBlockX; x+= spacing)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z += spacing)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = Objects.requireNonNull(start.getWorld().getBlockAt(x, y, z));
                    // This is where we can apply a blacklist of placeable blocks
                    if (block.getType().isAir()
                            && block.getRelative(BlockFace.UP, 1).getType().isAir()
                            && block.canPlace(Material.TORCH.createBlockData())
                            && block.getRelative(BlockFace.DOWN, 1).getType().isSolid()
                            && block.getLightFromBlocks() <= 4
                            && block.getLightLevel() <= 15
                    ) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }
}


