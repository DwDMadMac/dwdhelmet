package net.downwithdestruction.dwdhelmet.command;

import net.downwithdestruction.dwdhelmet.DwDHelmet;
import net.downwithdestruction.dwdhelmet.configuration.Lang;
import net.downwithdestruction.dwdhelmet.manager.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by madmac on 12/13/15.
 *
 */

public class CmdHelmet implements TabExecutor {
    private DwDHelmet plugin;

    public CmdHelmet(DwDHelmet plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length > 2){
            String name = strings[1];
            list.addAll(Bukkit.getOnlinePlayers().stream()
                .filter(target -> target.getName().toLowerCase().startsWith(name))
                .map(Player::getName).collect(Collectors.toList()));
        }
        return list;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            ChatManager.sendMessage(commandSender, Lang.ERROR_ONLY_PLAYERS_COMMAND);
            return true;
        }

        if (!commandSender.hasPermission("command.helmet")){
            ChatManager.sendMessage(commandSender, Lang.ERROR_COMMAND_NO_PERMISSION);
            return true;
        }

        Player target;
        ItemStack helmet;

        if (strings.length < 2){
            target = (Player) commandSender;
            helmet = target.getInventory().getItemInMainHand();
        } else {
            target = Bukkit.getServer().getPlayer(strings[1]);

            if (target == null){
                ChatManager.sendMessage(commandSender, Lang.ERROR_TARGET_NOT_FOUND);
                return true;
            }

            if (target.hasPermission("command.helmet.exempt")){
                ChatManager.sendMessage(commandSender, Lang.TARGET_HELMET_EXEMPT.replace("{target}", target.getName()));
                return true;
            }

            helmet = ((Player) commandSender).getInventory().getItemInMainHand();
        }

        if (!(commandSender == target) && !(commandSender.hasPermission("command.helmet.others"))){
            ChatManager.sendMessage(commandSender, Lang.ERROR_COMMAND_OTHERS_NO_PERMISSION);
            return true;
        }

        ItemStack oldHelmet = target.getInventory().getHelmet();

        if (strings.length > 0 && (strings[0].equalsIgnoreCase("remove") || strings[0].equalsIgnoreCase("off") || strings[0].equalsIgnoreCase("0"))){
            if (!(oldHelmet == null) && !(oldHelmet.getType().equals(Material.AIR))){
                if (target.getInventory().firstEmpty() == -1){
                    ChatManager.sendMessage(target, Lang.ERROR_TARGET_INVENTORY_FULL);
                    return true;
                }
                target.getInventory().addItem(oldHelmet);
            }
            target.getInventory().setHelmet(helmet);
            ChatManager.sendMessage(commandSender, Lang.HELMET_REMOVED);
            return true;
        } else {
            if (helmet == null || helmet.getType().equals(Material.AIR)){
                ChatManager.sendMessage(commandSender, Lang.ERROR_EMPTY_HAND);
                return true;
            }

            if (helmet.getAmount() == 1){
                target.getInventory().remove(helmet);

                if (!(oldHelmet == null) && !(oldHelmet.getType().equals(Material.AIR))){
                    target.getInventory().addItem(oldHelmet);
                }
                target.getInventory().setHelmet(helmet);
            } else {
                if (!(oldHelmet == null) && !(oldHelmet.getType().equals(Material.AIR))){
                    if (target.getInventory().firstEmpty() == -1){
                        ChatManager.sendMessage(target, Lang.ERROR_TARGET_INVENTORY_FULL);
                        return true;
                    }
                    target.getInventory().addItem(oldHelmet);
                }
                helmet.setAmount(helmet.getAmount() - 1);
                target.getInventory().setItemInMainHand(helmet);
                helmet.setAmount(1);
                target.getInventory().setHelmet(helmet);
            }
            ChatManager.sendMessage(commandSender, Lang.HELMET_SET);
        }
        return true;
    }
}
