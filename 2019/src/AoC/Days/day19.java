package AoC.Days;

import AoC.helpers.Day;
import AoC.helpers.IntcodeComputer;

public class day19 extends Day {
    private final String instructions;
    public day19(String fileStr) {
        super(fileStr);
        instructions = input.get(0);
    }

    public String part1() {
        long count = 0;
        for (long i = 0; i < 50; i++) {
            for(long j = 0; j < 50; j++) {
                if(check(i, j)) {
                    count ++;
                }
            }
        }
        return "Total Affected Points: " + count;
    }

    public String part2() {
        long[] values = find(100, 30);
        values = find(values[1] - 30, 1);
        return "Result: " + ((values[0] * 10000) + values[1]);
    }

    private long[] find(long yStart, long yOffset) {
        long x,y;
        boolean trackBeam = false;
        double XtoY = getXtoY();
        for(y = yStart;; y += yOffset){
            for(x = (int)(y * XtoY);; ++x){
                boolean beam = check(x, y);
                if(!trackBeam) {
                    trackBeam = beam;
                } else if(!beam || !check(x + 99, y)) {
                    break;
                }
                if(check(x, y + 99)){
                    return new long[]{x, y};
                }
            }
        }
    }

    private double getXtoY() {
        for(long x = 0;;x++){
            if(check(x, 100L)){
                return x/100D;
            }
        }
    }

    private boolean check(long x, long y) {
        IntcodeComputer intcode = new IntcodeComputer(instructions);
        intcode.addInput(x);
        intcode.addInput(y);
        intcode.runProgram();
        return intcode.getLastOutput() == 1;
    }
}
