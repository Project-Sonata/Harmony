CREATE TABLE track_artists
(
    artist_id BIGINT NOT NULL REFERENCES artists (artist_id) ON DELETE CASCADE,
    track_id  BIGINT NOT NULL REFERENCES tracks (id) ON DELETE CASCADE
);