package life.midorin.info.lifers.util;

import life.midorin.info.lifers.Lifers;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Datas
{
    public static void setLocker(Player player)
    {
        if (!Lifers.player_data.containsKey(player)) Lifers.player_data.put(player, new ArrayList<>());
        try { CustomConfig.data.load("plugins/Lifers/data.yml"); }
        catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
        if (CustomConfig.data.get(player.getName()) == null) return;
        Lifers.player_data.get(player).add(CustomConfig.data.get(player.getName()).toString().replace("[", "").replace("]", ""));
        System.out.println(Messages.PREFIX + ChatColor.GOLD + player.getName() + " のデータを読み込みました。");
        System.out.println(CustomConfig.data.get(player.getName()).toString());
        System.out.println(CustomConfig.data.get(player.getName()).toString().replace("[", "").replace("]", ""));
    }
}
