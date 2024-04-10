CREATE DATABASE IF NOT EXISTS wishlist_db;
USE wishlist_db;

CREATE TABLE users(
    username VARCHAR(50) NOT NULL PRIMARY KEY,
	password VARCHAR(500) NOT NULL,
	enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
	username VARCHAR(50) NOT NULL,
	authority VARCHAR(50) NOT NULL,
	CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);


CREATE TABLE wishlist(
    wishlist_id INT AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    picture VARCHAR(250),
    username VARCHAR(50),
    PRIMARY KEY (wishlist_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE wish(
    wish_id INT AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(500),
    link VARCHAR(250),
    price DECIMAL(11,2),
    picture VARCHAR(250),
    reserved BOOLEAN DEFAULT FALSE,
    wishlist_id INT,
    PRIMARY KEY (wish_id),
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id)
);