package io.disc99.collector;

import lombok.AllArgsConstructor;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static io.disc99.collector.Collectors2.toListAndThen;
import static io.disc99.collector.Collectors2.toListAndThenAfterOptional;
import static io.disc99.collector.Collectors2.toUnmodifiableList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Collectors2Test {

    private Stream<String> stream() {
        return Stream.of("1","2","3","10","20","30");
    }

    @Test
    public void t1() {
        // Collectorを作る
        // List を作るCollector

        // 楽にCollectorを使う
        List<String> c1 = stream().collect(toList());
        Set<String> c2 = stream().collect(toSet());
        String c3 = stream().collect(joining());
        String c4 = stream().collect(joining(","));
        String c5 = stream().collect(joining(",", "<", ">"));
        Map<Integer, List<String>> c6 = stream().collect(groupingBy(String::length)); // {1=[1, 2, 3, 4, 5, 6, 7, 8, 9], 2=[10]}
        Map<Integer, String> c7 = stream().collect(groupingBy(String::length, joining())); //{1=123456789, 2=10}
        TreeMap<Integer, String> c8 = stream().collect(groupingBy(String::length, TreeMap::new, joining())); // {1=123456789, 2=10}
        Map<Boolean, List<String>> c9 = stream().collect(partitioningBy(c -> c.equals("10"))); // {false=[1, 2, 3, 4, 5, 6, 7, 8, 9], true=[10]}
        Map<Integer, String> c10 = stream().collect(toMap(Integer::parseInt, identity()));
        Map<Integer, String> c11 = stream().collect(toMap(String::length, identity(), (s1, s2) -> s1 + ":" + s2)); // {1=1:2:3, 2=10:20:30}
        List<String> c12 = stream().collect(collectingAndThen(toList(), Collections::unmodifiableList));

        // Collectorを組み合わせる
        List<String> c13 = stream().collect(collectingAndThen(toList(), Collections::unmodifiableList));
        List<String> c14 = stream().collect(toUnmodifiableList());

        History c15 = stream().collect(collectingAndThen(toList(), History::new));
        History c16 = stream().collect(toListAndThen(History::new));

        // Optional
        Optional<History> c17 = stream().collect(collectingAndThen(toList(),
                events -> events.isEmpty() ? Optional.empty() : Optional.of(new History(events))));
        Optional<History> c18 = stream().collect(toListAndThenAfterOptional(History::new));

        // Future
        // Tuple
        // Either

    }


}
@AllArgsConstructor
class History {
    List<String> events;
}
