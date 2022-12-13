package AoC.Days;

import AoC.Helpers.Day;

import java.util.Arrays;

public class day4 extends Day {
    public day4(String fileStr) {
        super(fileStr);
    }

    public String part1(){
        int count = 0;

        for(String pairs: input) {
            String pair = pairs.replace(",", "-");
            int[] IDs = Arrays.stream(pair.split("-")).mapToInt(Integer::parseInt).toArray();
            if((IDs[0] <= IDs[2] && IDs[1] >= IDs[3]) || (IDs[0] >= IDs[2] && IDs[1] <= IDs[3])){
                count++;
            }
        }
        return "Fully contained pairs: " + count;
    }

    public String part2(){
        int count = 0;

        for(String pairs: input) {
            String pair = pairs.replace(",", "-");
            int[] IDs = Arrays.stream(pair.split("-")).mapToInt(Integer::parseInt).toArray();
            if(!((IDs[0]<IDs[2] && IDs[1]<IDs[2]) || (IDs[0]>IDs[3] && IDs[1]>IDs[3]))){
                count++;
            }
        }
        return "Overlapping pairs: " + count;
    }
}
