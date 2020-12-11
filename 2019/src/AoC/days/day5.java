package AoC.days;

import AoC.Day;
import AoC.intcode.ProgramExecutor;

import java.io.*;
import java.util.Scanner;

public class day5 extends Day {
    public String text;
    public day5(String fileStr) {
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
        ProgramExecutor computer2 = new ProgramExecutor(text, false, false);
        computer2.getIo().addInput(5);
        computer2.run();
        return computer2.getIo().getLastOutput();
    }
}
