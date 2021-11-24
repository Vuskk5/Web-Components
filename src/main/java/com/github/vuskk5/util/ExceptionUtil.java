package com.github.vuskk5.util;

import java.util.Arrays;
import java.util.stream.Stream;

public class ExceptionUtil {
    private static final String[] filters = {
            "org.testng", "com.intellij", "sun.reflect", "java.lang",
            "java.util", "org.openqa.selenium", "com.fasterxml.jackson", "java.time"
    };

    /**
     * Filters every {@link StackTraceElement} containing any of the {@code filters},
     * <br>from the given {@code throwable}.
     * @param throwable The throwable to filter.
     * @param filters   The filters to apply.
     * @return The filtered exception
     */
    public static <T extends Throwable> T filterStackTrace(T throwable, String... filters) {
        StackTraceElement[] filteredStack =
                Arrays.stream(throwable.getStackTrace())
                      .filter(element -> Stream.of(filters).noneMatch(element.getClassName()::contains))
                      .toArray(StackTraceElement[]::new);

        throwable.setStackTrace(filteredStack);

        return throwable;
    }

    /**
     * Filters every {@link StackTraceElement} containing any of the {@code filters},
     * <br>from the given {@code throwable}.
     * @param throwable The throwable to filter.
     * @return The filtered exception
     */
    public static <T extends Throwable> T filterStackTrace(T throwable) {
        return filterStackTrace(throwable, filters);
    }
}
