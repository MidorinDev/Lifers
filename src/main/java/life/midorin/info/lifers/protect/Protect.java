package life.midorin.info.lifers.protect;

import life.midorin.info.lifers.manager.DatabaseManager;
import life.midorin.info.lifers.util.SQLQuery;
import life.midorin.info.lifers.util.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Protect {

    private final String uuid,owner;
    private final World world;
    private final double x, y, z;
    private final Block block;

    private List<String> trusted_players;

    private int id;

    public Protect(String owner, String uuid, Location location, int id) {
        this.owner = owner;
        this.uuid = uuid;
        this.block = location.getBlock();
        this.world = block.getWorld();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.id = id;
        this.trusted_players = ProtectManager.get().getProtected_Block_Members(id);
    }

    public static void create(String owner, String uuid, Location loc) {
        new Protect(owner, uuid, loc, -1).create();
    }

    public void create() {
        if (id != -1) {
            //上書きを防止
            return;
        }

        if (uuid == null) return;

        if(getBlock().getType() != Material.WOOD_DOOR) {
            try {
                DatabaseManager.get().executeStatement(SQLQuery.INSERT_PROTECTED_BLOCK, owner, uuid, world.getName(), x, y, z, block.getType().name());

                try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_EXACT_PROTECTED_BLOCK, uuid, block.getType().name(), world.getName(), x, y, z)) {
                    if (rs.next()) {
                        id = rs.getInt("id");
                    } else {
                        Utils.log("!! IDを更新できない! サーバーを再起動して解決してください");
                        Utils.log("!! Failed at: " + toString());
                    }
                }
            } catch (SQLException ex) {

            }
        }
    }

    public void delete() {
        delete(null, false, true);
    }

    public void delete(String who, boolean massClear, boolean removeCache) {

        if (id == -1) {
            return;
        }
        DatabaseManager.get().executeStatement(SQLQuery.DELETE_PROTECTED_BLOCK, id);
        DatabaseManager.get().executeStatement(SQLQuery.DELETE_PROTECTED_BLOCK_MEMBERS, id);
    }

    public void addMember(Player player) {
        if(!trusted_players.contains(player))
        trusted_players.add(player.getUniqueId().toString());

        DatabaseManager.get().executeStatement(SQLQuery.INSERT_PROTECTED_BLOCK_MEMBER, this.id, player.getUniqueId());
    }

    public String getOwner() {
        return owner;
    }

    public String getUuid() {
        return uuid;
    }

    public Block getBlock() {
        return block;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getId() {
        return id;
    }

    public List<String> getTrusted_players() {
        return trusted_players;
    }

    public boolean isOwner(Player player) {
        return this.uuid.equals(player.getUniqueId().toString());
    }

    public boolean isAccess(String uuid) {
        return this.trusted_players.contains(uuid);
    }
}
