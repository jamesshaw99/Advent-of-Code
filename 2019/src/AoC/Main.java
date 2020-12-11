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
        TableVals.add(Arrays.asList("Day 1: The Tyranny of the Rocket Equation", Long.toString(day1.part1()), Long.toString(day1.part2())));
        day2 day2 = new day2("inputs/day2.txt");
        TableVals.add(Arrays.asList("Day 2: 1202 Program Alarm", Long.toString(day2.part1()), Integer.toString(day2.part2())));
        day3 day3 = new day3("inputs/day3.txt");
        TableVals.add(Arrays.asList("Day 3: Crossed Wires", Integer.toString(day3.part1()), Integer.toString(day3.part2())));
        day4 day4 = new day4("inputs/day4.txt");
        TableVals.add(Arrays.asList("Day 4: Secure Container", Integer.toString(day4.part1()), Integer.toString(day4.part2())));
        day5 day5 = new day5("inputs/day5.txt");
        TableVals.add(Arrays.asList("Day 5: Sunny with a Chance of Asteroids", Long.toString(day5.part1()), Long.toString(day5.part2())));
        day6 day6 = new day6("inputs/day6.txt");
        TableVals.add(Arrays.asList("Day 6: Universal Orbit Map", Long.toString(day6.part1()), Long.toString(day6.part2())));
        day7 day7 = new day7("inputs/day7.txt");
        TableVals.add(Arrays.asList("Day 7: Universal Orbit Map", Long.toString(day7.part1()), Long.toString(day7.part2()) + " <- rage quit (╯°□°）╯︵ ┻━┻ "));
        day8 day8 = new day8("inputs/day8.txt");

        String[] headers = { "Day", "Part 1", "Part 2"};
        String[][] data = new String[TableVals.size()][];
        for (int i = 0; i < TableVals.size(); i++) {
            List<String> row = TableVals.get(i);
            data[i] = row.toArray(new String[0]);
        }
        System.out.println(ANSI_RED + FlipTable.of(headers, data) + ANSI_RESET);
    }
}
