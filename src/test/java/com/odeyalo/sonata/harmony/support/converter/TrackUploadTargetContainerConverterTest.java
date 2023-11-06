package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.TrackContainerDto;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTargetContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.TrackContainerDtoFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.List;

import static testing.faker.TrackContainerDtoFaker.randomTracksDto;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class TrackUploadTargetContainerConverterTest {

    @Autowired
    TrackUploadTargetContainerConverter testable;
    // Support to convert values to TrackUploadTarget
    // Ofc it breaks the black-box testing, but it will be ugly if testing without this converter
    @Autowired
    TrackUploadTargetConverter trackUploadTargetConverter;


    @Test
    void toTrackUploadTargetContainerFromDto() {
        TrackContainerDto containerDto = randomTracksDto().get();

        List<TrackUploadTarget> expectedTargets = containerDto.stream()
                .map(trackUploadTargetConverter::toUploadTrackTarget)
                .toList();

        TrackUploadTargetContainer result = testable.toTrackUploadTargetContainer(containerDto);

        assertThat(result).containsAll(expectedTargets);
    }
}