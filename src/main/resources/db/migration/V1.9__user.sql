DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) CHARACTER SET utf8  NOT NULL,
  `password` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `email` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `phone` varchar(13) CHARACTER SET utf8  NULL DEFAULT NULL,
  `is_enable` tinyint(0) NULL DEFAULT NULL,
  `is_deleted` tinyint(0) NULL DEFAULT NULL,
  `role_id` int(0) NULL DEFAULT NULL,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `industry_id` int(0) NOT NULL,
  `birth_day` DATE NULL DEFAULT NULL,
  `fullName` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `gender` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `address` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `introduction` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,

--   `image` varchar(5000) ,
  PRIMARY KEY (`id`) USING BTREE
--   UNIQUE INDEX `user_name_UNIQUE`(`user_name`) USING BTREE,
--   UNIQUE INDEX `email_UNIQUE`(`email`) USING BTREE,
--   UNIQUE INDEX `phone_UNIQUE`(`phone`) USING BTREE
);
