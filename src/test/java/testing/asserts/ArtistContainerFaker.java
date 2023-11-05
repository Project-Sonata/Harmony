package testing.asserts;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistContainerFaker {

    ArtistContainer.ArtistContainerBuilder builder = ArtistContainer.builder();

    Faker faker = Faker.instance();

    public ArtistContainerFaker() {

        int artistCount = faker.random().nextInt(1, 5);

        for (int i = 0; i < artistCount; i++) {
            builder.item(ArtistFaker.create().get());
        }
    }

    public static ArtistContainerFaker create() {
        return new ArtistContainerFaker();
    }

    public ArtistContainer get() {
        return builder.build();
    }

    public ArtistContainerFaker item(Artist item) {
        builder.item(item);
        return this;
    }

    public ArtistContainerFaker items(Collection<? extends Artist> items) {
        builder.items(items);
        return this;
    }

    public ArtistContainerFaker clearItems() {
        builder.clearItems();
        return this;
    }
}
