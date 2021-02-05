package life.midorin.info.lifers.commands.teleport;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.spec.Arguments;
import life.midorin.info.lifers.command.abstraction.BaseCommand;
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
    protected void execute(CommandSender sender, String label, Arguments args) {
        if (!args.hasNext()) {
            UsersMenu.INVENTORY.open((Player) sender);
            return;
        }
        final User user = UserSet.getInstnace().getUser((Player) sender);
        final User receiver = UserSet.getInstnace().getUser(Bukkit.getPlayerExact(args.next()));

        switch (args.next()
        ) {
            case "accept":
                user.acceptRequest();
                break;
            case "deny":
                user.denyRequest();
                break;
            case "cancel":
                User player = args.hasNext() ? UserSet.getInstnace().getUser(Bukkit.getPlayerExact(args.next())) : null;
                user.cancelRequest(player);
                break;
            default:
                user.requestTeleport(receiver);
        }
    }
}
