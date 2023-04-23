DROP TABLE IF EXISTS `sell_detail`;
CREATE TABLE `sell_detail`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
    `title_id` int(0) NOT NULL,
    `image_id` int(0) NOT NULL,
    `province_id` int(0) NOT NULL,
    `district_id` int(0) NOT NULL,
    `ward_id` int(0) NOT NULL,
    `detail` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
     `name` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_date` datetime(0) NULL DEFAULT NULL,
    `updated_date` datetime(0) NULL DEFAULT NULL,
    `created_id` int(0) NOT NULL,
     `update_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
