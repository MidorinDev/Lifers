package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        final Player player = e.getPlayer();
        final Action action = e.getAction();
        Block block = e.getClickedBlock();

        if(block == null) return;

        if(action != Action.RIGHT_CLICK_BLOCK) return;

        //TODO コードをまとめる
        switch (block.getType()) {

            case WOOD_DOOR:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
            case TRAP_DOOR:
            case WOODEN_DOOR:
            case IRON_DOOR:
            case IRON_TRAPDOOR:

                BlockState blockState = block.getState();
                Door door = (Door) blockState.getData();

                if (door.isTopHalf()) {
                    block = Bukkit.getWorld(block.getWorld().getUID()).getBlockAt(block.getLocation().subtract(0, 1, 0));
                }

                break;
        }

        //保護されているブロックか？
        if(!ProtectManager.get().isProtect(block.getLocation())) return;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());

        //オーナーではないならキャンセル
        if(!protect.isOwner(player.getName())) {

            player.sendMessage("オーナー以外は使用できません");
            e.setCancelled(true);
            return;
        }

        return;

    }
}
