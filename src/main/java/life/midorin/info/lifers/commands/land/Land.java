package life.midorin.info.lifers.commands.land;

import life.midorin.info.lifers.menu.menus.land.LandMenu;
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
        LandMenu.INVENTORY.open(p);

        return true;
    }


}
