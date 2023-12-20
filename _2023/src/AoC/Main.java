package AoC;

import AoC.Helpers.Day;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

public class Main {
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
                "Day 10: Pipe Maze",
                "Day 11: Cosmic Expansion",
                "Day 12: Hot Springs"
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
            String className = "AoC.Days.Day" + dayNum;
            Class<?> day = Class.forName(className);
            Constructor<?> constructor = day.getConstructor(String.class);
            Day instance = (Day) constructor.newInstance("_2023/inputs/Day" + dayNum + ".txt");

            long startTime = System.nanoTime();
            String part1 = instance.part1();
            long endTime = System.nanoTime();
            print(new String[]{days.get(i), part1, Double.toString((endTime - startTime) / 1000000.0)}, Arrays.copyOfRange(sizes, 0, 3));

            startTime = System.nanoTime();
            String part2 = instance.part2();
            endTime = System.nanoTime();
            print(new String[]{part2, Double.toString((endTime - startTime) / 1000000.0)}, Arrays.copyOfRange(sizes, 3, 5));
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
