package life.midorin.info.lifers.commands;

import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.menus.LandMenu;
import org.bukkit.ChatColor;
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

        SmartInventory.builder()
                .id("customInventory")
                .provider(new LandMenu())
                .size(3, 9)
                .title(ChatColor.DARK_AQUA + "保護地操作パネル" + ChatColor.WHITE + " [Lands]")
                .closeable(true)
                .build()
        .open(p);

        return true;
    }


}
