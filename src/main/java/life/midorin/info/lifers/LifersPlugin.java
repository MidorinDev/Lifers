package life.midorin.info.lifers;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import jp.jyn.jecon.Jecon;
import life.midorin.info.lifers.commands.*;
import life.midorin.info.lifers.listeners.*;
import life.midorin.info.lifers.manager.DatabaseManager;
import life.midorin.info.lifers.menu.Items;
import life.midorin.info.lifers.menu.LandGUI;
import life.midorin.info.lifers.util.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;

public class LifersPlugin extends AbstractLifersPlugin {

    public static LifersPlugin plugin;
    public static Jecon jecon;
    public static WorldGuardPlugin guard;

    private CustomConfig configuration;

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;

        setup();

        this.configuration = new CustomConfig(this, resolveConfig());

        //リスナーを登録
        registerListeners(
                new BlockBreakListener(),
                new BlockPlaceListener(),
                new BlockPistonListener(),
                new ConnectListener(),
                new PlayerInteract()
        );

        //コマンドを登録
        /*registerCommands(
                new ServerGate(this)
        );*/

        getCommand("join").setExecutor(new Join());
        getCommand("leave").setExecutor(new Leave());
        getCommand("land").setExecutor(new Land());
        getCommand("xyz").setExecutor(new Xyz());
        getCommand("lock").setExecutor(new Lock());
        getCommand("unlock").setExecutor(new unLock());

        Plugin JPlugin = Bukkit.getPluginManager().getPlugin("Jecon");
        Plugin WGPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (JPlugin == null || !JPlugin.isEnabled()) getLogger().warning("Jecon is not available.");
        if (WGPlugin == null || !WGPlugin.isEnabled()) getLogger().warning("WorldGuard is not available.");
        jecon = (Jecon) JPlugin;
        guard = (WorldGuardPlugin) WGPlugin;

        Items.setup();
        LandGUI.setFream27(LandGUI.main);
        LandGUI.setFream27(LandGUI.setting);
        LandGUI.setFream27(LandGUI.list);
        LandGUI.main.setItem(11, Items.green_wool);
        LandGUI.main.setItem(13, Items.red_wool);
        LandGUI.main.setItem(15, Items.anvil);

    }

    @Override
    public void onDisable() {

        super.onDisable();

        DatabaseManager.get().shutdown();

        //設定ファイルをセーブ
        this.configuration.save();

    }

    public void setup() {
        try {
            DatabaseManager.get().setup(false);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static LifersPlugin  getPlugin()
    {
        return plugin;
    }

    public CustomConfig getConfiguration() {
        return configuration;
    }

    public Path getConfigDirectory() {
        return getDataFolder().toPath().toAbsolutePath();
    }

}

