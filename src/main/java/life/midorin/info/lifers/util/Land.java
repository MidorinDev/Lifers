package life.midorin.info.lifers.util;

import life.midorin.info.lifers.Lifers;
import life.midorin.info.lifers.manager.DatabaseManager;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Land {

    private final String uuid,owner;
    private final World world;
    private final double x, y, z;
    private final MaterialType type;

    private int id;

    public Land(String owner, String uuid, Location location, MaterialType material, int id) {
        this.owner = owner;
        this.uuid = uuid;
        this.type = material;
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.id = id;
    }

    public static void create(String owner, String uuid, Location location, MaterialType type) {
        new Land(owner, uuid, location, type, -1).create();
    }

    public void create() {
        if (id != -1) {
            //上書きを防止
            return;
        }

        if (uuid == null) return;

        if(getType() != MaterialType.DOOR) {
            try {
                DatabaseManager.get().executeStatement(SQLQuery.INSERT_LAND, getOwner(), getUuid(), getWorld().getName(), getX(), getY(), getZ(),getType().name());

                try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_EXACT_LAND, getUuid(), getType().name(), getWorld().getName(), getX(), getY(), getZ())) {
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
        DatabaseManager.get().executeStatement(SQLQuery.DELETE_LAND, getId());
    }

    public String getOwner() {
        return owner;
    }

    public String getUuid() {
        return uuid;
    }

    public MaterialType getType() {
        return type;
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
}
