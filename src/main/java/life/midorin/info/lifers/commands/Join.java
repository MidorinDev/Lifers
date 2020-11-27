package life.midorin.info.lifers.commands;

import life.midorin.info.lifers.Lifers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Join implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        if (sender.hasPermission("Lifers.join"))
        {
            for (Player au : Bukkit.getOnlinePlayers())
                au.showPlayer(Lifers.plugin, p);
            p.setGameMode(GameMode.CREATIVE);
            Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "Join" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName());
        }
        return true;
    }
}
