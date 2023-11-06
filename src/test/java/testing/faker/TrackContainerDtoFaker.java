package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.dto.TrackContainerDto;
import com.odeyalo.sonata.harmony.dto.TrackDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackContainerDtoFaker {

    TrackContainerDto.TrackContainerDtoBuilder builder = TrackContainerDto.builder();

    Faker faker = Faker.instance();

    public TrackContainerDtoFaker() {
        int trackCount = faker.random().nextInt(0, 10);

        for (int i = 1; i < trackCount + 1; i++) {
            TrackDto track = TrackDtoFaker.withIndex(i).get();
            builder.item(track);
        }
    }

    public static TrackContainerDtoFaker randomTracksDto() {
        return new TrackContainerDtoFaker();
    }

    public TrackContainerDto get() {
        return builder.build();
    }

    public TrackContainerDtoFaker totalTracksCount(int totalTracksCount) {
        builder.totalTracksCount(totalTracksCount);
        return this;
    }

    public TrackContainerDtoFaker item(TrackDto item) {
        builder.item(item);
        return this;
    }

    public TrackContainerDtoFaker items(Collection<? extends TrackDto> items) {
        builder.items(items);
        return this;
    }

    public TrackContainerDtoFaker clearItems() {
        builder.clearItems();
        return this;
    }
}
