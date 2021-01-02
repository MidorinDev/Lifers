-- Lifers SQLITE Schema

CREATE TABLE IF NOT EXISTS `Protected_blocks` 
    `id`           INTEGER PRIMARY KEY NOT NULL,
    `uuid`         VARCHAR(36)         NOT NULL,
    `world`        VARCHAR(64)         NOT NULL,
    `x`            BIGINT              NOT NULL,
    `y`            BIGINT              NOT NULL, 
    `z`            BIGINT              NOT NULL,
    `materialType` VARCHAR(36)         NOT NULL
);

CREATE TABLE IF NOT EXISTS `Protected_block_Members` (
    `id`                INTEGER PRIMARY KEY NOT NULL,
    `protected_blockId` VARCHAR(16)         NOT NULL,
    `uuid`              VARCHAR(36)         NOT NULL
);

CREATE TABLE IF NOT EXISTS `Homes` (
    `id`    INTEGER PRIMARY KEY NOT NULL,
    `uuid`  VARCHAR(36)         NOT NULL,
    `world` VARCHAR(64)         NOT NULL,
    `x`     BIGINT              NOT NULL,
    `y`     BIGINT              NOT NULL,
    `z`     BIGINT              NOT NULL,
    `Yaw`   BIGINT              NOT NULL,
    `Pitch` BIGINT              NOT NULL,
);