package life.midorin.info.lifers.command;

import java.util.Objects;

import lombok.NonNull;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

public abstract class SubCommand {

    private final String name, usage, description, permission;
    private final boolean playerOnly;
    private final int length;
    private final String[] aliases;

    /**
     * サブコマンドのコンストラクタ
     *
     * @param name このサブコマンドの名前。nullにしてはいけません!!
     * @param usage このサブコマンドの使用法。nullも可。
     * @param description このサブコマンドの説明。nullも可。
     * @param permission このサブコマンドの許可。nullも可。
     * @param playerOnly このサブコマンドをプレイヤーのみにするかどうか。
     * @param length このサブコマンドの長さ。1以上でなければなりません。
     * @param aliases このサブコマンドのエイリアス。
     */
    public SubCommand(@NonNull final String name, @Nullable final String usage, @Nullable final String description, @Nullable final String permission,
                      final boolean playerOnly, final int length, final String... aliases) {
        Objects.requireNonNull(name, "name");
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
        this.playerOnly = playerOnly;
        this.length = Math.max(length, 1);
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public int getLength() {
        return length;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract void execute(final CommandSender sender, final String label, final String[] args);
}