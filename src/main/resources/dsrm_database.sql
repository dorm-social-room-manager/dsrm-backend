-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2022-11-29 16:05:34.347

-- tables
-- Table: reservations
CREATE TABLE reservations (
    id int NOT NULL AUTO_INCREMENT,
    room_id int NOT NULL,
    start_time datetime NOT NULL,
    end_time datetime NOT NULL,
    user_id int NOT NULL,
    CONSTRAINT reservations_pk PRIMARY KEY (id)
);

-- Table: roles
CREATE TABLE roles (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL,
    CONSTRAINT roles_pk PRIMARY KEY (id)
) COMMENT 'e.g. card, cash, paypal, wire transfer';

-- Table: room_types
CREATE TABLE room_types (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL,
    CONSTRAINT room_types_pk PRIMARY KEY (id)
) COMMENT 'e.g. card, cash, paypal, wire transfer';

-- Table: rooms
CREATE TABLE rooms (
    id int NOT NULL AUTO_INCREMENT,
    room_number int NOT NULL,
    floor int NOT NULL,
    type_id int NOT NULL,
    max_capacity int NOT NULL,
    key_owner_id int NULL,
    opening_time time NULL,
    closing_time time NULL,
    unavailable_start date NULL,
    unavailable_end date NULL,
    CONSTRAINT rooms_pk PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE users (
    id int NOT NULL AUTO_INCREMENT,
    email varchar(100) NOT NULL,
    password varchar(256) NOT NULL,
    name varchar(20) NOT NULL,
    surname varchar(30) NOT NULL,
    room_number int NULL,
    ban_end date NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

-- Table: users_roles
CREATE TABLE users_roles (
    id int NOT NULL AUTO_INCREMENT,
    role_id int NOT NULL,
    user_id int NOT NULL,
    CONSTRAINT users_roles_pk PRIMARY KEY (id)
) COMMENT 'e.g. card, cash, paypal, wire transfer';

-- foreign keys
-- Reference: reservations_rooms (table: reservations)
ALTER TABLE reservations ADD CONSTRAINT reservations_rooms FOREIGN KEY (room_id)
    REFERENCES rooms (id);

-- Reference: reservations_users (table: reservations)
ALTER TABLE reservations ADD CONSTRAINT reservations_users FOREIGN KEY (user_id)
    REFERENCES users (id);

-- Reference: rooms_room_types (table: rooms)
ALTER TABLE rooms ADD CONSTRAINT rooms_room_types FOREIGN KEY (type_id)
    REFERENCES room_types (id);

-- Reference: rooms_users (table: rooms)
ALTER TABLE rooms ADD CONSTRAINT rooms_users FOREIGN KEY (key_owner_id)
    REFERENCES users (id);

-- Reference: users_roles_roles (table: users_roles)
ALTER TABLE users_roles ADD CONSTRAINT users_roles_roles FOREIGN KEY (role_id)
    REFERENCES roles (id);

-- Reference: users_roles_users (table: users_roles)
ALTER TABLE users_roles ADD CONSTRAINT users_roles_users FOREIGN KEY (user_id)
    REFERENCES users (id);

INSERT INTO room_types(name) VALUES ('Pokoj mieszkalny');
INSERT INTO room_types(name) VALUES ('Sala telewizyjna');
INSERT INTO room_types(name) VALUES ('Silownia');
INSERT INTO users(email, password, name, surname, room_number, ban_end)
VALUES ('test01@wp.pl', 'zaq1@WSX', 'Jan', 'Kowalski', 111, null);

INSERT INTO users(email, password, name, surname, room_number, ban_end)
VALUES ('test02@wp.pl', 'zaq1@WSX', 'Piotr', 'Nowak', 112, str_to_date('02-02-2023', '%d-%m-%Y'));

INSERT INTO users(email, password, name, surname, room_number, ban_end)
VALUES ('test03@wp.pl', 'zaq1@WSX', 'Stefan', 'Grabowski', 111, null);

INSERT INTO rooms (room_number, floor, type_id, max_capacity, key_owner_id, opening_time, closing_time, unavailable_start, unavailable_end)
VALUES (111, 1, 1, 2, 1, (120000), (230000), null, null);

INSERT INTO rooms (room_number, floor, type_id, max_capacity, key_owner_id, opening_time, closing_time, unavailable_start, unavailable_end)
VALUES (112, 1, 1, 2, 1, (120000), (230000), null, null);

INSERT INTO rooms (room_number, floor, type_id, max_capacity, key_owner_id, opening_time, closing_time, unavailable_start, unavailable_end)
VALUES (101, 1, 2, 6, 2, (120000), (230000), null, null);

-- End of file.

