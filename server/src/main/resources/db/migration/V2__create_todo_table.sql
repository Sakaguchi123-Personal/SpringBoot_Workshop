create table if not exists todo(
    id SERIAL PRIMARY KEY,
    name TEXT,
    finished boolean
);