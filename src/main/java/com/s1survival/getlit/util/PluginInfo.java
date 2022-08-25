package com.s1survival.getlit.util;

import com.s1survival.getlit.GetLit;

public class PluginInfo {
    GetLit plugin;
    String internalName;

    public PluginInfo(GetLit plugin) {
        this.plugin = plugin;
        this.internalName = GetLit.internalName;
    }

    /**
     * Gets the plugin internal name
     * @return pluginName
     */
    public static String pluginName() {
        return "GetLit";
    }

    /**
     * Gets the prefix for messages
     * @return Message prefix
     */
    public static String prefix() {
        return "§f[§5GetLit§f]§r ";
    }
}
