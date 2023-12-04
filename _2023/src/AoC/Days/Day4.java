package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class Day4 extends Day {
    private final Map<Integer, Integer> cardTracker = new HashMap<>();

    public Day4(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        int total = 0;
        for (String card : input) {
            String[] parts = card.split(":")[1].split("\\|");

            int[] winning = convertToIntArray(parts[0]);
            int[] myNums = convertToIntArray(parts[1]);

            int intersectionSize = computeIntersectionSize(winning, myNums);
            total += (1 << (intersectionSize - 1));
        }

        return "Total points: " + total;
    }

    public String part2() {
        part2Run(0, input.size());
        int totalCards = cardTracker.values().stream().mapToInt(Integer::intValue).sum();
        return "Total number of cards: " + totalCards;
    }

    private void part2Run(int start, int size) {
        Deque<RecursiveCall> stack = new ArrayDeque<>();
        stack.push(new RecursiveCall(start, size));

        while (!stack.isEmpty()) {
            RecursiveCall call = stack.pop();
            int i = call.start;
            int end = i + call.size;

            for (; i < end; i++) {
                cardTracker.merge(i, 1, Integer::sum);
                String card = input.get(i);
                String[] parts = card.split(":")[1].split("\\|");

                int[] winning = convertToIntArray(parts[0]);
                int[] myNums = convertToIntArray(parts[1]);

                int intersectionSize = computeIntersectionSize(winning, myNums);

                if (intersectionSize > 0) {
                    stack.push(new RecursiveCall(i + 1, intersectionSize));
                }
            }
        }
    }

    private static class RecursiveCall {
        int start;
        int size;

        RecursiveCall(int start, int size) {
            this.start = start;
            this.size = size;
        }
    }


    private static int[] convertToIntArray(String str) {
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int computeIntersectionSize(int[] arr1, int[] arr2) {
        int count = 0;
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        int i = 0, j = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                i++;
            } else if (arr1[i] > arr2[j]) {
                j++;
            } else {
                count++;
                i++;
                j++;
            }
        }
        return count;
    }
}
