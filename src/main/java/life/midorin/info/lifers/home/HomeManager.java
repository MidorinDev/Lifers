package life.midorin.info.lifers.home;

import life.midorin.info.lifers.manager.DatabaseManager;
import life.midorin.info.lifers.util.SQLQuery;
import life.midorin.info.lifers.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HomeManager {


    private static HomeManager instance = null;
    private Map<String,List<Home>> homes = new HashMap<>();

    public static synchronized HomeManager get() {
        return instance == null ? instance = new HomeManager() : instance;
    }

    public Home createAndLoadHome(Player player, Location location, String name) {
        try {
            DatabaseManager.get().executeStatement(SQLQuery.HOME_INSERT_DEFAULT,
                    name,
                    player.getUniqueId().toString(),
                    location.getWorld().getName(),
                    location.getX(),
                    location.getY(),
                    location.getZ(),
                    location.getYaw(),
                    location.getPitch()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loadHome(player, name).get();
    }

    public Optional<Home> loadHome(Player player, String name) {
        loadAllHomes();
        Home home = getPlayerHome(player.getUniqueId(), name);

        if (home == null) {
            return Optional.empty();
        }
        return Optional.of(home);
    }

    public void loadAllHomes() {
        this.homes = selectHomes();
    }

    public void deleteHome(Home home) {
        DatabaseManager.get().executeStatement(SQLQuery.HOME_DELETE, home.getId());
    }

    private Map<String,List<Home>> selectHomes() {
        Map<String, List<Home>> homes = new HashMap<>();

        try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.HOME_SELECT_ALL)) {

            while (rs.next()) {
                if (homes.get(rs.getString("uniqueId")) != null &&
                        homes.get(rs.getString("uniqueId")).contains(rs.getString("name"))) {
                    homes.get(rs.getString("uniqueId")).add(getPlayerHome(UUID.fromString(rs.getString("uniqueId")), rs.getString("name")));
                } else {
                    homes.put(rs.getString("uniqueId"), getPlayerHomeBy(UUID.fromString(rs.getString("uniqueId"))));
                }
            }
        } catch (SQLException ex) {
            Utils.debugSqlException(ex);
        }
        return homes;
    }

    public Home getPlayerHome(UUID uuid, String name) {
        Home home = null;
        try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.HOMES_SELECT_ALL_HOME_BY_UUID_NAME,uuid.toString(), name))
        {
            while (rs.next()) {
                home = getHomeFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Utils.debugSqlException(ex);
        }
        return home;
    }

    public List<Home> getPlayerHomeBy(UUID uuid) {
        List<Home> ptList = new ArrayList<>();

        try (ResultSet rs = DatabaseManager.get().executeResultStatement(SQLQuery.HOMES_SELECT_ALL_HOME_BY_UUID, uuid.toString()))
        {
            while (rs.next()) {
                Home home = getHomeFromResultSet(rs);
                ptList.add(home);
            }
        } catch (SQLException ex) {
            Utils.debugSqlException(ex);
        }

        return ptList;
    }

    public Home getHomeFromResultSet(ResultSet rs) throws SQLException {
        return new Home(
                rs.getString("name"),
                UUID.fromString(rs.getString("uniqueId")),
                new Location(Bukkit.getWorld(rs.getString("world")),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")),
                rs.getInt("id")
        );
    }
}
