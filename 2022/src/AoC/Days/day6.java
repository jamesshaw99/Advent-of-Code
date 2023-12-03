package AoC.Days;

import AoC.Helpers.Day;

import java.util.HashSet;
import java.util.Set;

public class day6 extends Day {
    String buffer;
    public day6(String fileStr) {
        super(fileStr);
        buffer = input.get(0);
    }

    public String part1() throws Exception {
        int received = run(4);
        return "complete after " + received + " characters";
    }

    public String part2() throws Exception {
        int received = run(14);
        return "complete after " + received + " characters";
    }

    public int run(int markerSize) {
        int received = markerSize;
        again:
        while(true) {
            Set<Character> set = new HashSet<>();
            char[] marker = buffer.substring(received-markerSize, received).toCharArray();
            for(Character c: marker){
                if(!set.add(c)){
                    received++;
                    continue again;
                }
            }
            break;
        }

        return received;
    }
}
