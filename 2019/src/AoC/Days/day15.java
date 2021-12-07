package AoC.Days;

import AoC.helpers.Day;
import AoC.helpers.IntcodeComputer;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class day15 extends Day {
    RepairDroid repairDroid;
    public day15(String fileStr) {
        super(fileStr);

        repairDroid = new RepairDroid(input.get(0), false);

        repairDroid.discover();
    }

    public String part1() {
        return "Shortest Path to oxygen: " + repairDroid.findShortestPathToOxygen();
    }

    public String part2() {
        return "Minutes for oxygen to spread: " + repairDroid.fillNewWorldWithOxygen();
    }

    private static class RepairDroid {
        private final IntcodeComputer intcodeComputer;
        private final Map<Point, Space> map;
        private final boolean printMap;

        Point currentPos = new Point(0,0);

        Stack<Direction> travelledList;

        RepairDroid(String instructions, boolean printMap) {
            this.printMap = printMap;
            intcodeComputer = new IntcodeComputer(instructions);
            map = new HashMap<>();
            map.put(currentPos, Space.EMPTY);
            travelledList = new Stack<>();
        }

        void discover() {
            boolean done = false;
            while(!done) {
                boolean breadCrumbTravel = false;
                Direction toTravel = findUnexploredDirFromCurrentPos();
                if(toTravel == null) {
                    breadCrumbTravel = true;
                    toTravel = Direction.findOpposite(travelledList.pop());
                }

                intcodeComputer.addInput(toTravel.code);
                intcodeComputer.runProgram();
                Space result = Space.fromCode(intcodeComputer.getOutputs().remove());

                Point point;
                switch (toTravel) {
                    case NORTH:
                        point = new Point(currentPos.x, currentPos.y + 1);
                        break;
                    case SOUTH:
                        point = new Point(currentPos.x, currentPos.y - 1);
                        break;
                    case EAST:
                        point = new Point(currentPos.x + 1, currentPos.y);
                        break;
                    case WEST:
                        point = new Point(currentPos.x - 1, currentPos.y);
                        break;
                    default:
                        throw new RuntimeException("Unexpected Direction");
                }

                map.put(point, result);
                if(result != Space.WALL) {
                    if(!breadCrumbTravel) {
                        travelledList.push(toTravel);
                    }
                    currentPos = point;
                }

                if(travelledList.isEmpty() && findUnexploredDirFromCurrentPos() == null) {
                    done = true;
                }
            }

            if (printMap) printMap();
        }

        Direction findUnexploredDirFromCurrentPos() {
            if(null == map.getOrDefault(new Point(currentPos.x, currentPos.y + 1), null)) {
                return Direction.NORTH;
            } else if(null == map.getOrDefault(new Point(currentPos.x, currentPos.y - 1), null)) {
                return Direction.SOUTH;
            } else if(null == map.getOrDefault(new Point(currentPos.x + 1, currentPos.y), null)) {
                return Direction.EAST;
            } else if(null == map.getOrDefault(new Point(currentPos.x - 1, currentPos.y), null)) {
                return Direction.WEST;
            } else {
                return null;
            }
        }

        int findShortestPathToOxygen() {
            Set<Point> exploredPositions = new HashSet<>();
            List<Point> currentSearching = new ArrayList<>();
            currentSearching.add(new Point(0,0));

            int countFound = 0;
            while(true) {
                countFound++;
                List<Point> nextSearching = new ArrayList<>();
                for(Point point: currentSearching) {
                    for(Point pointNear: getSearchablePathsFromPosition(point)) {
                        if(map.get(pointNear) == Space.OXYGEN) {
                            return countFound;
                        } else if(map.get(pointNear) != Space.WALL) {
                            if(exploredPositions.add(pointNear)){
                                nextSearching.add(pointNear);
                            }
                        }
                    }
                }
                currentSearching = nextSearching;
            }
        }

        private List<Point> getSearchablePathsFromPosition(Point position) {
            return List.of(
                    new Point(position.x + 1, position.y),
                    new Point(position.x - 1, position.y),
                    new Point(position.x, position.y - 1),
                    new Point(position.x, position.y + 1)
            );
        }

        void printMap() {
            System.out.println("\n\nMap Currently");

            int minX = (int) Math.round(map.keySet().stream().map(Point::getX).min(Double::compare).get());
            int maxX = (int) Math.round(map.keySet().stream().map(Point::getX).max(Double::compare).get());
            int minY = (int) Math.round(map.keySet().stream().map(Point::getY).min(Double::compare).get());
            int maxY = (int) Math.round(map.keySet().stream().map(Point::getY).max(Double::compare).get());

            for (int i = maxY; i >= minY; i--) {
                StringBuilder stringBuilder = new StringBuilder();
                for(int j = minX; j <= maxX; j++) {
                    Point point = new Point(j,i);
                    if(Space.WALL == map.get(point)) {
                        stringBuilder.append("#");
                    } else if(Space.EMPTY == map.get(point)){
                        stringBuilder.append(".");
                    } else if(Space.OXYGEN == map.get(point)) {
                        stringBuilder.append("0");
                    } else {
                        stringBuilder.append("U");
                    }
                }
                System.out.println(stringBuilder.toString());
            }
        }

        public int fillNewWorldWithOxygen() {
            int countMinutesToFill = 0;
            List<Point> oxygenPointsToSpread = map.entrySet().stream().filter(entrySet -> entrySet.getValue() == Space.OXYGEN).map(Map.Entry::getKey).collect(Collectors.toList());

            while (oxygenPointsToSpread.size() > 0) {
                List<Point> nextToSpread = new ArrayList<>();

                for (Point point: oxygenPointsToSpread) {
                    for(Point pointNear: getSearchablePathsFromPosition(point)) {
                        if(map.get(pointNear) == Space.EMPTY) {
                            map.put(pointNear, Space.OXYGEN);
                            nextToSpread.add(pointNear);
                        }
                    }
                }

                oxygenPointsToSpread = nextToSpread;
                if(oxygenPointsToSpread.size() >0) {
                    countMinutesToFill++;
                }
            }

            return countMinutesToFill;
        }
    }

    private enum Space {
        WALL(0L),
        EMPTY(1L),
        OXYGEN(2L);

        private final long code;
        Space(long code) {
            this.code = code;
        }

        public static Space fromCode(long code) {
            return Arrays.stream(Space.values()).filter(x -> x.code == code).findFirst().get();
        }
    }

    private enum Direction {
        NORTH(1L),
        SOUTH(2L),
        WEST(3L),
        EAST(4L);

        private final long code;
        Direction(long code){
            this.code = code;
        }

        private static Direction findOpposite(Direction direction) {
            switch(direction) {
                case NORTH:
                    return SOUTH;
                case SOUTH:
                    return NORTH;
                case EAST:
                    return WEST;
                case WEST:
                    return EAST;
                default:
                    throw new RuntimeException("No opposite of: " + direction);
            }
        }
    }
}
