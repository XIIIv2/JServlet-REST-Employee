CREATE SCHEMA IF NOT EXISTS `servlet_rest_employee`
DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `servlet_rest_employee`.`employees` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `employee_name` VARCHAR(128) NOT NULL,
  `position` VARCHAR(128) NOT NULL,
  `phone` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`));
