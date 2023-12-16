package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day9 extends Day {
    List<List<Long>> oasisReport = new ArrayList<>();

    public Day9(String fileStr) {
        super(fileStr);
        input.forEach(line -> {
            String[] stringArray = line.split("\\s+");
            List<Long> inputList = new ArrayList<>(stringArray.length);
            LongStream.range(0, stringArray.length).mapToObj(i -> Long.parseLong(stringArray[(int) i])).forEach(inputList::add);
            oasisReport.add(inputList);
        });
    }

    public String part1() {
        return "Sum of extrapolated values (forward): " + solution(true);
    }

    public String part2() {
        return "Sum of extrapolated values (backwards): " + solution(false);
    }

    private long solution(boolean part1) {
        return oasisReport.stream().mapToLong(longs -> (part1 ? longs.get(longs.size() - 1) + calcRes(longs, true) : longs.get(0) - calcRes(longs, false))).sum();
    }

    private long calcRes(List<Long> longs, boolean part1) {
        List<Long> deltas = connectedPairs(longs).mapToObj(i -> difference(longs.get(i - 1), longs.get(i))).toList();
        if (deltas.stream().distinct().count() > 1L) {
            return part1 ? deltas.get(deltas.size() - 1) + calcRes(deltas, true) : deltas.get(0) - calcRes(deltas, false);
        }
        return deltas.get(deltas.size() - 1);
    }

    public long difference(long l1, long l2) {
        return l2 - l1;
    }

    public static <A> IntStream connectedPairs(List<A> l) {
        return IntStream.range(1, l.size());
    }
}
