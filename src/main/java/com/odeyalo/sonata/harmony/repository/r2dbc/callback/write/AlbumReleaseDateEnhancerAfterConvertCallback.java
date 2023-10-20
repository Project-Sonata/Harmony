package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

@ReadingConverter
public class AlbumReleaseDateEnhancerAfterConvertCallback implements AfterConvertCallback<AlbumReleaseEntity> {

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onAfterConvert(@NotNull AlbumReleaseEntity entity,
                                                        @NotNull SqlIdentifier table) {

        String releaseDate = entity.getReleaseDateAsString();
        ReleaseDate.Precision precision = ReleaseDate.Precision.valueOf(entity.getReleaseDatePrecision());

        Assert.notNull(releaseDate, "Release date cannot be null!");
        Assert.notNull(precision, "Release date precision cannot be null!");
        ReleaseDate date = null;

        if (precision == ReleaseDate.Precision.YEAR) {
            date = ReleaseDate.onlyYear(Integer.parseInt(releaseDate));
        }

        if (precision == ReleaseDate.Precision.MONTH) {
            String[] yearAndMonth = releaseDate.split("-");
            int year = Integer.parseInt(yearAndMonth[0]);
            int month = Integer.parseInt(yearAndMonth[1]);

            date = ReleaseDate.withMonth(month, year);
        }

        if (precision == ReleaseDate.Precision.DAY) {
            String[] yearAndMonthAndDay = releaseDate.split("-");
            int year = Integer.parseInt(yearAndMonthAndDay[0]);
            int month = Integer.parseInt(yearAndMonthAndDay[1]);
            int day = Integer.parseInt(yearAndMonthAndDay[2]);

            date = ReleaseDate.withDay(day, month, year);
        }

        if (date == null) {
            throw new IllegalStateException("Cannot parse release date. The precision is unknown: " + precision);
        }
        entity.setReleaseDate(date);
        return Mono.just(entity);
    }
}
