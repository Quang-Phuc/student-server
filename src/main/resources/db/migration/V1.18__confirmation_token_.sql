DROP TABLE IF EXISTS `confirmation_token`;
CREATE TABLE `confirmation_token`  (
  `token_id` int(0) NOT NULL AUTO_INCREMENT,
   `confirmation_token` varchar(255) CHARACTER SET utf8  NULL DEFAULT NULL,
    `created_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`token_id`) USING BTREE
);

