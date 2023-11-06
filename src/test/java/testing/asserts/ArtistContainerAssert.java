package testing.asserts;

import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import org.assertj.core.api.AbstractIterableAssert;


public class ArtistContainerAssert extends AbstractIterableAssert
        <ArtistContainerAssert, ArtistContainer, Artist, ArtistAssert> {

    public ArtistContainerAssert(ArtistContainer actual) {
        super(actual, ArtistContainerAssert.class);
    }

    public static ArtistContainerAssert assertThat(ArtistContainer actual) {
        return new ArtistContainerAssert(actual);
    }

    @Override
    protected ArtistAssert toAssert(Artist value, String description) {
        return ArtistAssert.assertThat(value);
    }

    @Override
    protected ArtistContainerAssert newAbstractIterableAssert(Iterable<? extends Artist> iterable) {
        return this;
    }
}
