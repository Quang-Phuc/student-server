DROP TABLE IF EXISTS `file_price`;
CREATE TABLE `file_price`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `file_id` int(0) NULL DEFAULT NULL,
  `price` float NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);