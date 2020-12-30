package life.midorin.info.lifers.command;

import life.midorin.info.lifers.LifersPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseCommand extends AbstractCommand {

    protected BaseCommand(final LifersPlugin plugin, final String name, final String usage, final String description, final String permission, final int length,
                          final boolean playerOnly, final String... aliases) {
        super(plugin, name, usage, description, permission, length, playerOnly, aliases);
        this.plugin = plugin;
    }

    protected BaseCommand(final LifersPlugin plugin, final String name, final String usage, final String description, final int length, final boolean playerOnly,
                          final String... aliases) {
        this(plugin, name, usage, description, null, length, playerOnly, aliases);
    }

    protected BaseCommand(final LifersPlugin plugin, final String name, final String permission, final boolean playerOnly) {
        this(plugin, name, null, null, permission, -1, playerOnly);
    }

    @Override
    protected void handleMessage(final CommandSender sender, final MessageType type, final String... args) {
        switch (type) {
            case PLAYER_ONLY:
                super.handleMessage(sender, type, args);
                break;
            case NO_PERMISSION:
                //Message.COMMAND_NO_PERMISSION.send((Player) sender);
                break;
            case SUB_COMMAND_INVALID:

                //Message.NO_PERMISSION_FOR_SUBCOMMANDS.send((Player) sender);
                break;
            case SUB_COMMAND_USAGE:
                //Message.MAIN_COMMAND_USAGE_HEADER.send((Player) sender, type.name(), "");
                break;
        }
    }

    protected List<String> handleTabCompletion(final String argument, final Collection<String> collection) {
        return collection.stream()
                .filter(value ->value.toLowerCase().startsWith(argument.toLowerCase()))
                .map(value -> value.replace(" ", "-"))
                .collect(Collectors.toList());
    }

}