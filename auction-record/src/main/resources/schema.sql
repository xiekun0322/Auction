DROP TABLE IF EXISTS bid_record;
CREATE TABLE bid_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    idem_key VARCHAR(64) NOT NULL,
    create_time DATETIME NOT NULL,
    CONSTRAINT uk_bid_idem UNIQUE (idem_key)
);