package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistContainerEntityFaker {
    ArtistContainerEntity.ArtistContainerEntityBuilder builder = ArtistContainerEntity.builder();
    Faker faker = Faker.instance();

    public ArtistContainerEntityFaker() {
        int artistNumber = faker.random().nextInt(0, 5);

        for (int i = 0; i < artistNumber; i++) {
            ArtistEntity artistEntity = ArtistEntityFaker.create().get();
            builder.artist(artistEntity);
        }

    }

    public static ArtistContainerEntityFaker randomArtistEntities() {
        return new ArtistContainerEntityFaker();
    }

    public ArtistContainerEntity get() {
        return builder.build();
    }

    public ArtistContainerEntityFaker clearArtists() {
        builder.clearArtists();
        return this;
    }

    public ArtistContainerEntityFaker artists(Collection<? extends ArtistEntity> artists) {
        builder.artists(artists);
        return this;
    }

    public ArtistContainerEntityFaker artist(ArtistEntity artist) {
        builder.artist(artist);
        return this;
    }
}
