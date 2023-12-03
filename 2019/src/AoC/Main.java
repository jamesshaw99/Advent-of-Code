package AoC;

import AoC.Days.*;
import AoC.helpers.Day;
import com.jakewharton.fliptables.FlipTable;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        System.out.println("\uD83C\uDF84 \u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m \uD83C\uDF84");

        List<String> days = Stream.of(
                "Day 1: The Tyranny of the Rocket Equation",
                "Day 2: 1202 Program Alarm",
                "Day 3: Crossed Wires",
                "Day 4: Secure Container",
                "Day 5: Sunny with a Chance of Asteroids",
                "Day 6: Universal Orbit Map",
                "Day 7: Amplification Circuit",
                "Day 8: Space Image Format",
                "Day 9: Sensor Boost",
                "Day 10: Monitoring Station",
                "Day 11: Space Police",
                "Day 12: The N-Body Problem",
                "Day 13: Care Package",
                "Day 14: Space Stoichiometry",
                "Day 15: Oxygen System",
                "Day 16: Flawed Frequency Transmission",
                "Day 17: Set and Forget",
                "Day 18: Many-Worlds Interpretation",
                "Day 19: Tractor Beam",
                "Day 20: Donut Maze",
                "Day 21: Springdroid Adventure",
                "Day 22: Slam Shuffle",
                "Day 23: Category Six",
                "Day 24: Planet of Discord",
                "Day 25: Cryostasis"
        ).toList();

        List<List<String>> TableVals = new ArrayList<>();

        for(int i = 0; i < days.size(); i++) {
            String dayNum = String.valueOf(i+1);
            Class<?> day = Class.forName("AoC.Days.day" + dayNum);
            Constructor<?> constructor = day.getConstructor(String.class);
            Day instance = (Day)constructor.newInstance("2019/inputs/day"+dayNum+".txt");
            long startTime = System.nanoTime();
            TableVals.add(Arrays.asList(days.get(i), instance.part1(), instance.part2(),""));
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            TableVals.get(i).set(3, Double.toString(duration/ 1000000.0));
        }

        String[] headers = { "Day", "Part 1", "Part 2", "Total Duration (ms)"};
        String[][] data = new String[TableVals.size()][];
        for (int i = 0; i < TableVals.size(); i++) {
            List<String> row = TableVals.get(i);
            data[i] = row.toArray(new String[0]);
        }
        System.out.println(ANSI_RED + FlipTable.of(headers, data) + ANSI_RESET);
        System.exit(0); // added as a catch for if threads in day 23 don't terminate correctly
    }
}
