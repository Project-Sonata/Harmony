CREATE TABLE album_release_upload_tracking
(
    id SERIAL PRIMARY KEY,
    tracking_id VARCHAR UNIQUE ,
    album_id bigint references album_releases(id) NOT NULL
);