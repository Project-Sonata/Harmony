package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReleaseDateAssociationReleaseAlbumEntityBeforeSaveCallback implements BeforeSaveCallback<AlbumReleaseEntity> {

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onBeforeSave(
            @NotNull AlbumReleaseEntity entity,
            @NotNull OutboundRow row,
            @NotNull SqlIdentifier table) {

        ReleaseDate releaseDate = entity.getReleaseDate();

        StringBuilder builder = new StringBuilder();

        if (releaseDate.getPrecision() == ReleaseDate.Precision.YEAR) {
            builder.append(releaseDate.getYear());
        }

        if (releaseDate.getPrecision() == ReleaseDate.Precision.MONTH) {
            builder.append(releaseDate.getYear()).append("-").append(releaseDate.getMonth());
        }

        if (releaseDate.getPrecision() == ReleaseDate.Precision.DAY) {
            builder.append(releaseDate.getYear())
                    .append("-").append(releaseDate.getMonth())
                    .append("-").append(releaseDate.getDay());
        }

        @SuppressWarnings("deprecation")
        Parameter releaseDateParameter = Parameter.from(builder.toString());

        @SuppressWarnings("deprecation")
        Parameter releaseDatePrecisionParameter = Parameter.from(releaseDate.getPrecision().name());


        row.append("release_date", releaseDateParameter);

        row.append("release_date_precision", releaseDatePrecisionParameter);

        return Mono.just(entity);
    }
}
