package testing.asserts;

import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class UploadAlbumReleaseInfoAssert extends AbstractAssert<UploadAlbumReleaseInfoAssert, UploadAlbumReleaseInfo> {

    public UploadAlbumReleaseInfoAssert(UploadAlbumReleaseInfo actual) {
        super(actual, UploadAlbumReleaseInfoAssert.class);
    }

    public static UploadAlbumReleaseInfoAssert assertThat(UploadAlbumReleaseInfo actual) {
        return new UploadAlbumReleaseInfoAssert(actual);
    }

    public UploadAlbumReleaseInfoAssert hasAlbumName(String expected) {
        if ( Objects.equals(actual.getAlbumName(), expected) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getAlbumName(), expected, "Expected album name to be equal!");
    }

    public UploadAlbumReleaseInfoAssert hasAlbumType(AlbumType expected) {
        if ( Objects.equals(actual.getAlbumType(), expected) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getAlbumType(), expected, "Expected album type to be equal!");
    }

    public UploadAlbumReleaseInfoAssert hasReleaseDate(ReleaseDate expected) {
        if ( Objects.equals(actual.getReleaseDate(), expected) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getReleaseDate(), expected, "Expected album release date to be equal!");
    }

    public UploadAlbumReleaseInfoAssert hasTotalTracksCount(int expected) {
        if ( Objects.equals(actual.getTotalTracksCount(), expected) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getTotalTracksCount(), expected, "Expected total tracks count to be equal!");
    }
}
