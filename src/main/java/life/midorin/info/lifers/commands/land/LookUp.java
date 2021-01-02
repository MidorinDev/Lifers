package life.midorin.info.lifers.commands.land;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.BaseCommand;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.AbstractProtectListMenu;
import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.LookUpMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.ChatColor.RED;

public class LookUp extends BaseCommand {

    public LookUp(LifersPlugin plugin) {
        super(plugin, "lookup", "lifers.lookup", true);
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(RED + "プレイヤーを指定しください");
            return;
        }

        //第1引数をプレイヤー名として取得する
        String playerName = args[1];

        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = player.getUniqueId();

        if (player.getFirstPlayed() == 0) {
            sender.sendMessage(RED + playerName + "はデータベースに存在しません。");
            return;
        }
        LookUpMenu.INVENTORY(uuid, (Player) sender).open((Player) sender);
    }


}
