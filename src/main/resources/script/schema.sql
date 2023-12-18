CREATE SCHEMA IF NOT EXISTS `weather_analyzer` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `weather_analyzer` ;

-- -----------------------------------------------------
-- Table `weather_analyzer`.`weather`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weather_analyzer`.`weather` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `date_time` VARCHAR(255) NULL DEFAULT NULL,
  `humidity` INT NOT NULL,
  `pressure` FLOAT NOT NULL,
  `temperature` FLOAT NOT NULL,
  `weather_condition` VARCHAR(255) NULL DEFAULT NULL,
  `wind_speed` FLOAT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;