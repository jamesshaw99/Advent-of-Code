package AoC.days;

import AoC.Day;

public class day4 extends Day {
    private final int min;
    private final int max;
    public day4(String fileStr) {
        super(fileStr);
        min = Integer.parseInt(input.get(0).substring(0, input.get(0).indexOf('-')));
        max = Integer.parseInt(input.get(0).substring(input.get(0).indexOf('-')+1));
    }

    public static boolean rule1(String value){
        return value.length() == 6;
    }

    public static boolean rule2(String value) {
        char x = value.charAt(0);
        for (int i = 1; i < value.length(); i++){
            if (value.charAt(i) == x){
                return true;
            }
            x = value.charAt(i);
        }
        return false;
    }

    public static boolean rule3(String value) {
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) < value.charAt(i-1)){
                return false;
            }
        }
        return true;
    }

    public static boolean rule4(String value) {
        for (int i = 0; i < value.length(); i++){
            char someChar = value.charAt(i);
            int count = 0;
            for (int j = 0; j < value.length(); j++){
                if (value.charAt(j) == someChar) {
                    count++;
                }
            }
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    public int part1() {
        int count = 0;
        for (int i = min; i <= max; i++) {
            String value = Integer.toString(i);
            if (rule1(value) && rule2(value) && rule3(value)) {
                count++;
            }
        }

        return count;
    }

    public int part2() {
        int count = 0;
        for (int i = min; i <= max; i++){
            String value = Integer.toString(i);
            if (rule1(value) && rule2(value) && rule3(value) && rule4(value)){
                count++;
            }
        }

        return count;
    }
}