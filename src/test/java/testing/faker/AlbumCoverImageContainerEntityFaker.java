package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumCoverImageContainerEntityFaker {
    AlbumCoverImageContainerEntity.AlbumCoverImageContainerEntityBuilder builder = AlbumCoverImageContainerEntity.builder();

    Faker faker = Faker.instance();

    public AlbumCoverImageContainerEntityFaker() {
        int imageCount = faker.random().nextInt(0, 2);

        for (int i = 0; i < imageCount; i++) {
            AlbumCoverImageEntity image = AlbumCoverImageEntityFaker.create().get();
            builder.item(image);
        }
    }

    public static AlbumCoverImageContainerEntityFaker randomImageEntities() {
        return new AlbumCoverImageContainerEntityFaker();
    }

    public AlbumCoverImageContainerEntity get() {
        return builder.build();
    }

    public AlbumCoverImageContainerEntityFaker item(AlbumCoverImageEntity item) {
        builder.item(item);
        return this;
    }

    public AlbumCoverImageContainerEntityFaker items(Collection<? extends AlbumCoverImageEntity> items) {
        builder.items(items);
        return this;
    }

    public AlbumCoverImageContainerEntityFaker clearItems() {
        builder.clearItems();
        return this;
    }
}
