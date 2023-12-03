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

    public static void main(String[] args) {
        System.out.println("\uD83C\uDF84 \u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m \uD83C\uDF84");

        List<String> days = Stream.of(
                "Day 1: Report Repair",
                "Day 2: Password Philosophy",
                "Day 3: Toboggan Trajectory",
                "Day 4: Passport Processing",
                "Day 5: Binary Boarding",
                "Day 6: Custom Customs",
                "Day 7: Handy Haversacks",
                "Day 8: Handheld Halting",
                "Day 9: Encoding Error",
                "Day 10: Adapter Array",
                "Day 11: Seating System",
                "Day 12: Rain Risk",
                "Day 13: Shuttle Search",
                "Day 14: Docking Data",
                "Day 15: Rambunctious Recitation",
                "Day 16: Ticket Translation",
                "Day 17: Conway Cubes",
                "Day 18: Operation Order",
                "Day 19: Monster Messages",
                "Day 20: Jurassic Jigsaw",
                "Day 21: Allergen Assessment",
                "Day 22: Crab Combat",
                "Day 23: Crab Cups",
                "Day 24: Lobby Layout",
                "Day 25: Combo Breaker"
        ).collect(Collectors.toList());

        List<List<String>> TableVals = new ArrayList<>();

        for(int i = 0; i < days.size(); i++) {
            String dayNum = String.valueOf(i+1);
            try {
                Class<?> day = Class.forName("AoC.Days.day" + dayNum);
                Constructor<?> constructor = day.getConstructor(String.class);
                Day instance = (Day)constructor.newInstance("2020/inputs/day"+dayNum+".txt");
                long startTime = System.nanoTime();
                TableVals.add(Arrays.asList(days.get(i), instance.part1().toString(), instance.part2().toString(),""));
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                TableVals.get(i).set(3, Double.toString(duration/ 1000000.0));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        System.out.print("\n");
        String[] headers = { "Day", "Part 1", "Part 2", "Duration (ms)"};
        String[][] data = new String[TableVals.size()][];
        for (int i = 0; i < TableVals.size(); i++) {
            List<String> row = TableVals.get(i);
            data[i] = row.toArray(new String[0]);
        }
        System.out.println(ANSI_RED + FlipTable.of(headers, data) + ANSI_RESET);
    }
}
