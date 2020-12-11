package AoC.days;

import AoC.Day;
import AoC.intcode.ProgramExecutor;

import java.util.ArrayList;
import java.util.List;

public class day2 extends Day {
    private String text;
    private List<Integer> dataList = new ArrayList<>();
    private List<Integer> list = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();
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
