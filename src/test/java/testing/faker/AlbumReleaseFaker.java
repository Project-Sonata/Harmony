package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static testing.faker.ReleaseDateFaker.randomReleaseDate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumReleaseFaker {
    AlbumRelease.AlbumReleaseBuilder builder = AlbumRelease.builder();

    Faker faker = Faker.instance();

    public AlbumReleaseFaker() {
        TrackContainer tracks = TrackContainerFaker.randomTracks().get();
        builder.releaseDate(randomReleaseDate().get())
                .name(faker.name().title())
                .albumType(faker.options().option(AlbumType.class))
                .tracks(tracks)
                .artists(ArtistContainerFaker.create().get())
                .totalTracksCount(tracks.size())
                .images(ImageContainerFaker.randomImages().get());
    }

    public static AlbumReleaseFaker create() {
        return new AlbumReleaseFaker();
    }

    public AlbumRelease get() {
        return builder.build();
    }

    public AlbumReleaseFaker id(Long id) {
        builder.id(id);
        return this;
    }

    public AlbumReleaseFaker name(String name) {
        builder.name(name);
        return this;
    }

    public AlbumReleaseFaker totalTracksCount(Integer totalTracksCount) {
        builder.totalTracksCount(totalTracksCount);
        return this;
    }

    public AlbumReleaseFaker releaseDate(ReleaseDate releaseDate) {
        builder.releaseDate(releaseDate);
        return this;
    }

    public AlbumReleaseFaker albumType(AlbumType albumType) {
        builder.albumType(albumType);
        return this;
    }

    public AlbumReleaseFaker artists(ArtistContainer artists) {
        builder.artists(artists);
        return this;
    }

    public AlbumReleaseFaker tracks(TrackContainer tracks) {
        builder.tracks(tracks);
        return this;
    }

    public AlbumReleaseFaker images(ImageContainer images) {
        builder.images(images);
        return this;
    }
}
