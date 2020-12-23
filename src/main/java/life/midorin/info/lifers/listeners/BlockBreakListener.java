package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        final Player player = e.getPlayer();
        final Block block = e.getBlock();

        //保護されているブロックか？
        if(ProtectManager.get().isProtect(block.getLocation())) return;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());

        //オーナーであるか
        if(!protect.isOwner(player.getName())) e.setCancelled(true);

        //保護を解除
        protect.delete();

        return;
    }
}
