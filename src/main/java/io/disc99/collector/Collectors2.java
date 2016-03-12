package io.disc99.collector;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Collectors2 {

    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return collectingAndThen(toList(), Collections::unmodifiableList);
    }

    public static <T, R> Collector<T, ?, R> toListAndThen(Function<List<T>, R> finisher) {
        return collectingAndThen(toList(), finisher);
    }

    public static <T> Collector<T, ?, Optional<List<T>>> toOptionalList() {
        return collectingAndThen(toList(), ts -> ts.isEmpty() ? Optional.empty() : Optional.of(ts));
    }

    public static <T, R> Collector<T, ?, Optional<R>> toListAndThenAfterOptional(Function<List<T>, R> finisher) {
        return collectingAndThen(toOptionalList(), op -> op.map(finisher));
    }

    public static <T, K> Collector<T, ?, Stream<Map.Entry<K, List<T>>>> groupingEntity(Function<T, K> classifier) {
        return collectingAndThen(groupingBy(classifier), m -> m.entrySet().stream());
    }
}