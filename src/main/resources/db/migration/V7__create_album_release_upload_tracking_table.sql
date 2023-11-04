CREATE TABLE album_release_upload_tracking
(
    tracking_id VARCHAR PRIMARY KEY,
    album_id bigint references album_releases(id) NOT NULL
);