CREATE TABLE IF NOT EXISTS `tracker`.`User` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	PRIMARY KEY (`id`) ,
	`username` varchar(15) NOT NULL,
	`password` varchar(75) NOT NULL
);


CREATE TABLE IF NOT EXISTS `tracker`.`Location` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	 PRIMARY KEY (`id`) ,
	`FK_User_id` INT NOT NULL ,
	`Longitude` Double NOT NULL , 
	`Latitude` Double NOT NULL ,
	`timestamp` BIGINT NOT NULL , 
	FOREIGN KEY (FK_User_id) REFERENCES User(id) 
	ON DELETE NO ACTION
ON UPDATE NO ACTION);