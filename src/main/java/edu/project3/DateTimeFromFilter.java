package edu.project3;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class DateTimeFromFilter implements Predicate<LocalDateTime> {

    private final LocalDateTime from;

    public DateTimeFromFilter(LocalDateTime from) {
        this.from = from;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param localDateTime the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(LocalDateTime localDateTime) {
        return !this.from.isAfter(localDateTime);
    }
}
