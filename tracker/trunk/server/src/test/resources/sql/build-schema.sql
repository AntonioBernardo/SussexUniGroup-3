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



CREATE  TABLE IF NOT EXISTS`tracker`.`comments` (
 
 	`id` INT NOT NULL AUTO_INCREMENT ,
  
   	PRIMARY KEY (`id`) ,

  	`fK_use_id` INT NOT NULL ,

	`fK_loc_id` INT NOT NULL ,

	`comments` VARCHAR(50) NOT NULL , 

	`image` BLOB NOT NULL , 

	FOREIGN KEY (fK_use_id) REFERENCES user(id), 

	FOREIGN KEY (fK_loc_id) REFERENCES location(id) 

ON DELETE NO ACTION
  ON UPDATE NO ACTION);