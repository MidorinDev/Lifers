package life.midorin.info.lifers.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "Join" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(ChatColor.WHITE + "[" + ChatColor.RED + "Quit" + ChatColor.WHITE + "] " + ChatColor.GRAY + p.getName());
    }

}
