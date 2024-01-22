package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimplifiedTrackEntityFaker {
    public static final int FIRST_TRACK_INDEX = 0;
    public static final int FIRST_DISC_NUMBER = 1;
    SimplifiedTrackEntity.SimplifiedTrackEntityBuilder<?, ?> builder = SimplifiedTrackEntity.builder();
    Faker faker = Faker.instance();

    public SimplifiedTrackEntityFaker(int index, int discNumber) {
        ArtistContainerEntity artists = ArtistContainerEntityFaker.randomArtistEntities().get();
        builder.index(index)
                .discNumber(discNumber)
                .name(faker.funnyName().name())
                .artists(artists)
                .hasLyrics(faker.random().nextBoolean())
                .explicit(faker.random().nextBoolean())
                .trackUrl("https://s3.aws.com/odeyalo/tracks/" + UUID.randomUUID())
                .durationMs(Long.valueOf(faker.random().nextInt(1000, 100_000)));
    }

    public static SimplifiedTrackEntityFaker randomTrackEntity() {
        return new SimplifiedTrackEntityFaker(FIRST_TRACK_INDEX, FIRST_DISC_NUMBER);
    }

    public static SimplifiedTrackEntityFaker withIndex(int index) {
        return new SimplifiedTrackEntityFaker(index, FIRST_DISC_NUMBER);
    }

    public SimplifiedTrackEntity get() {
        return builder.build();
    }
}
