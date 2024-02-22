package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static testing.faker.AlbumCoverImageContainerEntityFaker.randomImageEntities;
import static testing.faker.ArtistContainerEntityFaker.randomArtistEntities;
import static testing.faker.ReleaseDateFaker.randomReleaseDate;
import static testing.faker.TrackContainerEntityFaker.randomTrackEntities;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumReleaseEntityFaker {
    AlbumReleaseEntity.AlbumReleaseEntityBuilder<?, ?> builder = AlbumReleaseEntity.builder();
    Faker faker = Faker.instance();

    public AlbumReleaseEntityFaker() {
        TrackContainerEntity tracks = randomTrackEntities().get();
        builder.releaseDate(randomReleaseDate().get())
                .albumName(faker.name().title())
                .albumType(faker.options().option(AlbumType.class))
                .tracks(tracks)
                .artists(randomArtistEntities().get())
                .totalTracksCount(tracks.size())
                .images(randomImageEntities().get());
    }

    public static AlbumReleaseEntityFaker create() {
        return new AlbumReleaseEntityFaker();
    }

    public AlbumReleaseEntity get() {
        return builder.build();
    }

    public AlbumReleaseEntityFaker id(long id) {
        builder.id(id);
        return this;
    }

    public AlbumReleaseEntityFaker albumName(String albumName) {
        builder.albumName(albumName);
        return this;
    }

    public AlbumReleaseEntityFaker albumType(AlbumType albumType) {
        builder.albumType(albumType);
        return this;
    }

    public AlbumReleaseEntityFaker durationMs(Long durationMs) {
        builder.durationMs(durationMs);
        return this;
    }

    public AlbumReleaseEntityFaker totalTracksCount(Integer totalTracksCount) {
        builder.totalTracksCount(totalTracksCount);
        return this;
    }

    public AlbumReleaseEntityFaker releaseDate(ReleaseDate releaseDate) {
        builder.releaseDate(releaseDate);
        return this;
    }

    public AlbumReleaseEntityFaker artists(ArtistContainerEntity artists) {
        builder.artists(artists);
        return this;
    }

    public AlbumReleaseEntityFaker images(AlbumCoverImageContainerEntity images) {
        builder.images(images);
        return this;
    }

    public AlbumReleaseEntityFaker withImage(AlbumCoverImageEntity image) {
        builder.images(AlbumCoverImageContainerEntity.single(image));
        return this;
    }

    public AlbumReleaseEntityFaker releaseDateAsString(String releaseDateAsString) {
        builder.releaseDateAsString(releaseDateAsString);
        return this;
    }

    public AlbumReleaseEntityFaker releaseDatePrecision(String releaseDatePrecision) {
        builder.releaseDatePrecision(releaseDatePrecision);
        return this;
    }
}
