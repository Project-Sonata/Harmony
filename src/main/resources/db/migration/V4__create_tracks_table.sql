CREATE TABLE tracks
(
    id   SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    duration_ms BIGINT NOT NULL
);