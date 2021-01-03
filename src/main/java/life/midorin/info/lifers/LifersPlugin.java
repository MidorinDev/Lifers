package life.midorin.info.lifers;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import jp.jyn.jecon.Jecon;
import life.midorin.info.lifers.commands.home.HomeCommand;
import life.midorin.info.lifers.commands.home.SetHome;
import life.midorin.info.lifers.commands.home.DelHome;
import life.midorin.info.lifers.commands.land.Land;
import life.midorin.info.lifers.commands.other.Join;
import life.midorin.info.lifers.commands.other.Leave;
import life.midorin.info.lifers.commands.other.Xyz;
import life.midorin.info.lifers.commands.protect.Lock;
import life.midorin.info.lifers.commands.protect.unLock;
import life.midorin.info.lifers.commands.teleport.teleportRequest;
import life.midorin.info.lifers.home.HomeManager;
import life.midorin.info.lifers.listeners.*;
import life.midorin.info.lifers.manager.DatabaseManager;
import life.midorin.info.lifers.menu.inv.InventoryManager;
import life.midorin.info.lifers.user.UserSet;
import life.midorin.info.lifers.util.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;

public class LifersPlugin extends AbstractLifersPlugin {

    public static LifersPlugin plugin;
    public static Jecon jecon;
    public static WorldGuardPlugin guard;

    private CustomConfig configuration;
    private InventoryManager invManager;

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;

        setup();

        this.configuration = new CustomConfig(this, resolveConfig());
        this.invManager = new InventoryManager(this);
        this.invManager.init();

        UserSet.load();
        HomeManager.get().loadAllHomes();

        //リスナーを登録
        registerListeners(
                new BlockBreakListener(),
                new BlockPlaceListener(),
                new BlockPistonListener(),
                new BlockPhysicsListener(),
                new HopperListener(),
                new ConnectListener(),
                new PlayerInteract()
        );

        //registerMainCommands();

        //コマンドを登録
        registerCommands(
                new Land(this),
                new teleportRequest(this),
                new HomeCommand(this),
                new SetHome(this),
                new DelHome(this)
        );

        getCommand("join").setExecutor(new Join());
        getCommand("leave").setExecutor(new Leave());
        getCommand("xyz").setExecutor(new Xyz());
        getCommand("lock").setExecutor(new Lock());
        getCommand("unlock").setExecutor(new unLock());

        Plugin JPlugin = Bukkit.getPluginManager().getPlugin("Jecon");
        Plugin WGPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (JPlugin == null || !JPlugin.isEnabled()) getLogger().warning("Jecon is not available.");
        if (WGPlugin == null || !WGPlugin.isEnabled()) getLogger().warning("WorldGuard is not available.");
        jecon = (Jecon) JPlugin;
        guard = (WorldGuardPlugin) WGPlugin;

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

    public InventoryManager getInvManager() {
        return invManager;
    }

}

