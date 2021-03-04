USE `nacos-test-metrics`;

DROP TABLE `register_instance_metric`;

CREATE TABLE IF NOT EXISTS `register_instance_metric`(
    `id` BIGINT UNSIGNED AUTO_INCREMENT,
    `test_no` BIGINT UNSIGNED,
    `client_id` VARCHAR(20),
    `sequence` BIGINT UNSIGNED NOT NULL DEFAULT 9999,
    `request` TEXT,
    `response` TEXT,
    `latency` BIGINT,
    `start_time` DATETIME,
    `end_time` DATETIME,
    `success` BIT,
    PRIMARY KEY (`id`),
    INDEX USING BTREE (`test_no`),
    INDEX USING BTREE (`client_id`),
    INDEX USING BTREE (`start_time`),
    INDEX USING BTREE (`end_time`)
);

CREATE TABLE IF NOT EXISTS `list_instance_metric`(
    `id` BIGINT UNSIGNED AUTO_INCREMENT,
    `test_no` BIGINT UNSIGNED,
    `client_id` VARCHAR(20),
    `sequence` BIGINT UNSIGNED NOT NULL DEFAULT 9999,
    `request` TEXT,
    `response` TEXT,
    `latency` BIGINT,
    `start_time` DATETIME,
    `end_time` DATETIME,
    `target_service` VARCHAR(200),
    `exist` BIT,
    PRIMARY KEY (`id`),
    INDEX USING BTREE (`test_no`),
    INDEX USING BTREE (`client_id`),
    INDEX USING BTREE (`start_time`),
    INDEX USING BTREE (`end_time`),
    INDEX USING BTREE (`target_service`)
);