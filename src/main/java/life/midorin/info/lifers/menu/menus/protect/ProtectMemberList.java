package life.midorin.info.lifers.menu.menus.protect;

import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.inv.content.SlotIterator;
import life.midorin.info.lifers.menu.menus.land.LandMenu;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import life.midorin.info.lifers.util.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class ProtectMemberList implements InventoryProvider {

    private final Protect protect;

    public ProtectMemberList(Protect protect) {
        this.protect = protect;
    }

    public static SmartInventory INVENTORY(Protect protect) {
        return SmartInventory.builder()
                .id("memberlist")
                .provider(new ProtectMemberList(protect))
                .size(4, 9)
                .title(DARK_AQUA + "保護メンバーリスト" + WHITE + " [Lands]")
                .closeable(true)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<String> test = ProtectManager.get().getProtected_Block_Members(protect.getId());
        ClickableItem[] items = new ClickableItem[test.size()];

        for (int i = 0; i < items.length; i++) {
            items[i] = ClickableItem.empty(SkullCreator.itemFromUuid(UUID.fromString(test.get(i))));
        }

        for (int i = 0; i < 4; i += 3)
            contents.fillRow(i, ClickableItem.empty(icon -> {
                icon.material = Material.STAINED_GLASS_PANE;
                icon.displayName = " ";
                icon.damage = DyeColor.GRAY.ordinal();
            }));

        pagination.setItems(items);
        pagination.setItemsPerPage(16);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 0).allowOverride(false));

        if(!pagination.isFirst()) {
            contents.set(3, 0, ClickableItem.of(i -> {
                i.material = Material.ARROW;
                i.displayName = GREEN + "前のページ";
                i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.previous().getPage() + 1) + " ページ目へいく");
            }, e -> INVENTORY(protect).open(player, pagination.previous().getPage())));
        }

        contents.set(3, 4,  ClickableItem.of(i ->{
            i.material = Material.ARROW;
            i.displayName = RED + "戻る";
            i.lore = Collections.singletonList(GRAY  + ChatColor.stripColor(ProtectSettingsMenu.INVENTORY(protect).getTitle())  + "  へ");
        }, e -> ProtectSettingsMenu.INVENTORY(protect).open(player)));

        if(!pagination.isLast()) {
            contents.set(3, 8, ClickableItem.of(i -> {
                i.material = Material.ARROW;
                i.displayName = GREEN + "次のページ";
                i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.next().getPage() + 1) + " ページ目へいく");
            }, e -> INVENTORY(protect).open(player, pagination.next().getPage())));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
