package life.midorin.info.lifers.user;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    /**
    * Essentialsをベースに作成
     */

    //UUID
    private  final UUID uuid;

    private transient User teleportRequester;
    private transient boolean teleportRequestHere;
    private transient Location teleportLocation;
    private transient long teleportRequestTime;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public void requestTeleport(final User player, final boolean here) {
        teleportRequestTime = System.currentTimeMillis();
        teleportRequester = player == null ? null : player;
        teleportRequestHere = here;
        if (player == null) {
            teleportLocation = null;
        } else {
            teleportLocation = here ? asBukkitPlayer().getLocation() : /*this.getLocation()*/ player.asBukkitPlayer().getLocation();
        }
    }

    public boolean hasOutstandingTeleportRequest() {
        if (getTeleportRequest() != null) { // Player has outstanding teleport request.
            final long timeout = 60;
            if (timeout != 0) {
                if ((System.currentTimeMillis() - getTeleportRequestTime()) / 1000 <= timeout) { // Player has outstanding request
                    return true;
                } else { // outstanding request expired.
                    requestTeleport(null, false);
                    return false;
                }
            } else { // outstanding request does not expire
                return true;
            }
        }
        return false;
    }

    public User getTeleportRequest() {
        return teleportRequester;
    }

    public boolean isTpRequestHere() {
        return teleportRequestHere;
    }

    public Location getTpRequestLocation() {
        return teleportLocation;
    }

    public long getTeleportRequestTime() {
        return teleportRequestTime;
    }

    public boolean isOnline() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(asBukkitPlayer().getName())) {
                return true;
            }
        }
        return false;
    }

    //このユーザーに対応したプレイヤーを取得する
    public Player asBukkitPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
