package com.odeyalo.sonata.harmony.repository.r2dbc.support.release;

import com.odeyalo.sonata.harmony.model.ReleaseDate;
import org.jetbrains.annotations.NotNull;

/**
 * Decode the release date from the given source
 * @param <S> - source
 */
public interface ReleaseDateDecoder<S> {

    @NotNull
    ReleaseDate decodeReleaseDate(@NotNull S source);

}
