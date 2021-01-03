package life.midorin.info.lifers.commands.home;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.Arguments;
import life.midorin.info.lifers.command.BaseCommand;
import life.midorin.info.lifers.home.Home;
import life.midorin.info.lifers.home.HomeManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.RED;

public class DelHome extends BaseCommand {
    public DelHome(LifersPlugin plugin) {
        super(plugin, "delhome", null, true);
    }

    @Override
    protected void execute(CommandSender sender, String label, Arguments args) {
        final Player player = (Player) sender;

        if(!args.hasNext()){
            sender.sendMessage(RED + "引数が足りません");
            return;
        }

        final String homeName = args.next();

        Home home = HomeManager.get().loadHome(player, homeName).orElse(null);
        if (home == null) {
            sender.sendMessage(RED + homeName + "は存在しません");
            return;
        }

        HomeManager.get().deleteHome(home);
        sender.sendMessage(RED + homeName + "を削除しました");

    }
}
