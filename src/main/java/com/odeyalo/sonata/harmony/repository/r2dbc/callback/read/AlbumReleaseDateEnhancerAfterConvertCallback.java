package com.odeyalo.sonata.harmony.repository.r2dbc.callback.read;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateDecoder;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateRowInfo;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AlbumReleaseDateEnhancerAfterConvertCallback implements AfterConvertCallback<AlbumReleaseEntity> {
    private final ReleaseDateDecoder<ReleaseDateRowInfo> releaseDateDecoder;

    public AlbumReleaseDateEnhancerAfterConvertCallback(ReleaseDateDecoder<ReleaseDateRowInfo> releaseDateDecoder) {
        this.releaseDateDecoder = releaseDateDecoder;
    }

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onAfterConvert(@NotNull AlbumReleaseEntity entity,
                                                        @NotNull SqlIdentifier table) {

        String releaseDate = entity.getReleaseDateAsString();
        ReleaseDate.Precision precision = toPrecision(entity);

        ReleaseDate date = releaseDateDecoder.decodeReleaseDate(ReleaseDateRowInfo.of(releaseDate, precision));

        entity.setReleaseDate(date);

        return Mono.just(entity);
    }

    @NotNull
    private static ReleaseDate.Precision toPrecision(@NotNull AlbumReleaseEntity entity) {
        return ReleaseDate.Precision.valueOf(entity.getReleaseDatePrecision());
    }
}
