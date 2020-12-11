package AoC.days;

import java.io.*;
import java.util.*;

import AoC.Day;
import AoC.intcode.ProgramExecutor;

public class day7 extends Day {
    private String text;
    public day7(String fileStr) {
        super(fileStr);
        text = input.get(0);
    }

    public long part1() throws Exception {
        List<List<Integer>> inputs = generatePerm(new ArrayList<>(Arrays.asList(0,1,2,3,4)));
        long maxValue = 0;
        for (List<Integer> inputSet : inputs) {
            long lastOutput = 0;
            for (Integer value : inputSet) {
                ProgramExecutor computer = new ProgramExecutor(text, false, false);
                computer.getIo().addInput(value);
                computer.getIo().addInput(lastOutput);
                computer.run();
                lastOutput = computer.getIo().getLastOutput();
            }
            if (lastOutput > maxValue) {
                maxValue = lastOutput;
            }
        }
        return maxValue;
    }

    public long part2() throws Exception {
        List<List<Integer>> inputs = generatePerm(new ArrayList<>(Arrays.asList(5,6,7,8,9)));
        long maxValue = 0;
        for(List<Integer> inputSet: inputs) {
            ProgramExecutor[] computers = new ProgramExecutor[5];
            for (int i = 0; i < computers.length; i++) {
                computers[i] = new ProgramExecutor(text, false, false);
                computers[i].getIo().addInput(inputSet.get(i));
            }
            long lastOutput = 0;
            for (ProgramExecutor c : computers) {
                c.getIo().addInput(lastOutput);
                c.run();
                lastOutput = c.getIo().getLastOutput();
            }
            if (lastOutput > maxValue) {
                maxValue = lastOutput;
            }
        }
        //ProgramExecutor computer2 = new ProgramExecutor(text, true, true);
        //computer2.run();
        return maxValue;
    }

    private <E> List<List<E>> generatePerm(List<E> original) {
        if (original.isEmpty()) {
            List<List<E>> result = new ArrayList<>();
            result.add(new ArrayList<E>());
            return result;
        }
        E firstElement = original.remove(0);
        List<List<E>> returnValue = new ArrayList<>();
        List<List<E>> permutations = generatePerm(original);
        for (List<E> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<E> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
}
