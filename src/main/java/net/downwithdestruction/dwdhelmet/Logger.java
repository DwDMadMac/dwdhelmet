package net.downwithdestruction.dwdhelmet;

import net.downwithdestruction.dwdhelmet.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    private static void log(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', "&4[&7" + DwDHelmet.getPlugin(DwDHelmet.class).getName() + "&4]&r " + msg);
        if (!Config.COLOR_LOGS.getBoolean()) {
            msg = ChatColor.stripColor(msg);
        }
        Bukkit.getServer().getConsoleSender().sendMessage(msg);
    }

    public static void debug(String msg) {
        if (Config.DEBUG_MODE.getBoolean()) {
                Logger.log("&7[&eDEBUG&7]&e " + msg);
        }
    }

    public static void warn(String msg) {
        Logger.log("&e[&6WARN&e]&6 " + msg);
    }

    public static void error(String msg) {
        Logger.log("&e[&4ERROR&e]&4 " + msg);
    }

    static void info(String msg) {
        Logger.log("&e[&fINFO&e]&r " + msg);
    }
}
