BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO users(username, password_hash, first_name, last_name, role) VALUES ('brian', '$2a$10$nP3XiNhuj1EzpJOv.wDrZOml9zV6eh8HLcLvwr/SPmxr9l7Fr6a1u', 'Brian', 'Engler', 'ROLE_ADMIN');
INSERT INTO users(username, password_hash, first_name, last_name, role) VALUES ('hagen', '$2a$10$K2VTobYUZldGdrgbiyvEJOdaUyUhUjPMivimAOdkkzkbVN.ARjsdy', 'Hagen', 'White', 'ROLE_ADMIN');
INSERT INTO users(username, password_hash, first_name, last_name, role) VALUES ('ellie', '$2a$10$wQ.YP3uUU6aLPafRg8yzwOemDDlrjxwJJ7SBHqU7Ag0zvbxeJV36y', 'Ellie', 'Lin', 'ROLE_ADMIN');
INSERT INTO users(username, password_hash, first_name, last_name, role) VALUES ('sam', '$2a$10$DS0l9zbQkTEmVB7xrDxxdeptgRaDyQk/i9EVQanPKpukbHi08aaam', 'Sam', 'VanBennekom', 'ROLE_ADMIN');

INSERT INTO game (name_of_game, game_start_date, game_end_date, owner_name) VALUES ('test game 1', '2023-9-25', '2023-10-25','ellie');
INSERT INTO game (name_of_game, game_start_date, game_end_date, owner_name) VALUES ('test game 2', '2023-9-21', '2018-10-21', 'hagen');

INSERT INTO game_user (game_id, user_id) VALUES ('1','1');
INSERT INTO game_user (game_id, user_id) VALUES ('1','4');
INSERT INTO game_user (game_id, user_id) VALUES ('1','5');
INSERT INTO game_user (game_id, user_id) VALUES ('2','1');
INSERT INTO game_user (game_id, user_id) VALUES ('2','3');
INSERT INTO game_user (game_id, user_id) VALUES ('2','4');
INSERT INTO game_user (game_id, user_id) VALUES ('2','5');
INSERT INTO game_user (game_id, user_id) VALUES ('2','6');

INSERT INTO stock (symbol) VALUES ('AAPL');
INSERT INTO stock (symbol) VALUES ('ACN');
INSERT INTO stock (symbol) VALUES ('AMT');
INSERT INTO stock (symbol) VALUES ('ATO');
INSERT INTO stock (symbol) VALUES ('ATI');

INSERT INTO transaction (transaction_type, price, number_of_shares, user_id, stock_id, game_id) VALUES ('buy', '20', 20, 3, 5, 1);
INSERT INTO transaction (transaction_type, price, number_of_shares, user_id, stock_id, game_id) VALUES ('buy', '10', 1, 4,  2, 1);
INSERT INTO transaction (transaction_type, price, number_of_shares, user_id, stock_id, game_id) VALUES ('buy', '15', 6, 5, 4, 2);
INSERT INTO transaction (transaction_type, price, number_of_shares, user_id, stock_id, game_id) VALUES ('buy', '30', 5, 6, 1, 2);

COMMIT TRANSACTION;
