package life.midorin.info.lifers.commands.land;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.Arguments;
import life.midorin.info.lifers.command.BaseCommand;
import life.midorin.info.lifers.menu.menus.land.LandMenu;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Land extends BaseCommand {

    public Land(LifersPlugin plugin) {
        super(plugin, "land", null, true);
        child(new LookUp(plugin));
    }

    @Override
    protected void execute(CommandSender sender, String label, Arguments args) {
        Player p = (Player) sender;
        p.playSound(p.getLocation(), Sound.ENTITY_MULE_CHEST, 50, 0);
        LandMenu.INVENTORY.open(p);
    }

}
