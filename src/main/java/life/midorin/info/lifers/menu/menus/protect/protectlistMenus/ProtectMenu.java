package life.midorin.info.lifers.menu.menus.protect.protectlistMenus;

import life.midorin.info.lifers.commands.land.LookUp;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.menus.land.LandMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;
import java.util.function.Consumer;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GRAY;

public class ProtectMenu extends AbstractProtectListMenu {

    public static SmartInventory INVENTORY(Player player) {
        return   SmartInventory.builder()
                .id("list")
                .provider(new ProtectMenu(player))
                .size(6, 9)
                .title(RED + "保護されたブロックリスト")
                .closeable(true)
                .build();
    }

    public ProtectMenu(Player player) {
        super(
                null ,
                inventoryContents -> {
                    Pagination pagination =inventoryContents.pagination();
                    if(!inventoryContents.pagination().isFirst()) {
                        inventoryContents.set(5, 0, ClickableItem.of(i -> {
                            i.material = Material.ARROW;
                            i.displayName = GREEN + "前のページ";
                            i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.previous().getPage() + 1) + " ページ目へいく");
                        }, e -> INVENTORY(player).open(player, pagination.previous().getPage())));
                    }

                    inventoryContents.set(5, 4,  ClickableItem.of(i ->{
                        i.material = Material.ARROW ;
                        i.displayName = RED + "戻る";
                        i.lore = Collections.singletonList(GRAY  + ChatColor.stripColor(LandMenu.INVENTORY.getTitle())  + "  へ");
                    }, e -> LandMenu.INVENTORY.open(player)));

                    if(!inventoryContents.pagination().isLast()) {
                        inventoryContents.set(5, 8, ClickableItem.of(i -> {
                            i.material = Material.ARROW;
                            i.displayName = GREEN + "次のページ";
                            i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.next().getPage() + 1) + " ページ目へいく");
                        }, e -> INVENTORY(player).open(player, pagination.next().getPage())));
                    }
                });
    }
}
