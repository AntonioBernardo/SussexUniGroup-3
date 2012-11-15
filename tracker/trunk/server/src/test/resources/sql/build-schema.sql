CREATE TABLE IF NOT EXISTS `tracker`.`User` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	PRIMARY KEY (`id`) ,
	`username` varchar(10) NOT NULL,
	`password` varchar(20) NOT NULL
);


CREATE TABLE IF NOT EXISTS `tracker`.`Location` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	 PRIMARY KEY (`id`) ,
	`FK_Use_id` INT NOT NULL ,
	`Longitude` Double NOT NULL , 
	`Latitude` Double NOT NULL ,
	`Loc_Date` Date NOT NULL , 
	FOREIGN KEY (FK_Use_id) REFERENCES User(id) 
	ON DELETE NO ACTION
ON UPDATE NO ACTION);