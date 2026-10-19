DROP TABLE IF EXISTS auction_order;
CREATE TABLE auction_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(64) NOT NULL,
    item_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    status VARCHAR(16) NOT NULL DEFAULT 'PENDING_PAY',
    create_time DATETIME NOT NULL,
    CONSTRAINT uk_order_item UNIQUE (item_id),
    CONSTRAINT uk_order_no UNIQUE (order_no)
);