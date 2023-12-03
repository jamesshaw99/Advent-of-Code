package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day1 extends Day {
    private static final List<Integer> intList = new ArrayList<>();

    public day1(String file) {
        super(file);
        for(String s: input) intList.add(Integer.valueOf(s));
        Collections.sort(intList);
    }

    public Integer part1() {
        int l = 0, r = intList.size() - 1, result = 0;

        while (l < r) {
            if (intList.get(l) + intList.get(r) == 2020) {
                result = (intList.get(l) * intList.get(r));
                break;
            } else if (intList.get(l) + intList.get(r) < 2020) {
                l++;
            } else {
                r--;
            }
        }
        return result;
    }

    public Integer part2(){
        int result = 0;
        for(int i = 0; i < intList.size() - 2; i++) {
            for(int j = i + 1; j < intList.size() - 1; j++) {
                for(int k = j + 1; k < intList.size(); k++) {
                    if(intList.get(i) + intList.get(j) + intList.get(k) == 2020){
                        result = (intList.get(i) * intList.get(j) * intList.get(k));
                    }
                }
            }
        }
        return result;
    }
}
