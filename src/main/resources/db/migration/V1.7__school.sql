DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);