CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL UNIQUE,
    balance DECIMAL(10, 2) NOT NULL,
    version BIGINT
);
