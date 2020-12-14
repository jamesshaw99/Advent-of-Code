package AoC.days;

import AoC.Day;
import AoC.intcode.ProgramExecutor;

public class day2 extends Day {
    private final String text;

    public day2(String fileStr) {
        super(fileStr);
        text = input.get(0);
    }

    public long part1() throws Exception {
        ProgramExecutor computer = new ProgramExecutor(text, false, false);
        computer.run();
        computer.setMemoryPointer(0);
        return computer.getAtPointer();
    }

    public int part2() throws Exception {
        for (int k = 0; k < 100; k++){
            for (int j = 0; j < 100; j++){
                ProgramExecutor computer = new ProgramExecutor(text, false, false);
                computer.set(1,k);
                computer.set(2,j);
                computer.run();
                computer.setMemoryPointer(0);
                if(computer.getAtPointer() == 19690720) {
                    return (k * 100 + j);
                }
            }
        }
        return 0;
    }
}
