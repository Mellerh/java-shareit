CREATE TABLE IF NOT EXISTS users (
  user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  email VARCHAR NOT NULL,
  name VARCHAR NOT NULL,
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);
--
--CREATE TABLE IF NOT EXISTS items (
--  item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--  name VARCHAR NOT NULL,
--  description VARCHAR NOT NULL,
--  is_available BOOLEAN,
--  owner_id INT NOT NULL REFERENCES users (user_id),
--  request_id INT REFERENCES requests (request_id)
--);
--
--CREATE TABLE IF NOT EXISTS bookings (
--    booking_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--    start_date TIMESTAMP NOT NULL,
--    end_date TIMESTAMP NOT NULL,
--    status VARCHAR,
--    item_id INT REFERENCES items (item_id),
--    booker_id INT REFERENCES users (user_id)
--);
--
--CREATE TABLE IF NOT EXISTS requests (
--    request_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--    description VARCHAR,
--    requestor_id INT REFERENCES users (user_id)
--);
--
--CREATE TABLE IF NOT EXISTS comments (
--    comment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--    text VARCHAR,
--    item_id INT REFERENCES items (item_id),
--    author_id INT REFERENCES users (user_id)
--);
