package life.midorin.info.lifers.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class User {

    //UUID
    private  final UUID uuid;

    private transient User teleportRequester;
    private transient long teleportRequestTime;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public void requestTeleport(final User player) {
        if(player == null) {
            asBukkitPlayer().sendMessage(RED + "プレイヤーが見つかりません");
            return;
        }

        final String requesterName = asBukkitPlayer().getName();
        final String playerName = player.asBukkitPlayer().getName();

        if (requesterName.equals(playerName)) {
            asBukkitPlayer().sendMessage(RED +  "自分にリクエストを送ることはできません!");
            return;
        }

        if(player.teleportRequester != null && player.teleportRequester.asBukkitPlayer() == asBukkitPlayer()) {
            asBukkitPlayer().sendMessage(RED + "すでにリクエストを送りました。");
            return;
        }

        if(player.isRequestValid()) {
            asBukkitPlayer().sendMessage(GOLD + "リクエストが無効です");
            return;
        }

        player.teleportRequestTime = System.currentTimeMillis();
        player.teleportRequester = this;

        List<String> requesterTexts = Arrays.asList(
                RED + playerName + GOLD + "にリクエストを送信しました",
                GOLD + "キャンセルするには" + RED + "/tprequest cancel " + playerName + GOLD + "を実行してください"
        );

        List<String> receiverTexts = Arrays.asList(
                RED + requesterName + GOLD + "からテレポートのリクエスト",
                GOLD + "テレポートの要求を許可するには" + RED + "/tprequest accept " + GOLD + "を実行してください",
                GOLD + "テレポートの要求を許可するには" + RED + "/tprequest deny " + GOLD + "を実行してください",
                GOLD + "このテレポートの要求は" + RED + "60" + GOLD + "秒以内に回答してください"
        );

        requesterTexts.forEach(text -> asBukkitPlayer().sendMessage(text));
        receiverTexts.forEach(text -> player.asBukkitPlayer().sendMessage(text));
    }

    public void acceptRequest() {
        if(!isReceived()) {
            asBukkitPlayer().sendMessage(RED + "リクエストを受け取っていません");
            return;
        }

        if(!teleportRequester.isOnline()) {
            asBukkitPlayer().sendMessage(RED + "現在オフラインです");
            return;
        }

        if(!isRequestValid()) {
            asBukkitPlayer().sendMessage(GOLD + "リクエストが無効です");
            return;
        }
        asBukkitPlayer().sendMessage(GREEN + "リクエストを承認しました");
        teleportRequester.asBukkitPlayer().teleport(asBukkitPlayer());
        teleportRequester = null;
    }

    public void denyRequest() {
        if(!isReceived()) {
            asBukkitPlayer().sendMessage(RED + "リクエストを受け取っていません");
            return;
        }

        asBukkitPlayer().sendMessage(GOLD + "要求を拒否しました");
        teleportRequester = null;
    }

    public void cancelRequest(User user) {
        if(user == null) {
            asBukkitPlayer().sendMessage(RED + "プレイヤーが見つかりません");
            return;
        }

        if(!asBukkitPlayer().getName().equals(user.asBukkitPlayer().getName())) return;

        if(!user.isReceived()) {
            asBukkitPlayer().sendMessage(RED + "リクエストをしていません");
            return;
        }

        asBukkitPlayer().sendMessage(RED + "キャンセルしました");
        user.asBukkitPlayer().sendMessage(RED + "キャンセルされました");
        user.teleportRequester = null;
    }

    public boolean isReceived() {
        return teleportRequester != null;
    }

    public boolean isRequestValid() {
        if (teleportRequester != null) {
            final long time = 60; //1分
            final boolean timeout = (System.currentTimeMillis() - teleportRequestTime) / 1000 <= time;

            return timeout;
        }
        return false;
    }

    public User getTeleportRequest() {
        return teleportRequester;
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
