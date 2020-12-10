package AoC;

import AoC.days.*;
import com.jakewharton.fliptables.FlipTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        System.out.println("\u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m");

        List<List<String>> TableVals = new ArrayList<>();

        day1 day1 = new day1("inputs/day1.txt");
        TableVals.add(Arrays.asList("Day 1: Report Repair", Integer.toString(day1.part1()), Integer.toString(day1.part2())));
        day2 day2 = new day2("inputs/day2.txt");
        TableVals.add(Arrays.asList("Day 2: Password Philosophy", Integer.toString(day2.part1()), Integer.toString(day2.part2())));
        day3 day3 = new day3("inputs/day3.txt");
        TableVals.add(Arrays.asList("Day 3: Toboggan Trajectory", Integer.toString(day3.part1()), Long.toString(day3.part2())));
        day4 day4 = new day4("inputs/day4.txt");
        TableVals.add(Arrays.asList("[33mDay 4: Passport Processing", Integer.toString(day4.part1()), Integer.toString(day4.part2())));
        day5 day5 = new day5("inputs/day5.txt");
        TableVals.add(Arrays.asList("Day 5: Binary Boarding", Integer.toString(day5.part1()), Integer.toString(day5.part2())));
        day6 day6 = new day6("inputs/day6.txt");
        TableVals.add(Arrays.asList("Day 6: Custom Customs", Integer.toString(day6.part1()), Integer.toString(day6.part2())));
        day7 day7 = new day7("inputs/day7.txt");
        TableVals.add(Arrays.asList("Day 7: Handy Haversacks", Integer.toString(day7.part1()), Integer.toString(day7.part2())));
        day8 day8 = new day8("inputs/day8.txt");
        TableVals.add(Arrays.asList("Day 8: Handheld Halting", Integer.toString(day8.part1()), Integer.toString(day8.part2())));
        day9 day9 = new day9("inputs/day9.txt");
        TableVals.add(Arrays.asList("Day 9: Encoding Error", Long.toString(day9.part1()), Long.toString(day9.part2())));
        day10 day10 = new day10("inputs/day10.txt");
        TableVals.add(Arrays.asList("Day 10: Adapter Array", Integer.toString(day10.part1()), Long.toString(day10.part2())));

        String[] headers = { "Day", "Part 1", "Part 2"};
        String[][] data = new String[TableVals.size()][];
        for (int i = 0; i < TableVals.size(); i++) {
            List<String> row = TableVals.get(i);
            data[i] = row.toArray(new String[0]);
        }
        System.out.println(ANSI_CYAN + FlipTable.of(headers, data) + ANSI_RESET);
    }
}
