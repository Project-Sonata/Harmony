package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.model.Image;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumCoverImageEntityFaker {
    AlbumCoverImageEntity.AlbumCoverImageEntityBuilder builder = AlbumCoverImageEntity.builder();
    Faker faker = Faker.instance();

    public AlbumCoverImageEntityFaker() {
        builder.url(faker.internet().url())
                .width(faker.random().nextInt(100, 500))
                .height(faker.random().nextInt(100, 500));
    }


    public static AlbumCoverImageEntityFaker create() {
        return new AlbumCoverImageEntityFaker();
    }

    public AlbumCoverImageEntity get() {
        return builder.build();
    }

    public AlbumCoverImageEntityFaker url(@NotNull @NonNull String url) {
        builder.url(url);
        return this;
    }

    public AlbumCoverImageEntityFaker height(@Nullable Integer height) {
        builder.height(height);
        return this;
    }

    public AlbumCoverImageEntityFaker width(@Nullable Integer width) {
        builder.width(width);
        return this;
    }
}
