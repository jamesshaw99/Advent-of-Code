package AoC.Days;

import AoC.helpers.Day;
import AoC.helpers.intcode.ProgramExecutor;

public class day9 extends Day {
    private final String text;

    public day9(String fileStr) {
        super(fileStr);
        text = input.get(0);
    }

    public String part1() throws Exception {
        ProgramExecutor computer = new ProgramExecutor(text, false, false);
        computer.getIo().addInput(1);
        computer.run();
        return Long.toString(computer.getIo().getLastOutput());
    }

    public String part2() throws Exception {
        ProgramExecutor computer = new ProgramExecutor(text, false, false);
        computer.getIo().addInput(2);
        computer.run();
        return Long.toString(computer.getIo().getLastOutput());
    }
}
