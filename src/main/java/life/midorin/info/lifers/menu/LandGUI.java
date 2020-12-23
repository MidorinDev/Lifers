package life.midorin.info.lifers.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class LandGUI
{
    public static Inventory main = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "保護地操作パネル" + ChatColor.WHITE + " [Lands]");
    public static Inventory setting = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "保護地設定" + ChatColor.WHITE + " [Lands]");

    public static Inventory list = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "保護地リスト" + ChatColor.WHITE + " [Lands]");

    public static void setFream27(Inventory inv)
    {
        inv.setItem(0, Items.glass);
        inv.setItem(9, Items.glass);
        inv.setItem(18, Items.glass);
        inv.setItem(8, Items.glass);
        inv.setItem(17, Items.glass);
        inv.setItem(26, Items.glass);
    }
}
