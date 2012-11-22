CREATE TABLE IF NOT EXISTS `tracker`.`user` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	PRIMARY KEY (`id`) ,
	`username` varchar(15) NOT NULL,
	`password` varchar(75) NOT NULL
);


CREATE TABLE IF NOT EXISTS `tracker`.`location` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	 PRIMARY KEY (`id`) ,
	`fk_user_id` INT NOT NULL ,
	`longitude` Double NOT NULL , 
	`latitude` Double NOT NULL ,
	`timestamp_added` BIGINT NOT NULL , 
	FOREIGN KEY (fk_user_id) REFERENCES user(id) 
	ON DELETE NO ACTION
ON UPDATE NO ACTION);