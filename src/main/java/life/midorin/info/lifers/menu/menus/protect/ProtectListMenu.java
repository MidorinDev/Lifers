package life.midorin.info.lifers.menu.menus.protect;

import life.midorin.info.lifers.protect.ProtectManager;
import life.midorin.info.lifers.menu.inv.ClickableItem;
import life.midorin.info.lifers.menu.inv.SmartInventory;
import life.midorin.info.lifers.menu.inv.content.InventoryContents;
import life.midorin.info.lifers.menu.inv.content.InventoryProvider;
import life.midorin.info.lifers.menu.inv.content.Pagination;
import life.midorin.info.lifers.menu.inv.content.SlotIterator;
import life.midorin.info.lifers.menu.menus.land.LandMenu;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.bukkit.ChatColor.*;

public class ProtectListMenu implements InventoryProvider {

    private final UUID uuid;

    public ProtectListMenu(UUID player) {
        this.uuid = player;
    }

    public ProtectListMenu() {
        this.uuid = null;
    }

    public static SmartInventory INVENTORY(UUID  uuid) {
         return SmartInventory.builder()
                 .id("list")
                .provider(new ProtectListMenu(uuid))
                .size(6, 9)
                .title(RED + Bukkit.getOfflinePlayer(uuid).getName() + "の保護されたブロックリスト")
                .closeable(true)
                .build();
    }

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("list")
            .provider(new ProtectListMenu())
            .size(6, 9)
            .title(RED + "保護されたブロックリスト")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<Protect> test = this.uuid != null ? ProtectManager.get().getProtected_Blocks(this.uuid) : ProtectManager.get().getProtected_Blocks(player);
        ClickableItem[] items = new ClickableItem[test.size()];

        for (int i = 0; i < items.length; i++) {
            Protect protect = test.get(i);
            items[i] = ClickableItem.of(createIconSettings(protect, player), e -> {

                if(e.isRightClick() && player.isOp()) {
                    player.teleport(protect.getBlock().getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5));
                    player.sendMessage(Messages.PREFIX + GREEN + "テレポートしました");
                }

                if(e.isLeftClick()) {
                    ProtectSettingsMenu.INVENTORY(protect).open(player);
                }
            });
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
            i.displayName = RED + "戻る";
            i.lore = Collections.singletonList(GRAY  + ChatColor.stripColor(LandMenu.INVENTORY.getTitle())  + "  へ");
        }, e -> LandMenu.INVENTORY.open(player)));

        if(!pagination.isLast()) {
            contents.set(5, 8, ClickableItem.of(i -> {
                i.material = Material.ARROW;
                i.displayName = GREEN + "次のページ";
                i.lore = Collections.singletonList(GRAY  + String.valueOf(pagination.next().getPage() + 1) + " ページ目へいく");
            }, e -> INVENTORY.open(player, pagination.next().getPage())));
        }

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private Consumer<ClickableItem> createIconSettings(Protect protect, Player player) {
        Material material = protect.getBlock().getType();

        List<String> lore = new ArrayList<>();

        lore.add("");
        if(player.isOp()) {
            lore.add(GREEN + "右クリックでテレポート");
            lore.add("");
        }
        lore.add(WHITE + "チャンク");
        lore.add(GRAY + " X: " + YELLOW + protect.getBlock().getChunk().getX());
        lore.add(GRAY + " Z: " + YELLOW + protect.getBlock().getChunk().getZ());
        lore.add(" ");
        lore.add(WHITE + "Biome: " + YELLOW + protect.getBlock().getBiome());
        lore.add(" ");
        lore.add(YELLOW + "左クリックで設定");


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
            i.displayName =
                    WHITE +
                    "World: " + AQUA +
                            protect.getWorld().getName() +
                            WHITE +
                    " X: " + AQUA +
                            protect.getX() +
                            WHITE +
                    " Y: " + AQUA +
                            protect.getY() +
                            WHITE +
                    " Z: " + AQUA +
                            protect.getZ();
            i.lore = lore;
        };
    }
}
