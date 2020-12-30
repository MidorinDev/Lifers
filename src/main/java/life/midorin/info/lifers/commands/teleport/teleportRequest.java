package life.midorin.info.lifers.commands.teleport;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.BaseCommand;
import life.midorin.info.lifers.menu.menus.user.UsersMenu;
import life.midorin.info.lifers.user.User;
import life.midorin.info.lifers.user.UserSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class teleportRequest extends BaseCommand {

    /**
     * Essentialsをベースに作成
     */

    public teleportRequest(LifersPlugin plugin) {
        super(plugin, "tprequest", "usage", true);
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length < 1) {
            UsersMenu.INVENTORY.open((Player) sender);
            return;
        }
        //TODO　なんのコードを書いてあるのかわかるように見やすくする
        User user = UserSet.getInstnace().getUser((Player) sender);
        User requester = null;

        if(args.length > 1 ) requester = UserSet.getInstnace().getUser(Bukkit.getPlayerExact(args[1]));

        switch (args[0]) {

            case "accept":
                if(requester == null) {
                    sender.sendMessage(RED + "プレイヤーが見つかりません");
                    return;
                }

                if (requester.getTeleportRequest() == null) {
                    sender.sendMessage(RED + "リクエストを受け取っていません");
                    return;
                }
                if (!requester.isOnline()) {
                    sender.sendMessage(RED + "現在オフラインです");
                    return;
                }

                if (!requester.hasOutstandingTeleportRequest()) {
                    requester.requestTeleport(null, false);
                    user.asBukkitPlayer().sendMessage(GOLD + "無効です");
                    return;
                }

                requester.asBukkitPlayer().teleport(requester.getTeleportRequest().asBukkitPlayer());
                requester.requestTeleport(null, false);

                break;
            case "deny":
                if(requester == null) {
                    sender.sendMessage(RED + "プレイヤーが見つかりません");
                    return;
                }
                requester.requestTeleport(null, false);
                user.asBukkitPlayer().sendMessage(GOLD + "要求を拒否しました");
                break;
            case "cancel":
                user.requestTeleport(null, false);
                user.asBukkitPlayer().sendMessage(GOLD + "キャンセルしました");
                break;
            default:
                final User player = UserSet.getInstnace().getUser(Bukkit.getPlayerExact(args[0]));

                if (player == null) {
                    sender.sendMessage(RED + "プレイヤーが見つかりません");
                    return;
                }

                if (sender.getName().equalsIgnoreCase(player.asBukkitPlayer().getName())) {
                    sender.sendMessage(RED +  "自分にリクエストを送ることはできません!");
                    return;
                }

                if(user.getTeleportRequest() != null && user.getTeleportRequest().asBukkitPlayer() == player.asBukkitPlayer() ) {
                        sender.sendMessage(RED + "すでにリクエストを送りました。");
                        return;
                }

                user.asBukkitPlayer().sendMessage(RED + player.asBukkitPlayer().getName() + GOLD + "にリクエストを送信しました");
                user.asBukkitPlayer().sendMessage(GOLD + "キャンセルするには" + RED + "/tprequest cancel" + GOLD + "を実行してください");

                player.asBukkitPlayer().sendMessage(GOLD + "テレポートの要求を許可するには" + RED + "/tprequest accept" + GOLD + "を実行してください");
                player.asBukkitPlayer().sendMessage(GOLD + "テレポートの要求を許可するには" + RED + "/tprequest deny" + GOLD + "を実行してください");
                player.asBukkitPlayer().sendMessage(GOLD + "このテレポートの要求は" + RED + "60" + GOLD + "秒以内に回答してください");

                user.requestTeleport(player, false);
        }
    }
}
