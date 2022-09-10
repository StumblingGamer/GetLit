package com.s1survival.getlit.commands;

import com.s1survival.getlit.util.functions.WorldFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("getlit")) {
            switch(args.length) {
                case 1:
                    List<String> arg1 = new ArrayList<>();
                    arg1.add("caves");
                    arg1.add("surface");
                    arg1.add("undo");
                    arg1.add("list");
                    arg1.add("help");

                    return arg1;
                case 2:
                    switch(args[0].toLowerCase()) {
                        case "caves":
                            List<String> caves_radius = new ArrayList<>();
                            //caves_radius.add("64");
                            caves_radius.add("128");
                            return caves_radius;
                        case "surface":
                            List<String> surface_radius = new ArrayList<>();
                            //surface_radius.add("16");
                            //surface_radius.add("32");
                            surface_radius.add("64");
                            //surface_radius.add("128");
                            return surface_radius;
                    }
                case 3:
                    if (sender instanceof Player) {
                        // Create sentBy instance of sender
                        Player sentBy = (Player) sender;

                        List<String> topheight = new ArrayList<>();
                        topheight.add(WorldFunctions.worldHeight(sentBy).toString());

                        return topheight;
                    } else {
                        List<String> topheight = new ArrayList<>();
                        topheight.add("64");
                        topheight.add("254");
                        topheight.add("320");

                        return topheight;
                    }
            }
        }

        return null;
    }
}
