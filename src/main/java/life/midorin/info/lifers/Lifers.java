package life.midorin.info.lifers;

import life.midorin.info.lifers.commands.Join;
import life.midorin.info.lifers.commands.Land;
import life.midorin.info.lifers.commands.Leave;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lifers extends JavaPlugin
{
    public static JavaPlugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Items.setup();
        LandGUI.setFream27(LandGUI.main);
        LandGUI.setFream27(LandGUI.setting);
        LandGUI.setFream27(LandGUI.list);
        LandGUI.main.setItem(11, Items.green_wool);
        LandGUI.main.setItem(13, Items.red_wool);
        LandGUI.main.setItem(15, Items.anvil);

        getCommand("join").setExecutor(new Join());
        getCommand("leave").setExecutor(new Leave());
        getCommand("land").setExecutor(new Land());
    }

    @Override
    public void onDisable()
    {
    }

    public static JavaPlugin getPlugin()
    {
        return plugin;
    }
}
