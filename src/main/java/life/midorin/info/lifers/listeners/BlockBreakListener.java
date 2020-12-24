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
        if(!ProtectManager.get().isProtect(block.getLocation())) return;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());

        if(protect == null) return;

        //オーナーではないならキャンセル
        if(!protect.isOwner(player.getName())) {

            player.sendMessage("オーナー以外は破壊できません");
            e.setCancelled(true);
            return;
        }

        //保護を解除
        protect.delete();

        player.sendMessage("保護を削除しました");

        return;
    }
}
