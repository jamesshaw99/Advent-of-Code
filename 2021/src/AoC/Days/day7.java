package AoC.Days;

import AoC.Helpers.Day;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day7 extends Day {
    int[] crabSubmarines;
    public day7(String fileStr) {
        super(fileStr);
        String test = "16,1,2,0,4,2,7,1,2,14";
        crabSubmarines = Arrays.stream(input.get(0).trim().split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public String part1() {
        int[] fuel = Arrays.stream(crabSubmarines).map(crab ->
                Math.abs(crab - Arrays.stream(crabSubmarines).sorted().toArray()[crabSubmarines.length/2])
        ).toArray();
        int sum = 0;
        for(int i: fuel) {
            sum += i;
        }

        return String.format("Fuel: %d", sum);
    }

    public String part2() {
        int mean = (int) Arrays.stream(crabSubmarines).average().getAsDouble();
        int fuel = 0;
        for (int crabPosition : crabSubmarines) {
            int diff = Math.abs(crabPosition - mean);
            fuel += diff * (diff + 1) / 2;
        }
        return String.format("Fuel: %d", fuel);
    }
}
