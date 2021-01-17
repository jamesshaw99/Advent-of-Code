package AoC.days;

import AoC.helpers.Day;
import AoC.helpers.IntcodeComputer;

import java.util.LinkedList;

public class day21 extends Day {
    private String instructions;
    public day21(String fileStr) {
        super(fileStr);
        instructions = input.get(0);
    }

    public String part1() {
        String inputString = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nWALK\n";
        return "Hull Damage: " + runSpringdroid(inputString);
    }

    public String part2() {
        String inputString = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nNOT E T\nNOT T T\nOR H T\nAND T J\nRUN\n";
        return "Hull Damage: " + runSpringdroid(inputString);
    }

    private long runSpringdroid(String inputString) {
        long input = 0, output = 0;
        String result = "NEED_INPUT";
        IntcodeComputer springdroid = new IntcodeComputer(instructions);
        while(true) {
            if(result.equals("NEED_INPUT")){
                input = inputString.charAt(0);
                inputString = inputString.substring(1);
                springdroid.addInput(input);
            } else {
                output = ((LinkedList<Long>)springdroid.getOutputs()).getLast();
                break;
            }
            result = springdroid.runProgram();
        }
        return output;
    }
}
