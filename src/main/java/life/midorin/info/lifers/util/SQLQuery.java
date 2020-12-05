package life.midorin.info.lifers.util;

import life.midorin.info.lifers.manager.DatabaseManager;

public enum  SQLQuery {

    CREATE_TABLE_LAND(
                "CREATE TABLE IF NOT EXISTS `Land` ("+
                        "`id` int NOT NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(16) NULL DEFAULT NULL," +
                        "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
                        "`world` VARCHAR(16) NULL DEFAULT NULL," +
                        "`x` BIGINT," +
                        "`y` BIGINT," +
                        "`z` BIGINT," +
                        "`materialType` VARCHAR(16) NULL DEFAULT NULL," +
                        "PRIMARY KEY (`id`))",

                "CREATE TABLE IF NOT EXISTS Land (" +
                        "id INTEGER PRIMARY KEY," +
                        "name VARCHAR(16)," +
                        "uuid VARCHAR(35)," +
                        "world VARCHAR(16)," +
                        "x BIGINT," +
                        "y BIGINT," +
                        "z BIGINT," +
                        "materialType VARCHAR(16))"
    ),
    INSERT_LAND(
            "INSERT INTO `Land` " +
                    "(`name`, `uuid`, `world`, `x`, `y`, `z`, `materialType`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)",

            "INSERT INTO Land " +
                    "(name, uuid, world, x, y, z, materialType) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
    ),
    SELECT_EXACT_LAND(
            "SELECT * FROM `Land` WHERE `uuid` = ? AND `materialType` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
            "SELECT * FROM Land WHERE uuid = ? AND materialType = ? AND world = ? AND x = ? AND y = ? AND z = ?"
    ),
    SELECT_LANDS(
            "SELECT * FROM `Land` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",
            "SELECT * FROM Land WHERE world = ? AND x = ? AND y = ? AND z = ?"
    ),
    DELETE_LAND(
            "DELETE FROM `Land` WHERE `id` = ?",
            "DELETE FROM Land WHERE id = ?"
    );

    private String mysql;
    private String sqlite;

    SQLQuery(String mysql, String sqlite) {
        this.mysql = mysql;
        this.sqlite = sqlite;
    }

    @Override
    public String toString() {
        return DatabaseManager.get().isUseMySQL() ? mysql : sqlite;
    }
}
