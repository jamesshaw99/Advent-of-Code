package AoC;

import AoC.Days.*;
import AoC.Helpers.Day;
import com.jakewharton.fliptables.FlipTable;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        System.out.println("\uD83C\uDF84 \u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m \uD83C\uDF84");

        List<String> days = Stream.of("Day 1: Sonar Sweep","Day 2: Dive!","Day 3: Binary Diagnostic","Day 4: Giant Squid").collect(Collectors.toList());

        List<List<String>> TableVals = new ArrayList<>();

        for(int i = 0; i < days.size(); i++) {
            String dayNum = String.valueOf(i+1);
            Class<?> day = Class.forName("AoC.Days.day" + dayNum);
            Constructor<?> constructor = day.getConstructor(String.class);
            Day instance = (Day)constructor.newInstance("inputs/day"+dayNum+".txt");
            long startTime = System.nanoTime();
            TableVals.add(Arrays.asList(days.get(i), instance.part1(), instance.part2(),""));
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            TableVals.get(i).set(3, Double.toString(duration/ 1000000.0));
        }

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