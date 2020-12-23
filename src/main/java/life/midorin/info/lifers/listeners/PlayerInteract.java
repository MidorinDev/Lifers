package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;

import java.lang.management.MonitorInfo;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        final Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if(block == null) return;

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

                if (door.isTopHalf()) block = Bukkit.getWorld(block.getWorld().getUID()).getBlockAt(block.getLocation().subtract(0,1,0));

                break;
            case CHEST:
            case ENDER_CHEST:
            case TRAPPED_CHEST:

                break;
        }
        //保護されているブロックか？
        if(!ProtectManager.get().isProtect(block.getLocation())) return;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());

        //オーナーであるか
        if(!protect.isOwner(player.getName())) event.setCancelled(true);

        return;

    }
}
