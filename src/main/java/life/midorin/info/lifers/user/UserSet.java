package life.midorin.info.lifers.user;

import life.midorin.info.lifers.LifersPlugin;
import life.midorin.info.lifers.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserSet implements PlayerJoinListener {

    private static UserSet instance;

    public static void load(){
        instance = new UserSet();
    }

    public static UserSet getInstnace(){
        return instance;
    }

    private final LifersPlugin plugin = LifersPlugin.getPlugin();

    //ユーザーデータを保存するフォルダー
    //public final File folder = new File(plugin.getDataFolder() + File.separator + "Users");

    private final Map<UUID, User> users = new HashMap<>();

    private UserSet(){
        //フォルダーが存在しなければ作成する
        /*if(!folder.exists()) folder.mkdirs();

        for(File file : Optional.ofNullable(folder.listFiles()).orElse(new File[0])){
            //ファイルをコンフィグとして読み込む
            Yaml yaml = new Yaml(plugin, file, "user.yml");*/

            //コンフィグを基にユーザーを生成する
            //User user = new User(yaml);

            //登録する
            //users.put(user.asBukkitPlayer().getUniqueId(), user);
    }

    public List<User> getOnlineUsers(){
        return Bukkit.getOnlinePlayers().stream().map(this::getUser).collect(Collectors.toList());
    }

    public User getUser(Player player){
        if(player == null) return null;

        final UUID uuid = player.getUniqueId();

        if(users.containsKey(uuid)) return users.get(player.getUniqueId());

        //コンフィグを基にユーザーを生成する
        User user = new User(uuid);

        //登録する
        users.put(uuid, user);

        return users.get(player.getUniqueId());
    }

    public User getUser(UUID uuid){
        if(users.containsKey(uuid)) return users.get(uuid);

        //コンフィグを基にユーザーを生成する
        User user = new User(uuid);

        //登録する
        users.put(uuid, user);

        return users.get(uuid);
    }

    public boolean containsUser(Player player){
        return containsUser(player.getUniqueId());
    }

    public boolean containsUser(UUID uuid){
        return users.containsKey(uuid);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        System.out.println("join");
        //既にユーザーデータが存在するのであれば戻る
        if(users.containsKey(uuid)) return;

        //コンフィグを基にユーザーを生成する
        User user = new User(uuid);

        //登録する
        users.put(uuid, user);
    }
}
