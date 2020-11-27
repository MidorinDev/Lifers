package life.midorin.info.lifers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Listeners implements Listener
{
    List<Player> setting_list = new ArrayList<Player>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        e.setJoinMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "Join" + ChatColor.WHITE + "] " + ChatColor.GRAY + e.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        e.setQuitMessage(ChatColor.WHITE + "[" + ChatColor.RED + "Quit" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName());
    }

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
                clicker.sendMessage("1");
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
}
