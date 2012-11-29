use trackertest;

CREATE TABLE IF NOT EXISTS `user` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	PRIMARY KEY (`id`) ,
	`username` varchar(15) NOT NULL,
	`password` varchar(75) NOT NULL
);


CREATE TABLE IF NOT EXISTS `location` (
	`id` INT NOT NULL AUTO_INCREMENT ,
	 PRIMARY KEY (`id`) ,
	`fk_user_id` INT NOT NULL ,
	`longitude` Double NOT NULL , 
	`latitude` Double NOT NULL ,
	`timestamp_added` BIGINT NOT NULL , 
	FOREIGN KEY (fk_user_id) REFERENCES user(id) 
	ON DELETE NO ACTION
ON UPDATE NO ACTION);

CREATE  TABLE IF NOT EXISTS `comments` (

 	`id` INT NOT NULL AUTO_INCREMENT ,
   	PRIMARY KEY (`id`) ,
  	`fk_user_id` INT NOT NULL ,
	`fk_loc_id` INT NOT NULL ,
	`comments` VARCHAR(128) NOT NULL ,
	`timestamp_added` BIGINT NOT NULL , 
	`image` BLOB, 
	FOREIGN KEY (fk_user_id) REFERENCES user(id), 
	FOREIGN KEY (fk_loc_id) REFERENCES location(id) 
ON DELETE NO ACTION
  ON UPDATE NO ACTION);