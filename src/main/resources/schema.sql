CREATE TABLE IF NOT EXISTS user_type (
    user_type_id VARCHAR(36) PRIMARY KEY,
    user_type_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(36) PRIMARY KEY,
    full_name VARCHAR(255),
    phone_number VARCHAR(255),
    user_password VARCHAR(255) NOT NULL,
    user_type_id VARCHAR(36) NOT NULL REFERENCES user_type (user_type_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS users_phone_number_unique
    ON users (phone_number);

CREATE TABLE IF NOT EXISTS customer (
    customer_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL UNIQUE REFERENCES users (user_id)
);