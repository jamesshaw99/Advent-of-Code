package AoC.days;

import AoC.helpers.Day;
import AoC.helpers.intcode.ProgramExecutor;

public class day5 extends Day {
    public String text;
    public day5(String fileStr) {
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
        ProgramExecutor computer2 = new ProgramExecutor(text, false, false);
        computer2.getIo().addInput(5);
        computer2.run();
        return Long.toString(computer2.getIo().getLastOutput());
    }
}
