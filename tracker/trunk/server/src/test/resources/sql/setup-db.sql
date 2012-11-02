create database IF NOT EXISTS tracker;

GRANT USAGE ON *.* TO 'tracker'@'localhost';
GRANT USAGE ON *.* TO 'tracker'@'%';

drop user 'tracker'@'localhost';
drop USER 'tracker'@'%';

CREATE USER 'tracker'@'localhost' IDENTIFIED BY 'tracker';

grant INSERT, SELECT, UPDATE, DELETE on tracker.* to 'tracker'@'localhost';
CREATE USER 'tracker'@'%' IDENTIFIED BY 'tracker';
GRANT INSERT, SELECT, UPDATE, DELETE ON tracker.* TO 'tracker'@'%';