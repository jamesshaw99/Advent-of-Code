package AoC.days;

import AoC.helpers.Day;
import AoC.helpers.IntcodeComputer;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;

public class day17 extends Day {
    private final IntcodeComputer intcodeComputer;
    private final HashMap<Point, Character> map = new HashMap<>();
    private final boolean print = false;

    public day17(String fileStr) {
        super(fileStr);
        intcodeComputer = new IntcodeComputer(input.get(0));

        intcodeComputer.runProgram();
    }

    public String part1() {
        Queue<Long> mapOutputs = intcodeComputer.getOutputs();
        int curX = 0, curY = 0;

        while (mapOutputs.size() > 0) {
            long output = mapOutputs.remove();
            if(output == 10) {
                curY++;
                curX = 0;
            } else {
                map.put(new Point(curX, curY), (char) output);
                curX++;
            }
        }

        List<Point> intersections = detectIntersectionsInMap(map, '#');
        int x = (int)Math.round(intersections.stream().map(position -> position.getX() * position.getY()).reduce(Double::sum).get());
        if(print) printMap(map);

        return "Calibration Parameter: " + x;
    }

    public String part2() {
        List<String> inputs = findInputsForPart2(map, new ArrayList<>());
        if(print) {
            System.out.println("\n\nInputs To Computer for part two");
            System.out.println("Main: " + inputs.get(0));
            System.out.println("A: " + inputs.get(1));
            System.out.println("B: " + inputs.get(2));
            System.out.println("C: " + inputs.get(3));
        }

        List<List<Long>> computerReadableInputs = inputs.stream().map(day17::stringToLongRepresentation).collect(Collectors.toList());

        computerReadableInputs.add(List.of((long) 'n', (long) '\n'));

        String goMode = "2" + input.get(0).substring(1);
        IntcodeComputer goComputer = new IntcodeComputer(goMode);
        for(List<Long> inputsList: computerReadableInputs) {
            for(Long input: inputsList) {
                goComputer.addInput(input);
            }
        }

        goComputer.runProgram();

        return "" + goComputer.getLastOutput();
    }

    private static List<Long> stringToLongRepresentation(String x) {
        List<Long> inputs = new ArrayList<>();

        for(Character character: x.toCharArray()) {
            inputs.add((long)character);
        }
        inputs.add((long)'\n');
        return inputs;
    }

    private static List<String> findInputsForPart2(HashMap<Point, Character> map, List<List<String>> bannedStartingAList) {
        String pathToEnd = findStringPathToEnd(map);

        List<String> a = findHighestValuedSectionOfStringToReplace(pathToEnd, bannedStartingAList);
        StringBuilder stringBuilder = new StringBuilder();
        a.forEach(aentry -> stringBuilder.append(aentry).append(","));
        pathToEnd = pathToEnd.replace(stringBuilder.toString(), "A,");

        List<String> b = findHighestValuedSectionOfStringToReplace(pathToEnd, new ArrayList<>());
        StringBuilder stringBuilder2 = new StringBuilder();
        b.forEach(bentry -> stringBuilder2.append(bentry).append(","));
        pathToEnd = pathToEnd.replace(stringBuilder2.toString(), "B,");

        List<String> c = findHighestValuedSectionOfStringToReplace(pathToEnd, new ArrayList<>());
        StringBuilder stringBuilder3 = new StringBuilder();
        c.forEach(centry -> stringBuilder3.append(centry).append(","));
        pathToEnd = pathToEnd.replace(stringBuilder3.toString(), "C,");


        boolean finished = true;
        for (String string :  pathToEnd.split(",")) {
            if (string.length() != 1) {
                finished = false;
                break;
            }
        }

        if (finished) {
            // removing comma at the end of the path because its there for reasons.
            return List.of(
                    pathToEnd.substring(0, pathToEnd.length() - 1),
                    joinElementsWithCommaBetween(a).replace(" ", ","),
                    joinElementsWithCommaBetween(b).replace(" ", ","),
                    joinElementsWithCommaBetween(c).replace(" ", ",")
            );

        }
        else {
            bannedStartingAList.add(a);
            return findInputsForPart2(map, bannedStartingAList);
        }
    }

