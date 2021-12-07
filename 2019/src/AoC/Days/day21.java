package AoC.Days;

import AoC.helpers.Day;
import AoC.helpers.IntcodeComputer;

public class day21 extends Day {
    private final String instructions;
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
        long input, output;
        String result = "NEED_INPUT";
        IntcodeComputer springdroid = new IntcodeComputer(instructions);
        while(true) {
            if(result.equals("NEED_INPUT")){
                input = inputString.charAt(0);
                inputString = inputString.substring(1);
                springdroid.addInput(input);
            } else {
                output = springdroid.getLastOutput();
                break;
            }
            result = springdroid.runProgram();
        }
        return output;
    }
}
