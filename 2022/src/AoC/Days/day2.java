package AoC.Days;

import AoC.Helpers.Day;

import java.util.HashMap;
import java.util.Map;

public class day2 extends Day {

    public day2(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        Map<String, Integer> outcomes = Map.of(
            "A X", 4,
            "A Y", 8,
            "A Z", 3,
            "B X", 1,
            "B Y", 5,
            "B Z", 9,
            "C X", 7,
            "C Y", 2,
            "C Z", 6
        );

        return calculateScore(outcomes);
    }

    public String part2() {
        Map<String, Integer> outcomes = Map.of(
            "A X", 3,
            "A Y", 4,
            "A Z", 8,
            "B X", 1,
            "B Y", 5,
            "B Z", 9,
            "C X", 2,
            "C Y", 6,
            "C Z", 7
        );

        return calculateScore(outcomes);
    }

    public String calculateScore (Map<String, Integer> outcomes){
        int score = 0;
        for(String round: input) {
            score += outcomes.get(round);
        }

        return "Total score: " + score;
    }
}
