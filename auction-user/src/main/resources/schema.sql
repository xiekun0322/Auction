DROP TABLE IF EXISTS account;
CREATE TABLE account (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    balance DECIMAL(18,2) NOT NULL DEFAULT 0,
    frozen  DECIMAL(18,2) NOT NULL DEFAULT 0,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT uk_acct_user UNIQUE (user_id)
);

DROP TABLE IF EXISTS fund_flow;
CREATE TABLE fund_flow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    flow_no VARCHAR(64) NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    type VARCHAR(16) NOT NULL,
    create_time DATETIME NOT NULL,
    CONSTRAINT uk_flow_no UNIQUE (flow_no)
);

DROP TABLE IF EXISTS local_message;
CREATE TABLE local_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    msg_key VARCHAR(64) NOT NULL,
    topic VARCHAR(64) NOT NULL,
    payload TEXT,
    status VARCHAR(16) NOT NULL DEFAULT 'PENDING',
    retry INT NOT NULL DEFAULT 0,
    create_time DATETIME NOT NULL,
    CONSTRAINT uk_msg_key UNIQUE (msg_key)
);

DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    role VARCHAR(16) NOT NULL,
    CONSTRAINT uk_username UNIQUE (username)
);