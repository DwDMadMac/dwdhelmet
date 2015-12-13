package net.downwithdestruction.dwdhelmet.configuration;

import net.downwithdestruction.dwdhelmet.Logger;
import net.downwithdestruction.dwdhelmet.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Lang {
    COMMAND_NO_PERMISSION("&4[&7DwD&4] &4You do not have permission for this command!");

    private final String def;

    private static File configFile;
    private static FileConfiguration config;

    Lang(String def) {
        this.def = def;
        reload();
    }

    public static void reload() {
        reload(false);
    }

    public static void reload(boolean force) {
        if (configFile == null || force) {
            String lang = Config.LANGUAGE_FILE.getString();
            Logger.debug("Loading language file: " + lang);
            configFile = new File(Main.getPlugin(Main.class).getDataFolder(), lang);
            if (!configFile.exists()) {
                Main.getPlugin(Main.class).saveResource(Config.LANGUAGE_FILE.getString(), false);
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private String getKey() {
        return name().toLowerCase().replace("_", "-");
    }

    @Override
    public String toString() {
        String value = config.getString(name());
        if (value == null) {
            value = config.getString(getKey());
        }
        if (value == null) {
            Logger.warn("Missing lang data in file: " + getKey());
            value = def;
        }
        if (value == null) {
            Logger.error("Missing default lang data: " + getKey());
            value = "&c[missing lang data]";
        }
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    public String replace(String find, String replace) {
        return toString().replace(find, replace);
    }
}
