CREATE TABLE human (
    idx BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    money INT NOT NULL,
    birth DATE,
    version INT DEFAULT 1
);
