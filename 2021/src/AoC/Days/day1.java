package AoC.Days;

import AoC.Helpers.Day;

public class day1 extends Day {
    int increased;
    Integer[] depths = new Integer[input.size()];
    public day1(String fileStr) {
        super(fileStr);
        for (int i = 0; i < input.size(); i++){
            depths[i] = Integer.parseInt(input.get(i));
        }
    }

    public String part1() {
        increased = 0;
        for (int i = 1; i < input.size(); i++){
            if(depths[i] > depths[i-1])
                increased++;
        }
        return "Measurements increased " + increased + " times.";
    }

    public String part2() {
        increased = 0;
        for (int i = 3; i < input.size(); i++){
            int Group1 = depths[i] + depths[i-1] + depths[i-2];
            int Group2 = depths[i-1] + depths[i-2] + depths[i-3];
            if(Group1 > Group2)
                increased++;
        }
        return "Measurements increased " + increased + " times.";
    }
}
