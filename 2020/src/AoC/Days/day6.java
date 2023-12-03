package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class day6 extends Day {
    public day6(String fileStr) {
        super(fileStr);
        input.add("");
    }

    public Integer part1() {
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        List<Character> seenChars = new ArrayList<>();
        for (String line : input) {
            if (!line.isEmpty()) {
                for (int i = 0; i < line.length(); i++) {
                    if (!seenChars.contains(line.charAt(i))) {
                        seenChars.add(line.charAt(i));
                        count++;
                    }
                }
            } else {
                counts.add(count);
                count = 0;
                seenChars = new ArrayList<>();
            }
        }
        return counts.stream().mapToInt(Integer::intValue).sum();
    }

    public Integer part2() {
        List<Integer> counts = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for(String line: input) {
            if(!line.isEmpty()){
                answers.add(line);
            } else {
                Set<Character> commonChars = convertStringToSetOfChars(answers.get(0));
                answers.stream().skip(1).forEach(s -> commonChars.retainAll(convertStringToSetOfChars(s)));

                counts.add(commonChars.size());
                answers = new ArrayList<>();
            }
        }
        return counts.stream().mapToInt(Integer::intValue).sum();
    }

    public Set<Character> convertStringToSetOfChars(String string) {
        if (string == null || string.isEmpty()){
            return Collections.emptySet();
        }

        Set<Character> set = new HashSet<>(string.length() + 10);
        for (char c: string.toCharArray()){
            set.add(c);
        }
        return set;
    }
}
