package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Door;

public class HopperListener implements Listener {

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        InventoryHolder sourceHolder = e.getSource().getHolder();

        Location loc = null;

        if (sourceHolder instanceof BlockState) {
            BlockState blockState = (BlockState) sourceHolder;

            loc = blockState.getLocation();
        } else if (sourceHolder instanceof DoubleChest) {
            DoubleChest chest = (DoubleChest) sourceHolder;
            loc = chest.getLocation();
        }

        if (loc != null) {
            Block block = loc.getBlock();

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
                        block = block.getRelative(BlockFace.DOWN);
                    }

                    break;
            }

            //保護されているブロックか？
            if(!ProtectManager.get().isProtect(block.getLocation())) return;

            e.setCancelled(true);
        }
    }

}
