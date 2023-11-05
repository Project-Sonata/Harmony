package testing.faker;

import com.odeyalo.sonata.harmony.dto.ReleaseArtistDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReleaseArtistDtoFaker {
    ReleaseArtistDto.ReleaseArtistDtoBuilder builder = ReleaseArtistDto.builder();

    public ReleaseArtistDtoFaker() {
        builder.id(RandomStringUtils.randomAlphanumeric(22));
    }

    public static ReleaseArtistDtoFaker create() {
        return new ReleaseArtistDtoFaker();
    }

    public ReleaseArtistDto get() {
        return builder.build();
    }

    // Wrapper method for ReleaseArtistDtoBuilder

    public ReleaseArtistDtoFaker id(@NotNull String id) {
        builder.id(id);
        return this;
    }
}
