package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
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
            case WOODEN_DOOR:
            case IRON_DOOR:
            case IRON_DOOR_BLOCK:

                BlockState blockState = block.getState();
                Door door = (Door) blockState.getData();

                if (door.isTopHalf()) {
                    block = e.getClickedBlock().getRelative(BlockFace.DOWN);
                }

                break;
            default:
                block = e.getClickedBlock().getRelative(BlockFace.SELF);
        }

        //保護されているブロックか？
        if(!ProtectManager.get().isProtect(block.getLocation())) return;

        final Protect protect = ProtectManager.get().getProtected_Block(block.getLocation());

        //オーナーではないならキャンセル
        if(!protect.isOwner(player.getName())) {

            player.sendMessage(Messages.PREFIX + ChatColor.RED + "ドアの所有者以外はドアを解放、閉鎖できません。");
            e.setCancelled(true);
            return;
        }

        final ItemStack held = player.getInventory().getItemInMainHand();

        if(block.getType() == Material.IRON_DOOR_BLOCK && e.getHand().equals(EquipmentSlot.HAND) && held.getType() == Material.AIR) {

            BlockState state = block.getState();
            Door door = (Door) state.getData();

            if (door.isOpen()) {

                door.setOpen(false);
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 100, (float) 0.945);

            } else {

                door.setOpen(true);
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 100, (float) 0.945);

            }

            state.update();

        }

    }

}
