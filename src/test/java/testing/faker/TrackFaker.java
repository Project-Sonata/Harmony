package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import com.odeyalo.sonata.harmony.model.SimplifiedAlbumRelease;
import com.odeyalo.sonata.harmony.model.Track;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URI;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackFaker {
    Track.TrackBuilder builder = Track.builder();

    Faker faker = Faker.instance();

    public TrackFaker(int index, int discNumber) {
        builder
                .id(UUID.randomUUID().toString())
                .index(index)
                .discNumber(discNumber)
                .trackName(faker.funnyName().name())
                .artists(ArtistContainerFaker.create().get())
                .hasLyrics(faker.random().nextBoolean())
                .isExplicit(faker.random().nextBoolean())
                .durationMs(faker.random().nextInt(1000, 100_000))
                .trackUrl(URI.create("https://s3.aws.com/tracks/" + UUID.randomUUID()));
    }

    public static TrackFaker create() {
        return new TrackFaker(0, 1);
    }

    public static TrackFaker withIndex(int index) {
        return new TrackFaker(index, 1);
    }

    public static TrackFaker withIndexAndDisc(int index, int discNumber) {
        return new TrackFaker(index, discNumber);
    }

    public Track get() {
        return builder.build();
    }

    public TrackFaker album(SimplifiedAlbumRelease album) {
        builder.album(album);
        return this;
    }

    public TrackFaker artists(ArtistContainer artists) {
        builder.artists(artists);
        return this;
    }

    public TrackFaker index(int index) {
        builder.index(index);
        return this;
    }

    public TrackFaker discNumber(int discNumber) {
        builder.discNumber(discNumber);
        return this;
    }

    public TrackFaker hasLyrics(boolean hasLyrics) {
        builder.hasLyrics(hasLyrics);
        return this;
    }

    public TrackFaker isExplicit(boolean isExplicit) {
        builder.isExplicit(isExplicit);
        return this;
    }

    public TrackFaker durationMs(long durationMs) {
        builder.durationMs(durationMs);
        return this;
    }

    public TrackFaker trackName(String trackName) {
        builder.trackName(trackName);
        return this;
    }

    public TrackFaker id(String id) {
        builder.id(id);
        return this;
    }
}
