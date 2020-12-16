package AoC.days;

import AoC.Day;

import java.util.ArrayList;
import java.util.List;

public class day1 extends Day {
    private final List<Integer> list = new ArrayList<>();

    public day1(String fileStr) {
        super(fileStr);
        for(String s: input) list.add(Integer.valueOf(s));
    }

    public String part1() {
        long totalFuel = 0;
        for (Integer integer : list) {
            totalFuel += (Math.floor(integer / 3.0) - 2);
        }
        return "Total fuel: " + totalFuel;
    }

    public String part2() {
        long totalFuel = 0;
        for (Integer integer : list) {
            int reqFuel = ((int) Math.floor(integer / 3.0) - 2);
            while (reqFuel > 0) {
                totalFuel += reqFuel;
                reqFuel = ((int) Math.floor(reqFuel / 3.0) - 2);
            }
        }
        return "Total fuel: " + totalFuel;
    }
}