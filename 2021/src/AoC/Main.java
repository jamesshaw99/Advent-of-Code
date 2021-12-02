package AoC;

import AoC.Days.*;
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
        TableVals.add(Arrays.asList("Day 1: Sonar Sweep", day1.part1(), day1.part2(),""));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        TableVals.get(0).set(3, Double.toString(duration/ 1000000.0));

        day2 day2 = new day2("inputs/day2.txt");
        startTime = System.nanoTime();
        TableVals.add(Arrays.asList("Day 2: Dive!", day2.part1(), day2.part2(),""));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        TableVals.get(1).set(3, Double.toString(duration/ 1000000.0));

        String[] headers = {"Day", "Part 1", "Part 2", "Total Duration (ms)"};
        String[][] data = new String[TableVals.size()][];
        for (int i = 0; i < TableVals.size(); i++) {
            List<String> row = TableVals.get(i);
            data[i] = row.toArray(new String[0]);
        }
        System.out.println(ANSI_RED + FlipTable.of(headers, data) + ANSI_RESET);
        System.exit(0); // added as a catch for if threads in day 23 don't terminate correctly
    }
}