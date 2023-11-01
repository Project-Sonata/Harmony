CREATE TABLE album_cover_images
(
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL UNIQUE,
    width integer,
    height integer,
    album_id bigint references album_releases(id) NOT NULL
);