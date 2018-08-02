package net.downwithdestruction.dwdhelmet;

import net.downwithdestruction.dwdhelmet.command.CmdHelmet;
import net.downwithdestruction.dwdhelmet.configuration.Lang;
import net.downwithdestruction.dwdhelmet.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DwDHelmet extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        Lang.reload();

        getCommand("helmet").setExecutor(new CmdHelmet(this));

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        Logger.info(getName() + " v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        Logger.info(getName() + " disabled.");
    }
}
