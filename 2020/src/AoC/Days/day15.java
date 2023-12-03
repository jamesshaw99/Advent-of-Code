package AoC.Days;

import AoC.Helpers.Day;
import AoC.Helpers.LinkedNonBlockingQueue;

import java.util.HashMap;

import static java.util.Collections.singletonList;

public class day15 extends Day {

    private final HashMap<Integer, LinkedNonBlockingQueue<Integer>> occurences = new HashMap<>();
    private int lastNumber, turnNumber;

    public day15(String fileStr) {
        super(fileStr);
        lastNumber = 0;
        turnNumber = 0;

        String[] numbers = input.get(0).split(",");
        for(int i = 0; i < numbers.length; i++){
            int number = Integer.parseInt(numbers[i]);
            incrementOccurence(number, i+1);
            lastNumber = number;
            turnNumber++;
        }
    }

    public Integer part1() {
        while(turnNumber < 2020) {
            nextTurn();
        }
        return lastNumber;
    }

    public Integer part2() {
        while(turnNumber < 30000000) {
            nextTurn();
        }
        return lastNumber;
    }

    public void nextTurn() {
        turnNumber++;
        if(countOccurrences(lastNumber) == 1) {
            lastNumber = 0;
            incrementOccurence(0, turnNumber);
        } else {
            int difference = getDifferenceLastTurns(lastNumber);
            lastNumber = difference;
            incrementOccurence(difference, turnNumber);
        }
    }

    public long countOccurrences(int value){
        return occurences.get(value).size();
    }

    private void incrementOccurence(int value, int turn) {
        if(occurences.containsKey(value)) {
            occurences.get(value).add(turn);
        } else {
            occurences.put(value, new LinkedNonBlockingQueue<>(singletonList(turn), 2));
        }
    }

    private int getDifferenceLastTurns(int value) {
        return occurences.get(value).getLast() - occurences.get(value).get(occurences.get(value).size() -2);
    }
}
