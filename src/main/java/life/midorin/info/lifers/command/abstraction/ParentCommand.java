package life.midorin.info.lifers.command.abstraction;

import com.google.common.base.Predicates;
import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.CommandResult;
import life.midorin.info.lifers.command.spec.CommandSpec;
import life.midorin.info.lifers.command.tabcomplete.CompletionSupplier;
import life.midorin.info.lifers.command.tabcomplete.TabCompleter;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParentCommand extends Command {

    /** Child sub commands */
    private final List<Command> children;
    /** The type of parent command */
    private final Type type;

    public ParentCommand(CommandSpec spec, String name, Type type, List<Command> children) {
        super(spec, name, null, Predicates.alwaysFalse());
        this.children = children;
        this.type = type;
    }

    public @NonNull List<Command> getChildren() {
        return this.children;
    }

    @Override
    public CommandResult execute(LifersPlugin plugin, CommandSender sender, String[] args, String label) {
        // check if required argument and/or subcommand is missing
        if (args.length < this.type.minArgs) {
            sendUsage(sender, label);
            return CommandResult.INVALID_ARGS;
        }

        Command sub = getChildren().stream()
                .filter(s -> s.getName().equalsIgnoreCase(args[this.type.cmdIndex]))
                .findFirst()
                .orElse(null);

        if (sub == null) {
            //Message.COMMAND_NOT_RECOGNISED.send(sender);
            return CommandResult.INVALID_ARGS;
        }

        if (!sub.isAuthorized(sender)) {
            //Message.COMMAND_NO_PERMISSION.send(sender);
            return CommandResult.NO_PERMISSION;
        }

        if (sub.getArgumentCheck().test(args.length - this.type.minArgs)) {
            sub.sendDetailedUsage(sender, label);
            return CommandResult.INVALID_ARGS;
        }

        CommandResult result;
        result = sub.execute(plugin, sender, args, label);

        return result;
    }

    @Override
    public List<String> tabComplete(LifersPlugin plugin, CommandSender sender, List<String> args) {
        switch (this.type) {
            case TAKES_ARGUMENT_FOR_TARGET:
                return TabCompleter.create()
                        .at(0, CompletionSupplier.startsWith(() -> getChildren().stream()
                                .filter(s -> s.isAuthorized(sender))
                                .map(s -> s.getName().toLowerCase())
                        ))
                        .from(1, partial -> getChildren().stream()
                                .filter(s -> s.isAuthorized(sender))
                                .filter(s -> s.getName().equalsIgnoreCase(args.get(0)))
                                .findFirst()
                                .map(cmd -> cmd.tabComplete(plugin, sender, args.subList(2, args.size())))
                                .orElse(Collections.emptyList())
                        )
                        .complete(args);
            case NO_TARGET_ARGUMENT:
                return TabCompleter.create()
                        .at(0, CompletionSupplier.startsWith(() -> getChildren().stream()
                                .filter(s -> s.isAuthorized(sender))
                                .map(s -> s.getName().toLowerCase())
                        ))
                        .from(1, partial -> getChildren().stream()
                                .filter(s -> s.isAuthorized(sender))
                                .filter(s -> s.getName().equalsIgnoreCase(args.get(0)))
                                .findFirst()
                                .map(cmd -> cmd.tabComplete(plugin, sender, args.subList(1, args.size())))
                                .orElse(Collections.emptyList())
                        )
                        .complete(args);
            default:
                throw new AssertionError(this.type);
        }
    }

    @Override
    public void sendUsage(CommandSender sender, String label) {
        List<Command> subs = getChildren().stream()
                .filter(s -> s.isAuthorized(sender))
                .collect(Collectors.toList());

        if (!subs.isEmpty()) {
            //Message.MAIN_COMMAND_USAGE_HEADER.send(sender, getName(), String.format(getUsage(), label));
            for (Command s : subs) {
                s.sendUsage(sender, label);
            }
        } else {
            //Message.COMMAND_NO_PERMISSION.send(sender);
        }
    }

    @Override
    public void sendDetailedUsage(CommandSender sender, String label) {
        sendUsage(sender, label);
    }

    @Override
    public boolean isAuthorized(CommandSender sender) {
        return getChildren().stream().anyMatch(sc -> sc.isAuthorized(sender));
    }

    public enum Type {
        // e.g. /lp log sub-command....
        NO_TARGET_ARGUMENT(0),
        // e.g. /lp user <USER> sub-command....
        TAKES_ARGUMENT_FOR_TARGET(1);

        private final int cmdIndex;
        private final int minArgs;

        Type(int cmdIndex) {
            this.cmdIndex = cmdIndex;
            this.minArgs = cmdIndex + 1;
        }
    }

}
