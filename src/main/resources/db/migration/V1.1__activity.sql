DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `activity` varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL,
   `name` varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
