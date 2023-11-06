package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.harmony.model.TrackContainer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackContainerFaker {
    Faker faker = Faker.instance();

    TrackContainer.TrackContainerBuilder builder = TrackContainer.builder();

    public TrackContainerFaker() {
        int trackCount = faker.random().nextInt(1, 12);

        for (int index = 1; index < trackCount + 1; index++) {
            Track track = TrackFaker.withIndex(index).get();
            builder.item(track);
        }
    }

    public static TrackContainerFaker randomTracks() {
        return new TrackContainerFaker();
    }

    public TrackContainer get() {
        return builder.build();
    }

    public TrackContainerFaker item(Track item) {
        builder.item(item);
        return this;
    }

    public TrackContainerFaker items(Collection<? extends Track> items) {
        builder.items(items);
        return this;
    }

    public TrackContainerFaker clearItems() {
        builder.clearItems();
        return this;
    }
}
