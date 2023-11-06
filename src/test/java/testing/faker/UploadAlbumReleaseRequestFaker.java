package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto;
import com.odeyalo.sonata.harmony.dto.TrackContainerDto;
import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest.UploadAlbumReleaseRequestBuilder;
import com.odeyalo.sonata.harmony.model.AlbumType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static testing.faker.ReleaseDateFaker.randomReleaseDate;
import static testing.faker.ReleaseArtistContainerDtoFaker.randomReleaseArtistsDto;
import static testing.faker.TrackContainerDtoFaker.randomTracksDto;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadAlbumReleaseRequestFaker {
    UploadAlbumReleaseRequestBuilder builder = UploadAlbumReleaseRequest.builder();

    Faker faker = Faker.instance();

    public UploadAlbumReleaseRequestFaker() {
        builder.albumName(faker.funnyName().name())
                .albumType(faker.options().option(AlbumType.class))
                .tracks(randomTracksDto().get())
                .performers(randomReleaseArtistsDto().get())
                .releaseDate(randomReleaseDate().get());

    }


    public static UploadAlbumReleaseRequestFaker create() {
        return new UploadAlbumReleaseRequestFaker();
    }


    public UploadAlbumReleaseRequest get() {
        return builder.build();
    }

    public UploadAlbumReleaseRequestFaker albumName(@NotNull String albumName) {
        builder.albumName(albumName);
        return this;
    }

    public UploadAlbumReleaseRequestFaker albumType(@NotNull AlbumType albumType) {
        builder.albumType(albumType);
        return this;
    }

    public UploadAlbumReleaseRequestFaker tracks(@NotNull TrackContainerDto tracks) {
        builder.tracks(tracks);
        return this;
    }

    public UploadAlbumReleaseRequestFaker performers(@NotNull ReleaseArtistContainerDto performers) {
        builder.performers(performers);
        return this;
    }
}
