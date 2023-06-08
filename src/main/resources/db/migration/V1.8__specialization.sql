DROP TABLE IF EXISTS `specialization`;
CREATE TABLE `specialization`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `value` varchar(500) CHARACTER SET utf8  NULL DEFAULT NULL,
  `industryId` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
