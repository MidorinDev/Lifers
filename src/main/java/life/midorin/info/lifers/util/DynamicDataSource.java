package life.midorin.info.lifers.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import life.midorin.info.lifers.Lifers;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class DynamicDataSource {

    private HikariConfig config = new HikariConfig();

    public DynamicDataSource(boolean preferMySQL) throws ClassNotFoundException {
        if (preferMySQL) {
            //msqlも使用できるようにする
            /*Class.forName("com.mysql.jdbc.Driver");
            config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?"+properties);
            config.setUsername(usrName);
            config.setPassword(password);*/
        } else {
            String path = Lifers.getPlugin().getDataFolder().getPath() + "/data/";

            File dataFolder = new File(path);
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File databaseFile = new File(dataFolder, "sqlite.db");
            if (!databaseFile.exists()) {
                try {
                    databaseFile.createNewFile();
                } catch (IOException e) {
                    //エラー
                }
            }

            String driverClassName = "org.sqlite.JDBC";
            Class.forName(driverClassName);
            config.setDriverClassName(driverClassName);
            config.setJdbcUrl("jdbc:sqlite:" + path + "sqlite.db");

        }
    }

    public HikariDataSource generateDataSource(){
        return new HikariDataSource(config);
    }

}
