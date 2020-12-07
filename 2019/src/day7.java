import java.io.*;
import java.util.*;

import intcode.ProgramExecutor;

public class day7 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner( new File("inputs/day7.txt"));
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();

        System.out.println("Part 1");
        List<List<Integer>> inputs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++){
                if (j != i) {
                    for (int k = 0; k < 5; k++) {
                        if (k != i && k != j) {
                            for (int l = 0; l < 5; l++) {
                                if(l != i && l != j && l !=k) {
                                    for (int m = 0; m < 5; m++){
                                        if(m != i && m != j && m != k && m != l) {
                                            inputs.add(Arrays.asList(i,j,k,l,m));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        long maxValue = 0;
        List<Integer> winningInput = new ArrayList<>();
        for (List<Integer> inputSet: inputs){
            long lastOutput = 0;
            for (Integer value: inputSet) {
                ProgramExecutor computer = new ProgramExecutor(text, false, false);
                computer.getIo().addInput(value);
                computer.getIo().addInput(lastOutput);
                computer.run();
                lastOutput = computer.getIo().getLastOutput();
            }
            if(lastOutput > maxValue){
                maxValue = lastOutput;
                winningInput = inputSet;
            }
        }
        System.out.println("Max Value: " + maxValue);
        System.out.println("Using values: " + winningInput);

        System.out.print("\n");

        System.out.println("Part 2");
        ProgramExecutor computer2 = new ProgramExecutor(text, true, true);
        computer2.run();
    }
}
