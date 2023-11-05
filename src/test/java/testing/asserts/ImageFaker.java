package testing.asserts;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.Image;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageFaker {
    Image.ImageBuilder builder = Image.builder();
    Faker faker = Faker.instance();

    public ImageFaker() {
        builder.url(faker.internet().url())
                .width(faker.random().nextInt(100, 500))
                .height(faker.random().nextInt(100, 500));
    }


    public static ImageFaker create() {
        return new ImageFaker();
    }

    public Image get() {
        return builder.build();
    }

    public ImageFaker url(@NotNull @NonNull String url) {
        builder.url(url);
        return this;
    }

    public ImageFaker height(@Nullable Integer height) {
        builder.height(height);
        return this;
    }

    public ImageFaker width(@Nullable Integer width) {
        builder.width(width);
        return this;
    }
}
