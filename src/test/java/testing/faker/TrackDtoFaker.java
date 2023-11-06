package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto;
import com.odeyalo.sonata.harmony.dto.TrackDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;

import static testing.faker.ReleaseArtistContainerDtoFaker.randomReleaseArtistsDto;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackDtoFaker {
    public static final int FIRST_TRACK_INDEX = 0;
    public static final int FIRST_DISC_NUMBER = 1;
    TrackDto.TrackDtoBuilder builder = TrackDto.builder();

    Faker faker = Faker.instance();

    public TrackDtoFaker(int index, int discNumber) {
        trackName(faker.funnyName().name())
                .index(index)
                .discNumber(discNumber)
                .durationMs(faker.random().nextLong())
                .hasLyrics(faker.random().nextBoolean())
                .isExplicit(faker.random().nextBoolean())
                .artists(randomReleaseArtistsDto().get())
                .fileId(RandomStringUtils.random(10) + ".mp3");
    }

    public static TrackDtoFaker create() {
        return new TrackDtoFaker(FIRST_TRACK_INDEX, FIRST_DISC_NUMBER);
    }

    public static TrackDtoFaker withIndex(int index) {
        return new TrackDtoFaker(index, FIRST_DISC_NUMBER);
    }

    public TrackDtoFaker trackName(String trackName) {
        builder.trackName(trackName);
        return this;
    }

    public TrackDtoFaker durationMs(long durationMs) {
        builder.durationMs(durationMs);
        return this;
    }

    public TrackDtoFaker isExplicit(boolean isExplicit) {
        builder.isExplicit(isExplicit);
        return this;
    }

    public TrackDtoFaker hasLyrics(boolean hasLyrics) {
        builder.hasLyrics(hasLyrics);
        return this;
    }

    public TrackDtoFaker discNumber(int discNumber) {
        builder.discNumber(discNumber);
        return this;
    }

    public TrackDtoFaker index(int index) {
        builder.index(index);
        return this;
    }

    public TrackDtoFaker artists(ReleaseArtistContainerDto artists) {
        builder.artists(artists);
        return this;
    }

    public TrackDtoFaker fileId(String fileId) {
        builder.fileId(fileId);
        return this;
    }

    public TrackDto get() {
        return builder.build();
    }
}
