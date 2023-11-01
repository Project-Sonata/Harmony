CREATE TABLE album_cover_images
(
    url VARCHAR(255) NOT NULL UNIQUE,
    width integer,
    height integer,
    album_id bigint references album_releases(id) NOT NULL
);