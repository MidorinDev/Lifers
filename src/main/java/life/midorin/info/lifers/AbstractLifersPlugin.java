package life.midorin.info.lifers;

import life.midorin.info.lifers.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /*public boolean registerSubCommand(@NonNull final String command, @NonNull final SubCommand subCommand) {
        Objects.requireNonNull(command, "command");
        Objects.requireNonNull(subCommand, "subCommand");

        final AbstractCommand<ServerGatePlugins> result = commands.get(command.toLowerCase());

        if (result == null || result.isChild(subCommand.getName().toLowerCase())) {
            return false;
        }

        result.child(new AbstractCommand<ServerGatePlugins>(ServerGatePlugins.getPlugin(), subCommand) {
            @Override
            protected void execute(final CommandSender sender, final String label, final String[] args) {
                subCommand.execute(sender, label, args);
            }
        });
        return true;
    }*/

    protected void registerListeners(Listener... listeners){
        for(Listener listener : listeners){
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

}

