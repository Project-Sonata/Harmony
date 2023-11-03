package com.odeyalo.sonata.harmony.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class Image {
    @Nullable
    Integer width;
    @Nullable
    Integer height;
    @NotNull
    @NonNull
    String url;

    public static Image urlOnly(@NotNull String url) {
        return builder().url(url).build();
    }
}
