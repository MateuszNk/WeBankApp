DROP DATABASE IF EXISTS webankapp;
CREATE DATABASE webankapp CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci;
USE webankapp;

CREATE TABLE IF NOT EXISTS login_data (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS personal_data (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(32) NOT NULL,
    birth_date DATETIME NOT NULL,
    city VARCHAR(32) NOT NULL,
    postal_code VARCHAR(16) NOT NULL,
    address VARCHAR(64) NOT NULL,
    FOREIGN KEY (id) REFERENCES login_data (id)
);

CREATE TABLE IF NOT EXISTS news (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(80) NOT NULL,
    url VARCHAR(500) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    date_added DATETIME NOT NULL
);

insert into news (title, url, description, date_added) values ('molestie lorem quisque', 'http://ow.ly/odio/curabitur/convallis.js', 'sed accumsan felis ut at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero non mattis pulvinar nulla pede ullamcorper augue a suscipit nulla elit ac nulla sed vel enim', '2020-04-09 13:45:51');
insert into news (title, url, description, date_added) values ('hac', 'http://yahoo.co.jp/aliquam/non/mauris/morbi.xml', 'augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris vulputate elementum nullam varius nulla facilisi', '2020-01-22 11:55:54');
insert into news (title, url, description, date_added) values ('blandit lacinia', 'http://washington.edu/velit/id/pretium/iaculis/diam/erat.png', 'nec euismod scelerisque quam turpis adipiscing lorem vitae mattis nibh ligula nec sem duis aliquam convallis nunc proin at turpis a pede posuere nonummy integer non velit donec diam neque vestibulum eget vulputate ut ultrices vel augue vestibulum ante ipsum primis in', '2020-07-05 13:02:27');
insert into news (title, url, description, date_added) values ('ipsum primis in', 'http://pinterest.com/platea/dictumst/morbi/vestibulum/velit/id.json', 'lacus morbi quis tortor id nulla ultrices aliquet maecenas leo odio condimentum id luctus nec molestie sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est et tempus semper est quam pharetra magna ac', '2020-11-18 14:13:53');

ALTER TABLE login_data
ADD COLUMN phone_number VARCHAR(9) NOT NULL UNIQUE,
ADD COLUMN registration_date DATETIME NOT NULL;

ALTER TABLE login_data RENAME registration_data;

ALTER TABLE registration_data
RENAME COLUMN login TO username;

CREATE TABLE IF NOT EXISTS user_role (
    username VARCHAR(12) NOT NULL UNIQUE,
    role_name VARCHAR(20) NOT NULL DEFAULT 'USER',
    PRIMARY KEY (username, role_name),
    FOREIGN KEY (username) REFERENCES registration_data(username)
);

ALTER TABLE registration_data
ADD COLUMN newsletter BOOL NOT NULL;

ALTER TABLE registration_data
MODIFY username VARCHAR(32) NOT NULL UNIQUE;

ALTER TABLE user_role
MODIFY username VARCHAR(32) NOT NULL UNIQUE;