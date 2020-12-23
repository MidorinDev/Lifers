package life.midorin.info.lifers.listeners;

import life.midorin.info.lifers.menu.Items;
import life.midorin.info.lifers.menu.LandGUI;
import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.MaterialType;
import life.midorin.info.lifers.util.Messages;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import java.util.ArrayList;
import java.util.List;

public class Listeners implements Listener
{
/*    List<Player> setting_list = new ArrayList<Player>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.getType() == Material.IRON_AXE && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "XYZ Selector"))
        {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                //Lands.posX.put(p, e.getClickedBlock().getX());
                //Lands.posY.put(p, e.getClickedBlock().getY());
            }
            else if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
            }
        }
    }

    int i = 0;

    @EventHandler
    public void onClickInventory(InventoryClickEvent e)
    {
        Player clicker = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().equalsIgnoreCase(LandGUI.main.getName()))
        {
            if (item == null) return;
            e.setCancelled(true);
            if (item.getItemMeta().getDisplayName().equals(Items.green_wool.getItemMeta().getDisplayName()) && item.getType() == Items.green_wool.getType())
            {
                clicker.closeInventory();
                //if (!Lifers.player_data.containsKey(clicker)) Lifers.player_data.put(clicker, new ArrayList<>());
                //Lifers.player_data.get(clicker).add("data" + i);
                i = i + 1;
                System.out.println(i);
            }
            else if (item.getItemMeta().getDisplayName().equals(Items.red_wool.getItemMeta().getDisplayName()) && item.getType() == Items.red_wool.getType())
            {
                clicker.openInventory(LandGUI.list);
            }
            else if (item.getItemMeta().getDisplayName().equals(Items.anvil.getItemMeta().getDisplayName()) && item.getType() == Items.anvil.getType())
            {
                clicker.openInventory(LandGUI.list);
                setting_list.add(clicker);
            }
        }
        else if (e.getInventory().getName().equalsIgnoreCase(LandGUI.list.getName()))
        {
            if (item == null) return;
            e.setCancelled(true);
            if (setting_list.contains(clicker))
            {
                setting_list.remove(clicker);
            }
            else
            {
            }
        }
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e)
    {
        Messages.sendMessageToOp(ChatColor.GRAY + "[Log] " + e.getPlayer().getName() + " : Command | " + e.getMessage());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if (b.getType() == Material.TNT || b.getType() == Material.LAVA_BUCKET || b.getType() == Material.LAVA) {
            e.setCancelled(true);
            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.RED + p.getName() + "さんが禁止ブロックを設置しようとしました。");
            Messages.sendMessageToOp(ChatColor.GRAY + "[Log] " + p.getName() + " : Place " + " | " + b.getX() + " " + b.getY() + " " + b.getZ());
            p.kickPlayer("禁止ブロックの無断設置");
        } else if (b.getType() == Material.IRON_DOOR_BLOCK) {
            //if (!Lifers.player_data.containsKey(p)) Lifers.player_data.put(p, new ArrayList<>());
            //Lifers.player_data.get(p).add("[door-" + b + "]");
            p.sendMessage(Messages.PREFIX + ChatColor.YELLOW + "ドアを登録しました。");
        } else if (b.getType() == Material.CHEST) {

            if (isProtect(b.getLocation())) {
                Messages.sendMessageToOp(ChatColor.RED + "既に登録されています");
                return;
            }

            Protect.create(p.getName(), p.getUniqueId().toString(), b.getLocation(), MaterialType.CHEST);
            p.sendMessage(Messages.PREFIX + ChatColor.YELLOW + "チェストを登録しました。");

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        if (b.getType() == Material.IRON_DOOR_BLOCK)
        {
            //Lifers.player_data.get(p).remove("[door-" + b + "]");
            p.sendMessage(Messages.PREFIX + ChatColor.RED + "ドアの登録を解除しました。");
        }
        else if (b.getType() == Material.CHEST)
        {
            Protect protect = ProtectManager.get().getLand(b.getLocation());
            if(protect == null) return;

            if(!p.getUniqueId().toString().contains(protect.getUuid())) {
                e.setCancelled(true);
                p.sendMessage(Messages.PREFIX + ChatColor.RED + "所有者以外は破壊できません。");
                return;
            }

            protect.delete();
            p.sendMessage(Messages.PREFIX + ChatColor.RED + "チェストの登録を解除しました。");

        }
    }

    *//***
    @EventHandler
    public void onRightClickEvent(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        ItemStack held = player.getItemInHand();
        Action action = e.getAction();
        Block clicked = e.getClickedBlock();
        if (!e.getHand().equals(EquipmentSlot.HAND) || !(clicked.getType() == Material.IRON_DOOR_BLOCK) || !(action == Action.RIGHT_CLICK_BLOCK)) return;
        if (held.getType() == Material.AIR)
        {
            for (int i=0; i<Lifers.player_data.size(); i++)
            {
                if (Lifers.player_data.get(player).get(i).equals("door-" + clicked))
                {
                    BlockState state = clicked.getState();
                    Door door = (Door) state.getData();
                    if (door.isOpen())
                    {
                        door.setOpen(false);
                        player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 100, (float) 0.945);
                        state.update();
                    }
                    else
                    {
                        door.setOpen(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 100, (float) 0.945);
                        state.update();
                    }
                }
                else player.sendMessage(Messages.PREFIX + ChatColor.RED + "ドアの所有者以外はドアを解放、閉鎖できません。");
            }
        }
    }
    ***//*

    @EventHandler
    public void onRightClickEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block clicked = e.getClickedBlock();
        if (e.getHand().equals(EquipmentSlot.HAND) && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (clicked.getType() == Material.IRON_DOOR_BLOCK) {
                BlockState state = clicked.getState();
                Door door = (Door) state.getData();

                if (door.isTopHalf()) state = clicked.getRelative(BlockFace.DOWN).getState();
                Openable openable = (Openable) state.getData();
                openable.setOpen(!openable.isOpen());
                state.setData((MaterialData) openable);
                state.update();
                player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 100, (float) 0.945);
            } else if (clicked.getType() == Material.CHEST) {
                Protect protect = ProtectManager.get().getLand(clicked.getLocation());
                if (protect == null) return;

                if (!player.getUniqueId().toString().contains(protect.getUuid())) {
                    player.sendMessage(Messages.PREFIX + ChatColor.RED + "チェストの所有者以外はチェストを開けません。");
                    e.setCancelled(true);
                }
            }
        }
    }*/
}
