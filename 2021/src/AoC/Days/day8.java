package AoC.Days;

import AoC.Helpers.Day;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class day8 extends Day {
    public day8(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        long count = Arrays.stream(input.stream()
                        .map(string -> string.substring(string.lastIndexOf('|') + 1))
                        .collect(Collectors.joining())
                        .trim()
                        .split("\\s+"))
                .filter(el -> el.length() < 5 || el.length() > 6)
                .count();
        return "Count: " + count;
    }

    public String part2() {
        LinkedList<Long> number = new LinkedList<>();
        for (String line : input) {
            Display display = new Display();
            String[] numbers = line.substring(0, line.indexOf('|'))
                    .trim()
                    .split("\\s+");
            String[] decode = line.substring(line.indexOf('|') + 1)
                    .trim()
                    .split("\\s+");

            fillDisplay(numbers, display);
            long temp = 0;
            for (int i = 0; i < 4; i++) {
                temp += display.getNumber(decode[i]) * Math.pow(10, 3 - i);
            }
            number.add(temp);
        }
        return "Sum of output values: " + number.stream().reduce(0L, Long::sum);
    }

    private void fillDisplay(String[] numbers, Display display) {
        char[] one = new char[2];
        char[] four = new char[4];
        char[] six = new char[6];
        AtomicBoolean ifContainOne = new AtomicBoolean(false);
        AtomicBoolean ifContainFour = new AtomicBoolean(false);
        Arrays.stream(numbers)
                .peek(string -> {
                    if (string.length() == 2) {
                        display.matchStringAndNumber(string, 1);
                        one[0] = string.charAt(0);
                        one[1] = string.charAt(1);
                    } else if (string.length() == 3)
                        display.matchStringAndNumber(string, 7);
                    else if (string.length() == 4) {
                        display.matchStringAndNumber(string, 4);
                        four[0] = string.charAt(0);
                        four[1] = string.charAt(1);
                        four[2] = string.charAt(2);
                        four[3] = string.charAt(3);
                    } else if (string.length() == 7)
                        display.matchStringAndNumber(string, 8);
                })
                .filter(string -> string.length() > 4 && string.length() < 7)
                .collect(Collectors.toList())
                .stream()
                .peek(string -> {
                    ifContainOne.set(string.contains("" + one[0]) && string.contains("" + one[1]));
                    ifContainFour.set(string.contains("" + four[0]) && string.contains("" + four[1]) && string.contains("" + four[2]) && string.contains("" + four[3]));
                    if (string.length() == 5) {
                        if (ifContainOne.get())
                            display.matchStringAndNumber(string, 3);
                    } else if (string.length() == 6) {
                        if (!ifContainOne.get()) {
                            display.matchStringAndNumber(string, 6);
                            six[0] = string.charAt(0);
                            six[1] = string.charAt(1);
                            six[2] = string.charAt(2);
                            six[3] = string.charAt(3);
                            six[4] = string.charAt(4);
                            six[5] = string.charAt(5);
                        } else if (ifContainFour.get())
                            display.matchStringAndNumber(string, 9);
                        else
                            display.matchStringAndNumber(string, 0);
                    }
                })
                .filter(string -> {
                    if (string.length() == 5) {
                        return !string.contains("" + one[0]) || !string.contains("" + one[1]);
                    } else
                        return false;
                })
                .collect(Collectors.toList())
                .forEach(string -> {
                    int count = 0;
                    if (string.length() == 5) {
                        for (char c : six) {
                            if (string.contains("" + c))
                                count++;
                        }
                        if (count == 5)
                            display.matchStringAndNumber(string, 5);
                        else
                            display.matchStringAndNumber(string, 2);
                    }
                });
    }

    private class Display {
        private final String[] numberCodes = new String[10];

        public void matchStringAndNumber (String code, int display) {
            numberCodes[display] = code;
        }

        public int getNumber(String value) {
            for(int i = 0; i < 10; i++) {
                if(value.length() == numberCodes[i].length()){
                    int count = 0;
                    for(int j = 0; j < value.length(); j++) {
                        if(numberCodes[i].contains("" + value.charAt(j))){
                            count++;
                        }
                    }
                    if(count == value.length()){
                        return i;
                    }
                }
            }
            return -10000;
        }
    }
}