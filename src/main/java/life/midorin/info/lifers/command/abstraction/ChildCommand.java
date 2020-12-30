package life.midorin.info.lifers.command.abstraction;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.CommandResult;
import life.midorin.info.lifers.command.spec.CommandSpec;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

public class ChildCommand extends Command {

    public ChildCommand(CommandSpec spec, String name, String permission, Predicate<Integer> argumentCheck) {
        super(spec, name, permission, argumentCheck);
    }

    @Override
    public CommandResult execute(LifersPlugin plugin, CommandSender sende, String[] args, String label) throws CommandException {
        return null;
    }

    @Override
    public void sendUsage(CommandSender sender, String label) {

        /*sender.sendMessage(builder.build())*/
    }

    @Override
    public void sendDetailedUsage(CommandSender sender, String label) {
        /*Message.COMMAND_USAGE_DETAILED_HEADER.send(sender, getName(), getDescription());
        if (getArgs().isPresent()) {
            Message.COMMAND_USAGE_DETAILED_ARGS_HEADER.send(sender);
            for (Argument arg : getArgs().get()) {
                Message.COMMAND_USAGE_DETAILED_ARG.send(sender, arg.asPrettyString(), arg.getDescription());
            }
        }*/
    }
}
