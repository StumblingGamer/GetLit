package com.s1survival.getlit.torch;

import org.bukkit.block.Block;
import java.util.UUID;

public class PlacedTorch {
    private UUID _uuid;
    private Block _block;

    public PlacedTorch(UUID uuid, Block block) {
        this._uuid = uuid;
        this._block = block;
    }

    public UUID getPlayerUUID() {
        return this._uuid;
    }

    public Block getBlock() {
        return this._block;
    }

}
