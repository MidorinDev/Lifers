package life.midorin.info.lifers;

import life.midorin.info.lifers.command.AbstractCommand;
import life.midorin.info.lifers.command.BukkitCommandExecutor;
import life.midorin.info.lifers.listeners.PlayerJoinListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbstractLifersPlugin extends JavaPlugin implements Listener {

    private final Map<String, AbstractCommand<LifersPlugin>> commands = new HashMap<>();
    private BukkitCommandExecutor commandManager;
    private final ArrayList<PlayerJoinListener> joinListeners = new ArrayList<>();

    @Override
    public void onEnable(){
        for(Player player : getServer().getOnlinePlayers()) {
            PlayerJoinEvent event = new PlayerJoinEvent(player, "");
            for (PlayerJoinListener listener : joinListeners)
                listener.onJoin(event);
        }
    }

    @Override
    public void onDisable(){
        HandlerList.unregisterAll();
    }

    @SafeVarargs
    protected  final void registerCommands(final AbstractCommand<LifersPlugin>... commands) {
        for (final AbstractCommand<LifersPlugin> command : commands) {
            this.commands.put(command.getName().toLowerCase(), command);
            command.register();
        }
    }

    protected void registerMainCommands() {
        PluginCommand command = this.getCommand("lifers");
        if (command == null) {
            getLogger().severe("Unable to register /lifers command with the server");
            return;
        }

        if (false) {
            //this.commandManager = new BukkitAsyncCommandExecutor(this, command);
        } else {
            this.commandManager = new BukkitCommandExecutor(LifersPlugin.getPlugin(), command);
        }

        this.commandManager.register();

    }

    protected File resolveConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.getDataFolder().mkdirs();
            this.saveResource("config.yml", false);
        }
        return configFile;
    }

    protected void registerListeners(Listener... listeners){
        for(Listener listener : listeners){
            getServer().getPluginManager().registerEvents(listener, this);
            if(listener instanceof PlayerJoinListener) joinListeners.add((PlayerJoinListener) listener);
        }
    }

    public BukkitCommandExecutor getCommandManager() {
        return this.commandManager;
    }

}

