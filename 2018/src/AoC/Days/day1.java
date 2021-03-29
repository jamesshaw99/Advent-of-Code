package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day1 extends Day {
    public day1(String fileStr) {
        super(fileStr);
    }

    public String part1(){
        int frequency = 0;

        for (String line: input) {
            if (line.startsWith("+")){
                frequency += Integer.parseInt(line.substring(1));
            } else {
                frequency -= Integer.parseInt(line.substring(1));
            }
        }

        return "Resulting frequency is " + frequency;
    }

    public String part2() {
        Set<Integer> frequencies = new HashSet<>();
        int frequency = 0;

        while(true) {
            for (String line : input) {
                if (line.startsWith("+")) {
                    frequency += Integer.parseInt(line.substring(1));
                } else {
                    frequency -= Integer.parseInt(line.substring(1));
                }
                if(!frequencies.add(frequency)){
                    return "First duplicate frequency is " + frequency;
                }
            }
        }

    }
}
