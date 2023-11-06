package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.Artist;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistFaker {

    Artist.ArtistBuilder builder = Artist.builder();

    Faker faker = Faker.instance();

    public ArtistFaker() {
        builder.sonataId(RandomStringUtils.randomAlphanumeric(22))
                .name(faker.artist().name());
    }


    public static ArtistFaker create() {
        return new ArtistFaker();
    }

    public Artist get() {
        return builder.build();
    }

    public ArtistFaker name(String name) {
        builder.name(name);
        return this;
    }

    public ArtistFaker sonataId(String sonataId) {
        builder.sonataId(sonataId);
        return this;
    }
}
