package AoC.days;

import AoC.Day;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Comparator.*;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

public class day10 extends Day {
    private static final Set<Point> asteroids = new LinkedHashSet<>();
    public day10(String fileStr) {
        super(fileStr);
        initAsteroidsPositions();
    }

    public String part1() {
        long count = 0;
        for (Point point: asteroids){
            long visible = getVisibleAsteroidsCount(point);
            if(visible > count) {
                count = visible;
            }
        }
        return Long.toString(count);
    }

    public String part2() {
        Point max = asteroids.stream().max(comparing(day10::getVisibleAsteroidsCount)).get();
        Map<Double, LinkedList<Point>> pointsByAngle = asteroids.stream().filter(not(max::equals)).sorted(comparing(max::manhattanDistance)).collect(groupingBy(max::getAngle, toCollection(LinkedList::new)));
        Double[] angles = pointsByAngle.keySet().stream().sorted().toArray(Double[]::new);

        LinkedList<Point> removedPoints = new LinkedList<>();

        int i = 0;
        while(removedPoints.size() != 200) {
            LinkedList<Point> points = pointsByAngle.get(angles[i++ % angles.length]);
            ofNullable(points.poll()).ifPresent(removedPoints::add);
        }

        Point Point200 = removedPoints.getLast();
        return Integer.toString(Point200.x*100 + Point200.y);
    }

    private static long getVisibleAsteroidsCount(Point point) {
        return asteroids.stream().filter(not(point::equals)).filter(checkVisibility(point)).count();
    }

    private static Predicate<? super Point> checkVisibility(Point from) {
        Map<Double, Set<Point>> byAngle = asteroids.stream().filter(not(from::equals)).collect(groupingBy(from::getAngle, toSet()));
        return to -> byAngle.get(from.getAngle(to)).stream().map(from::manhattanDistance).noneMatch(distance -> distance < from.manhattanDistance(to));
    }

    private void initAsteroidsPositions() {
        for (int y = 0; y < input.size(); y++){
            for (int x = 0; x < input.get(y).length(); x++){
                if(input.get(y).charAt(x) == '#'){
                    asteroids.add(new Point(x, y));
                }
            }
        }
    }

    public static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }

    public static class Point {
        private int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Double getAngle(day10.Point other) {
            double angle = 90-Math.toDegrees(Math.atan2(y-other.y, other.x-x));
            return angle < 0 ? angle+360 : angle;
        }

        public long manhattanDistance(day10.Point other) {
            return Math.abs(x-other.x) + Math.abs(y-other.y);
        }
    }

}
