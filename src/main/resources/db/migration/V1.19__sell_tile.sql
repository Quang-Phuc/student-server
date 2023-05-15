DROP TABLE IF EXISTS `sell_title`;
CREATE TABLE `sell_title`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL,
    `create_date` datetime(0) NULL DEFAULT NULL,
     `updated_date` datetime(0) NULL DEFAULT NULL,
    `created_id` int(0) NOT NULL,
  `update_id` int(0),
  PRIMARY KEY (`id`) USING BTREE
);
