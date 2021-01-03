package life.midorin.info.lifers.home;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Home {

    private final String name;
    private final UUID uniqueId;
    private final World world;
    private final double x, y, z;
    private final float yaw, pitch;
    private final Location location;
    private int id;

    public Home(String name ,UUID uniqueId, Location location, int id) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.world = location.getWorld();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.location = location;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public Location getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

}
