package net.downwithdestruction.dwdhelmet.listener;

import net.downwithdestruction.dwdhelmet.Logger;
import net.downwithdestruction.dwdhelmet.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by madmac on 12/13/15.
 *
 */
public class PlayerListener implements Listener {
    private Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        ItemStack customHelmet = player.getInventory().getHelmet();

        if (    /*player.getInventory().getHelmet().equals(Material.DIAMOND_HELMET) ||
                player.getInventory().getHelmet().equals(Material.CHAINMAIL_HELMET) ||
                player.getInventory().getHelmet().equals(Material.GOLD_HELMET) ||
                player.getInventory().getHelmet().equals(Material.IRON_HELMET) ||
                player.getInventory().getHelmet().equals(Material.LEATHER_HELMET) */
                (customHelmet.getType().equals(Material.DIAMOND_HELMET)) ||
                (customHelmet.getType().equals(Material.DIAMOND_HELMET)) ||
                (customHelmet.getType().equals(Material.CHAINMAIL_HELMET)) ||
                (customHelmet.getType().equals(Material.GOLD_HELMET)) ||
                (customHelmet.getType().equals(Material.IRON_HELMET)) ||
                (customHelmet.getType().equals(Material.LEATHER_HELMET)) ){
            Logger.debug(
                    "&6==================================================\n" +
                    "&2PlayerQuitEvent Helmet Removal Canceled! \n" +
                    "&3" + player.getName() + "&2 has a non-custom helmet set. \n" +
                    "&3" + player.getName() + "&2 helmet will not be removed\n" +
                    "&6=================================================="
            );
            return;
        }

        if (!(customHelmet.getType() == null) && !(customHelmet.getType().equals(Material.AIR))){
            if (player.getInventory().firstEmpty() == -1){
                Logger.debug(
                        "&6==================================================\n" +
                        "&3" + player.getName() + "&2 inventory is full! \n" +
                        "&3" + player.getName() + "&2 custom helmet can not be removed! \n" +
                        "&2Purging" + " &3" + player.getName() + "&2 custom helmet!\n" +
                        "&6=================================================="
                );
                player.getInventory().remove(customHelmet);
                return;
            }
            player.getInventory().addItem(customHelmet);
        }
        player.getInventory().setHelmet(null);
    }
}
