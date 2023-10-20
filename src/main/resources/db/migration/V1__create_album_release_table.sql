CREATE TABLE album_releases
(
    id                     SERIAL PRIMARY KEY,
    album_name             VARCHAR(255) NOT NULL,
    album_type             VARCHAR(100) NOT NULL,
    duration_ms            BIGINT       NOT NULL,
    total_tracks_count     INTEGER      NOT NULL,
    release_date           VARCHAR(100) NOT NULL,
    release_date_precision VARCHAR(50)  NOT NULL
);