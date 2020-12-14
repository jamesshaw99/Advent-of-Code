package AoC.days;

import AoC.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day2 extends Day {
    private static final List<List<String>> lines = new ArrayList<>();

    public day2(String fileStr) {
        super(fileStr);
        for(String line: input){
            lines.add(Arrays.asList(line.split(" ")));
        }
    }

    public int part1() {
        int noValid = 0;
        for (List<String> line : lines) {
            int columnNo = 1;
            String value1 = "", value2 = "";
            char searchChar = ' ';
            int charCount = 0;
            for (String value : line) {
                if (columnNo == 1) {
                    value1 = value.substring(0, value.indexOf("-"));
                    value2 = value.substring(value.indexOf("-") + 1);
                } else if (columnNo == 2) {
                    searchChar = value.charAt(0);
                } else if (columnNo == 3) {
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) == searchChar) {
                            charCount++;
                        }
                    }
                    if (Integer.parseInt(value1) <= charCount && charCount <= Integer.parseInt(value2)) {
                        noValid++;
                    }
                }
                columnNo++;
            }
        }
        return noValid;

    }

    public int part2() {
        int noValid2 = 0;
        for(List<String> line: lines) {
            int columnNo = 1;
            String value1 = "", value2 = "";
            char searchChar = ' ';
            for (String value: line) {
                if (columnNo == 1){
                    value1 = value.substring(0, value.indexOf("-"));
                    value2 = value.substring(value.indexOf("-") + 1);
                } else if (columnNo == 2) {
                    searchChar = value.charAt(0);
                } else if(columnNo == 3) {
                    if (value.charAt(Integer.parseInt(value1) - 1) == searchChar ^ value.charAt(Integer.parseInt(value2) - 1) == searchChar) {
                        noValid2++;
                    }
                }
                columnNo++;
            }
        }
        return noValid2;
    }
}
