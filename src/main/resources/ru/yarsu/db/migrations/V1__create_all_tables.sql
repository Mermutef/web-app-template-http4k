DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS degrees;
DROP TABLE IF EXISTS announcements;

CREATE TABLE users (
	id INT PRIMARY KEY,
	fcs TEXT NOT NULL,
	degree INT[],
	phone TEXT NOT NULL,
	vkId TEXT NOT NULL,
	login TEXT UNIQUE NOT NULL,
    password BYTEA NOT NULL,
    registerDate TIMESTAMP NOT NULL,
    permissions INT NOT NULL
);

CREATE TABLE categories (
	id INT PRIMARY KEY,
	ru TEXT NOT NULL,
	needLicense BOOLEAN NOT NULL
);

CREATE TABLE degrees (
	id INT PRIMARY KEY,
	type TEXT NOT NULL,
	ru TEXT NOT NULL
);

CREATE TABLE announcements (
	id INT PRIMARY KEY,
	publishingDate TIMESTAMP NOT NULL,
	category INT NOT NULL,
	title TEXT NOT NULL,
	description TEXT NOT NULL,
    specialist INT NOT NULL
);