    private static String joinElementsWithCommaBetween(List<String> input) {
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < input.size(); i++) {
            if(i == input.size() - 1) {
                output.append(input.get(i));
            } else {
                output.append(input.get(i)).append(",");
            }
        }

        return output.toString();
    }

    private static List<String> findHighestValuedSectionOfStringToReplace(String path, List<List<String>> bannedStartingAList) {
        List<String> pathTurnGoHeadCombos = Arrays.asList(path.split(","));

        Map<List<String>, Integer> subsetsInCommon = findCommonSubsectionsOfListBest(pathTurnGoHeadCombos);

        List<List<String>> sortedValueList = subsetsInCommon.entrySet().stream()
                .filter(listIntegerEntry -> listIntegerEntry.getKey().size() < 6 && listIntegerEntry.getKey().size() > 1)
                .filter(listIntegerEntry -> !bannedStartingAList.contains(listIntegerEntry.getKey()))
                .sorted((o1, o2) -> Integer.compare(o2.getValue() * o2.getKey().size(), o1.getValue() * o1.getKey().size()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedValueList.get(0);
    }

    private static Map<List<String>, Integer> findCommonSubsectionsOfListBest(List<String> pathTurnGoHeadCombos) {
        int indexToStart = 0;

        for(int i = 0; i < pathTurnGoHeadCombos.size(); i++) {
            if(pathTurnGoHeadCombos.get(i).length() != 1) {
                indexToStart = i;
                break;
            }
        }

        Map<List<String>, Integer> subsectionMatchCount = new HashMap<>();
        for(int j = indexToStart + 1; j < pathTurnGoHeadCombos.size(); j++) {
            List<String> subSection = pathTurnGoHeadCombos.subList(indexToStart, j);

            int matches = 1;
            for(int startOfNextSection = indexToStart + subSection.size(); startOfNextSection + subSection.size() <= pathTurnGoHeadCombos.size(); startOfNextSection++) {
                if(subSection.equals(pathTurnGoHeadCombos.subList(startOfNextSection, startOfNextSection + subSection.size()))) {
                    matches++;
                }
            }

            if(subSection.get(subSection.size() - 1).length() == 1) {
                matches = 0;
                break;
            }
            subsectionMatchCount.put(subSection, matches);
        }
        return subsectionMatchCount;
    }

    private static String findStringPathToEnd(HashMap<Point, Character> map) {
        Point startPosition = map.entrySet().stream().filter(entry -> entry.getValue().equals('^')).map(Map.Entry::getKey).findFirst().get();
        DirectionFacing facing = DirectionFacing.UP;
        boolean notDone =true;
        Point position = new Point((int)Math.round(startPosition.getX()), (int)Math.round(startPosition.getY()));
        StringBuilder builder = new StringBuilder();
        int aheadCount = 0;
        while(notDone) {
            Point ahead = getNextPositionAheadFromCurrent(position, facing);

            if(map.getOrDefault(ahead, 'Å') == '#') {
                aheadCount++;
                position = ahead;
            }
            else {
                if(aheadCount != 0) {
                    builder.append(aheadCount).append(",");
                    aheadCount = 0;
                }
                char turnDirection = findTurnDir(map, position, facing);
                if(turnDirection == ' ') {
                    notDone = false;
                } else {
                    facing = updateFacingBasedOnTurn(facing, turnDirection);
                    builder.append(turnDirection).append(" ");
                }
            }
        }

        return builder.toString();
    }

    private static DirectionFacing updateFacingBasedOnTurn(DirectionFacing facing, char turnDir) {
        switch(turnDir) {
            case 'R':
                return DirectionFacing.valueOf(facing.toRight);
            case 'L':
                return DirectionFacing.valueOf(facing.toLeft);
            default:
                throw new RuntimeException("CANT TURN THIS WAY: " + turnDir);
        }
    }

    private static char findTurnDir(Map<Point, Character> map, Point pos, DirectionFacing facing) {
        if(facing == DirectionFacing.UP || facing == DirectionFacing.DOWN) {
            return findDirVertical(map, pos, facing);
        } else {
            return findDirHorizontal(map, pos, facing);
        }
    }

    private static char findDirHorizontal(Map<Point, Character> map, Point pos, DirectionFacing facing) {
        char plusY = map.getOrDefault(new Point((int)Math.round(pos.getX()), (int)Math.round(pos.getY()) + 1), 'Å');
        char minusY = map.getOrDefault(new Point((int)Math.round(pos.getX()), (int)Math.round(pos.getY()) - 1), 'Å');

        if(plusY == '#') {
            if(facing == DirectionFacing.LEFT) {
                return 'L';
            } else {
                return 'R';
            }
        }
        else if (minusY == '#') {
            if(facing == DirectionFacing.LEFT) {
                return 'R';
            } else {
                return 'L';
            }
        }
        else {
            return ' ';
        }
    }

    private static char findDirVertical(Map<Point, Character> map, Point pos, DirectionFacing facing) {
        char plusX = map.getOrDefault(new Point((int)Math.round(pos.getX()) + 1, (int)Math.round(pos.getY())), 'Å');
        char minusX = map.getOrDefault(new Point((int)Math.round(pos.getX()) - 1, (int)Math.round(pos.getY())), 'Å');

        if(plusX == '#') {
            if(facing == DirectionFacing.UP) {
                return 'R';
            } else {
                return 'L';
            }
        }
        else if (minusX == '#') {
            if(facing == DirectionFacing.UP) {
                return 'L';
            } else {
                return 'R';
            }
        }
        else {
            return ' ';
        }
    }

    private static Point getNextPositionAheadFromCurrent(Point pos, DirectionFacing facing) {
        switch(facing) {
            case UP:
                return new Point((int)Math.round(pos.getX()), (int)Math.round(pos.getY()) - 1);
            case DOWN:
                return new Point((int)Math.round(pos.getX()), (int)Math.round(pos.getY()) + 1);
            case LEFT:
                return new Point((int)Math.round(pos.getX()) - 1, (int)Math.round(pos.getY()));
            case RIGHT:
                return new Point((int)Math.round(pos.getX()) + 1, (int)Math.round(pos.getY()));
            default:
                throw new RuntimeException("Facing is weird");
        }
    }

    private enum DirectionFacing {
        UP("LEFT", "RIGHT"),
        RIGHT("UP", "DOWN"),
        LEFT("DOWN", "UP"),
        DOWN("RIGHT", "LEFT");

        String toLeft;
        String toRight;
        DirectionFacing(String left, String right) {
            this.toLeft = left;
            this.toRight = right;
        }
    }

    private List<Point> detectIntersectionsInMap(HashMap<Point, Character> map, Character c) {
        return map.keySet().stream()
                .filter(x -> map.get(x).equals(c))
                .filter(x -> map.getOrDefault(new Point((int)Math.round(x.getX()) + 1, (int)Math.round(x.getY())), 'Q').equals(c))
                .filter(x -> map.getOrDefault(new Point((int)Math.round(x.getX()) - 1, (int)Math.round(x.getY())), 'Q').equals(c))
                .filter(x -> map.getOrDefault(new Point((int)Math.round(x.getX()), (int)Math.round(x.getY()) + 1), 'Q').equals(c))
                .filter(x -> map.getOrDefault(new Point((int)Math.round(x.getX()), (int)Math.round(x.getY()) - 1), 'Q').equals(c))
                .collect(Collectors.toList());
    }

    private void printMap(Map<Point, Character> map) {
        System.out.println("\n\nMap Currently");

        int minX = (int) Math.round(map.keySet().stream().map(Point::getX).min(Double::compare).get());
        int maxX = (int) Math.round(map.keySet().stream().map(Point::getX).max(Double::compare).get());
        int minY = (int) Math.round(map.keySet().stream().map(Point::getY).min(Double::compare).get());
        int maxY = (int) Math.round(map.keySet().stream().map(Point::getY).max(Double::compare).get());

        for(int i = minY; i <= maxY; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = minX; j <= maxX; j++) {
                stringBuilder.append(map.get(new Point(j, i)));
            }
            System.out.println(stringBuilder.toString());
        }
    }
}
