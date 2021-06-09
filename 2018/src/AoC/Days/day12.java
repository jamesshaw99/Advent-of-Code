package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;

public class day12 extends Day {
    private String initial;
    private final List<String> growList = new ArrayList<>();
    int zero;

    public day12(String fileStr) {
        super(fileStr);
        initial = "..." + input.get(0).split(" ")[2];
        //System.out.println("[0]: " + initial);
        for(int i = 2; i < input.size(); i++){
            String rule[] = input.get(i).split(" ");
            if(rule[2].equals("#")){
                growList.add(rule[0]);
            }
        }
    }

    public String part1() {
        int sum = part1Sum();
        return "Total plants after 20 generations: " + sum;
    }

    public String part2() {
        long sum = 0L;

        for(int i = 0; i < 4; i++){
            sum = part1Sum();
        }

        int last = 0, diff = 0;

        for(int i = 0; i < 3; i++) {
            int next = part1Sum();
            diff = next - last;
            last = next;
        }
        sum += ((50_000_000_000L - 100) /20) * diff;
        return "After 50000000000 iterations: " + sum;
    }

    private Integer part1Sum() {
        for(int i = 0; i < 20; i++) {
            String last = "....." + initial + "...";
            String next = "";
            for (int j = 2; j < last.length() - 2; j++) {
                if (growList.contains(last.substring(j - 2, j + 3))) {
                    next += "#";
                } else {
                    next += ".";
                }

            }
            initial = next.substring(3);
            //System.out.println("[" + (i+1) + "]: " + initial);
        }
        int sum = 0;
        for (int i = 0; i < initial.length(); i++) {
            sum += initial.charAt(i) == '#' ? i - 3 : 0;
        }
        return sum;
    }
}
