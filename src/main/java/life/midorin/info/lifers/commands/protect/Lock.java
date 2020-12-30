package life.midorin.info.lifers.commands.protect;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lock implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        final Player player = (Player) sender;
        final Block block = player.getTargetBlock(null, 5);;

        //保護対象のブロックか？
        if(!ProtectManager.get().PROTECTABLE_MATERIALS.contains(block.getType())) return false;

        //既に保護されているか？
        if(ProtectManager.get().isProtect(block.getLocation())) return false;

        //保護する
        Protect.create(player.getName(), player.getUniqueId().toString(), block.getLocation());

        player.sendMessage(Messages.PREFIX + ChatColor.YELLOW + "保護しました。");

        return true;
    }
}
