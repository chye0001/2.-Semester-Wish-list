INSERT INTO users VALUES('test','password',TRUE);
INSERT INTO users VALUES('test2','password',TRUE);
INSERT INTO wishlist (name,picture,username) VALUES('test','picture','test');

INSERT INTO wish (name, description, link, price, picture, wishlist_id)
VALUES ('testName', 'testDescription', 'testLink', 0, 'testPicture', 1);