package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day19 extends Day {
    private HashMap<Integer, String> rules = new HashMap<>();
    private List<String> messages = new ArrayList<>();

    public day19(String fileStr) {
        super(fileStr);int i = 0;
        for (;; i++) {
            final String line = input.get(i);
            if (line.isEmpty()) {
                i++;
                break;
            }

            final String[] parts = line.split(": ");
            rules.put(Integer.parseInt(parts[0]), parts[1]);
        }

        for (; i < input.size(); i++) {
            messages.add(input.get(i));
        }
    }

    public Long part1() {
        String regex = rules.get(0);
        while (!regex.matches("^[a-z \"\\|\\(\\)]+$")) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();
        }

        String pattern = "^" + regex.replaceAll("[ \"]", "") + "$";

        return messages.stream().filter(a -> a.matches(pattern)).count();
    }

    public Long part2() {
        rules.put(8, "42 | 42 8");
        rules.put(11, "42 31 | 42 11 31");

        String regex = rules.get(0);
        long prev = 0;
        while (true) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();

            final String pattern = "^" + regex.replaceAll("([ \"])|42|31", "") + "$";

            final long count = messages.stream().filter(a -> a.matches(pattern)).count();
            if (count > 0 && count == prev) {
                return count;
            }

            prev = count;
        }
    }
}
