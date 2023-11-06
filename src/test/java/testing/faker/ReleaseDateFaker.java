package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.model.ReleaseDate.Precision;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReleaseDateFaker {
    static final int MIN_YEAR = 1500;
    static final int MAX_YEAR = 2024;

    ReleaseDate.ReleaseDateBuilder builder = ReleaseDate.builder();

    Faker faker = Faker.instance();

    public ReleaseDateFaker() {
        Precision releaseDatePrecision = faker.options().option(Precision.class);

        builder.precision(releaseDatePrecision);

        if ( releaseDatePrecision == Precision.DAY ) {
            randomForDayPrecision();
        }

        if ( releaseDatePrecision == Precision.MONTH ) {
            randomForMonthPrecision();
        }

        if ( releaseDatePrecision == Precision.YEAR ) {
            randomForYearPrecision();
        }
    }

    public static ReleaseDateFaker randomReleaseDate() {
        return new ReleaseDateFaker();
    }

    public ReleaseDateFaker precision(@NotNull @NonNull Precision precision) {
        builder.precision(precision);
        return this;
    }

    public ReleaseDateFaker year(@NotNull @NonNull Integer year) {
        builder.year(year);
        return this;
    }

    public ReleaseDateFaker month(@Nullable Integer month) {
        builder.month(month);
        return this;
    }

    public ReleaseDateFaker day(@Nullable Integer day) {
        builder.day(day);
        return this;
    }

    public ReleaseDate get() {
        return builder.build();
    }

    private ReleaseDate.ReleaseDateBuilder randomForYearPrecision() {
        Integer year = faker.random().nextInt(MIN_YEAR, MAX_YEAR);
        return builder.year(year);
    }

    private ReleaseDate.ReleaseDateBuilder randomForMonthPrecision() {
        Integer monthNumber = faker.random().nextInt(1, 12);
        return randomForYearPrecision()
                .month(monthNumber);
    }

    private ReleaseDate.ReleaseDateBuilder randomForDayPrecision() {
        return randomForMonthPrecision()
                .day(faker.random().nextInt(1, 31));
    }
}
