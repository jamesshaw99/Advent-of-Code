package AoC.days;

import AoC.Day;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class day19 extends Day {
    private final Pattern REGEX_RULE = Pattern.compile("^([0-9]+): (.*)$");
    private final Pattern REGEX_SENTENCE = Pattern.compile("^([ab]+)$");
    private final HashMap<Integer, Rule> rules = new HashMap<>();
    private final List<String> sentences;
    public day19(String fileStr) {
        super(fileStr);
        input.stream().forEach(x -> {
            Matcher matcher = REGEX_RULE.matcher(x.replace("\"", ""));
            if (matcher.matches()) {
                int number = Integer.parseInt(matcher.group(1));
                String[] values = matcher.group(2).split(" \\| ");

                ArrayList<Integer> left = new ArrayList<>();
                if (values[0].equals("a") || values[0].equals("b")) {
                    rules.put(number, new Rule(values[0]));
                } else {
                    left.addAll(Arrays.stream(values[0].split(" ")).map(Integer::parseInt).collect(Collectors.toList()));

                    ArrayList<Integer> right = new ArrayList<>();
                    if (values.length > 1) {
                        right.addAll(Arrays.stream(values[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
                    }

                    rules.put(number, new Rule(left, right));
                }
            }
        });

        sentences = input.stream().filter(x -> REGEX_SENTENCE.matcher(x).matches()).collect(Collectors.toList());
    }

    public long part1() {
        Pattern regex = Pattern.compile(rules.get(0).getRegex(rules, -1, 0));
        return sentences.parallelStream().filter(x -> regex.matcher(x).matches()).count();
    }

    public long part2() {
        rules.put(8, new Rule(new ArrayList<>(Arrays.asList(42)), new ArrayList<>(Arrays.asList(42, 8))));
        rules.put(11, new Rule(new ArrayList<>(Arrays.asList(42, 31)), new ArrayList<>(Arrays.asList(42, 11, 31))));
        Pattern regex2 = Pattern.compile(rules.get(0).getRegex(rules, -1, 0));
        return sentences.parallelStream().filter(x -> regex2.matcher(x).matches()).count();
    }

    public static class Rule {
        public final ArrayList<Integer> left;
        public final ArrayList<Integer> right;
        private String value;
        private int alreadyCalledItself;

        public Rule(ArrayList<Integer> left, ArrayList<Integer> right) {
            this.left = left;
            this.right = right;
        }

        public Rule(String value) {
            this.value = value;
            this.left = new ArrayList<>();
            this.right = new ArrayList<>();
        }

        public String getRegex(HashMap<Integer, Rule> rules, int previous, int number) {
            if (value != null) {
                return value;
            }

            StringBuilder regex = new StringBuilder("(");
            for (Integer rule : left) {
                regex.append(rules.get(rule).getRegex(rules, number, rule));
            }
            if (!right.isEmpty()) {
                regex.append("|");
                for (Integer rule : right) {
                    if (number == rule) {
                        if (previous == number) {
                            alreadyCalledItself++;
                        } else {
                            alreadyCalledItself = 0;
                        }

                        if (alreadyCalledItself <= 1) {
                            regex.append(rules.get(rule).getRegex(rules, number, rule));
                        } else {
                            regex.append(".+");
                        }
                    } else {
                        regex.append(rules.get(rule).getRegex(rules, number, rule));
                    }
                }
            }

            return regex + ")";
        }
    }
}
