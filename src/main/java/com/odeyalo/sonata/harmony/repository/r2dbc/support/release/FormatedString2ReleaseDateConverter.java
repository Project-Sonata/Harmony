package com.odeyalo.sonata.harmony.repository.r2dbc.support.release;

import com.odeyalo.sonata.harmony.model.ReleaseDate;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

public class FormatedString2ReleaseDateConverter implements ReleaseDateConverter<ReleaseDateRowInfo, String> {

    private static final String DATE_SPLITERATOR = "-";

    @Override
    @NotNull
    public ReleaseDate decodeReleaseDate(@NotNull ReleaseDateRowInfo source) {
        String releaseDate = source.date();
        ReleaseDate.Precision precision = source.precisionHint();

        try {
            if (precision == ReleaseDate.Precision.YEAR) {
                return parseYearAwareReleaseDate(releaseDate);
            }

            if (precision == ReleaseDate.Precision.MONTH) {
                return parseMonthAwareReleaseDate(releaseDate);
            }

            if (precision == ReleaseDate.Precision.DAY) {
                return parseDayAwareReleaseDate(releaseDate);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(format("Precision hint with date mismatch. Expected date to be: %s but was: %s", precision.name(), releaseDate));
        }

        throw new IllegalArgumentException("The precision is unknown and cannot be parsed");
    }

    @Override
    public String encodeReleaseDate(@NotNull ReleaseDate releaseDate) {
        return "null";
    }

    private static ReleaseDate parseYearAwareReleaseDate(String releaseDate) {
        return ReleaseDate.onlyYear(Integer.parseInt(releaseDate));
    }

    private static ReleaseDate parseMonthAwareReleaseDate(String releaseDate) {
        String[] yearAndMonth = splitByRegex(releaseDate);
        int year = Integer.parseInt(yearAndMonth[0]);
        int month = Integer.parseInt(yearAndMonth[1]);

        return ReleaseDate.withMonth(month, year);
    }

    private static ReleaseDate parseDayAwareReleaseDate(String releaseDate) {
        String[] yearAndMonthAndDay = splitByRegex(releaseDate);
        int year = Integer.parseInt(yearAndMonthAndDay[0]);
        int month = Integer.parseInt(yearAndMonthAndDay[1]);
        int day = Integer.parseInt(yearAndMonthAndDay[2]);

        return ReleaseDate.withDay(day, month, year);
    }

    @NotNull
    private static String[] splitByRegex(String releaseDate) {
        return releaseDate.split(DATE_SPLITERATOR);
    }
}
