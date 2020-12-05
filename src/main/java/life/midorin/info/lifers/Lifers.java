package life.midorin.info.lifers;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import jp.jyn.jecon.Jecon;
import life.midorin.info.lifers.commands.*;
import life.midorin.info.lifers.manager.DatabaseManager;
import life.midorin.info.lifers.util.CustomConfig;
import life.midorin.info.lifers.util.Datas;
import life.midorin.info.lifers.util.Utils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public final class Lifers extends JavaPlugin
{
    public static Lifers plugin;
    public static Jecon jecon;
    public static WorldGuardPlugin guard;

    public static HashMap<Player, List<String>> player_data = new HashMap<>();

    @Override
    public void onEnable()
    {
        plugin = this;

        //データベースをセットアップ
        setup();

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);

        Plugin JPlugin = Bukkit.getPluginManager().getPlugin("Jecon");
        Plugin WGPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (JPlugin == null || !JPlugin.isEnabled()) getLogger().warning("Jecon is not available.");
        if (WGPlugin == null || !WGPlugin.isEnabled()) getLogger().warning("WorldGuard is not available.");
        jecon = (Jecon) JPlugin;
        guard = (WorldGuardPlugin) WGPlugin;

        /**
        try { CustomConfig.data.load("plugins/Lifers/data.yml"); }
        catch (IOException | InvalidConfigurationException ioException) { ioException.printStackTrace(); }
        for (String key : CustomConfig.data.getKeys(false))
        {
            Player player = Bukkit.getPlayer(key);
            Lifers.player_data.put(player, new ArrayList<>());
            Lifers.player_data.get(player).add(CustomConfig.data.get(player.getName()).toString());
        }
         **/

        Items.setup();
        LandGUI.setFream27(LandGUI.main);
        LandGUI.setFream27(LandGUI.setting);
        LandGUI.setFream27(LandGUI.list);
        LandGUI.main.setItem(11, Items.green_wool);
        LandGUI.main.setItem(13, Items.red_wool);
        LandGUI.main.setItem(15, Items.anvil);

        CustomConfig.create("data");
        for (Player ap : Bukkit.getOnlinePlayers())
            Datas.setLocker(ap);

        getCommand("join").setExecutor(new Join());
        getCommand("leave").setExecutor(new Leave());
        getCommand("land").setExecutor(new Land());
        getCommand("xyz").setExecutor(new Xyz());
        getCommand("lock").setExecutor(new Lock());
        getCommand("unlock").setExecutor(new unLock());
    }

    @Override
    public void onDisable()
    {
        //データベースをシャットダウン
        shutdown();

        for (Player player : player_data.keySet())
        {
            String name = player.getName();
            CustomConfig.data.set(name, player_data.get(player));
        }
        try { CustomConfig.data.save(plugin.getDataFolder() + "/data.yml"); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void setup() {
        try {
            DatabaseManager.get().setup(false);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void shutdown() {
        DatabaseManager.get().shutdown();
    }

    public static Lifers getPlugin()
    {
        return plugin;
    }
}
