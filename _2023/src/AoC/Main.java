package AoC;

import AoC.Helpers.Day;
import com.jakewharton.fliptables.FlipTable;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        System.out.println("\uD83C\uDF84 \u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m \uD83C\uDF84");

        List<String> days = List.of(
                "Day 1: Trebuchet?!",
                "Day 2: Cube Conundrum",
                "Day 3: Gear Ratios",
                "Day 4: Scratchcards",
                "Day 5: If You Give A Seed A Fertilizer",
                "Day 6: Wait For It",
                "Day 7: Camel Cards",
                "Day 8: Haunted Wasteland",
                "Day 9: Mirage Maintenance",
                "Day 10: Pipe Maze"
        );
        List<List<String>> tableVals = new ArrayList<>();

        for (int i = 0; i < days.size(); i++) {
            String dayNum = String.valueOf(i + 1);
            Class<?> day = Class.forName("AoC.Days.Day" + dayNum);
            Constructor<?> constructor = day.getConstructor(String.class);
            Day instance = (Day) constructor.newInstance("_2023/inputs/Day" + dayNum + ".txt");
            long startTime = System.nanoTime();
            tableVals.add(Arrays.asList(days.get(i), instance.part1(), instance.part2(), ""));
            long endTime = System.nanoTime();
            tableVals.get(i).set(3, Double.toString((endTime - startTime) / 1000000.0));
        }

        String[] headers = {"Day", "Part 1", "Part 2", "Total Duration (ms)"};
        String[][] data = tableVals.stream().map(row -> row.toArray(new String[0])).toArray(String[][]::new);
        System.out.println(ANSI_RED + FlipTable.of(headers, data) + ANSI_RESET);
    }
}