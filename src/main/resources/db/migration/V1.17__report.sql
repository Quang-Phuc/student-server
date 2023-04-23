DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(0) NULL DEFAULT NULL,
  `id_user` INT NOT NULL,
  `id_file` INT NOT NULL,
  `type_report` VARCHAR(45) NOT NULL,
  `content_report` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
