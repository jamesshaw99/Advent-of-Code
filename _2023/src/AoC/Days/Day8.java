package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class Day8 extends Day {
    private final String instructions;
    private final Map<String, String[]> nodes = new HashMap<>();

    public Day8(String fileStr) {
        super(fileStr);
        instructions = input.get(0);
        parseNodes();
    }

    private void parseNodes() {
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String[] parts = line.split(" = ");
            nodes.put(parts[0], parts[1].replace("(", "").replace(")", "").split(", "));
        }
    }

    public String part1() {
        int steps = navigateUntilZ("AAA");
        return "Number of steps: " + steps;
    }

    public String part2() {
        List<Long> allSteps = new ArrayList<>();

        for (String current : nodes.keySet()) {
            if (current.endsWith("A")) {
                long steps = navigateUntilZ(current);
                allSteps.add(steps);
            }
        }

        return "Number of steps: " + findLCM(allSteps.stream().mapToLong(Long::longValue).toArray());
    }

    private int navigateUntilZ(String current) {
        int steps = 0;
        while (!current.endsWith("Z")) {
            for (char step : instructions.toCharArray()) {
                current = nodes.get(current)[step == 'L' ? 0 : 1];
                steps++;
            }
        }
        return steps;
    }

    private static long findGCD(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long findLCM(long a, long b) {
        return (a * b) / findGCD(a, b);
    }

    private static long findLCM(long[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        long lcm = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            lcm = findLCM(lcm, numbers[i]);
        }

        return lcm;
    }
}
