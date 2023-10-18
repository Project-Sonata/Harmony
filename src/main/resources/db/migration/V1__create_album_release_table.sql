CREATE TABLE album_releases
(
    id SERIAL PRIMARY KEY,
    album_name VARCHAR(255) NOT NULL,
    album_type VARCHAR(100) NOT NULL
);