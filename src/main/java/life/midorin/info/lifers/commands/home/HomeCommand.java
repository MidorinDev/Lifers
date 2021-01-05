package life.midorin.info.lifers.commands.home;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.spec.Arguments;
import life.midorin.info.lifers.command.abstraction.BaseCommand;
import life.midorin.info.lifers.home.Home;
import life.midorin.info.lifers.home.HomeManager;
import life.midorin.info.lifers.menu.menus.home.HomeMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class HomeCommand extends BaseCommand {

    public HomeCommand(LifersPlugin plugin) {
        super(plugin, "home", null, true);
    }

    @Override
    protected void execute(CommandSender sender, String label, Arguments args) {

        final Player player = (Player) sender;

        //第1引数が無ければGUIを開く
        if(!args.hasNext()){
            HomeMenu.INVENTORY().open(player);
            return;
        }

        final String homeName = args.next();

        Home home = HomeManager.get().loadHome(player, homeName).orElse(null);
        if (home == null) {
            sender.sendMessage(RED + homeName + "は存在しません");
            return;
        }

        player.teleport(home.getLocation());
        player.sendMessage(GREEN + "テレポートしました");
        return;

    }
}
