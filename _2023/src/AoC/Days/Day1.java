package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 extends Day {
    private final List<Integer> values = new ArrayList<>();

    public Day1(String fileStr) {
        super(fileStr);
    }

    public String part1() throws Exception {
        setValues(input);
        return "Sum of values: " + values.stream().mapToInt(Integer::intValue).sum();
    }

    public String part2() throws Exception {
        List<String> newInput = modifyInput(input);
        setValues(newInput);
        return "Sum of values: " + values.stream().mapToInt(Integer::intValue).sum();
    }

    private List<String> modifyInput(List<String> input) {
        Map<String, String> map = new HashMap<>();
        map.put("one", "o1e");
        map.put("two", "t2");
        map.put("three", "3e");
        map.put("four", "4");
        map.put("five", "5e");
        map.put("six", "6");
        map.put("seven", "7n");
        map.put("eight", "8t");
        map.put("nine", "9e");

        List<String> newInput = new ArrayList<>();
        for (String line : input) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                line = line.replace(entry.getKey(), entry.getValue());
            }
            newInput.add(line);
        }
        return newInput;
    }

    private void setValues(List<String> strings) {
        values.clear();
        for (String line : strings) {
            List<String> allMatches = new ArrayList<>();
            Matcher m = Pattern.compile("(\\d)").matcher(line);
            while (m.find()) {
                allMatches.add(m.group());
            }
            values.add(Integer.parseInt(allMatches.get(0) + allMatches.get(allMatches.size() - 1)));
        }
    }
}