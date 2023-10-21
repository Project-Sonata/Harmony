CREATE TABLE album_artists
(
    artist_id   BIGINT NOT NULL REFERENCES artists(artist_id),
    album_id BIGINT NOT NULL REFERENCES album_releases(id)
);