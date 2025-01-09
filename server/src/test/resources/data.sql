INSERT INTO users (name, email)
VALUES ('Taras', 'vasilenko.taras2015@yandex.ru');


INSERT INTO items (owner_id, name, description, available)
VALUES (1, 'Phone', 'good phone', TRUE);

INSERT INTO bookings (start_date, end_date, status, item_id, booker_id)
VALUES ('2024-01-21 15:30:00', '2024-01-22 15:30:00', 'WAITING', 1, 1);

INSERT INTO comments (text, created, item_id, user_id)
VALUES ('comment good phone', '2024-01-23 15:30:00', 1, 1);