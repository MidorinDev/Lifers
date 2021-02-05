package life.midorin.info.lifers;

import life.midorin.info.lifers.command.abstraction.AbstractCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AbstractLifersPlugin extends JavaPlugin implements Listener {

    private final Map<String, AbstractCommand<LifersPlugin>> commands = new HashMap<>();

    @Override
    public void onEnable(){
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
        }
    }

}

