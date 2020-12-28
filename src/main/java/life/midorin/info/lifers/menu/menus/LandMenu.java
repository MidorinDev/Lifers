package life.midorin.info.lifers.menu.menus;

import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LandMenu implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        for(int i = 0; i < 9; i += 8)
            contents.fillColumn(i, ClickableItem.empty(icon -> {
                icon.material = Material.STAINED_GLASS_PANE;
                icon.displayName = " ";
            }));

        contents.set(1, 2, ClickableItem.of(i -> {
            i.material = Material.WOOL;
            i.damage = DyeColor.GREEN.ordinal();
            i.displayName  = ChatColor.GREEN + "保護地を作成";
        }, e -> {
            if(e.isRightClick()) {
                player.sendMessage("test");
            }

        }));

        contents.set(1, 4, ClickableItem.of(i -> {
            i.material = Material.WOOL;
            i.damage = DyeColor.RED.ordinal();
            i.displayName  = ChatColor.RED + "保護地を削除";
        }, e -> {
            if(e.isRightClick()) {
                player.sendMessage("test");
            }

        }));

        contents.set(1, 6, ClickableItem.of(i -> {
            i.material = Material.ANVIL;
            i.displayName = ChatColor.DARK_GRAY + "保護地の設定";
        }, e -> {
            if(e.isRightClick()) {
                player.sendMessage("test");
            }

        }));

        contents.set(2, 8, ClickableItem.of(i -> {
            i.material = Material.TOTEM;
            i.displayName = ChatColor.GREEN + "保護されたブロック";
        }, e -> ProtectListMenu.INVENTORY.open(player)));
    }


    @Override
    public void update(Player player, InventoryContents contents) {}

}