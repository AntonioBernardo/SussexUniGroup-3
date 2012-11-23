create database IF NOT EXISTS trackertest;
drop database trackertest;
create database IF NOT EXISTS trackertest;

GRANT USAGE ON *.* TO 'trackertest'@'localhost';
GRANT USAGE ON *.* TO 'trackertest'@'%';

drop user 'trackertest'@'localhost';
drop USER 'trackertest'@'%';

CREATE USER 'trackertest'@'localhost' IDENTIFIED BY 'tracker';

grant INSERT, SELECT, UPDATE, DELETE on trackertest.* to 'trackertest'@'localhost';
CREATE USER 'trackertest'@'%' IDENTIFIED BY 'tracker';
GRANT INSERT, SELECT, UPDATE, DELETE ON trackertest.* TO 'trackertest'@'%';