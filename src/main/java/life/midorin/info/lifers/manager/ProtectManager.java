package life.midorin.info.lifers.manager;

import com.google.common.collect.ImmutableSet;
import life.midorin.info.lifers.protect.Protect;
import life.midorin.info.lifers.util.SQLQuery;
import life.midorin.info.lifers.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProtectManager {


    private static ProtectManager instance = null;
    private final Set<Protect> protects = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> cached = Collections.synchronizedSet(new HashSet<>());
    public  static final Set<Material> PROTECTABLE_MATERIALS;

    public static synchronized ProtectManager get() {
        return instance == null ? instance = new ProtectManager() : instance;
    }

    static {
        PROTECTABLE_MATERIALS = ImmutableSet.copyOf(Arrays.asList(
                Material.DARK_OAK_DOOR,
                Material.ACACIA_DOOR,
                Material.BIRCH_DOOR,
                Material.IRON_DOOR,
                Material.JUNGLE_DOOR,
                Material.SPRUCE_DOOR,
                Material.TRAP_DOOR,
                Material.WOODEN_DOOR,
                Material.CHEST,
                Material.ENDER_CHEST,
                Material.TRAP_DOOR,
                Material.TRAPPED_CHEST,
                Material.IRON_TRAPDOOR,
                Material.IRON_DOOR_BLOCK
        ));
    }

    public Protect getProtected_Block(Location location) {
        List<Protect> protects = getProtected_Blocks(location);
        return protects.isEmpty() ? null : protects.get(0);
    }

    public Protect getProtected_Block(Player player) {
        List<Protect> protects = getProtected_Blocks(player);
        return protects.isEmpty() ? null : protects.get(0);
    }

    public List<Protect> getProtected_Blocks(Location location ) {
        List<Protect> ptList = new ArrayList<>();

            try (ResultSet rs = DatabaseManager.get().executeResultStatement(
                    SQLQuery.SELECT_PROTECTED_BLOCK,
                    location.getWorld().getName(),
                    location.getX(),
                    location.getY(),
                    location.getZ()))
            {
                while (rs.next()) {
                    Protect protect = getLandFromResultSet(rs);

                        ptList.add(protect);
                }
            } catch (SQLException ex) {
                Utils.debugSqlException(ex);
                Utils.log("の土地を取得する際にエラーが発生しました" );
            }

        return ptList;
    }

    public List<Protect> getProtected_Blocks(Player player) {
        List<Protect> ptList = new ArrayList<>();

        try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.SELECT_PROTECTED_PLAYER_BLOCK_LIST,player.getUniqueId()))
        {
            while (rs.next()) {
                Protect protect = getLandFromResultSet(rs);

                ptList.add(protect);
            }
        } catch (SQLException ex) {
            Utils.debugSqlException(ex);
            Utils.log("の土地を取得する際にエラーが発生しました" );
        }

        return ptList;
    }

    public boolean isProtect(Location location) {
        return getProtected_Block(location) != null;
    }

    public Protect getLandFromResultSet(ResultSet rs) throws SQLException {
        return new Protect(
                rs.getString("name"),
                rs.getString("uuid"),
                new Location(Bukkit.getWorld(rs.getString("world")),
                        rs.getLong("x"),
                        rs.getLong("y"),
                        rs.getLong("z")),
                rs.getInt("id"));
    }

}
