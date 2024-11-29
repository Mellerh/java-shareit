CREATE TABLE IF NOT EXISTS users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(320),
    CONSTRAINT UQ_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item_requests (
    item_request_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description varchar,
    requestor_id INT REFERENCES users (user_id) ON DELETE CASCADE,
    creation_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
  item_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  owner_id INT NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
  name VARCHAR NOT NULL,
  description VARCHAR,
  is_available BOOL,
  request_id INT REFERENCES item_requests (item_request_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    status VARCHAR,
    item_id INT REFERENCES items (item_id) ON DELETE CASCADE,
    booker_id INT REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
    comment_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text VARCHAR NOT NULL,
    item_id INT REFERENCES items (item_id) ON DELETE CASCADE,
    author_id INT REFERENCES users (user_id) ON DELETE CASCADE
);
