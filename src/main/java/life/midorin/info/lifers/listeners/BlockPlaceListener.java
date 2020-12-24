package life.midorin.info.lifers.listeners;

import com.google.common.collect.ImmutableSet;
import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.Set;

public class BlockPlaceListener implements Listener {

    private static final Set<Material> PROTECTABLE_MATERIALS;

    static {
        PROTECTABLE_MATERIALS = ImmutableSet.copyOf(Arrays.asList(
                Material.WOOD_DOOR,
                Material.DARK_OAK_DOOR,
                Material.ACACIA_DOOR,
                Material.BIRCH_DOOR,
                Material.IRON_DOOR,
                Material.JUNGLE_DOOR,
                Material.SPRUCE_DOOR,
                Material.TRAP_DOOR,
                Material.WOODEN_DOOR,
                Material.CHEST,
                Material.ENDER_CHEST,
                Material.TRAP_DOOR
        ));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        final Block block = e.getBlock();

        //保護対象のブロックか？
        if(!PROTECTABLE_MATERIALS.contains(block.getType())) return;

        final Player player = e.getPlayer();

        //既に保護されているか？
        if(ProtectManager.get().isProtect(block.getLocation())) return;

        //保護する
        Protect.create(player.getName(), player.getUniqueId().toString(), block.getLocation());

        player.sendMessage("保護しました");
    }
}
