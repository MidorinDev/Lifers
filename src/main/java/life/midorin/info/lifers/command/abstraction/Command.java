package life.midorin.info.lifers.command.abstraction;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.CommandResult;
import life.midorin.info.lifers.command.spec.Argument;
import life.midorin.info.lifers.command.spec.CommandSpec;
import lombok.NonNull;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class Command {

    private final @NonNull CommandSpec spec;

    private final @NonNull String name;

    private final @NonNull String permission;

    private final @NonNull Predicate<Integer> argumentCheck;

    public Command(@NonNull CommandSpec spec, @NonNull String name, @Nullable String permission, @NonNull Predicate<Integer> argumentCheck) {
        this.spec = spec;
        this.name = name;
        this.permission = permission;
        this.argumentCheck = argumentCheck;
    }

    public @NonNull CommandSpec getSpec() {
        return this.spec;
    }

    public @NonNull String getName() {
        return this.name;
    }

    public String getPermission() {
        return permission;
    }

    public @NonNull Predicate<Integer> getArgumentCheck() {
        return this.argumentCheck;
    }

    public String getDescription() {
        return getSpec().description();
    }

    public String getUsage() {
        String usage = getSpec().usage();
        return usage == null ? "" : usage;
    }

    public Optional<List<Argument>> getArgs() {
        return Optional.ofNullable(getSpec().args());
    }

    // Main execution method for the command.
    public abstract CommandResult execute(LifersPlugin plugin, final CommandSender sender, final String[] args, final String label) throws CommandException;

    // タブ補完メソッド - タブ補完を提供しないコマンドがあるため、デフォルトの実装が用意されています。
    public List<String> tabComplete(LifersPlugin plugin, final CommandSender sender, final List<String> args) {
        return Collections.emptyList();
    }

    public abstract void sendUsage(CommandSender sender, String label);

    public abstract void sendDetailedUsage(CommandSender sender, String label);

    public boolean isAuthorized(CommandSender sender) {
        return this.permission == null || (sender.hasPermission(this.permission));
    }

    public boolean shouldDisplay() {
        return true;
    }

}
