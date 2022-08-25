package com.s1survival.getlit.util.functions;

import org.bukkit.*;

import java.util.HashMap;
import java.util.Map;

public class StringFunctions {
    /**
     * Checks if a string is null or empty
     * @param text String to check
     * @return If true, string is null or empty
     */
    public static boolean isNullOrEmpty(String text) {
        if (text != null) {
            return text.length() == 0;
        }
        return true;
    }

    /**
     * Checks if a string is a number
     * @param text to check
     * @return If true, string is null or empty
     */
    public static boolean isNumeric(String text) {
        for (int i = 0; i < text.length(); i++) {

            try {
                Integer.parseInt(text.substring(i));
            }
            catch (NumberFormatException e) {

                return false;
            }
        }
        return true;
    }

    /**
     * validates that number can be used as integer
     * @param number to check
     * @return If true, string is null or empty
     */
    public static Integer safeToInt(String number) {
        if (isNumeric(number))
        {
            return Integer.parseInt(number);
        }
        return null;
    }

    /**
     * Converts & color codes to color
     * @param input String to colorize
     * @return Colorized string
     */
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Gets a color by name
     * @param name Color to lookup
     * @return Color requested, WHITE if not found
     */
    public static Color colorFromName(String name) {
        Color ret;
        switch (name)
        {
            case "AQUA" :
                ret = Color.AQUA;
                break;
            case "BLACK" :
                ret = Color.BLACK;
                break;
            case "BLUE" :
                ret = Color.BLUE;
                break;
            case "FUCHSIA" :
                ret = Color.FUCHSIA;
                break;
            case "GRAY" :
                ret = Color.GRAY;
                break;
            case "GREEN" :
                ret = Color.GREEN;
                break;
            case "LIME" :
                ret = Color.LIME;
                break;
            case "MAROON" :
                ret = Color.MAROON;
                break;
            case "NAVY" :
                ret = Color.NAVY;
                break;
            case "OLIVE" :
                ret = Color.OLIVE;
                break;
            case "ORANGE" :
                ret = Color.ORANGE;
                break;
            case "PURPLE" :
                ret = Color.PURPLE;
                break;
            case "SILVER" :
                ret = Color.SILVER;
                break;
            case "TEAL" :
                ret = Color.TEAL;
                break;
            case "YELLOW" :
                ret = Color.YELLOW;
                break;
            case "WHITE" :
            default:
                ret = Color.WHITE;
                break;
        }
        return ret;
    }

    /**
     * Gets the arguments into a Map from a string array
     * @param args String array to convert
     * @return Map of Integer and String.  Integer is position, String is argument
     */
    public static Map<Integer, String> getArguments(String[] args) {
        Map<Integer, String> arguments = new HashMap<>();
        int argumentCount = 0;
        if (args.length > 0) // Check to make sure we have some arguments
        {
            for (Object o : args) {
                arguments.put(argumentCount, o.toString());
                argumentCount++;
            }
        }
        return arguments;
    }


}
