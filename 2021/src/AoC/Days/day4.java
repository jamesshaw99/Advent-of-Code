package AoC.Days;

import AoC.Helpers.BingoBoard;
import AoC.Helpers.Day;

import java.util.*;
import java.util.stream.Collectors;

public class day4 extends Day{
    int[] numbers;
    Map<Integer, BingoBoard> boards = new HashMap<Integer, BingoBoard>();
    Map<Integer, BingoBoard> boards2 = new HashMap<Integer, BingoBoard>();

    public day4(String fileStr) {
        super(fileStr);
        int boardNum = 0;
        numbers = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        List<String> tempBoard = new ArrayList<>();
        for(int i = 2; i < input.size(); i++){
            if(input.get(i).trim().equals("")){
                continue;
            }
            tempBoard.add(input.get(i));
            if(tempBoard.size() == 5){
                boards.put(++boardNum, new BingoBoard(tempBoard));
                boards2.put(boardNum, new BingoBoard(tempBoard));
                tempBoard = new ArrayList<>();
            }
        }
    }

    public String part1() {
        int winner = 0, score = 0;
        outerloop:
        for (int number : numbers) {
            for (int key : boards.keySet()) {
                if (boards.get(key).checkInput(number)) {
                    winner = key;
                    score = boards.get(key).getScore();
                    break outerloop;
                }
            }
        }
        return String.format("Winner: %d, score: %d", winner, score);
    }

    public String part2() {
        int winner = 0, score = 0;

        Map<Integer, BingoBoard> boardsCopy2;
        outerloop:
        for (int number : numbers) {
            boardsCopy2 = new HashMap<>();
            for (int key: boards2.keySet()){
                boardsCopy2.put(key, boards2.get(key).clone());
            }
            for (int key : boardsCopy2.keySet()) {
                if (boards2.get(key).checkInput(number)) {
                    if (boards2.size() == 1) {
                        winner = new ArrayList<>(boards2.keySet()).get(0);
                        score = boards2.get(winner).getScore();
                        break outerloop;
                    }
                    boards2.remove(key);
                }
            }
        }
        return String.format("Winner: %d, score: %d", winner, score);
    }
}
