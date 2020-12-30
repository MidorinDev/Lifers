package life.midorin.info.lifers.command.abstraction;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.CommandResult;
import life.midorin.info.lifers.command.spec.Argument;
import life.midorin.info.lifers.command.spec.CommandSpec;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class SingleCommand extends Command {

    public SingleCommand(CommandSpec spec, String name, String permission, Predicate<Integer> argumentCheck) {
        super(spec, name, permission, argumentCheck);
    }

    @Override
    public CommandResult execute(LifersPlugin plugin, CommandSender sender, String[] args, String label) {
        return execute(plugin, sender, args, label);
    }

    @Override
    public void sendUsage(CommandSender sender, String label) {
        /*TextComponent.Builder builder = Component.text()
                .append(Component.text('>', NamedTextColor.DARK_AQUA))
                .append(Component.space())
                .append(Component.text(getName().toLowerCase(), NamedTextColor.GREEN));

        if (getArgs().isPresent()) {
            List<Component> argUsages = getArgs().get().stream()
                    .map(Argument::asPrettyString)
                    .collect(Collectors.toList());

            builder.append(Component.text(" - ", NamedTextColor.DARK_AQUA))
                    .append(Component.join(Component.space(), argUsages))
                    .build();
        }

        sender.sendMessage(builder.build());*/
    }

    @Override
    public void sendDetailedUsage(CommandSender sender, String label) {
        //Message.COMMAND_USAGE_DETAILED_HEADER.send(sender, getName(), getDescription());
        if (getArgs().isPresent()) {
            //Message.COMMAND_USAGE_DETAILED_ARGS_HEADER.send(sender);
            for (Argument arg : getArgs().get()) {
                //Message.COMMAND_USAGE_DETAILED_ARG.send(sender, arg.asPrettyString(), arg.getDescription());
            }
        }
    }
}