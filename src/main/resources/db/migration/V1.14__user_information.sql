DROP TABLE IF EXISTS `user_history_file`;
CREATE TABLE `user_history_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_hisoty_id` int(0) NULL DEFAULT NULL,
  `file_id` int(0) NULL DEFAULT NULL,
  `activity_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);