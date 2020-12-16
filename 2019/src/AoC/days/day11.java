//Stole from https://github.com/BrettGoreham/adventOfCode2019/blob/master/adventOfCode2019/src/main/java/day11/Day11.java as my implementation of the intcode computer was causing issues and i got stuck

package AoC.days;

import AoC.Day;

import java.util.*;
import java.util.stream.Collectors;

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


    public static class IntcodeComputer {

        private final List<Long> programMem;
        private final Map<Long, Long> extraTerrestrialMemory;
        private int programCount;

        long relativeBase = 0;

        private final Queue<Long> inputs = new LinkedList<>();
        private final Queue<Long> outputs = new LinkedList<>();


        IntcodeComputer(String instructionSet) {
            programMem =  Arrays.stream(instructionSet.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            extraTerrestrialMemory = new HashMap<>();

        }

        void addInput(Long input) {
            inputs.add(input);
        }

        Queue<Long> getOutputs() {
            return outputs;
        }


        private String runProgram() {
            while (true) {
                long instruction = programMem.get(programCount++);

                long operationCode = instruction % 100;
                long modes = instruction / 100;
                long[] parameterModes = new long[3];
                int modesCount = 0;
                while (modes > 0) {
                    parameterModes[modesCount++] = modes % 10;
                    modes = modes / 10;
                }

                if(operationCode == 99) {
                    return "EXITED";
                }
                else if (isThreeParameterOpCode(operationCode)) {
                    long firstParameter = getParameterValue(parameterModes[0], programCount++);
                    long secondParameter = getParameterValue(parameterModes[1], programCount++);
                    long finalPosition = parameterModes[2] == 2 ? relativeBase + getParameterValueFromMemory((long) programCount++) : getParameterValueFromMemory((long)programCount ++);

                    long valueToSetToFinalPosition;
                    if (operationCode == 1) {
                        valueToSetToFinalPosition = firstParameter + secondParameter;
                    }
                    else if (operationCode == 2) {
                        valueToSetToFinalPosition =  firstParameter * secondParameter;
                    }
                    else if(operationCode == 7) {
                        valueToSetToFinalPosition = firstParameter < secondParameter ? 1 : 0;
                    }
                    else if (operationCode == 8) {
                        valueToSetToFinalPosition = firstParameter == secondParameter ? 1 : 0;
                    }
                    else {
                        throw new RuntimeException("unexpected 3 param opCode...");
                    }

                    setParameterValueToMemory(finalPosition, valueToSetToFinalPosition);
                }
                else if (operationCode == 3 || operationCode == 4) {
                    if (operationCode == 3) {
                        if (inputs.size() == 0) {
                            programCount -= 1; //try this again if this gets ran again!
                            return "NEED_INPUT";
                        }
                        else {
                            long parameter1 = programMem.get(programCount++);
                            if (parameterModes[0] == 2) {
                                parameter1 = relativeBase + parameter1;
                            }
                            setParameterValueToMemory(parameter1, inputs.remove());
                        }
                    }
                    else if (operationCode == 4) {
                        long output = getParameterValue(parameterModes[0], programCount++);
                        outputs.add(output);
                    }
                }
                else if (operationCode == 5 || operationCode == 6) {
                    long parameter1 = getParameterValue(parameterModes[0], programCount++);
                    long parameter2 = getParameterValue(parameterModes[1], programCount++);

                    if (operationCode == 5) {
                        if (parameter1 != 0) {
                            programCount = (int)parameter2;
                        }
                    }
                    else if (operationCode == 6){
                        if (parameter1 == 0) {
                            programCount = (int)parameter2;
                        }
                    }
                }
                else if (operationCode == 9) {
                    relativeBase += getParameterValue(parameterModes[0], programCount++);
                }
                else {
                    throw new RuntimeException("unexpected Opcode " + operationCode);
                }
            }
        }

        private static boolean isThreeParameterOpCode(long opCode) {
            return opCode == 1 || opCode == 2 || opCode == 7 || opCode == 8;
        }

        long getParameterValue(long parameterMode, long paramValue) {
            if(parameterMode == 0) {
                return getParameterValueFromMemory((getParameterValueFromMemory(paramValue)));
            }
            else if (parameterMode == 1) {
                return getParameterValueFromMemory(paramValue);
            }
            else if (parameterMode == 2) {
                return getParameterValueFromMemory(relativeBase + getParameterValueFromMemory(paramValue));
            }
            else {
                throw new RuntimeException("unexpected parameterMode");
            }
        }

        long getParameterValueFromMemory(Long index) {
            if(index >= programMem.size()) {
                return extraTerrestrialMemory.getOrDefault(index, 0L);
            }
            else {
                return programMem.get(index.intValue());
            }
        }

        void setParameterValueToMemory(Long index, Long value){
            if(index >= programMem.size()) {
                extraTerrestrialMemory.put(index,value);
            }
            else {
                programMem.set(index.intValue(), value);
            }
        }
    }
}
