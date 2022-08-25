package com.s1survival.getlit.data;

import com.s1survival.getlit.GetLit;
import com.s1survival.getlit.torch.PlacedTorch;

import java.util.*;

public class Data {
    GetLit plugin;

    public Data(GetLit getLit) {
        plugin = getLit;
    }

    public Map<UUID, List<PlacedTorch>> torches = new HashMap<>();

}
