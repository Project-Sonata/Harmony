package com.odeyalo.sonata.harmony.repository.r2dbc.support.release;

import com.odeyalo.sonata.harmony.model.ReleaseDate;
import org.jetbrains.annotations.NotNull;

public record ReleaseDateRowInfo(@NotNull String date,
                                 @NotNull ReleaseDate.Precision precisionHint) {

    public static ReleaseDateRowInfo of(String date, ReleaseDate.Precision precisionHint) {
        return new ReleaseDateRowInfo(date, precisionHint);
    }
}
