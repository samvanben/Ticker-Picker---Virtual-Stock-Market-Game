BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
   user_id SERIAL,
   username varchar(50) NOT NULL UNIQUE,
   password_hash varchar(200) NOT NULL,
   role varchar(50) default 'ROLE_USER' NOT NULL,
   profile_balance numeric default 0 NOT NULL,
   first_name varchar(50),
   last_name varchar(50),
   CONSTRAINT PK_user PRIMARY KEY (user_id),
   CONSTRAINT CHK_user_type CHECK (role in ('ROLE_USER', 'ROLE_ADMIN'))
);

CREATE TABLE stock (
   stock_id SERIAL,
   symbol varchar (20) NOT NULL UNIQUE,
   current_share_price numeric(11, 2),
   company_name varchar (20),
   description varchar(50),
   CONSTRAINT PK_stock PRIMARY KEY (stock_id)
);

CREATE TABLE game (
   game_id SERIAL,
   name_of_game varchar (50) NOT NULL UNIQUE,
    game_start_date date default CURRENT_DATE,
    game_end_date date,
    owner_name varchar(50) NOT NULL,
    is_current_game boolean default true,
   CONSTRAINT PK_game PRIMARY KEY (game_id),
   CONSTRAINT FK_game_owner FOREIGN KEY (owner_name) REFERENCES users (username)
);

CREATE TABLE transaction (
   transaction_id SERIAL,
   user_id integer NOT NULL,
   stock_symbol varchar(50) NOT NULL,
   game_id integer NOT NULL,
   quantity int NOT NULL,
   CONSTRAINT PK_transaction PRIMARY KEY (transaction_id),
   CONSTRAINT FK_transaction_user FOREIGN KEY (user_id) REFERENCES users (user_id),
   CONSTRAINT FK_transaction_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

CREATE TABLE game_user (
<<<<<<< HEAD
	game_user_id SERIAL,
	game_id integer,
	user_id integer,
	available_balance numeric default 100000,
	total_balance numeric default 100000,
	username varchar(50),
	CONSTRAINT PK_game_user PRIMARY KEY (game_user_id),
	CONSTRAINT FK_game_user_user FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT FK_game_user_username FOREIGN KEY (username) REFERENCES users (username),
=======
   game_user_id SERIAL,
   game_id integer,
   user_id integer,
   available_balance numeric default 100000,
   total_balance numeric default 100000,
   username varchar(50),
   CONSTRAINT PK_game_user PRIMARY KEY (game_user_id),
   CONSTRAINT FK_game_user_user FOREIGN KEY (user_id) REFERENCES users (user_id),
   CONSTRAINT FK_game_user_username FOREIGN KEY (username) REFERENCES users (username),
>>>>>>> main
    CONSTRAINT FK_game_user_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

COMMIT TRANSACTION;