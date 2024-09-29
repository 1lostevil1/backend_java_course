package edu.project3;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class DateTimeToFilter implements Predicate<LocalDateTime> {
    private final LocalDateTime to;

    public DateTimeToFilter(LocalDateTime to) {
        this.to = to;
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
        return !localDateTime.isAfter(to);
    }
}
