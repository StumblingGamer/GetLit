package com.s1survival.getlit;

import com.s1survival.getlit.util.Log;
import com.s1survival.getlit.data.Data;
import com.s1survival.getlit.commands.*;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public final class GetLit extends JavaPlugin {
    public static final String internalName = "GetLit";
    public static GetLit instance;
    public Log log;
    public Data data;

    private void loadCommands() {
        try {
            getCommand("getlit").setExecutor(new GetLitCommand(this));
            getCommand("getlit").setTabCompleter(new TabCompleter());
        } catch (Exception ex) {
            log.toConsole("Unexpected error registering commands");
            log.toConsole(ex.getMessage());
        }
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            return null;
        }

        return CoreProtect;
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

        CoreProtectAPI api = getCoreProtect();
        if (api != null){ // Ensure we have access to the API
            api.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
        }
    }

    @Override
    public void onDisable() {

    }
}



