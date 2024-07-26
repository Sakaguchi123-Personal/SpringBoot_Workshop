create table if not exists users(
    id SERIAL PRIMARY KEY,
    nickname TEXT,
    email TEXT
);