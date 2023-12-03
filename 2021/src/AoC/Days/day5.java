package AoC.Days;

import AoC.Helpers.Day;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day5 extends Day {
    int[][] grid;
    List<Line> lines = new ArrayList<>();
    int maxX, maxY;

    public day5(String fileStr) {
        super(fileStr);

        for(String line : input){
            String[] coords = line.split(" -> ");
            lines.add(new Line(coords));
        }
        for(Line line : lines){
            Point maxPoint = line.maxCoords();
            maxX = Math.max(maxPoint.x, maxX);
            maxY = Math.max(maxPoint.y, maxY);
        }
        grid = new int[maxY+1][maxX+1];
        for(int[] row: grid){
            Arrays.fill(row, 0);
        }
    }

    public String part1() {
        int overlap = 0;
        for (Line line: lines) {
            List<Point> points = line.getNonDiagPoints();
            for (Point point : points) {
                grid[point.y][point.x]++;
                if(grid[point.y][point.x] == 2){
                    overlap++;
                }
            }
        }
        return String.format("overlap points: %d", overlap);
    }

    public String part2() {
        int overlap = 0;
        grid = new int[maxY+1][maxX+1];
        for(int[] row: grid){
            Arrays.fill(row, 0);
        }
        for (Line line: lines) {
            List<Point> points = line.getPoints();
            for (Point point : points) {
                grid[point.y][point.x]++;
                if(grid[point.y][point.x] == 2){
                    overlap++;
                }
            }
        }
        return String.format("overlap points: %d", overlap);
    }

    private class Line{
        private List<Point> nonDiagPoints = new ArrayList<>(),
                DiagPoints = new ArrayList<>();
        private final int maxX, maxY;

        public Line(String[] coords) {
            int[] coord1 = Arrays.stream(coords[0].split(",")).mapToInt(Integer::parseInt).toArray(),
                    coord2 = Arrays.stream(coords[1].split(",")).mapToInt(Integer::parseInt).toArray();
            int x1 = Math.min(coord1[0], coord2[0]),
                y1 = Math.min(coord1[1], coord2[1]);
            maxX = Math.max(coord1[0], coord2[0]);
            maxY = Math.max(coord1[1], coord2[1]);
            if(x1 == maxX){
                for(int y = y1; y <= maxY; y++){
                    nonDiagPoints.add(new Point(x1,y));
                }
            } else if(y1 == maxY){
                for(int x = x1; x <= maxX; x++){
                    nonDiagPoints.add(new Point(x,y1));
                }
            } else {
                if (coord1[0] > coord2[0]){
                    if(coord1[1] > coord2[1]){
                        //diagonal bottom right to top left
                        for (int x = coord1[0], y = coord1[1]; x >= coord2[0] && y >= coord2[1]; x--, y--){
                            DiagPoints.add(new Point(x, y));
                        }
                    } else {
                        //diagonal top right to bottom left
                        for (int x = coord1[0], y = coord1[1]; x >= coord2[0] && y <= coord2[1]; x--, y++){
                            DiagPoints.add(new Point(x, y));
                        }
                    }
                } else {
                    if(coord1[1] > coord2[1]){
                        //diagonal bottom left to top right
                        for (int x = coord1[0], y = coord1[1]; x <= coord2[0] && y >= coord2[1]; x++, y--){
                            DiagPoints.add(new Point(x, y));
                        }
                    } else {
                        //diagonal top left to bottom right
                        for (int x = coord1[0], y = coord1[1]; x <= coord2[0] && y <= coord2[1]; x++, y++){
                            DiagPoints.add(new Point(x, y));
                        }
                    }
                }
            }
        }

        public Point maxCoords() {
            return new Point(maxX, maxY);
        }

        public List<Point> getNonDiagPoints() {
            return this.nonDiagPoints;
        }

        public List<Point> getPoints() {
            return this.nonDiagPoints.size() > 0 ? this.nonDiagPoints : this.DiagPoints;
        }
    }
}
