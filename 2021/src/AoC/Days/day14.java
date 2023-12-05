package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;
import java.util.stream.Collectors;

public class day14 extends Day {
    private final String template;
    private final Map<String, String> rules;

    public day14(String fileStr) {
        super(fileStr);
        template = input.get(0);
        rules = input.stream().skip(2).map(line -> line.split(" -> ")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    public String part1() {
        return "Quantity difference: " + runSimulation(10);
    }

    public String part2() {
        return "Quantity difference: " + runSimulation(40);
    }

    private String runSimulation(int iterations) {
        var counter = initializeCounter();
        for (var step = 0; step < iterations; step++) {
            counter = performStep(counter);
        }
        return String.valueOf(calculateDifference(counter));
    }

    private Map<String, Long> initializeCounter() {
        var counter = new HashMap<String, Long>();
        for (var i = 0; i < template.length() - 1; i++) {
            counter.merge(template.substring(i, i + 2), 1L, Long::sum);
        }
        return counter;
    }

    private Map<String, Long> performStep(Map<String, Long> counter) {
        var next = new HashMap<String, Long>();
        counter.forEach((pair, value) -> {
            rules.get(pair).chars().mapToObj(c -> (char) c).forEach(middle -> {
                next.merge(pair.charAt(0) + String.valueOf(middle), value, Long::sum);
                next.merge(String.valueOf(middle) + pair.charAt(1), value, Long::sum);
            });
        });
        return next;
    }

    private long calculateDifference(Map<String, Long> counter) {
        var map = new long[26];
        counter.forEach((pair, value) -> map[pair.charAt(0) - 'A'] += value);
        map[template.charAt(template.length() - 1) - 'A']++;
        var max = Arrays.stream(map).filter(i -> i > 0).max().orElseThrow();
        var min = Arrays.stream(map).filter(i -> i > 0).min().orElseThrow();
        return max - min;
    }
}
