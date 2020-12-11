package AoC.days;

import AoC.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day8 extends Day {
    private List<String> pixels = new ArrayList<>();
    public day8(String fileStr) {
        super(fileStr);
        int rows = input.get(0).length()/6;
        pixels = Arrays.asList(input.get(0).split("(?<=\\G.{"+rows+"})"));
        for (String row: pixels){
            System.out.println(row);
        }
    }

}
