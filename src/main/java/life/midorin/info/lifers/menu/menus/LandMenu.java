package life.midorin.info.lifers.menu.menus;

import life.midorin.info.lifers.menu.Items;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LandMenu implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        for(int i = 0; i < 9; i += 8)
            contents.fillColumn(i, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));

        contents.set(1, 4, ClickableItem.empty(new ItemStack(Material.STICK)));
    }


    @Override
    public void update(Player player, InventoryContents contents) {}

}