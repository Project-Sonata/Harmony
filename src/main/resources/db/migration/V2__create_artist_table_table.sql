CREATE TABLE artists
(
    artist_id   SERIAL PRIMARY KEY,
    sonata_id   VARCHAR(255) NOT NULL UNIQUE,
    artist_name VARCHAR(255) NOT NULL
);