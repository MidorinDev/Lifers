package life.midorin.info.lifers.commands.home;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.spec.Arguments;
import life.midorin.info.lifers.command.abstraction.BaseCommand;
import life.midorin.info.lifers.home.HomeManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

public class SetHome extends BaseCommand {

    public SetHome(LifersPlugin plugin) {
        super(plugin, "sethome", null, true);
    }

    @Override
    protected void execute(CommandSender sender, String label, Arguments args) {
        if(!args.hasNext()){
            sender.sendMessage(RED + "引数が足りません");
            return;
        }

        final Player player = (Player) sender;
        final String homeName = args.next();

        if(HomeManager.get().loadHome(player, homeName).isPresent()) {
            sender.sendMessage(RED + "同じ名前での保存はできません");
            return;
        }

        if(HomeManager.get().getPlayerHomeBy(player.getUniqueId()).size() >= 5) {
            sender.sendMessage(RED + "5個以上は設定できません");
            return;
        }

        HomeManager.get().createAndLoadHome(player, player.getLocation(), homeName);

        sender.sendMessage(GREEN + homeName + "を設定しました");
        return;
    }
}
