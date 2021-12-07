package AoC.Days;

import AoC.helpers.Day;
import AoC.helpers.intcode.ProgramExecutor;

import java.util.List;

public class day13 extends Day {
    public day13(String fileStr) {
        super(fileStr);
    }

    public String part1() throws Exception {
        ProgramExecutor computer = new ProgramExecutor(input.get(0), false, false);
        computer.run();
        List<Long> output = computer.getIo().getOutputsLog();
        int count = 0;
        for (int i = 0; i < output.size(); i++) {
            if ((i+1)%3 == 0 && output.get(i) == 2){
                count++;
            }
        }
        return count + " block tiles";
    }

    public String part2() throws Exception {
        String program = "2" + input.get(0).substring(1);
        ProgramExecutor computer = new ProgramExecutor(program, true, false, true);
        computer.run();
        return "Score: " + computer.getIo().getLastOutput();
    }
}
