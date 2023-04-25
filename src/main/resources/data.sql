INSERT INTO room_types VALUES (1, 'Pokoj mieszkalny');
INSERT INTO room_types VALUES (2, 'Sala telewizyjna');
INSERT INTO room_types VALUES (3, 'Silownia');
INSERT INTO users(id, email, password, name, surname, room_number, ban_end)
VALUES (1, 'test01@wp.pl', 'zaq1@WSX', 'Jan', 'Kowalski', 111, null);

INSERT INTO users(id, email, password, name, surname, room_number, ban_end)
VALUES (2, 'test02@wp.pl', 'zaq1@WSX', 'Piotr', 'Nowak', 112, str_to_date('02-02-2023', '%d-%m-%Y'));

INSERT INTO users(id, email, password, name, surname, room_number, ban_end)
VALUES (3, 'test03@wp.pl', 'zaq1@WSX', 'Stefan', 'Grabowski', 111, null);

INSERT INTO rooms(id, room_number, floor, type_id, max_capacity, key_owner_id, opening_time, closing_time, unavailable_start, unavailable_end)
VALUES (1, 111, 1, 1, 2, 1, (120000), (230000), null, null);

INSERT INTO rooms(id, room_number, floor, type_id, max_capacity, key_owner_id, opening_time, closing_time, unavailable_start, unavailable_end)
VALUES (2, 112, 1, 1, 2, 1, (120000), (230000), null, null);

INSERT INTO rooms(id, room_number, floor, type_id, max_capacity, key_owner_id, opening_time, closing_time, unavailable_start, unavailable_end)
VALUES (3, 101, 1, 2, 6, 2, (120000), (230000), null, null);


INSERT INTO roles(id, name) VALUES (1, 'Administrator');
INSERT INTO roles(id, name) VALUES (2, 'Uzytkownik');
INSERT INTO roles(id, name) VALUES (3, 'Student');

INSERT INTO user_roles(role_id, user_id)
VALUES (1,1);

INSERT INTO user_roles(role_id, user_id)
VALUES (1,2);

INSERT INTO reservations(id, room_id, start_time, end_time, user_id)
VALUES(1, 1, str_to_date('02,02,2023 12,00,00', '%d,%m,%Y %H,%i,%S'), str_to_date('02,02,2023 13,00,00', '%d,%m,%Y %H,%i,%S'), 3);

INSERT INTO reservations(id, room_id, start_time, end_time, user_id)
VALUES(2, 1, str_to_date('03,02,2023 12,00,00', '%d,%m,%Y %H,%i,%S'), str_to_date('03,02,2023 13,00,00', '%d,%m,%Y %H,%i,%S'), 2);

