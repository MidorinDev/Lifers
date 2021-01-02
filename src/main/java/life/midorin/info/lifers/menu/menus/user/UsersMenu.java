package life.midorin.info.lifers.menu.menus.user;

import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.inv.content.SlotIterator;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.user.User;
import life.midorin.info.lifers.user.UserSet;
import life.midorin.info.lifers.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

public class UsersMenu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("users")
            .provider(new UsersMenu())
            .size(6, 9)
            .title(DARK_AQUA + "ユーザーリスト")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        User requester = UserSet.getInstnace().getUser(player);
        List<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers().stream().filter(player1 -> !player1.equals(requester.asBukkitPlayer())).collect(Collectors.toList());
        ClickableItem[] items = new ClickableItem[onlinePlayers.size()];

        for (int i = 0; i < items.length; i++) {
            User user = UserSet.getInstnace().getUser(onlinePlayers.get(i).getUniqueId());
            items[i] = ClickableItem.of(createIconSettings(user.asBukkitPlayer()), e -> requester.requestTeleport(user));
        }

        for (int i = 0; i < 6; i += 5)
            contents.fillRow(i, ClickableItem.empty(icon -> {
                icon.material = Material.STAINED_GLASS_PANE;
                icon.displayName = " ";
                icon.damage = DyeColor.GRAY.ordinal();
            }));

        pagination.setItems(items);
        pagination.setItemsPerPage(36);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 0).allowOverride(false));

        if(!pagination.isFirst()) {
            contents.set(5, 0, ClickableItem.of(i -> {
                i.material = Material.ARROW;
                i.displayName = GREEN + "前のページ";
                i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.previous().getPage() + 1) + " ページ目へいく");
            }, e -> INVENTORY.open(player, pagination.previous().getPage())));
        }

        contents.set(5, 4,  ClickableItem.of(i ->{
            i.material = Material.ARROW;
            i.displayName = RED + "閉じる";
            i.lore = Collections.singletonList(GRAY + "閉じる");
        }, e -> INVENTORY.close(player)));

        if(!pagination.isLast()) {
            contents.set(5, 8, ClickableItem.of(i -> {
                i.material = Material.ARROW;
                i.displayName = GREEN + "次のページ";
                i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.next().getPage() + 1) + " ページ目へいく");
            }, e -> INVENTORY.open(player, pagination.next().getPage())));
        }
    }

    private Consumer<ClickableItem> createIconSettings(Player player) {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add(YELLOW + "左クリックでリクエストを送る");

        return i -> {
            i.basedItemStack = SkullCreator.itemFromUuid(player.getUniqueId());
            i.displayName = "";
            i.lore = lore;
        };
    }
}
