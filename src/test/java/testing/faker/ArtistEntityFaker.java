package testing.faker;


import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistEntityFaker {
    ArtistEntity.ArtistEntityBuilder builder = ArtistEntity.builder();

    Faker faker = Faker.instance();

    public ArtistEntityFaker() {
        builder.id(faker.random().nextLong(100_000))
                .sonataId(RandomStringUtils.randomAlphanumeric(22))
                .name(faker.artist().name());
    }

    public static ArtistEntityFaker create() {
        return new ArtistEntityFaker();
    }

    public ArtistEntity get() {
        return builder.build();
    }

    // Wrapper methods for ArtistEntityBuilder

    public ArtistEntityFaker id(Long id) {
        builder.id(id);
        return this;
    }

    public ArtistEntityFaker sonataId(String sonataId) {
        builder.sonataId(sonataId);
        return this;
    }

    public ArtistEntityFaker name(String name) {
        builder.name(name);
        return this;
    }
}
