package life.midorin.info.lifers.menu.menus.land;

import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.ProtectMenu;
import life.midorin.info.lifers.protect.ProtectManager;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.menus.protect.protectlistMenus.AbstractProtectListMenu;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class LandMenu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("land")
                .provider(new LandMenu())
            .size(3, 9)
                .title(DARK_AQUA + "保護地操作パネル" + WHITE + " [Lands]")
                .closeable(true)
                .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        for(int i = 0; i < 9; i += 8)
            contents.fillColumn(i, ClickableItem.empty(icon -> {
                icon.material = Material.STAINED_GLASS_PANE;
                icon.damage = DyeColor.GRAY.ordinal();
                icon.displayName = " ";
            }));

        contents.set(1, 2, ClickableItem.of(new Wool(DyeColor.GREEN).toItemStack(1), i -> {
            i.displayName = GREEN + "保護地を作成";
        }, e -> player.sendMessage(Messages.PREFIX + RED + "現在利用できません")));

        contents.set(1, 4, ClickableItem.of(new Wool(DyeColor.RED).toItemStack(1), i -> {
            i.displayName = RED + "保護地を削除";
        }, e -> player.sendMessage(Messages.PREFIX + RED + "現在利用できません")));

        contents.set(1, 6, ClickableItem.of(i -> {
            i.material = Material.ANVIL;
            i.displayName = DARK_GRAY + "保護地の設定";
        }, e -> player.sendMessage(Messages.PREFIX + RED + "現在利用できません")));

        contents.set(2, 8, ClickableItem.of(i -> {
            List<String> lore = new ArrayList<>();

            lore.add(YELLOW + String.valueOf(ProtectManager.get().getProtected_Blocks(player).size()) + GRAY + "個のブロックが保護されています");

            i.material = Material.CHEST;
            i.displayName = GREEN + "保護されたブロック";
            i.lore = lore;
        }, e -> ProtectMenu.INVENTORY(player).open(player)));
    }


    @Override
    public void update(Player player, InventoryContents contents) {}

}