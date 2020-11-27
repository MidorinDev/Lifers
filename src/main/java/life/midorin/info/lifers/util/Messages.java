package life.midorin.info.lifers.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages
{
    public static final String PREFIX = ChatColor.WHITE + "[" + ChatColor.GREEN + "LifeHelper" + ChatColor.WHITE + "] ";
    public static void sendPermissionError(CommandSender sender)
    {
        sender.sendMessage(PREFIX + ChatColor.RED + "実行する権限がありません。");
    }
    public static void sendMessageToOp(String message)
    {
        for (Player player : Bukkit.getOnlinePlayers())
            if (player.isOp())
                player.sendMessage(message);
    }
}
