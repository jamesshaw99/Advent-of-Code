package AoC.days;

import AoC.helpers.Day;
import AoC.helpers.GraphBuilder;
import AoC.helpers.InfiniteUndirectedGraph;
import com.google.common.collect.Sets;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import javax.vecmath.Point3d;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class day20 extends Day {
    private final String puzzleInput;
    public day20(String fileStr) {
        super(fileStr);
        StringBuilder builder = new StringBuilder();
        for(String line: input) {
            line = String.format("%-129s", line);
            builder.append(line).append('\n');
        }
        puzzleInput = builder.toString();
    }

    public String part1() {
        Maze2D maze = new Maze2D(puzzleInput);
        return String.valueOf(maze.getShortestPathLength());
    }

    public String part2() {
        Maze3D maze = new Maze3D(puzzleInput);
        return String.valueOf(maze.getShortestPathLength());
    }

    private class Maze2D {
        private final Point start;
        private final Point end;
        private final Graph<Point, DefaultWeightedEdge> graph;

        private Maze2D(String input) {
            String[][] grid = getRawGrid(input);
            Set<Point> tunnels = getTunnels(grid);

            Map<String, Point> outer = getOuterLabels(grid);
            Map<String, Point> inner = getInnerLabels(grid);

            this.start = outer.get("AA");
            this.end = outer.get("ZZ");

            Map<Point, Point> portals = new HashMap<>();
            inner.forEach((label, point1) -> {
                Point point2 = outer.get(label);
                portals.put(point1, point2);
                portals.put(point2, point1);
            });

            this.graph = GraphBuilder.buildUndirectedGraph(tunnels, point -> adjacentPoints(tunnels, portals, point));
            GraphBuilder.reduceGraph(this.graph, Sets.union(portals.keySet(), Set.of(this.start, this.end)));
        }

        private long getShortestPathLength() {
            return (long) new BidirectionalDijkstraShortestPath<>(this.graph).getPath(this.start, this.end).getWeight();
        }

        private Set<Point> adjacentPoints(Set<Point> tunnels, Map<Point, Point> portals, Point point) {
            Set<Point> adjacent = adjacentPoints(point);
            adjacent.removeIf(p -> !tunnels.contains(p));
            Point portal = portals.get(point);
            if(portal != null){
                adjacent.add(portal);
            }
            return adjacent;
        }

        public Set<Point> adjacentPoints(Point point) {
            int x = point.x, y = point.y;
            Set<Point> adjacent = new HashSet<>();
            adjacent.add(new Point(x, y - 1));
            adjacent.add(new Point(x, y + 1));
            adjacent.add(new Point(x - 1, y));
            adjacent.add(new Point(x + 1, y));
            return adjacent;
        }
    }

    private class Maze3D {
        private final Point3d start;
        private final Point3d end;
        private final Maze3DGraph graph;

        private Maze3D(String input) {
            String[][] grid = getRawGrid(input);
            Set<Point> tunnels = getTunnels(grid);

            Map<String, Point> outer = getOuterLabels(grid);
            Map<String, Point> inner = getInnerLabels(grid);

            Point planarStart = outer.get("AA");
            this.start = new Point3d(planarStart.x, planarStart.y, 0);
            Point planerEnd = outer.get("ZZ");
            this.end = new Point3d(planerEnd.x, planerEnd.y, 0);

            Map<Point, Point> innerPortals = new HashMap<>();
            Map<Point, Point> outerPortals = new HashMap<>();
            inner.forEach((label, innerPoint) -> {
                Point outerPoint = outer.get(label);
                innerPortals.put(innerPoint, outerPoint);
                outerPortals.put(outerPoint, innerPoint);
            });

            this.graph = new Maze3DGraph(this.start, this.end, tunnels, innerPortals, outerPortals);
        }

        private long getShortestPathLength() {
            return new BidirectionalDijkstraShortestPath<>(this.graph).getPath(this.start, this.end).getLength();
        }
    }

    private static class Maze3DGraph extends InfiniteUndirectedGraph<Point3d> {
        private final Point3d start;
        private final Point3d end;
        private final Set<Point> tunnels;
        private final Map<Point, Point> innerPortals;
        private final Map<Point, Point> outerPortals;

        private Maze3DGraph(Point3d start, Point3d end, Set<Point> tunnels, Map<Point, Point> innerPortals, Map<Point, Point> outerPortals) {
            this.start = start;
            this.end = end;
            this.tunnels = tunnels;
            this.innerPortals = innerPortals;
            this.outerPortals = outerPortals;
        }

        @Override
        public Set<Point3d> adjacentVertices(Point3d vertex) {
            Set<Point3d> adjacent = new HashSet<>();
            adjacent.add(new Point3d(vertex.x, vertex.y - 1, vertex.z));
            adjacent.add(new Point3d(vertex.x, vertex.y + 1, vertex.z));
            adjacent.add(new Point3d(vertex.x - 1, vertex.y, vertex.z));
            adjacent.add(new Point3d(vertex.x + 1, vertex.y, vertex.z));

            Point in = new Point((int)Math.round(vertex.x), (int)Math.round(vertex.y));
            Point out = this.innerPortals.get(in);
            if(out != null) {
                adjacent.add(new Point3d(out.x, out.y, vertex.z + 1));
            }

            out = this.outerPortals.get(in);
            if(out != null) {
                adjacent.add(new Point3d(out.x, out.y, vertex.z -1));
            }

            adjacent.removeIf(p -> !containsVertex(p));

            return adjacent;
        }

        @Override
        public boolean containsVertex(Point3d vertex) {
            if(vertex.z < 0) {
                return false;
            }

            if(vertex.x == this.start.x && vertex.y == this.start.y && vertex.z != 0){
                return false;
            }

            if(vertex.x == this.end.x && vertex.y == this.end.y && vertex.z != 0){
                return false;
            }

            Point planarPoint = new Point((int)Math.round(vertex.x), (int)Math.round(vertex.y));

            if(!this.tunnels.contains(planarPoint)){
                return false;
            }

            return !this.outerPortals.containsKey(planarPoint) || vertex.z != 0;
        }
    }

    private Set<Point> getTunnels(String[][] grid) {
        Set<Point> tunnels = new HashSet<>();
        int width = grid.length;
        int height = grid[0].length;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(grid[x][y].equals(".")){
                    tunnels.add(new Point(x, y));
                }
            }
        }

        return tunnels;
    }

    private Map<String, Point> getInnerLabels(String[][] grid) {
        Map<String, Point> labels = new HashMap<>();

        int width = grid.length;
        int height = grid[0].length;

        // Find top edge
        int top = height / 2;
        while (!grid[width / 2][top].equals("#") && !grid[width / 2][top].equals(".")) {
            top--;
        }

        // Find bottom edge
        int bottom = height / 2;
        while (!grid[width / 2][bottom].equals("#") && !grid[width / 2][bottom].equals(".")) {
            bottom++;
        }

        // Find left edge
        int left = width / 2;
        while (!grid[left][height / 2].equals("#") && !grid[left][height / 2].equals(".")) {
            left--;
        }

        // Find right edge
        int right = width / 2;
        while (!grid[right][height / 2].equals("#") && !grid[right][height / 2].equals(".")) {
            right++;
        }

        // Top edge
        for (int x = left + 1; x < right; x++) {
            String label = grid[x][top + 1] + grid[x][top + 2];
            if (isValidLabel(label)) {
                labels.put(label, new Point(x, top));
            }
        }

        // Bottom edge
        for (int x = left + 1; x < right; x++) {
            String label = grid[x][bottom - 2] + grid[x][bottom - 1];
            if (isValidLabel(label)) {
                labels.put(label, new Point(x, bottom));
            }
        }

        // Left edge
        for (int y = top + 1; y < bottom; y++) {
            String label = grid[left + 1][y] + grid[left + 2][y];
            if (isValidLabel(label)) {
                labels.put(label, new Point(left, y));
            }
        }

        // Right edge
        for (int y = top + 1; y < bottom; y++) {
            String label = grid[right - 2][y] + grid[right - 1][y];
            if (isValidLabel(label)) {
                labels.put(label, new Point(right, y));
            }
        }

        return labels;
    }

    private static Map<String, Point> getOuterLabels(String[][] grid) {
        Map<String, Point> labels = new HashMap<>();

        int width = grid.length;
        int height = grid[0].length;

        // Top edge
        for (int x = 0; x < width; x++) {
            String label = grid[x][0] + grid[x][1];
            if (isValidLabel(label)) {
                labels.put(label, new Point(x, 2));
            }
        }

        // Bottom edge
        for (int x = 0; x < width; x++) {
            String label = grid[x][height - 2] + grid[x][height - 1];
            if (isValidLabel(label)) {
                labels.put(label, new Point(x, height - 3));
            }
        }

        // Left edge
        for (int y = 0; y < height; y++) {
            String label = grid[0][y] + grid[1][y];
            if (isValidLabel(label)) {
                labels.put(label, new Point(2, y));
            }
        }

        // Right edge
        for (int y = 0; y < height; y++) {
            String label = grid[width - 2][y] + grid[width - 1][y];
            if (isValidLabel(label)) {
                labels.put(label, new Point(width - 3, y));
            }
        }

        return labels;
    }

    private static boolean isValidLabel(String label) {
        return label != null
                && label.length() == 2
                && Character.isAlphabetic(label.charAt(0))
                && Character.isAlphabetic(label.charAt(1));
    }

    private static String[][] getRawGrid(String input) {
        input = input.replace("\r\n", "\n");
        List<String> lines = input.lines().collect(Collectors.toList());
        int height = lines.size();
        int width = lines.get(0).length();

        String[][] grid = new String[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pos = (y * (width + 1)) + x; // We add 1 to the width to account for the newline we don't capture
                grid[x][y] = String.valueOf(input.charAt(pos));
            }
        }

        return grid;
    }
}
