package life.midorin.info.lifers.command;

import com.google.common.collect.ImmutableList;
import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.command.abstraction.Command;
import life.midorin.info.lifers.command.tabcomplete.CompletionSupplier;
import life.midorin.info.lifers.command.tabcomplete.TabCompleter;
import life.midorin.info.lifers.util.ImmutableCollectors;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubCommandManager {

    private final LifersPlugin plugin;

    // the default executor to run commands on
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    //private final TabCompletions tabCompletions;

    private final Map<String, Command> subCommands;

    public SubCommandManager(LifersPlugin plugin) {
        this.plugin = plugin;
        //this.tabCompletions = new TabCompletions(plugin);
        this.subCommands = ImmutableList.<Command>builder()
                .add()
                .build()
                .stream()
                .collect(ImmutableCollectors.toMap(c -> c.getName().toLowerCase(), Function.identity()));
    }

    public LifersPlugin getPlugin() {
        return this.plugin;
    }

    /*public TabCompletions getTabCompletions() {
        return this.tabCompletions;
    }*/

    public CompletableFuture<CommandResult> executeCommand(CommandSender sender, String label, List<String> args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(sender, label, args);
            } catch (Throwable e) {
                this.plugin.getLogger().severe("Exception whilst executing command: " + args);
                return null;
            }
        }, this.executor);
    }

    public boolean hasPermissionForAny(CommandSender sender) {
        return this.subCommands.values().stream().anyMatch(c -> c.shouldDisplay() && c.isAuthorized(sender));
    }

    private CommandResult execute(CommandSender sender, String label, List<String> arguments) {
        //applyConvenienceAliases(arguments, true);
        // Handle no arguments

        // メインコマンドを探します。
        Command main = this.subCommands.get(label.toLowerCase());
        System.out.println("1");
        // メインコマンドが見つかりませんでした。
        if (main == null) {
            sendCommandUsage(sender, label);
            return CommandResult.INVALID_ARGS;
        }
        System.out.println("2");
        // 送信者がメインコマンドの使用許可を持っていることを確認します。
        /*if (!main.isAuthorized(sender)) {
            sendCommandUsage(sender, label);
            return CommandResult.NO_PERMISSION;
        }*/
        //arguments.remove(0); // remove the main command arg.

        // メインコマンドに与えられた引数の数が正しいことを確認。
        /*if (main.getArgumentCheck().test(arguments.size())) {
            main.sendDetailedUsage(sender, label);
            return CommandResult.INVALID_ARGS;
        }*/
        // コマンドを実行してみる。
        CommandResult result;
        try {
            result = main.execute(this.plugin, sender, arguments.toArray(new String[0]), label);
        } catch (Throwable e) {
            e.printStackTrace();
            result = CommandResult.FAILURE;
        }
        return result;
    }

    public List<String> tabCompleteCommand(CommandSender sender, List<String> arguments) {
        //applyConvenienceAliases(arguments, false);

        final List<Command> mains = this.subCommands.values().stream()
                .filter(Command::shouldDisplay)
                .collect(Collectors.toList());

        return TabCompleter.create()
                .at(0, CompletionSupplier.startsWith(() -> mains.stream().map(c -> c.getName().toLowerCase())))
                .from(1, partial -> mains.stream()
                        .filter(m -> m.getName().equalsIgnoreCase(arguments.get(0)))
                        .findFirst()
                        .map(cmd -> cmd.tabComplete(this.plugin, sender,(arguments.subList(1, arguments.size()))))
                        .orElse(Collections.emptyList())
                )
                .complete(arguments);
    }

    private void sendCommandUsage(CommandSender sender, String label) {

    }
}
