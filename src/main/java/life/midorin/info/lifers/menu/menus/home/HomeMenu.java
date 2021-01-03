package life.midorin.info.lifers.menu.menus.home;

import life.midorin.info.lifers.home.Home;
import life.midorin.info.lifers.home.HomeManager;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.inv.content.SlotIterator;
import life.midorin.info.lifers.menu.menus.protect.ProtectMemberList;
import life.midorin.info.lifers.menu.menus.protect.ProtectSettingsMenu;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.protect.ProtectManager;
import life.midorin.info.lifers.util.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GRAY;

public class HomeMenu implements InventoryProvider {

    public static SmartInventory INVENTORY() {
        return SmartInventory.builder()
                .id("home")
                .provider(new HomeMenu())
                .size(3, 9)
                .title(DARK_AQUA + "Home")
                .closeable(true)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        List<Home> test = HomeManager.get().getPlayerHomeBy(player.getUniqueId());
        ClickableItem[] items = new ClickableItem[test.size()];

        for (int i = 0; i < items.length; i++) {
            int finalI = i;
            contents.set(1, 2 + i, ClickableItem.of(icon -> {
                icon.material = Material.LOG;
                icon.displayName = test.get(finalI).getName();
                icon.lore = Collections.singletonList(GRAY + "");
            }, e -> player.teleport(test.get(finalI).getLocation())));
        }

        for (int i = 0; i < 3; i += 2)
            contents.fillRow(i, ClickableItem.empty(icon -> {
                icon.material = Material.STAINED_GLASS_PANE;
                icon.displayName = " ";
                icon.damage = DyeColor.GRAY.ordinal();
            }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
