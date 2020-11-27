package life.midorin.info.lifers.commands;

import life.midorin.info.lifers.LandGUI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Land implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        p.playSound(p.getLocation(), Sound.ENTITY_MULE_CHEST, 50, 0);
        p.openInventory(LandGUI.main);
        return true;
    }
}
