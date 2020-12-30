package life.midorin.info.lifers.command.abstraction;

import life.midorin.info.lifers.command.CommandResult;
import org.bukkit.command.CommandSender;

public abstract class CommandException extends Exception {

    protected abstract CommandResult handle(CommandSender sender);

    public CommandResult handle(CommandSender sender, String label, Command command) {
        return handle(sender);
    }

}