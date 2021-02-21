-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema EventManagerDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema EventManagerDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `EventManagerDB` DEFAULT CHARACTER SET utf8 ;
USE `EventManagerDB` ;

-- -----------------------------------------------------
-- Table `EventManagerDB`.`Person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EventManagerDB`.`Person` (
  `idPerson` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `surName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPerson`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EventManagerDB`.`Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EventManagerDB`.`Room` (
  `idRoom` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `maxCapacity` INT NULL,
  `roomType` INT NULL,
  PRIMARY KEY (`idRoom`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EventManagerDB`.`Room_Allocation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EventManagerDB`.`Room_Allocation` (
  `Person_idPerson` INT NOT NULL,
  `Room_idRoom` INT NOT NULL,
  `eventPhase` INT NULL,
  PRIMARY KEY (`Person_idPerson`, `Room_idRoom`),
  INDEX `fk_Person_has_Room_Room1_idx` (`Room_idRoom` ASC) VISIBLE,
  INDEX `fk_Person_has_Room_Person_idx` (`Person_idPerson` ASC) VISIBLE,
  CONSTRAINT `fk_Person_has_Room_Person`
    FOREIGN KEY (`Person_idPerson`)
    REFERENCES `EventManagerDB`.`Person` (`idPerson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_has_Room_Room1`
    FOREIGN KEY (`Room_idRoom`)
    REFERENCES `EventManagerDB`.`Room` (`idRoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
