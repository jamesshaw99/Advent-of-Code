package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class day6 extends Day {
    List<Integer> Lanternfish  = new ArrayList<>();
    public day6(String fileStr) {
        super(fileStr);
        Lanternfish  = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public String part1() {
        int numDays = 80;
        long count = countLanternFish(Lanternfish, numDays);
        return String.format("After %d days, there are a total of %d fish", numDays, count);
    }

    public String part2() {
        Lanternfish  = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
        int numDays = 256;
        long count = countLanternFish(Lanternfish, numDays);
        return String.format("After %d days, there are a total of %d fish", numDays, count);
    }

    private long countLanternFish(List<Integer> state, int days) {
        long[] fish = new long[9];
        state.forEach(i -> fish[i]++);

        for (int day = 0; day < days; day++) {
            long newFish = fish[0];
            System.arraycopy(fish, 1, fish, 0, fish.length - 1);
            fish[6] += newFish;
            fish[8] = newFish;
        }

        return Arrays.stream(fish).sum();
    }
}
