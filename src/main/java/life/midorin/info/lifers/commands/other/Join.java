package life.midorin.info.lifers.commands.other;

import life.midorin.info.lifers.LifersPlugin;
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
                au.showPlayer(LifersPlugin.getPlugin(), p);
            p.setGameMode(GameMode.CREATIVE);
            Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "Join" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName());
        }
        return true;
    }
}
