package AoC;

import AoC.Helpers.Day;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\uD83C\uDF84 \u001b[30mA\u001b[31md\u001b[32mv\u001b[33me\u001b[34mn\u001b[35mt \u001b[36mO\u001b[37mf \u001b[30mC\u001b[31mo\u001b[32md\u001b[33me\u001b[0m \uD83C\uDF84");

        List<String> days = List.of(
                "Day 1: Chronal Calibration",
                "Day 2: Inventory Management System",
                "Day 3: No Matter How You Slice It",
                "Day 4: Repose Record",
                "Day 5: Alchemical Reduction",
                "Day 6: Chronal Coordinates",
                "Day 7: The Sum of Its Parts",
                "Day 8: Memory Maneuver",
                "Day 9: Marble Mania",
                "Day 10: The Stars Align",
                "Day 11: Chronal Charge",
                "Day 12: Subterranean Sustainability",
                "Day 13: Mine Cart Madness",
                "Day 14: Chocolate Charts",
                "Day 15: Beverage Bandits"
        );
        int[] sizes = {50,50,20,50,20};

        String lineBreak = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
        String[] headers = {"Day", "Part 1", "Part 1 Duration (ms)", "Part 2", "Part 2 Duration (ms)"};
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < headers.length; i++) {
            header.append("|").append(center(headers[i], sizes[i]));
        }
        System.out.format("%s%n%s%n%s%n", new String[]{lineBreak, String.valueOf(header), lineBreak});

        for (int i = 0; i < days.size(); i++) {
            String dayNum = String.valueOf(i + 1);
            String className = "AoC.Days.day" + dayNum;
            Class<?> day = Class.forName(className);
            Constructor<?> constructor = day.getConstructor(String.class);
            Day instance = (Day) constructor.newInstance("2018/inputs/day" + dayNum + ".txt");

            long startTime = System.nanoTime();
            String part1 = instance.part1();
            long endTime = System.nanoTime();
            String time = Double.toString((endTime - startTime) / 1000000.0);
            print(new String[]{days.get(i), part1, time}, Arrays.copyOfRange(sizes, 0, 3));

            startTime = System.nanoTime();
            String part2 = instance.part2();
            endTime = System.nanoTime();
            String time2 = Double.toString((endTime - startTime) / 1000000.0);
            print(new String[]{part2, time2}, Arrays.copyOfRange(sizes, 3, 5));
            System.out.format("|%n");
        }

        System.out.println(lineBreak);

    }

    public static void print(String[] s, int[] size) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            line.append("|").append(center(s[i], size[i]));
        }
        System.out.print(line);
    }

    public static String center(String s, int size) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        sb.append(" ".repeat((size - s.length()) / 2));
        sb.append(s);
        while (sb.length() < size) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
