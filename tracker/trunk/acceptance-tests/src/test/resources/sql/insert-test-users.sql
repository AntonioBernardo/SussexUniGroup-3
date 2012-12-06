use trackertest;

replace into user (username, password, name, surname, age, gender, about, interests, lastLogginDate, signupDate) values ('testing_user1@test.com', 'e8f868b0d8fa4e8bdcc86ee8cffff972ed0507fb2b17b1153a570cfc8fca3e90', 'testName', 'testSurname', 21, 'M', 'Some interesting text', 'Conkers', 1354745217141, 1354745217141);


replace into user (username, password, name, surname, age, gender, about, interests, lastLogginDate, signupDate) values ('u@u.c', 'fa996088c088eb7a911c01719b697c89cfaa2ca12dd8129b8b8c7cf2a9f9846c', 'testName1', 'testSurname1', 101, 'F', '', 'Conkers', 1354745217141, 1354745217141);

replace into user (username, password, name, surname, age, gender, about, interests, lastLogginDate, signupDate) values ('another_user@test.com', 'fa996088c088eb7a911c01719b697c89cfaa2ca12dd8129b8b8c7cf2a9f9846c', 'testName2', 'testSurname2', 54, 'F', '', 'Bananas', 1354745217141, 1354745217141);

replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='testing_user1@test.com'), 45.563, 34.98, 43567768674); -- location for user testing_user1 being old and miles awaz from anywhere else
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='testing_user1@test.com'), 45745.563, 22234.98, 53567768674); -- location for user testing_user1
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='u@u.c'), 45745.568, 22234.94, 47567768674); -- location for user u in range of testing_user1's location
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='u@u.c'), 455.568, 24532234.94, 47567768664); -- location for user u out of range of testing_user1`s location and being old
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='another_user@test.com'), 455.568, 24532234.94, 47567768666); -- location for user another_user out of range of testing_user1`s location

replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='another_user@test.com'), 45747.568, 22237.94, 47567768655); -- location for user another_user in range of testing_user1`s location but old

replace into comments (fk_user_id, fk_loc_id, comments, timestamp_added) values ((select id from user where username='testing_user1@test.com'), (select id from location where timestamp_added = 47567768655), 'a nice comment', 66666665111);

replace into comments (fk_user_id, fk_loc_id, comments, timestamp_added) values ((select id from user where username='testing_user1@test.com'), (select id from location where timestamp_added = 47567768655), 'another nice comment', 66666666433);

