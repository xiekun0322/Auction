DROP TABLE IF EXISTS auction_item;
CREATE TABLE auction_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_no VARCHAR(64) NOT NULL,
    title VARCHAR(128) NOT NULL,
    start_price DECIMAL(18,2) NOT NULL,
    deposit DECIMAL(18,2) NOT NULL,
    current_price DECIMAL(18,2) NOT NULL,
    leader_id BIGINT NULL,
    end_time DATETIME NOT NULL,
    status VARCHAR(16) NOT NULL DEFAULT 'ONGOING',
    version INT NOT NULL DEFAULT 0
);