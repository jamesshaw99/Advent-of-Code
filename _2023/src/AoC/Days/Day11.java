package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends Day {
    private final List<Integer> emptyRows = new ArrayList<>();
    private final List<Integer> emptyCols = new ArrayList<>();
    private final List<int[]> galaxies = new ArrayList<>();

    public Day11(String fileStr) {
        super(fileStr);
        processInput();
    }

    private void processInput() {
        for (int i = 0; i < input.size(); i++) {
            String row = input.get(i);
            if (!row.contains("#")) {
                emptyRows.add(i);
            }
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) != '.') {
                    galaxies.add(new int[]{i, j});
                }
            }
        }

        for (int j = 0; j < input.get(0).length(); j++) {
            boolean isEmptyCol = true;
            for (String row : input) {
                if (row.charAt(j) != '.') {
                    isEmptyCol = false;
                    break;
                }
            }
            if (isEmptyCol) {
                emptyCols.add(j);
            }
        }
    }

    public String part1() {
        return "Sum of shortest paths: " + computeTotalDistance(2);
    }

    public String part2() {
        return "Sum of shortest paths: " + computeTotalDistance(1000000);
    }

    private long computeTotalDistance(int expansionRatio) {
        long totalDistance = 0;

        for (int i = 0; i < galaxies.size() - 1; i++) {
            int[] srcGalaxy = findLocationAfterExpansion(galaxies.get(i), expansionRatio);
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] dstGalaxy = findLocationAfterExpansion(galaxies.get(j), expansionRatio);
                totalDistance += Math.abs(srcGalaxy[0] - dstGalaxy[0]) + Math.abs(srcGalaxy[1] - dstGalaxy[1]);
            }
        }
        return totalDistance;
    }

    private int[] findLocationAfterExpansion(int[] pos, int expansionRatio) {
        int x = pos[0];
        int y = pos[1];
        expansionRatio--;

        int expandedX = 0;
        int expandedY = 0;
        for (int row : emptyRows) {
            if (x > row) expandedX += expansionRatio;
        }

        for (int col : emptyCols) {
            if (y > col) expandedY += expansionRatio;
        }
        return new int[]{x + expandedX, y + expandedY};
    }
}
