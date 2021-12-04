package AoC.Helpers;

import java.util.Arrays;
import java.util.List;

public class BingoBoard {
    private int[][] board = new int[5][5];
    private int number;

    public BingoBoard (List<String> input) {
        for (int i = 0; i < input.size(); i++){
            board[i] = Arrays.stream(input.get(i).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        }
    }

    public BingoBoard (int[][] newBoard) {
        board = newBoard;
    }

    public boolean checkInput(int num) {
        number = num;
        for(int row = 0; row < 5; row++){
            for (int col = 0; col < 5; col++){
                if(board[row][col] == number){
                    board[row][col] = -1;

                    if((board[0][col] + board[1][col] + board[2][col] + board[3][col] + board[4][col] == -5) || (board[row][0] + board[row][1] + board[row][2] + board[row][3] + board[row][4] == -5)){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public int getScore() {
        int score = 0;
        for(int row = 0; row < 5; row++){
            for (int col = 0; col < 5; col ++){
                if (board[row][col] != -1){
                    score += board[row][col];
                }
            }
        }
        return score * number;
    }

    public void printBoard(){
        for (int i = 0; i < 5; i++) {
            System.out.printf("%2d %2d %2d %2d %2d%n", board[i][0], board[i][1], board[i][2], board[i][3], board[i][4]);
        }
    }

    public BingoBoard clone() {
        return new BingoBoard(board);
    }
}
