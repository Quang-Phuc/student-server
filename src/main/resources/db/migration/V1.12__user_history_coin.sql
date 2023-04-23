DROP TABLE IF EXISTS `user_history_coin`;
CREATE TABLE `user_history_coin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `coin` int NULL DEFAULT NULL,
  `activity_date` datetime(0) NULL DEFAULT NULL,
  `transaction` int NULL DEFAULT NULL,
  `description` varchar(2000),
  PRIMARY KEY (`id`) USING BTREE 
);