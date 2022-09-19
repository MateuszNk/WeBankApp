USE webankapp;

CREATE TABLE IF NOT EXISTS account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_number INT(26) NOT NULL UNIQUE,
    balance FLOAT NOT NULL DEFAULT(0),
    FOREIGN KEY (id) REFERENCES registration_data(id)
);

ALTER TABLE account
MODIFY account_number VARCHAR(26) NOT NULL UNIQUE;
