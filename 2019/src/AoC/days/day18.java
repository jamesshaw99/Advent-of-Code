package AoC.days;

import AoC.helpers.Day;
import AoC.helpers.GraphBuilder;
import org.jgrapht.Graph;
import org.jgrapht.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class day18 extends Day {
    private final String puzzleInput;
    public day18(String fileStr) {
        super(fileStr);
        StringBuilder builder = new StringBuilder();
        for(String line: input) {
            builder.append(line).append('\n');
        }
        puzzleInput = builder.toString();
    }

    public String part1() {
        Vault vault = new Vault(puzzleInput, false);
        return String.valueOf(vault.minKeyCollectionPathLength());
    }

    public String part2() {
        Vault vault = new Vault(puzzleInput, true);
        return String.valueOf(vault.minKeyCollectionPathLength());
    }

    private Set<Point> adjacentPoints(Point point) {
        Set<Point> adjacent = new HashSet<>();
        int x = point.x, y = point.y;
        adjacent.add(new Point(x, y - 1));
        adjacent.add(new Point(x, y + 1));
        adjacent.add(new Point(x - 1, y));
        adjacent.add(new Point(x + 1, y));
        return adjacent;
    }

    private class Vault {
        private final Map<Set<Point>, Graph<Point, DefaultWeightedEdge>> _memoizedGraphs = new ConcurrentHashMap<>();
        private final Map<Memo, Double> _memoizedCosts = new ConcurrentHashMap<>();
        private final List<Point> _entrances;
        private final DoorsAndKeys _doorsAndKeys;
        private final Graph<Point, DefaultWeightedEdge> _graph;

        private Vault(String input, boolean part2) {
            int x = 0, y = 0;
            Point entrance = null;
            Set<Point> tunnels = new HashSet<>();
            Map<Character, Point> doorsAndKeys = new HashMap<>();
            for (char c: input.trim().toCharArray()){
                Point p = new Point(x, y);
                if(c == '@'){
                    entrance = p;
                }
                if(Character.isAlphabetic(c)) {
                    doorsAndKeys.put(c, p);
                }
                if(c != '#' && c != '\n') {
                    tunnels.add(p);
                }
                if(c == '\n'){
                    x = 0;
                    y++;
                } else {
                    x++;
                }
            }

            _doorsAndKeys = new DoorsAndKeys(doorsAndKeys);
            assert entrance != null;
            if(!part2) {
                _entrances = List.of(entrance);
            } else {
                _entrances = List.of(
                        new Point(entrance.x - 1, entrance.y - 1),
                        new Point(entrance.x - 1, entrance.y + 1),
                        new Point(entrance.x + 1, entrance.y - 1),
                        new Point(entrance.x + 1, entrance.y + 1)
                );
                tunnels.remove(entrance);
                tunnels.removeAll(adjacentPoints(entrance));
            }

            Set<Point> irreduciblePoints = new HashSet<>();
            irreduciblePoints.addAll(_entrances);
            irreduciblePoints.addAll(_doorsAndKeys.doors);
            irreduciblePoints.addAll(_doorsAndKeys.keys);

            Function<Point, Set<Point>> adjacentPoints = point -> adjacentPoints(point).stream().filter(tunnels::contains).collect(Collectors.toSet());
            _graph = GraphBuilder.buildUndirectedGraph(tunnels, adjacentPoints);

            GraphBuilder.reduceGraph(_graph, irreduciblePoints);
        }

        private long minKeyCollectionPathLength() {
            Memo memo = new Memo(_entrances, _doorsAndKeys.keys);
            return (long) minKeyCollectionPathLength(memo);
        }

        private double minKeyCollectionPathLength(Memo input) {
            if(input.remainingKeys.isEmpty()) {
                return 0d;
            }

            if(_memoizedCosts.containsKey(input)){
                return _memoizedCosts.get(input);
            }

            double minCost = Double.MAX_VALUE;
            for(int i = 0; i < input.robots.size(); i++){
                double cost = minKeyCollectionPathLength(input, i);
                minCost = Math.min(cost, minCost);
            }

            _memoizedCosts.put(input, minCost);

            return minCost;
        }

        private double minKeyCollectionPathLength(Memo input, int robotIndex){
            Map<Point, Double> costs = keyCosts(input.remainingKeys, input.robots.get(robotIndex));

            double minCost = Double.MAX_VALUE;
            for(Map.Entry<Point, Double> keyCost: costs.entrySet()){
                Point key = keyCost.getKey();
                double cost = keyCost.getValue();

                List<Point> robots = new ArrayList<>(input.robots);
                robots.set(robotIndex, key);
                Set<Point> remainingKeys = new HashSet<>(input.remainingKeys);
                remainingKeys.remove(key);

                Memo memo = new Memo(robots, remainingKeys);
                double pathCost = cost + minKeyCollectionPathLength(memo);

                minCost = Math.min(minCost, pathCost);
            }
            return minCost;
        }

        private Map<Point, Double> keyCosts(Set<Point> remainingKeys, Point location) {
            Graph<Point, DefaultWeightedEdge> graph = graph(remainingKeys);

            Map<Point, Double> pathCosts = new HashMap<>();
            SingleSourcePaths<Point, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph).getPaths(location);
            for(Point key: remainingKeys){
                GraphPath<Point, DefaultWeightedEdge> path = dijkstra.getPath(key);
                if(path != null && !pathObscured(path, remainingKeys)) {
                    pathCosts.put(key, path.getWeight());
                }
            }
            return pathCosts;
        }

        private boolean pathObscured(GraphPath<Point, DefaultWeightedEdge> path, Set<Point> remainingKeys) {
            List<Point> vertices = path.getVertexList();
            for(int i = 1; i < vertices.size() - 1; i++) {
                Point vertex = vertices.get(i);
                if(remainingKeys.contains(vertex)){
                    return true;
                }
            }
            return false;
        }

        private Graph<Point, DefaultWeightedEdge> graph(Set<Point> remainingKeys) {
            if(_memoizedGraphs.containsKey(remainingKeys)){
                return _memoizedGraphs.get(remainingKeys);
            }

            Graph<Point, DefaultWeightedEdge> graph = GraphBuilder.copyGraph(_graph);
            Set<Point> closedDoors = _doorsAndKeys.closedDoors(remainingKeys);
            for(Point door: closedDoors){
                graph.removeVertex(door);
            }

            _memoizedGraphs.put(remainingKeys, graph);

            return graph;
        }
    }

    private static class DoorsAndKeys {
        private final Set<Point> doors = new HashSet<>();
        private final Set<Point> keys = new HashSet<>();
        private final Map<Point, Point> keyToDoor = new HashMap<>();

        private DoorsAndKeys(Map<Character, Point> doorsAndKeys) {
            Point[] doors = new Point[26];
            Point[] keys = new Point[26];
            doorsAndKeys.forEach((c, point) -> {
                if(c >= 'A' && c <= 'Z') {
                    doors[c - 'A'] = point;
                } else {
                    keys[c - 'a'] = point;
                }
            });
            for (int i = 0; i < 26; i++) {
                Point door = doors[i];
                Point key = keys[i];
                if(door != null) {
                    this.doors.add(door);
                }
                if (key != null) {
                    this.keys.add(key);
                    if(door != null) {
                        keyToDoor.put(key, door);
                    }
                }
            }
        }

        private Set<Point> closedDoors(Set<Point> remainingKeys){
            Set<Point> closedDoors = new HashSet<>();
            for(Point key: remainingKeys) {
                Point door = keyToDoor.get(key);
                closedDoors.add(door);
            }
            return closedDoors;
        }
    }

    private static class Memo {
        private final List<Point> robots;
        private final Set<Point> remainingKeys;

        private Memo(List<Point> robots, Set<Point> remainingKeys) {
            this.robots = Collections.unmodifiableList(robots);
            this.remainingKeys = Collections.unmodifiableSet(remainingKeys);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if(obj == null || getClass() != obj.getClass()) return false;
            Memo memo = (Memo) obj;
            return Objects.equals(robots, memo.robots) && Objects.equals(remainingKeys, memo.remainingKeys);
        }

        @Override
        public int hashCode() {
            return Objects.hash(robots, remainingKeys);
        }
    }


}
