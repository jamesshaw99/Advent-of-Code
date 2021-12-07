package AoC.Days;

import AoC.helpers.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day16 extends Day {
    private static final int[] basePattern = new int[]{0, 1, 0, -1};
    private final List<Integer> numbers;

    public day16(String fileStr) {
        super(fileStr);
        numbers = split(input.get(0), 1);
    }

    public String part1(){
        List<Integer> outputAfterPhases = calculateNthPhase(numbers, 100);

        return "First 8 digits: " + getFirst8IntsOfOutput(outputAfterPhases.stream().mapToInt(i -> i).toArray());
    }

    public String part2() {
        String totalSet = input.get(0).repeat(10000);

        String mattersSet = totalSet.substring(Integer.parseInt(totalSet.substring(0,7)));
        List<Integer> numbers = split(mattersSet, 1);

        int[] inputArray = numbers.stream().mapToInt(i->i).toArray();
        int[] outputAfterPhases = calculateNthPhaseFaster(inputArray, 100);

        return "First 8 digits: " + getFirst8IntsOfOutput(outputAfterPhases);
    }

    private List<Integer> calculateNthPhase(List<Integer> numbers, int numPhases) {
        for (int rounds = 0; rounds < numPhases; rounds++) {
            List<Integer> phaseOutput = new ArrayList<>();

            for(int i = 1; i < numbers.size() + 1; i++) {
                int total = 0;
                for (int j = 1; j < numbers.size() + 1; j++){
                    total += basePattern[(j/i)%basePattern.length] * numbers.get(j-1);
                }
                if(total < 0) {
                    phaseOutput.add((-1 * total) % 10);
                } else {
                    phaseOutput.add((total % 10));
                }
            }
            numbers = phaseOutput;
        }
        return numbers;
    }

    private int[] calculateNthPhaseFaster(int[] numbers, int phases) {
        for (int i = 0; i < phases; i++){
            int[] phaseNums = new int[numbers.length];
            for(int index = numbers.length - 1; index >= 0; index--) {
                if(index == numbers.length - 1) {
                    phaseNums[index] = numbers[index];
                } else {
                    phaseNums[index] = (numbers[index] + phaseNums[index + 1]) % 10;
                }
            }
            numbers = phaseNums;
        }
        return numbers;
    }

    private String getFirst8IntsOfOutput(int... outputAfterPhases) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            s.append(outputAfterPhases[i]);
        }
        return s.toString();
    }

    private List<Integer> split(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for(int i = 0; i < length; i+= size) {
            parts.add(text.substring(i, Math.min(length, i+ size)));
        }

        return parts.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
