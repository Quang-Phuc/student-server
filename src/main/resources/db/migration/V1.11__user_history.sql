DROP TABLE IF EXISTS `user_history`;
CREATE TABLE `user_history`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `activity_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);