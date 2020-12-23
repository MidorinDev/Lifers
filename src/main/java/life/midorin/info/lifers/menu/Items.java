package life.midorin.info.lifers.menu;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items
{
    public static ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
    public static ItemMeta im0 = glass.getItemMeta();
    public static ItemStack green_wool = new ItemStack(Material.WOOL);
    public static ItemMeta im1 = green_wool.getItemMeta();
    public static ItemStack red_wool = new ItemStack(Material.WOOL);
    public static ItemMeta im2 = red_wool.getItemMeta();
    public static ItemStack anvil = new ItemStack(Material.ANVIL);
    public static ItemMeta im3 = anvil.getItemMeta();
    public static ItemStack close_barrier = new ItemStack(Material.BARRIER);
    public static ItemMeta im4 = close_barrier.getItemMeta();
    public static ItemStack dirt = new ItemStack(Material.DIRT);
    public static ItemMeta im5 = dirt.getItemMeta();
    public static void setup()
    {
        im0.setDisplayName(" ");
        glass.setItemMeta(im0);
        im1.setDisplayName(ChatColor.GREEN + "保護地を作成");
        green_wool.setDurability(DyeColor.GREEN.getWoolData());
        green_wool.setItemMeta(im1);
        im2.setDisplayName(ChatColor.RED + "保護地を削除");
        red_wool.setDurability(DyeColor.RED.getWoolData());
        red_wool.setItemMeta(im2);
        im3.setDisplayName(ChatColor.DARK_GRAY + "保護地の設定");
        anvil.setItemMeta(im3);
        im4.setDisplayName(ChatColor.RED + "閉じる");
        close_barrier.setItemMeta(im4);
        im5.setDisplayName(ChatColor.AQUA + "保護名: name");
        dirt.setItemMeta(im5);
    }
}
