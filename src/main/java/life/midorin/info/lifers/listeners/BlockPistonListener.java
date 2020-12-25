package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.material.Door;

import java.util.List;

public class BlockPistonListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockPistonEvent(BlockPistonExtendEvent e) {

        if (compareWithDefault(e.getBlocks())) e.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockPistonEvent(BlockPistonRetractEvent e) {

        if (compareWithDefault(e.getBlocks())) e.setCancelled(true);

    }

    private boolean compareWithDefault(List<Block> blocks) {

        for (Block block : blocks) {

            //保護対象のブロックか？
            if (!ProtectManager.get().PROTECTABLE_MATERIALS.contains(block.getType())) return false;

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
            if (!ProtectManager.get().isProtect(block.getLocation())) return false;

            return true;
        }
        return false;
    }
}
