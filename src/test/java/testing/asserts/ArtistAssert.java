package testing.asserts;

import com.odeyalo.sonata.harmony.model.Artist;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class ArtistAssert extends AbstractAssert<ArtistAssert, Artist> {

    public ArtistAssert(Artist actual) {
        super(actual, ArtistAssert.class);
    }

    public static ArtistAssert assertThat(Artist actual) {
        return new ArtistAssert(actual);
    }

    public ArtistAssert hasSonataId(String expected) {
        if ( Objects.equals(actual.getSonataId(), expected) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getName(), expected, "Expected Sonata ID to be equal!");
    }

    public ArtistAssert hasName(String expected) {
        if ( Objects.equals(actual.getName(), expected) ) {
            return this;
        }
        throw failureWithActualExpected(actual.getName(), expected, "Expected name to be equal!");
    }
}
