package life.midorin.info.lifers.menu.menus.protect;

import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.AbstractProtectListMenu;
import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.LookUpMenu;
import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.ProtectMenu;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.*;


public class ProtectSettingsMenu implements InventoryProvider {

    private final Protect protect;

    public ProtectSettingsMenu(Protect protect) {
        this.protect = protect;
    }

    public static SmartInventory INVENTORY(Protect protect) {
        return SmartInventory.builder()
                .id("settings")
                .provider(new ProtectSettingsMenu(protect))
                .size(3, 9)
                .title(DARK_AQUA + "保護設定パネル" + WHITE + " [Lands]")
                .closeable(true)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.fill(ClickableItem.empty(i -> {
            i.material = Material.STAINED_GLASS_PANE;
            i.displayName = " ";
            i.damage = DyeColor.GRAY.ordinal();
        }));

        contents.set(1, 2, ClickableItem.of(i -> {
            List<String> lore = new ArrayList<>();

            lore.add(GRAY + "保護で信頼されているプレイヤーのリストを取得します");

            i.material = Material.SKULL_ITEM;
            i.displayName = GREEN + "メンバーリスト";
            i.lore = lore;
        }, e -> ProtectMemberList.INVENTORY(protect).open(player)));

        contents.set(1, 6, ClickableItem.of(new Dye(DyeColor.RED).toItemStack(1), i -> {
            List<String> lore = new ArrayList<>();

            lore.add(GRAY + "保護ブロックを解除する");
            lore.add(" ");
            lore.add(RED + "注意:" + WHITE + " 設定されたすべてのデータが削除されます");

            i.displayName = RED + "保護ブロックの解除";
            i.lore = lore;
        }, e -> {
            protect.delete();
            player.sendMessage(Messages.PREFIX + RED + "保護を解除しました。");
            ProtectMenu.INVENTORY(player).open(player);
        }));

        contents.set(2, 4, ClickableItem.of(i -> {
            i.material = Material.ARROW;
            i.displayName = RED + "戻る";
            i.lore = Collections.singletonList(GRAY + ChatColor.stripColor(ProtectMenu.INVENTORY(player).getTitle()) + "  へ");
        }, e -> {
            if (protect.getUuid() == player.getUniqueId().toString()) {
                ProtectMenu.INVENTORY(player).open(player);
            } else {
                LookUpMenu.INVENTORY(UUID.fromString(protect.getUuid()), player).open(player);
            }

        }));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
