SELECT account_id, accounts.user_id, balance, username FROM accounts
JOIN users ON accounts.user_id = users.user_id
WHERE username = ?;


INSERT INTO users(user_id, username, password_hash)
VALUES (DEFAULT, ?, ?) RETURNING user_id;

SELECT * FROM users;

START TRANSACTION;                                                                         
ROLLBACK;

SELECT account_id, user_id, balance FROM accounts WHERE account_id = ?;


SELECT user_id, username FROM users WHERE username != ?;

SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, a.username AS from_username, b.username AS to_username
FROM transfers
JOIN accounts ac ON transfers.account_from = ac.account_id
JOIN accounts bc ON transfers.account_to = bc.account_id
JOIN users a ON ac.user_id = a.user_id
JOIN users b ON bc.user_id = b.user_id
WHERE transfer_id = ?;

SELECT * FROM users;
SELECT * FROM accounts;
SELECT * FROM transfers;


SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount  FROM transfers;

SELECT * FROM transfer_statuses;
SELECT * FROM transfer_types;

INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) 
VALUES (2, 2, 0, 0, 0, 0);

SELECT user_id FROM users WHERE username = ?;

INSERT INTO accounts(account_id, user_id, balance)
VALUES(DEFAULT, (SELECT user_id FROM users WHERE username = ?), ?);

DELETE FROM users WHERE user_id = 1001;

SELECT * FROM accounts;

START TRANSACTION;
ROLLBACK;
COMMIT;

SELECT account_id FROM accounts WHERE user_id = ?;

INSERT INTO accounts(account_id, user_id, balance)
VALUES(DEFAULT, ?, ?);

SELECT * FROM users;

INSERT INTO users (user_id , username , password_hash)
VALUES (DEFAULT , ? , ? );

SELECT * FROM accounts;

INSERT INTO accounts (account_id, user_id, balance)
VALUES(DEFAULT, ?, ?);

SELECT * FROM accounts;

UPDATE accounts 
LEFT JOIN transfers ON accounts.account_id = transfers.account_from
SET accounts.balance = account.balance + transfer.amount
WHERE transfers.account_id = (SELECT accounts.account_id FROM accounts WHERE user_id = ? );

UPDATE accounts SET balance = (balance - ?) WHERE user_id = ?; 

SELECT transfer_type_id, transfer_status_id, account_from, account_to , amount FROM transfers;

SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to , amount 
FROM transfers
WHERE account_from = ?;

UPDATE accounts 
SET balance = (balance - ?)
WHERE account_id = (SELECT account_id FROM accounts
WHERE user_id = ?);

SELECT * FROM accounts;

SELECT account_id FROM accounts WHERE user_id = ?;

SELECT user_id FROM users WHERE username = ?;

UPDATE accounts
SET balance = (balance - ?)
WHERE account_id = (SELECT account_id FROM accounts
WHERE user_id = (SELECT user_id FROM users WHERE username = ?));

SELECT account_id, user_id, balance FROM accounts WHERE account_id = (SELECT account_id FROM accounts
WHERE user_id = (SELECT user_id FROM users WHERE username = ?));

INSERT INTO accounts(account_id, user_id, balance)
VALUES(DEFAULT, (SELECT user_id FROM users WHERE username = ?), ?) RETURNING account_id;

SELECT user_id FROM users WHERE username = ?;

SELECT * FROM accounts;
SELECT * FROM users;

INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) 
VALUES ( 2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?) 
RETURNING transfer_id;
--Are we returning a transfer_id? should we include this in out Select in our Query?

INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) 
VALUES ( 2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?) RETURNING transfer_id;
--Thi

SELECT * FROM accounts;

SELECT account_id FROM accounts WHERE user_id = ?;

DELETE FROM accounts WHERE user_id = 1009;

SELECT * FROM transfers;


SELECT account_id FROM accounts WHERE user_id = ?;

SELECT account_id
FROM accounts
WHERE user_id = ?; 


INSERT INTO users (user_id, username, password_hash) VALUES (DEFAULT, '', '');
