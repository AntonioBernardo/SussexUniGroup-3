use trackertest;

replace into user (username, password) values ('testing_user1', 'e8f868b0d8fa4e8bdcc86ee8cffff972ed0507fb2b17b1153a570cfc8fca3e90');

replace into user (username, password) values ('u', 'fa996088c088eb7a911c01719b697c89cfaa2ca12dd8129b8b8c7cf2a9f9846c');

replace into user (username, password) values ('another_user', 'fa996088c088eb7a911c01719b697c89cfaa2ca12dd8129b8b8c7cf2a9f9846c');

replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='testing_user1'), 45.563, 34.98, 43567768674); -- location for user testing_user1 being old and miles awaz from anywhere else
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='testing_user1'), 45745.563, 22234.98, 53567768674); -- location for user testing_user1
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='u'), 45745.568, 22234.94, 47567768674); -- location for user u in range of testing_user1's location
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='u'), 455.568, 24532234.94, 47567768664); -- location for user u out of range of testing_user1`s location and being old
replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='another_user'), 455.568, 24532234.94, 47567768666); -- location for user another_user out of range of testing_user1`s location

replace into location (fk_user_id, longitude, latitude, timestamp_added) values ((select id from user where username='another_user'), 45747.568, 22237.94, 47567768655); -- location for user another_user in range of testing_user1`s location but old

replace into comments (fk_user_id, fk_loc_id, comments, timestamp_added) values ((select id from user where username='testing_user1'), (select id from location where timestamp_added = 47567768655), 'a nice comment', 66666665111);

replace into comments (fk_user_id, fk_loc_id, comments, timestamp_added) values ((select id from user where username='testing_user1'), (select id from location where timestamp_added = 47567768655), 'another nice comment', 66666666433);