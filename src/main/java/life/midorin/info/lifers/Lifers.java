package life.midorin.info.lifers;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import jp.jyn.jecon.Jecon;
import life.midorin.info.lifers.commands.*;
import life.midorin.info.lifers.util.CustomConfig;
import life.midorin.info.lifers.util.Datas;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class Lifers extends JavaPlugin
{
    public static JavaPlugin plugin;
    public static Jecon jecon;
    public static WorldGuardPlugin guard;

    public static HashMap<Player, List<String>> player_data = new HashMap<>();

    @Override
    public void onEnable()
    {
        plugin = this;
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
        for (Player player : player_data.keySet())
        {
            String name = player.getName();
            CustomConfig.data.set(name, player_data.get(player));
        }
        try { CustomConfig.data.save(plugin.getDataFolder() + "/data.yml"); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static JavaPlugin getPlugin()
    {
        return plugin;
    }
}
