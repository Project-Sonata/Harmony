package testing.asserts;

import com.odeyalo.sonata.harmony.dto.AlbumReleaseUploadAcceptedResponse;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class AlbumReleaseUploadAcceptedResponseAssert extends AbstractAssert<AlbumReleaseUploadAcceptedResponseAssert, AlbumReleaseUploadAcceptedResponse> {

    protected AlbumReleaseUploadAcceptedResponseAssert(AlbumReleaseUploadAcceptedResponse actual) {
        super(actual, AlbumReleaseUploadAcceptedResponseAssert.class);
    }

    public static AlbumReleaseUploadAcceptedResponseAssert assertThat(AlbumReleaseUploadAcceptedResponse actual) {
        return new AlbumReleaseUploadAcceptedResponseAssert(actual);
    }

    public AlbumReleaseUploadAcceptedResponseAssert hasTrackingId(String expected) {
        if ( Objects.equals(expected, actual.getTrackingId()) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getTrackingId(), expected, "Expected the track id and expected to be equal");
    }

}
