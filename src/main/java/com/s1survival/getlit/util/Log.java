package com.s1survival.getlit.util;

import com.s1survival.getlit.GetLit;
import com.s1survival.getlit.util.functions.StringFunctions;
import org.bukkit.command.ConsoleCommandSender;

public class Log {
    GetLit plugin;
    public Log(GetLit plugin) {
        this.plugin = plugin;
    }

    /**
     * Quick console output with a flag for prefix
     * @param message Message to output
     * @param prefix boolean value to include prefix
     */
    public void toConsole(String message,boolean prefix) {
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();

        if (StringFunctions.isNullOrEmpty(message)) {
            return;
        }
        if (message == "empty"){
            message = "";
        }
        if (prefix) {
            message = PluginInfo.prefix() + message;
        }
        ccs.sendMessage(StringFunctions.color(message));
    }
    /**
     * Quick console output with a prefix by default
     * @param message Message to output
     */
    public void toConsole(String message) {
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();

        if (StringFunctions.isNullOrEmpty(message)) {
            return;
        }
        if (message == "empty"){
            message = "";
        }
        message = PluginInfo.prefix() + message;
        ccs.sendMessage(StringFunctions.color(message));
    }
}
