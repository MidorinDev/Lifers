package life.midorin.info.lifers.commands;

import life.midorin.info.lifers.Lifers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        if (sender.hasPermission("Lifers.leave"))
        {
            for (Player au : Bukkit.getOnlinePlayers())
                au.hidePlayer(Lifers.plugin, p);
            p.setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.RED + "Quit" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName());
        }
        return true;
    }
}
