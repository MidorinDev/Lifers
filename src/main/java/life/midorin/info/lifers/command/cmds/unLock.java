package life.midorin.info.lifers.command.cmds;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Door;

public class unLock implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        final Player player = (Player) sender;

        Block block = player.getTargetBlock(null, 5);

        //保護対象のブロックか？
        if(!ProtectManager.get().PROTECTABLE_MATERIALS.contains(block.getType())) block = block.getRelative(BlockFace.UP);

        //TODO コードをまとめる
        switch (block.getType()) {

            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
            case WOODEN_DOOR:
            case IRON_DOOR:
            case IRON_DOOR_BLOCK:

                BlockState blockState = block.getState();
                Door door = (Door) blockState.getData();

                if (door.isTopHalf()) {
                    block = player.getTargetBlock(null, 5).getRelative(BlockFace.DOWN);
                }

                break;
            default:
                block = player.getTargetBlock(null, 5).getRelative(BlockFace.SELF);
        }

        //保護されているブロックか？
        if(!ProtectManager.get().isProtect(block.getLocation())) return false;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());


        //オーナーではないならキャンセル
        if(!protect.isOwner(player.getName()))
        {
            player.sendMessage(Messages.PREFIX + ChatColor.RED + "オーナー以外は解除することはできません");
            return false;
        }

        //保護を解除
        protect.delete();

        player.sendMessage(Messages.PREFIX + ChatColor.RED + "保護を解除しました。");

        return true;
    }
}
