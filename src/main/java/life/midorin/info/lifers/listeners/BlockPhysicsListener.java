package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.material.Door;

public class BlockPhysicsListener implements Listener {

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {

        Block block = e.getBlock();

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
                        block = block.getRelative(BlockFace.DOWN);
                    }

                    break;
            }

            //保護されているブロックか？
            if (!ProtectManager.get().isProtect(block.getLocation())) e.setCancelled(true);

        }

}
