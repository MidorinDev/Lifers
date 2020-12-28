package life.midorin.info.lifers.menu.menus;

import life.midorin.info.lifers.commands.Lock;
import life.midorin.info.lifers.manager.ProtectManager;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.inv.content.SlotIterator;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ProtectListMenu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("list")
            .provider(new ProtectListMenu())
            .size(6, 9)
            .title(ChatColor.RED + "保護されたブロックリスト")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();


        List<Protect> test = ProtectManager.get().getProtected_Blocks(player);
        ClickableItem[] items = new ClickableItem[test.size()];

        for(int i = 0; i < test.size(); i++) {
            Protect protect = test.get(i);
            items[i] = ClickableItem.of(createIconSettings(protect), e -> {
                if(e.getAction().name() == "NOTHING") {
                    protect.delete();
                    player.sendMessage(Messages.PREFIX + ChatColor.RED + "保護を解除しました。");
                    INVENTORY.open(player);
                }
            });
        }

        contents.fillBorders(ClickableItem.empty(i ->{
           i.material = Material.STAINED_GLASS_PANE;
           i.damage = DyeColor.WHITE.ordinal();
        }));

        pagination.setItems(items);
        pagination.setItemsPerPage(42);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));

        contents.set(5, 3, ClickableItem.of(i -> {
            i.material = Material.ARROW;
            i.displayName = "前のページへ";
                },
                e -> INVENTORY.open(player, pagination.previous().getPage())));

        contents.set(5, 5, ClickableItem.of(i -> {
            i.material = Material.ARROW;
            i.displayName = "次のページへ";
                },
                e -> INVENTORY.open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private Consumer<ClickableItem> createIconSettings(Protect protect) {

        Material material = protect.getBlock().getType();

        List<String> lore = Arrays.stream("座標".split("\\r?\\n"))
                .map(s -> ChatColor.WHITE + s)
                .collect(Collectors.toList());

        lore.add(0, "");

        lore.add(ChatColor.GRAY + "name:" + protect.getWorld().getName() + " x:" + protect.getX() + " y:" + protect.getY() + " z:" + protect.getZ());

        lore.add(ChatColor.RED + "ホイールクリックで削除");

        //TODO もっといいやり方があるはず
        switch (material) {
            case SPRUCE_DOOR:
                material = Material.SPRUCE_DOOR_ITEM;
                break;
            case BIRCH_DOOR:
                material = Material.BIRCH_DOOR_ITEM;
                break;
            case JUNGLE_DOOR:
                material = Material.JUNGLE_DOOR_ITEM;
                break;
            case ACACIA_DOOR:
                material = Material.ACACIA_DOOR_ITEM;
                break;
            case DARK_OAK_DOOR:
                material = Material.DARK_OAK_DOOR_ITEM;
                break;
            case WOODEN_DOOR:
                material = Material.WOOD_DOOR;
                break;
            case IRON_DOOR_BLOCK:
                material = Material.IRON_DOOR;
                break;
        }

        Material finalMaterial = material;

        return i -> {
            i.material = finalMaterial;
            i.lore = lore;
        };
    }
}
