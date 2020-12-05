package life.midorin.info.lifers.manager;

import life.midorin.info.lifers.Lifers;
import life.midorin.info.lifers.util.Land;
import life.midorin.info.lifers.util.MaterialType;
import life.midorin.info.lifers.util.SQLQuery;
import life.midorin.info.lifers.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LandManager {


    private static LandManager instance = null;
    private final Set<Land> lands = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> cached = Collections.synchronizedSet(new HashSet<>());

    public static synchronized LandManager get() {
        return instance == null ? instance = new LandManager() : instance;
    }

    public Land getLand(Location location) {
        List<Land> lands = getLands(location);
        return lands.isEmpty() ? null : lands.get(0);
    }

    public List<Land> getLands(Location location ) {
        List<Land> ptList = new ArrayList<>();

            try (ResultSet rs = DatabaseManager.get().executeResultStatement(
                    SQLQuery.SELECT_LANDS,
                    location.getWorld().getName(),
                    location.getX(),
                    location.getY(),
                    location.getZ()))
            {
                while (rs.next()) {
                    Land land  = getLandFromResultSet(rs);

                        ptList.add(land);
                }
            } catch (SQLException ex) {
                Utils.debugSqlException(ex);
                Utils.log("の土地を取得する際にエラーが発生しました" );
            }

        return ptList;
    }

    public boolean isProtect(Location location) {
        return getLand(location) != null;
    }

    public Land getLandFromResultSet(ResultSet rs) throws SQLException {
        return new Land(
                rs.getString("name"),
                rs.getString("uuid"),
                new Location(Bukkit.getWorld(rs.getString("world")),
                        rs.getLong("x"),
                        rs.getLong("y"),
                        rs.getLong("z")),
                MaterialType.valueOf(rs.getString("materialType")),
                rs.getInt("id"));
    }

}
