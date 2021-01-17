package AoC;

import AoC.days.*;
import com.jakewharton.fliptables.FlipTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        System.out.println("\uD83C\uDF84 \u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m \uD83C\uDF84");

        List<List<String>> TableVals = new ArrayList<>();

        day1 day1 = new day1("inputs/day1.txt");
        long startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 1: The Tyranny of the Rocket Equation", day1.part1(), day1.part2(),""));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        TableVals.get(0).set(3, Double.toString(duration/ 1000000.0));

        day2 day2 = new day2("inputs/day2.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 2: 1202 Program Alarm", day2.part1(), day2.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(1).set(3,Double.toString(duration/ 1000000.0));

        day3 day3 = new day3("inputs/day3.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 3: Crossed Wires", day3.part1(), day3.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(2).set(3,Double.toString(duration/ 1000000.0));

        day4 day4 = new day4("inputs/day4.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 4: Secure Container", day4.part1(), day4.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(3).set(3,Double.toString(duration/ 1000000.0));

        day5 day5 = new day5("inputs/day5.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 5: Sunny with a Chance of Asteroids", day5.part1(), day5.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(4).set(3,Double.toString(duration/ 1000000.0));

        day6 day6 = new day6("inputs/day6.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 6: Universal Orbit Map", day6.part1(), day6.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(5).set(3,Double.toString(duration/ 1000000.0));

        day7 day7 = new day7("inputs/day7.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 7: Universal Orbit Map", day7.part1(), day7.part2(), ""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(6).set(3,Double.toString(duration/ 1000000.0));

        day8 day8 = new day8("inputs/day8.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 8: Space Image Format", day8.part1(), day8.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(7).set(3,Double.toString(duration/ 1000000.0));

        day9 day9 = new day9("inputs/day9.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 9: Sensor Boost", day9.part1(), day9.part2(), ""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(8).set(3,Double.toString(duration/ 1000000.0));

        day10 day10 = new day10("inputs/day10.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 10: Monitoring Station", day10.part1(), day10.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(9).set(3,Double.toString(duration/ 1000000.0));

        day11 day11 = new day11("inputs/day11.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 11: Space Police", day11.part1(), day11.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(10).set(3,Double.toString(duration/ 1000000.0));

        day12 day12 = new day12("inputs/day12.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 12: The N-Body Problem", day12.part1(), day12.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(11).set(3,Double.toString(duration/ 1000000.0));

        day13 day13 = new day13("inputs/day13.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 13: Care Package", day13.part1(), day13.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(12).set(3,Double.toString(duration/ 1000000.0));

        day14 day14 = new day14("inputs/day14.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 14: Space Stoichiometry", day14.part1(), day14.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(13).set(3,Double.toString(duration/ 1000000.0));

        day15 day15 = new day15("inputs/day15.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 15: Oxygen System", day15.part1(), day15.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(14).set(3,Double.toString(duration/ 1000000.0));

        day16 day16 = new day16("inputs/day16.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 16: Flawed Frequency Transmission", day16.part1(), day16.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(15).set(3,Double.toString(duration/ 1000000.0));

        day17 day17 = new day17("inputs/day17.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 17: Set and Forget", day17.part1(), day17.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(16).set(3,Double.toString(duration/ 1000000.0));

        day18 day18 = new day18("inputs/day18.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 18: Many-Worlds Interpretation", day18.part1(), day18.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(17).set(3,Double.toString(duration/ 1000000.0));

        day19 day19 = new day19("inputs/day19.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 19: Tractor Beam", day19.part1(), day19.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(18).set(3,Double.toString(duration/ 1000000.0));

        day20 day20 = new day20("inputs/day20.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 20: Donut Maze", day20.part1(), day20.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(19).set(3,Double.toString(duration/ 1000000.0));

        day21 day21 = new day21("inputs/day21.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 21: Springdroid Adventure", day21.part1(), day21.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(20).set(3,Double.toString(duration/ 1000000.0));


        String[] headers = { "Day", "Part 1", "Part 2", "Total Duration (ms)"};
        String[][] data = new String[TableVals.size()][];
        for (int i = 0; i < TableVals.size(); i++) {
            List<String> row = TableVals.get(i);
            data[i] = row.toArray(new String[0]);
        }
        System.out.println(ANSI_RED + FlipTable.of(headers, data) + ANSI_RESET);
    }
}
