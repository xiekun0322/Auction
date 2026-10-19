DROP TABLE IF EXISTS reconcile_diff;
CREATE TABLE reconcile_diff (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    db_balance DECIMAL(18,2) NOT NULL,
    flow_balance DECIMAL(18,2) NOT NULL,
    diff DECIMAL(18,2) NOT NULL,
    status VARCHAR(16) NOT NULL DEFAULT 'PENDING',
    create_time DATETIME NOT NULL
);