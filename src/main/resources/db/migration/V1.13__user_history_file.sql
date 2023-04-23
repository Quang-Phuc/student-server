DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `birth_date` datetime(0) NULL DEFAULT NULL,
  `gender` smallint(0) NULL DEFAULT NULL,
  `school_id` int(0) NULL DEFAULT NULL,
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `specialized` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `yourself` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
