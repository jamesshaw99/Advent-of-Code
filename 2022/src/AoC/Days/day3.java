package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day3 extends Day {
    String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public day3(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        int res = input.stream().map(this::splitInHalf).map(this::getCommonChar).mapToInt(this::computePriority).sum();

        return "Sum of priorities: " + res;
    }

    public String part2() {
        int sum = 0;
        for (int i = 0; i < input.size(); i += 3){
            String commonChar = getCommonChar(Arrays.asList(
                    input.get(i),
                    input.get(i+1),
                    input.get(i+2)
            ));
            sum += computePriority(commonChar);
        }

        return "Group sum of priorities: " + sum;
    }

    private List<String> splitInHalf(String inputStr) {
        return Arrays.asList(
            inputStr.substring(0, inputStr.length() / 2),
            inputStr.substring(inputStr.length() / 2)
        );
    }

    private String getCommonChar(List<String> inputs) {
        if(inputs.size() == 2) {
            String compartment1 = inputs.get(0);
            String compartment2 = inputs.get(1);
            for (int i = 0; i < compartment1.length(); i++) {
                if (compartment2.indexOf(compartment1.charAt(i)) > -1) {
                    return String.valueOf(compartment1.charAt(i));
                }
            }
        } else {
            String compartment1 = inputs.get(0);
            String compartment2 = inputs.get(1);
            String compartment3 = inputs.get(2);
            for (int i = 0; i < compartment1.length(); i++) {
                if (compartment2.indexOf(compartment1.charAt(i)) > -1 && compartment3.indexOf(compartment1.charAt(i)) > -1) {
                    return String.valueOf(compartment1.charAt(i));
                }
            }
        }
        return "";
    }

    private int computePriority(String i) {
        return letters.indexOf(i)+1;
    }
}
