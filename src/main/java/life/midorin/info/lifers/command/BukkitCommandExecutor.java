package life.midorin.info.lifers.command;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.util.CommandMapUtil;
import lombok.NonNull;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class BukkitCommandExecutor extends CommandManager implements TabExecutor, Listener {
    private static final boolean SELECT_ENTITIES_SUPPORTED;

    static {
        boolean selectEntitiesSupported = false;
        try {
            Server.class.getMethod("selectEntities", CommandSender.class, String.class);
            selectEntitiesSupported = true;
        } catch (NoSuchMethodException e) {
            // ignore
        }
        SELECT_ENTITIES_SUPPORTED = selectEntitiesSupported;
    }

    protected final LifersPlugin plugin;
    protected final PluginCommand command;

    public BukkitCommandExecutor(LifersPlugin plugin, PluginCommand command) {
        super(plugin);
        this.plugin = plugin;
        this.command = command;
    }

    public void register() {
        this.command.setExecutor(this);
        this.command.setTabCompleter(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        //CommandManager wrapped = this.plugin.getSenderFactory().wrap(sender);
        //List<String> arguments = resolveSelectors(sender, ArgumentTokenizer.EXECUTE.tokenizeInput(args));
        executeCommand(sender, label, Arrays.asList(args));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        //CommandManager wrapped = this.plugin.getSenderFactory().wrap(sender);
        //List<String> arguments = resolveSelectors(sender, ArgumentTokenizer.TAB_COMPLETE.tokenizeInput(args));
        return tabCompleteCommand(sender, Arrays.asList(args));
    }

    // Support LP commands prefixed with a '/' from the console.
    @EventHandler(ignoreCancelled = true)
    public void onConsoleCommand(ServerCommandEvent e) {
        if (!(e.getSender() instanceof ConsoleCommandSender)) {
            return;
        }

        String buffer = e.getCommand();
        if (buffer.isEmpty() || buffer.charAt(0) != '/') {
            return;
        }

        buffer = buffer.substring(1);

        String commandLabel;
        int firstSpace = buffer.indexOf(' ');
        if (firstSpace == -1) {
            commandLabel = buffer;
        } else {
            commandLabel = buffer.substring(0, firstSpace);
        }

        Command command = CommandMapUtil.getCommandMap(this.plugin.getServer()).getCommand(commandLabel);
        if (command != this.command) {
            return;
        }

        e.setCommand(buffer);
    }

    private List<String> resolveSelectors(CommandSender sender, List<String> args) {
        if (!SELECT_ENTITIES_SUPPORTED) {
            return args;
        }

        /*if (!this.plugin.getConfiguration().get(ConfigKeys.RESOLVE_COMMAND_SELECTORS)) {
            return args;
        }*/

        for (ListIterator<String> it = args.listIterator(); it.hasNext(); ) {
            String arg = it.next();
            if (arg.isEmpty() || arg.charAt(0) != '@') {
                continue;
            }

            List<Player> matchedPlayers;
            try {
                matchedPlayers = this.plugin.getServer().matchPlayer(sender.getName()).stream()
                        .filter(e -> e instanceof Player)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                //this.plugin.getLogger().warn("Error parsing selector '" + arg + "' for " + sender + " executing " + args, e);
                continue;
            }

            if (matchedPlayers.isEmpty()) {
                continue;
            }

            if (matchedPlayers.size() > 1) {
                //this.plugin.getLogger().warn("Error parsing selector '" + arg + "' for " + sender + " executing " + args +
                 //       ": ambiguous result (more than one player matched) - " + matchedPlayers);
                continue;
            }

            Player player = matchedPlayers.get(0);
            it.set(player.getUniqueId().toString());
        }

        return args;
    }
}
