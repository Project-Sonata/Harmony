CREATE TABLE tracks
(
    id   SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    duration_ms BIGINT NOT NULL,
    is_explicit BOOLEAN NOT NULL,
    has_lyrics BOOLEAN NOT NULL,
    disc_number INTEGER NOT NULL,
    index INTEGER NOT NULL,
    album_id BIGINT NOT NULL REFERENCES album_releases(id)
);