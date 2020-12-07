import intcode.ProgramExecutor;

import java.io.*;
import java.util.Scanner;

public class day5 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner( new File("inputs/day5.txt"));
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();

        System.out.println("Part 1");
        ProgramExecutor computer = new ProgramExecutor(text, false, true);
        computer.getIo().addInput(1);
        computer.run();

        System.out.println("Part 2");
        ProgramExecutor computer2 = new ProgramExecutor(text, false, true);
        computer2.getIo().addInput(5);
        computer2.run();
    }
}
