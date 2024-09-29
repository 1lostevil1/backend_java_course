package edu.project3;

import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DateTimeFromFilterTest {
    static Arguments[] expectations() {
        return new Arguments[]{
            Arguments.of(LocalDateTime.of(1970, 1, 1, 1, 1, 2), true),
            Arguments.of(LocalDateTime.of(1970, 1, 1, 1, 1, 1), true),
            Arguments.of(LocalDateTime.of(1970, 1, 1, 1, 1, 0), false),
        };
    }

    @ParameterizedTest
    @DisplayName("Проверка фильтров")
    @MethodSource("expectations")
    void successful(LocalDateTime dateTime, boolean expects) throws Exception {
        //given
        var filter = new DateTimeFromFilter(LocalDateTime.of(1970, 1, 1, 1, 1, 1));
        //expect
        assertThat(filter.test(dateTime)).isEqualTo(expects);
    }
}
