package com.odeyalo.sonata.harmony.repository.r2dbc.support.release;

import com.odeyalo.sonata.harmony.model.ReleaseDate;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FormattedString2ReleaseDateConverterTest {

    FormattedString2ReleaseDateConverter converter = new FormattedString2ReleaseDateConverter();

    @Test
    void shouldDecodeReleaseDateForDayPrecisionAndReturnValidString() {
        ReleaseDate actual = converter.decodeReleaseDate(ReleaseDateRowInfo.of("2023-12-03", ReleaseDate.Precision.DAY));

        assertThat(actual).isEqualTo(ReleaseDate.withDay(3, 12, 2023));
    }

    @Test
    void shouldDecodeReleaseDateForMonthPrecisionAndReturnValidString() {
        ReleaseDate actual = converter.decodeReleaseDate(ReleaseDateRowInfo.of("2023-08", ReleaseDate.Precision.MONTH));

        assertThat(actual).isEqualTo(ReleaseDate.withMonth(8, 2023));
    }

    @Test
    void shouldDecodeReleaseDateForYearPrecisionAndReturnValidString() {
        ReleaseDate actual = converter.decodeReleaseDate(ReleaseDateRowInfo.of("2023", ReleaseDate.Precision.YEAR));

        assertThat(actual).isEqualTo(ReleaseDate.onlyYear(2023));
    }

    @Test
    void shouldThrowExceptionIfTheDateAndPrecisionHintHasMismatch() {
        ReleaseDateRowInfo invalidRowInfo = ReleaseDateRowInfo.of("2023", ReleaseDate.Precision.MONTH);
        assertThatThrownBy(() -> converter.decodeReleaseDate(invalidRowInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(format("Precision hint with date mismatch. Expected date to be: %s but was: %s", "MONTH", 2023));
    }

    @Test
    void shouldEncodeReleaseDateAndAddZeroToDayIfDateIsLowerThan10() {
        ReleaseDate expected = ReleaseDate.withDay(3, 12, 2012);

        String actual = converter.encodeReleaseDate(expected);

        assertThat(actual).isEqualTo("2012-12-03");
    }

    @Test
    void shouldEncodeReleaseDateAndAddZeroToMonthIfDateIsLowerThan10() {
        ReleaseDate expected = ReleaseDate.withDay(3, 9, 2012);

        String actual = converter.encodeReleaseDate(expected);

        assertThat(actual).isEqualTo("2012-09-03");
    }

    @Test
    void shouldEncodeReleaseDateToValidString() {
        ReleaseDate expected = ReleaseDate.withDay(30, 12, 2012);

        String actual = converter.encodeReleaseDate(expected);

        assertThat(actual).isEqualTo("2012-12-30");
    }
}