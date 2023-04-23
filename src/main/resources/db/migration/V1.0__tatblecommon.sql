DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `link` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `id_action` int(0) NOT NULL,
     `create_date` datetime(0) NULL DEFAULT NULL,
     `updated_date` datetime(0) NULL DEFAULT NULL,
    `created_id` int(0) NOT NULL,
     `update_date` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
