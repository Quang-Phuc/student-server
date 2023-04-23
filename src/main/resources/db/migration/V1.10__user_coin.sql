DROP TABLE IF EXISTS `user_coin`;
CREATE TABLE `user_coin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `total_coin` float NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_UNIQUE`(`user_id`) USING BTREE
);
