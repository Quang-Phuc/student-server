DROP TABLE IF EXISTS `user_report`;
CREATE TABLE `user_report`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `file_id` int(0) NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `updated_date` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
)