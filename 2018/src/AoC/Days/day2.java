package AoC.Days;

import AoC.Helpers.Day;
import com.sun.xml.bind.v2.util.EditDistance;

import java.util.HashMap;
import java.util.Map;

public class day2 extends Day {
    public day2(String fileStr) {
        super(fileStr);
    }

    public String part1(){
        int two = 0, three = 0;

        for(String boxID: input) {
            Map<Character, Integer> frequencies = new HashMap<>();
            boolean found2 = false, found3 = false;

            for(int i = 0; i < boxID.length(); i++){
                frequencies.put(boxID.charAt(i), frequencies.getOrDefault(boxID.charAt(i), 0) + 1);
            }

            for(Map.Entry<Character, Integer> character: frequencies.entrySet()){
                if(!found2 && character.getValue() == 2) {
                    two++;
                    found2 = true;
                }
                if(!found3 && character.getValue() == 3) {
                    three++;
                    found3 = true;
                }

            }
        }

        return "Checksum: " + (two * three);
    }

    public String part2() {
        for (int i = 0; i < input.size() - 1; i++) {
            for(int j = 1; j < input.size(); j++) {
                int dist = EditDistance.editDistance(input.get(i), input.get(j));
                if(dist == 1) {
                    return "Common letters: " + removeDifference(input.get(i), input.get(j));
                }
            }
        }

        return "No common letters found";
    }

    private String removeDifference(String a, String b) {
        String result = "";
        for(int i = 0; i < a.length(); i++) {
            if(a.charAt(i) == b.charAt(i)){
                result += a.charAt(i);
            }
        }
        return result;
    }
}
