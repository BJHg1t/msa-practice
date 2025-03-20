CREATE DATABASE IF NOT EXISTS `info`;
USE info;
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    num VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    create_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP NOT NULL,
    version INT NOT NULL
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;