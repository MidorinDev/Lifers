package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        final Block block = e.getBlock();

        //保護対象のブロックか？
        if(!ProtectManager.get().PROTECTABLE_MATERIALS.contains(block.getType())) return;

        final Player player = e.getPlayer();

        //既に保護されているか？
        if(ProtectManager.get().isProtect(block.getLocation())) return;

        //保護する
        Protect.create(player.getName(), player.getUniqueId().toString(), block.getLocation());

        player.sendMessage("保護しました");
    }

}
