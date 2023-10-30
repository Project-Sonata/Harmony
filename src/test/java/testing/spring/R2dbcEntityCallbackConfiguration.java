package testing.spring;

import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcAlbumReleaseRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.callback.read.*;
import com.odeyalo.sonata.harmony.repository.r2dbc.callback.write.*;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumReleaseRepositoryDelegate;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcArtistRepositoryDelegate;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.FormattedString2ReleaseDateConverter;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateDecoder;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateEncoder;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateRowInfo;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        ReleaseDateAssociationReleaseAlbumEntityBeforeSaveCallback.class,
        AlbumReleaseDateEnhancerAfterConvertCallback.class,
        AlbumArtistsEnhancerAfterConvertCallback.class,
        AlbumReleaseArtistsAssociationAfterSaveCallback.class,
        TrackAlbumEnhancerAfterConvertCallback.class,
        TrackArtistsEnhancerAfterConvertCallback.class,
        TrackArtistAssociationAfterSaveCallback.class,
        SaveArtistOnMissingBeforeSaveCallback.class,
        SaveAlbumTracksAfterSaveCallback.class,
        AlbumTracksEnhancerAfterConvertCallback.class
})
public class R2dbcEntityCallbackConfiguration {

    @Bean
    public ReleaseDateEncoder<String> releaseDateEncoder() {
        return new FormattedString2ReleaseDateConverter();
    }

    @Bean
    public ReleaseDateDecoder<ReleaseDateRowInfo> releaseDateDecoder() {
        return new FormattedString2ReleaseDateConverter();
    }

    @Bean
    public R2dbcAlbumReleaseRepository r2dbcAlbumReleaseRepository(R2dbcAlbumReleaseRepositoryDelegate delegate) {
        return new R2dbcAlbumReleaseRepository(delegate);
    }

    @Bean
    public ArtistRepository artistRepository(R2dbcArtistRepositoryDelegate delegate) {
        return new R2dbcArtistRepository(delegate);
    }
}
