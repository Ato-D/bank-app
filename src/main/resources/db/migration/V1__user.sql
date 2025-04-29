CREATE TABLE IF NOT EXISTS tbl_user (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    other_name VARCHAR(255),
    gender VARCHAR(50),
    address VARCHAR(255),
    state_of_origin VARCHAR(255),
    account_number VARCHAR(255),
    account_balance DECIMAL(15, 2),
    email VARCHAR(255),
    phone_number VARCHAR(50),
    alternative_phone_number VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
    );