package life.midorin.info.lifers.command.cmds;

import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Xyz implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        Location loc = p.getLocation();
        sender.sendMessage(Messages.PREFIX + ChatColor.GREEN + "現在の座標は以下の通りです。");
        sender.sendMessage(ChatColor.WHITE + "X: " + loc.getX() + ChatColor.GRAY + " | " + ChatColor.WHITE + "Y: " + loc.getY() + ChatColor.GRAY + " | " + ChatColor.WHITE + "Z: " + loc.getZ());
        return true;
    }
}
