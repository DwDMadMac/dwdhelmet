package net.downwithdestruction.dwdhelmet.configuration;

import net.downwithdestruction.dwdhelmet.Main;

public enum Config {
    COLOR_LOGS(true),
    DEBUG_MODE(false),
    LANGUAGE_FILE("lang-en.yml");

    private final Main plugin;
    private final Object def;

    Config(Object def) {
        this.plugin = Main.getPlugin(Main.class);
        this.def = def;
    }

    private String getKey() {
        return name().toLowerCase().replace("_", "-");
    }

    public String getString() {
        return plugin.getConfig().getString(getKey(), (String) def);
    }

    public boolean getBoolean() {
        return plugin.getConfig().getBoolean(getKey(), (Boolean) def);
    }

    public static void reload() {
        Main.getPlugin(Main.class).reloadConfig();
    }
}
