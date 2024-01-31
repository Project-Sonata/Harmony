package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.harmony.model.ImageContainer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageContainerFaker {
    ImageContainer.ImageContainerBuilder builder = ImageContainer.builder();

    Faker faker = Faker.instance();

    public ImageContainerFaker() {
        int imageCount = faker.random().nextInt(1, 2);

        for (int i = 0; i < imageCount; i++) {
            Image image = ImageFaker.create().get();
            builder.item(image);
        }
    }

    public static ImageContainerFaker randomImages() {
        return new ImageContainerFaker();
    }

    public ImageContainer get() {
        return builder.build();
    }

    public ImageContainerFaker item(Image item) {
        builder.item(item);
        return this;
    }

    public ImageContainerFaker items(Collection<? extends Image> items) {
        builder.items(items);
        return this;
    }

    public ImageContainerFaker clearItems() {
        builder.clearItems();
        return this;
    }
}
