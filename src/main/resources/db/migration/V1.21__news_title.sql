DROP TABLE IF EXISTS `news_title`;
CREATE TABLE `news_title`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL,
    `create_date` datetime(0) NULL DEFAULT NULL,
     `updated_date` datetime(0) NULL DEFAULT NULL,
    `created_id` int(0) NOT NULL,
     `update_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
