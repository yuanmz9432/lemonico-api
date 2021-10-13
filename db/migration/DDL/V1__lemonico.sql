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
CREATE TABLE IF NOT EXISTS `lemonico`.`customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `uuid` VARCHAR(36) NOT NULL COMMENT 'UUID',
  `first_name` VARCHAR(45) NOT NULL COMMENT '姓',
  `last_name` VARCHAR(45) NOT NULL COMMENT '名',
  `sex` INT NOT NULL COMMENT '性別',
  `birthday` VARCHAR(8) NOT NULL COMMENT '生年月日（ハイフォンなし）',
  `email` VARCHAR(100) NOT NULL COMMENT 'メールアドレス',
  `created_by` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` VARCHAR(255) NOT NULL,
  `modified_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `create_time_UNIQUE` (`created_at` ASC)
) COMMENT='会員' ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
