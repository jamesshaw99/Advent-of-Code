package AoC.days;

import AoC.Day;
import AoC.intcode.ProgramExecutor;

public class day9 extends Day {
    private final String text;

    public day9(String fileStr) {
        super(fileStr);
        text = input.get(0);
    }

    public long part1() throws Exception {
        ProgramExecutor computer = new ProgramExecutor(text, false, false);
        computer.getIo().addInput(1);
        computer.run();
        return computer.getIo().getLastOutput();
    }

    public long part2() throws Exception {
        ProgramExecutor computer = new ProgramExecutor(text, false, false);
        computer.getIo().addInput(2);
        computer.run();
        return computer.getIo().getLastOutput();
    }
}
