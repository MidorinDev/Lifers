package life.midorin.info.lifers.commands.teleport;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.BaseCommand;
import life.midorin.info.lifers.menu.menus.user.UsersMenu;
import life.midorin.info.lifers.user.User;
import life.midorin.info.lifers.user.UserSet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class teleportRequest extends BaseCommand {

    public teleportRequest(LifersPlugin plugin) {
        super(plugin, "tprequest", "usage", true);
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length < 1) {
            UsersMenu.INVENTORY.open((Player) sender);
            return;
        }
        final User user = UserSet.getInstnace().getUser((Player) sender);
        final User receiver = UserSet.getInstnace().getUser(Bukkit.getPlayerExact(args[0]));

        switch (args[0]) {
            case "accept":
                user.acceptRequest();
                break;
            case "deny":
                user.denyRequest();
                break;
            case "cancel":
                User player = args.length > 1 ? UserSet.getInstnace().getUser(Bukkit.getPlayerExact(args[1])) : null;
                user.cancelRequest(player);
                break;
            default:
                user.requestTeleport(receiver);
        }
    }
}
