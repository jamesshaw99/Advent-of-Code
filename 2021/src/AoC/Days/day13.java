package AoC.Days;

import AoC.Helpers.Day;

import java.awt.*;
import java.util.*;
import java.util.List;

public class day13 extends Day {
    private final List<String> instructions = new ArrayList<>();
    private final List<Point> points = new ArrayList<>();

    public day13(String fileStr) {
        super(fileStr);
        parseInput();
    }

    private void parseInput() {
        boolean isInstructions = false;

        for (String line : input) {
            if (line.isBlank()) {
                isInstructions = true;
                continue;
            }
            if (isInstructions) {
                instructions.add(line);
            } else {
                String[] coordinates = line.split(",");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                points.add(new Point(x, y));
            }
        }
    }

    public String part1() {
        runInstructions(1);
        int visibleDots = countVisibleDots(points);
        return "Visible dots after one fold: " + visibleDots;
    }

    public String part2() {
        runInstructions(instructions.size());
        return "Infrared thermal imaging camera system code:\n" + printLetters();
    }

    private String printLetters() {
        int minX = points.stream().mapToInt(point -> point.x).min().orElseThrow();
        int minY = points.stream().mapToInt(point -> point.y).min().orElseThrow();
        int maxX = points.stream().mapToInt(point -> point.x).max().orElseThrow();
        int maxY = points.stream().mapToInt(point -> point.y).max().orElseThrow();
        boolean[][] image = new boolean[maxY - minY + 1][maxX - minX + 1];

        for (Point point : points) {
            image[point.y - minY][point.x - minX] = true;
        }

        StringBuilder sb = new StringBuilder();
        for (boolean[] row : image) {
            for (boolean dot : row) {
                sb.append(dot ? "#" : " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private void runInstructions(int noFolds) {
        for (int i = 0; i < noFolds; i++) {
            String line = instructions.get(i);
            String[] parts = line.split(" ");
            String[] valueParts = parts[2].split("=");
            String direction = valueParts[0];
            int value = Integer.parseInt(valueParts[1]);
            fold(points, direction, value);
        }
    }

    private static void fold(List<Point> dots, String direction, int value) {
        for (Point dot : dots) {
            if (Objects.equals(direction, "y") && dot.y > value) {
                dot.setLocation(dot.x, 2 * value - dot.y);
            }
            else if(Objects.equals(direction, "x") && dot.x > value){
                dot.setLocation(2 * value - dot.x, dot.y);
            }
        }
    }

    private static int countVisibleDots(List<Point> dots) {
        Set<String> uniquePositions = new HashSet<>();
        for (Point dot : dots) {
            uniquePositions.add(dot.x + "," + dot.y);
        }
        return uniquePositions.size();
    }
}
