package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Door;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Block block = e.getBlock();

        //保護対象のブロックか？
        if(!ProtectManager.get().PROTECTABLE_MATERIALS.contains(block.getType())) block = block.getRelative(BlockFace.UP);

        //TODO コードをまとめる
        switch (block.getType()) {

            case WOOD_DOOR:
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
                    block = e.getBlock().getRelative(BlockFace.DOWN);
                }

                break;
            default:
                block = e.getBlock().getRelative(BlockFace.SELF);
        }

        //保護されているブロックか？
        if(!ProtectManager.get().isProtect(block.getLocation())) return;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());
        final Player player = e.getPlayer();

        //オーナーではないならキャンセル
        if(!protect.isOwner(player.getName()))
        {
            player.sendMessage(Messages.PREFIX + ChatColor.RED + "オーナー以外は破壊できません。");
            e.setCancelled(true);
            return;
        }

        //保護を解除
        protect.delete();

        player.sendMessage(Messages.PREFIX + ChatColor.RED + "保護を解除しました。");

        return;
    }
}
