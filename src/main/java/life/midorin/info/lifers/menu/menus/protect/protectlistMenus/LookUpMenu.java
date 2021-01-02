package life.midorin.info.lifers.menu.menus.protect.protectlistMenus;

import life.midorin.info.lifers.commands.land.LookUp;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.menus.land.LandMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GRAY;

public class LookUpMenu extends AbstractProtectListMenu{

    public static SmartInventory INVENTORY(UUID uuid, Player player) {
        return SmartInventory.builder()
                .id("list")
                .provider(new LookUpMenu(uuid, player))
                .size(6, 9)
                .title(RED + Bukkit.getOfflinePlayer(uuid).getName() + "の保護されたブロックリスト")
                .closeable(true)
                .build();
    }

    public LookUpMenu(UUID uuid, Player player) {
        super(
                uuid,
                inventoryContents -> {
                    Pagination pagination =inventoryContents.pagination();

                    if(!inventoryContents.pagination().isFirst()) {
                        inventoryContents.set(5, 0, ClickableItem.of(i -> {
                            i.material = Material.ARROW;
                            i.displayName = GREEN + "前のページ";
                            i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.previous().getPage() + 1) + " ページ目へいく");
                        }, e -> INVENTORY(uuid, player).open(player, pagination.previous().getPage())));
                    }

                    if(!inventoryContents.pagination().isLast()) {
                        inventoryContents.set(5, 8, ClickableItem.of(i -> {
                            i.material = Material.ARROW;
                            i.displayName = GREEN + "次のページ";
                            i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.next().getPage() + 1) + " ページ目へいく");
                        }, e -> INVENTORY(uuid, player).open(player, pagination.next().getPage())));
                    }
        });
    }


}
