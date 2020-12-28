package life.midorin.info.lifers.util;

import life.midorin.info.lifers.manager.DatabaseManager;

public enum  SQLQuery {

    CREATE_TABLE_PROTECTED_BLOCKS(
            "CREATE TABLE IF NOT EXISTS `Protected_blocks` ("+
                    "`id` int NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(16) NULL DEFAULT NULL," +
                    "`uuid` VARCHAR(35) NULL DEFAULT NULL," +
                    "`world` VARCHAR(16) NULL DEFAULT NULL," +
                    "`x` BIGINT," +
                    "`y` BIGINT," +
                    "`z` BIGINT," +
                    "`materialType` VARCHAR(16) NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`))",

            "CREATE TABLE IF NOT EXISTS Protected_blocks (" +
                    "id INTEGER PRIMARY KEY," +
                    "name VARCHAR(16)," +
                    "uuid VARCHAR(35)," +
                    "world VARCHAR(16)," +
                    "x BIGINT," +
                    "y BIGINT," +
                    "z BIGINT," +
                    "materialType VARCHAR(16))"
    ),
    INSERT_PROTECTED_BLOCK(
            "INSERT INTO `Protected_blocks` " +
                    "(`name`, `uuid`, `world`, `x`, `y`, `z`, `materialType`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)",

            "INSERT INTO Protected_blocks " +
                    "(name, uuid, world, x, y, z, materialType) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
    ),
    SELECT_EXACT_PROTECTED_BLOCK(
            "SELECT * FROM `Protected_blocks` WHERE `uuid` = ? AND `materialType` = ? AND `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",

            "SELECT * FROM Protected_blocks WHERE uuid = ? AND materialType = ? AND world = ? AND x = ? AND y = ? AND z = ?"
    ),
    SELECT_PROTECTED_BLOCK(
            "SELECT * FROM `Protected_blocks` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?",

            "SELECT * FROM Protected_blocks WHERE world = ? AND x = ? AND y = ? AND z = ?"
    ),
    SELECT_PROTECTED_PLAYER_BLOCK_LIST(
            "SELECT * FROM `Protected_blocks` WHERE `uuid` = ?",

            "SELECT * FROM Protected_blocks WHERE uuid = ?"
    ),
    DELETE_PROTECTED_BLOCK(
            "DELETE FROM `Protected_blocks` WHERE `id` = ?",

            "DELETE FROM Protected_blocks WHERE id = ?"
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
