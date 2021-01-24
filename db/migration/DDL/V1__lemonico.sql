-- MySQL Script generated by MySQL Workbench
-- Mon Mar  2 12:03:39 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

DROP database IF EXISTS `lemonico`;
CREATE DATABASE `lemonico`;
use `lemonico`;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='';

-- -----------------------------------------------------
-- Table `lemonico`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lemonico`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'アカウントID',
  `first_name` VARCHAR(45) NOT NULL COMMENT '姓',
  `last_name` VARCHAR(45) NOT NULL COMMENT '名',
  `email` VARCHAR(100) NOT NULL COMMENT 'メールアドレス',
  `password` VARCHAR(100) NOT NULL COMMENT 'パスワード',
  `version_no` INT NOT NULL,
  `create_by` VARCHAR(30) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_by` VARCHAR(30) NOT NULL,
  `update_time` DATETIME NOT NULL,
  `is_actived` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `create_time_UNIQUE` (`create_time` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
