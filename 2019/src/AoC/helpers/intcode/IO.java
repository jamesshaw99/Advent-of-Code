package AoC.helpers.intcode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class IO {
    public static final Scanner scanner = new Scanner(System.in);
    private final List<Long> inputsLog = new ArrayList<>();
    private final List<Long> outputsLog = new ArrayList<>();
    private final ProgramExecutor executor;
    private boolean consoleInput;
    private boolean consoleOutput;
    private final boolean gameMode;
    private long score;
    private int paddleX;
    private int ballX;
    private boolean outputInterrupt = false;
    private List<Long> inputs = new ArrayList<>();
    private Iterator<Long> inputsIterator = inputs.iterator();


    public IO(ProgramExecutor executor, boolean consoleInput, boolean consoleOutput) {
        this(executor, consoleInput, consoleOutput, false);
    }
    public IO(ProgramExecutor executor, boolean consoleInput, boolean consoleOutput, boolean gameMode) {
        this.executor = executor;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.gameMode = gameMode;
    }

    public void out(long i) {
        if (consoleOutput && !gameMode) {
            System.out.println("OUT: " + i);
        }
        if(outputInterrupt) {
            executor.pause();
        }
        outputsLog.add(i);
    }

    public long in() throws InterruptedException {
        if(gameMode) {
            String[][] board = new String[20][38];
            int x = 0, y = 0;
            score = 0;
            for(int i = 0; i < outputsLog.size(); i++){
                if (i%3 == 0){
                    x = outputsLog.get(i).intValue();
                } else if (i%3 == 1) {
                    y = outputsLog.get(i).intValue();
                } else {
                    if (x == -1 && y == 0){
                        score = outputsLog.get(i);
                    } else {
                        if (outputsLog.get(i) == 0) {
                            board[y][x] = " ";
                        } else if (outputsLog.get(i) == 1) {
                            board[y][x] = "X";
                        } else if (outputsLog.get(i) == 2) {
                            board[y][x] = "#";
                        } else if (outputsLog.get(i) == 3) {
                            board[y][x] = "_";
                            paddleX = x;
                        } else if (outputsLog.get(i) == 4) {
                            board[y][x] = "o";
                            ballX = x;
                        }
                    }
                }
            }
            if(consoleOutput) {
                System.out.println("Current score: " + score);
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        System.out.print(board[i][j]);
                    }
                    System.out.print("\n");
                }
            }
        }

        long input;
        if(!consoleInput && inputsIterator.hasNext()) {
            input = inputsIterator.next();
        } else if(gameMode) {
            if (paddleX < ballX) {
                input = 1;
            } else if (paddleX > ballX) {
                input = -1;
            } else {
                input = 0;
            }
        } else if (!consoleInput){
            input = 99;
        } else {
            input = scanner.nextInt();
        }

        return input;
    }

    public void addInput(long input) {
        inputs.add(input);
        this.inputsIterator = this.inputs.iterator();
    }

    public void clearInputs() {
        inputs = new ArrayList<>();
    }

    public void setInputs(List<Long> inputs) {
        this.inputs = new ArrayList<>(inputs);
        this.inputsIterator = this.inputs.iterator();
    }

    public List<Long> getOutputsLog() {
        return outputsLog;
    }

    public long getScore() {
        return this.score;
    }

    public List<Long> getInputsLog() {
        return inputsLog;
    }

    public ProgramExecutor getExecutor() {
        return executor;
    }

    public long getLastOutput() {
        return outputsLog.get(outputsLog.size() - 1);
    }

    public void enableConsoleInput(boolean consoleInput) {
        this.consoleInput = consoleInput;
    }

    public void enableConsoleOutput(boolean consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    public void enableOutputInterrupt(boolean outputInterrupt) {
        this.outputInterrupt = outputInterrupt;
    }
}
