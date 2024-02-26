BEGIN;

DROP TABLE IF EXISTS user_ticket;
DROP TABLE IF EXISTS lottery;
DROP TABLE IF EXISTS users;

CREATE TABLE lottery (
                         ticket_id SERIAL PRIMARY KEY,
                         ticket_number VARCHAR(6) NOT NULL,
                         price INTEGER NOT NULL,
                         amount INTEGER NOT NULL
);

CREATE TABLE users (
                       user_id VARCHAR(10) NOT NULL PRIMARY KEY,
                        ticket_number VARCHAR(6) NOT NULL,
                       price INTEGER NOT NULL,
                       datetime TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE TABLE user_ticket (
                             transaction_id SERIAL PRIMARY KEY,
                             datetime TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                             ticket_number VARCHAR(6) NOT NULL,
                             user_id VARCHAR(10) NOT NULL,
                             price INTEGER NOT NULL,
                             transaction_type VARCHAR(4) CHECK (transaction_type IN ('BUY', 'SELL'))
);

CREATE INDEX idx_ticket_id ON lottery(ticket_id);
CREATE INDEX idx_user_id ON users(user_id);

COMMIT;