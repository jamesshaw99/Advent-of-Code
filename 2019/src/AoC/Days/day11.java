//Stole from https://github.com/BrettGoreham/adventOfCode2019/blob/master/adventOfCode2019/src/main/java/day11/Day11.java as my implementation of the intcode computer was causing issues and i got stuck

package AoC.Days;

import AoC.helpers.Day;
import AoC.helpers.IntcodeComputer;

import java.util.*;

public class day11 extends Day {
    private final String text;

    public day11(String fileStr) {
        super(fileStr);
        text = input.get(0);
    }

    public String part1() {
        LilPaintyRobot paintyBoy = new LilPaintyRobot(text, true);
        paintyBoy.paintAway();

        return ("Painted " + paintyBoy.paintBoard.keySet().size() + " squares");
    }

    public String part2(){
        LilPaintyRobot paintyBoyPart2 = new LilPaintyRobot(text, false);
        paintyBoyPart2.paintAway();

        return paintyBoyPart2.printRobotsPaintBoard();
    }

    public static class LilPaintyRobot {
        private final IntcodeComputer intcodeComputer;
        private final HashMap<Cord, PaintColor> paintBoard;
        private Cord currentLocation = new Cord(0,0);
        private Direction facing = Direction.UP;

        LilPaintyRobot(String instructionSet, boolean isPart1) {
            intcodeComputer = new IntcodeComputer(instructionSet);
            paintBoard = new HashMap<>();
            if (!isPart1) {
                paintBoard.put(currentLocation, PaintColor.WHITE);
            }

        }

        void paintAway() {
            boolean done = false;
            while (!done) {
                PaintColor standingOn = paintBoard.getOrDefault(currentLocation, PaintColor.BLACK);
                Long input = standingOn == PaintColor.BLACK ? 0L : 1L;
                intcodeComputer.addInput(input);

                String exitCode = intcodeComputer.runProgram();

                if(exitCode.equals("EXITED")) {
                    done = true;
                }

                Long colorToPaint = intcodeComputer.getOutputs().poll();
                Long directionTurn = intcodeComputer.getOutputs().poll();

                if(colorToPaint == 0) {
                    paintBoard.put(currentLocation, PaintColor.BLACK);
                } else {
                    paintBoard.put(currentLocation, PaintColor.WHITE);
                }

                if(directionTurn == 0) {
                    turnLeft();
                }
                else {
                    turnRight();
                }

                moveForward();
            }
        }

        private void turnLeft() {
            switch (facing) {
                case UP:
                    facing = Direction.LEFT;
                    break;
                case DOWN:
                    facing = Direction.RIGHT;
                    break;
                case LEFT:
                    facing = Direction.DOWN;
                    break;
                case RIGHT:
                    facing = Direction.UP;
                    break;
            }
        }

        private void turnRight() {
            switch (facing) {
                case UP:
                    facing = Direction.RIGHT;
                    break;
                case DOWN:
                    facing = Direction.LEFT;
                    break;
                case LEFT:
                    facing = Direction.UP;
                    break;
                case RIGHT:
                    facing = Direction.DOWN;
                    break;
            }
        }

        private void moveForward() {
            switch (facing) {
                case UP:
                    currentLocation = new Cord(currentLocation.x, currentLocation.y + 1);
                    break;
                case DOWN:
                    currentLocation = new Cord(currentLocation.x, currentLocation.y - 1);
                    break;
                case RIGHT:
                    currentLocation = new Cord(currentLocation.x + 1, currentLocation.y);
                    break;
                case LEFT:
                    currentLocation = new Cord(currentLocation.x - 1, currentLocation.y);
                    break;

            }
        }

        private String printRobotsPaintBoard() {
            int minX = paintBoard.keySet().stream().map(Cord::getX).min(Integer::compareTo).get();
            int maxX = paintBoard.keySet().stream().map(Cord::getX).max(Integer::compareTo).get();
            int minY = paintBoard.keySet().stream().map(Cord::getY).min(Integer::compareTo).get();
            int maxY = paintBoard.keySet().stream().map(Cord::getY).max(Integer::compareTo).get();

            StringBuilder result = new StringBuilder();
            for(int i = maxY; i >= minY; i--) { // Y is upside down because max x is lowest value so have to flip it
                StringBuilder stringBuilder = new StringBuilder();
                for(int j = minX; j <= maxX; j++) {
                    if (PaintColor.WHITE == paintBoard.get(new Cord(j, i))) {
                        stringBuilder.append("#");
                    }
                    else {
                        stringBuilder.append(" ");
                    }
                }
                result.append(stringBuilder.toString()).append("\n");
            }
            return result.toString();
        }
    }

    public enum Direction{
        UP,
        LEFT,
        DOWN,
        RIGHT
    }

    public enum PaintColor{
        BLACK,
        WHITE
    }

    public static class Cord{
        private final int x;
        private final int y;

        Cord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            Cord other = (Cord)obj;
            return this.x == other.x && this.y == other.y;
        }
        @Override
        public int hashCode() {
            return  x + y;
        }
    }
}
