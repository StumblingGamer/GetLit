package com.s1survival.getlit;

import com.s1survival.getlit.util.Log;
import com.s1survival.getlit.data.Data;
import com.s1survival.getlit.commands.*;

import org.bukkit.plugin.java.JavaPlugin;

public final class GetLit extends JavaPlugin {
    public static final String internalName = "GetLit";
    public static GetLit instance;
    public Log log;
    public Data data;

    private void loadCommands() {
        try {
            getCommand("getlit").setExecutor(new GetLitCommand(this));
        } catch (Exception ex) {
            log.toConsole("Unexpected error registering commands");
            log.toConsole(ex.getMessage());
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance=this;
        log = new Log(this);
        data = new Data(this);

        log.toConsole("§5   ______     __  §6__    _ __  ",false);
        log.toConsole("§5  / ____/__  / /_§6/ /   (_) /_ ",false);
        log.toConsole("§5 / / __/ _ \\/ __/§6 /   / / __/ ",false);
        log.toConsole("§5/ /_/ /  __/ /_§6/ /___/ / /_   ",false);
        log.toConsole("§5\\____/\\___/\\__/§6_____/_/\\__/   ",false);
        log.toConsole("§dShed some §llight§r§d on your world",false);
        log.toConsole("empty",false);

        loadCommands();
    }

    @Override
    public void onDisable() {

    }
}



