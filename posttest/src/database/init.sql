BEGIN;

DROP TABLE IF EXISTS user_ticket;
DROP TABLE IF EXISTS lottery;
DROP TABLE IF EXISTS users;

CREATE TABLE lottery (
                         ticket_id SERIAL PRIMARY KEY,
                         ticket_number VARCHAR(6) NOT NULL,
                         price INTEGER NOT NULL,
                         created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                         updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE TABLE users (
                       user_id VARCHAR(10) NOT NULL PRIMARY KEY,
                       created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                       updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE TABLE user_ticket (
                             transaction_id SERIAL PRIMARY KEY,
                             datetime TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                             ticket_id INT NOT NULL,
                             user_id VARCHAR(10) NOT NULL,
                             price DECIMAL NOT NULL,
                             transaction_type VARCHAR(4) CHECK (transaction_type IN ('BUY', 'SELL')),
                             created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                             updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
                             FOREIGN KEY (ticket_id) REFERENCES lottery(ticket_id),
                             FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_ticket_id ON user_ticket(ticket_id);
CREATE INDEX idx_user_id ON user_ticket(user_id);

COMMIT;