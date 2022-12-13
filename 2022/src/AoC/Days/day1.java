package AoC.Days;

import AoC.Helpers.Day;

public class day1 extends Day {
    Integer[] calories = new Integer[input.size()+1];
    public day1(String fileStr) {
        super(fileStr);
        for (int i = 0; i < input.size(); i++){
            if(input.get(i).equals("")){
                calories[i] = 0;
            }
            else {
                calories[i] = Integer.parseInt(input.get(i));
            }
        }
        calories[input.size()] = 0;
    }

    public String part1(){
        int max = 0,
            total = 0;
        for(int i = 0; i < calories.length; i++){
            total += calories[i];
            if(calories[i] == 0) {
                if (max < total){
                    max = total;
                }
                total = 0;
            }
        }

        return "The most calories carried by an elf is " + max;
    }

    public String part2() {
        int first = 0, second = 0, third = 0,
            total = 0;
        for(int i = 0; i < calories.length; i++){
            total += calories[i];
            if(calories[i] == 0) {
                if (first < total){
                    third = second;
                    second = first;
                    first = total;
                } else if (second < total) {
                    third = second;
                    second = total;
                } else if (third < total) {
                    third = total;
                }
                total = 0;
            }
        }

        return "The total calories of the top three elves is " + (first + second + third);
    }
}
