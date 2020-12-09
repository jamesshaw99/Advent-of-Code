package AoC.days;

import AoC.Day;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day5 extends Day {
    private List<Integer> SeatIDs = new ArrayList<>();
    public day5(String fileStr) {
        super(fileStr);
        for (String line: input) {
            int rowNo = Integer.parseInt(line.substring(0,7).replaceAll("F", "0").replaceAll("B", "1"),2);
            int columnNo = Integer.parseInt(line.substring(7).replaceAll("R", "1").replaceAll("L", "0"),2);
            SeatIDs.add((rowNo * 8) + columnNo);
        }
    }

    public int part1() {
        return Collections.max(SeatIDs);
    }

    public int part2() {
        int output = 0;
        for(int i = Collections.min(SeatIDs); i < Collections.max(SeatIDs); i++){
            if(!SeatIDs.contains(i)){
                output = i;
            }
        }
        return output;
    }
}