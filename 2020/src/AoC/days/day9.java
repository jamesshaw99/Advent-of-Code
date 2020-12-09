package AoC.days;

import AoC.Day;

import java.util.ArrayList;
import java.util.List;

public class day9 extends Day {
    private List<Long> data = new ArrayList<>();
    public day9(String fileStr) {
        super(fileStr);
        for(String s: input) data.add(Long.valueOf(s));
    }

    public long part1() {
        int preambleLen = 25;
        Long result = 0L;
        for (int i = 0; i < data.size(); i++) {
            boolean found = false;
            if (i > preambleLen) {
                for (int j = i - preambleLen; j < i; j++) {
                    for (int k = i - preambleLen; k < i; k++) {
                        if (j != k && (data.get(j) + data.get(k) == data.get(i))) {
                            found = true;
                            break;
                        }
                        if (found) {
                            break;
                        }
                    }
                }
                if (!found) {
                    result = data.get(i);
                    break;
                }
            }
        }
        return result;
    }

    public long part2() {
        Long goal = this.part1();
        for (int i = 0; i < data.size()-1; i++){
            Long sum = data.get(i),
                min = data.get(i),
                max = data.get(i);
            for (int j = i+1; j < data.size(); j++){
                sum += data.get(j);
                min = Math.min(min, data.get(j));
                max = Math.max(max, data.get(j));
                if (sum > goal){
                    break;
                }
                if(sum.equals(goal)) {
                    return (min + max);
                }
            }
        }
        return 0;
    }
}
