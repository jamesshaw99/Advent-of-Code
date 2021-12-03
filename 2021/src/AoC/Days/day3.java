package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class day3 extends Day {
    public day3(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        int gamma = getVal(true), epsilon = getVal(false);
        return String.format("Gamma: %1$d, Epsilon: %2$d, Power Consumption: %3$d", gamma, epsilon, gamma*epsilon);
    }

    public String part2() {
        int OxyRating = getRating(true), CO2 = getRating(false);
        return String.format("Oxygen Generator: %1$d, CO2 Scrubber: %2$d, Life Support Rating: %3$d", OxyRating, CO2, OxyRating*CO2);
    }

    private int getVal(boolean Gamma){
        String val = "";
        int res;
        for(int i = 0; i < input.get(0).length(); i++){
            int ones = 0;
            for (String number: input) {
                if(number.charAt(i) == '1'){
                    ones++;
                }
            }
            if(ones > ((float)input.size())/2){
                val += "1";
            } else {
                val += "0";
            }
        }
        if(Gamma){
            res = Integer.parseInt(val, 2);
        } else {
            res = Integer.parseInt(val.replace('0', '2').replace('1', '0').replace('2', '1'), 2);
        }
        return res;
    }

    private int getRating(boolean Oxygen){
        List<String> inputCopy = new ArrayList<>(input);
        int i = 0;
        while(inputCopy.size() > 1){
            int ones = 0;
            char val;
            for (String number: inputCopy) {
                if(number.charAt(i) == '1'){
                    ones++;
                }
            }
            float size = ((float)inputCopy.size())/2;
            if(ones == size){
                if(Oxygen)
                    val = '1';
                else
                    val = '0';
            }else if(ones > size){
                if(Oxygen)
                    val = '1';
                else
                    val = '0';
            } else {
                if(Oxygen)
                    val = '0';
                else
                    val = '1';
            }
            int finalI1 = i;
            inputCopy = inputCopy.stream().filter(p -> p.charAt(finalI1) == val).collect(Collectors.toList());
            i++;
        }
        return Integer.parseInt(inputCopy.get(0), 2);
    }
}
