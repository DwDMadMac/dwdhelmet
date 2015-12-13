package net.downwithdestruction.dwdhelmet.manager;

import net.downwithdestruction.dwdhelmet.configuration.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class ChatManager {
    public static void sendMessage(CommandSender sender, Lang message) {
        sendMessage(sender, message.toString());
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (message == null || ChatColor.stripColor(message).equals("")) {
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
