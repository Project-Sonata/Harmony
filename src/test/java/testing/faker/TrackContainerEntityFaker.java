package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackContainerEntityFaker {
    TrackContainerEntity.TrackContainerEntityBuilder builder = TrackContainerEntity.builder();
    Faker faker = Faker.instance();

    public TrackContainerEntityFaker() {
        int trackCount = faker.random().nextInt(0, 10);

        for (int i = 0; i < trackCount; i++) {
            SimplifiedTrackEntity trackEntity = SimplifiedTrackEntityFaker.withIndex(i).get();
            builder.item(trackEntity);
        }
    }


    public static TrackContainerEntityFaker randomTrackEntities() {
        return new TrackContainerEntityFaker();
    }

    public TrackContainerEntity get() {
        return builder.build();
    }

    public TrackContainerEntityFaker item(SimplifiedTrackEntity item) {
        builder.item(item);
        return this;
    }

    public TrackContainerEntityFaker items(Collection<? extends SimplifiedTrackEntity> items) {
        builder.items(items);
        return this;
    }

    public TrackContainerEntityFaker clearItems() {
        builder.clearItems();
        return this;
    }
}
