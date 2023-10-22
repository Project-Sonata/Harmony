CREATE TABLE album_artists
(
    artist_id   BIGINT NOT NULL REFERENCES artists(artist_id) ON DELETE CASCADE,
    album_id BIGINT NOT NULL REFERENCES album_releases(id) ON DELETE CASCADE
);