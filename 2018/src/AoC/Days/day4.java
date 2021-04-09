package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class day4 extends Day {
    Map<Integer, int[]> guards = new HashMap<>();
    public day4(String fileStr) {
        super(fileStr);
        Collections.sort(input);
    }

    public String part1() {
        int currentGuard = 0;
        int startMin = 0;
        for(String record: input){
            int min = Integer.parseInt(record.substring(15,17));
            if(record.contains("shift")){
                String[] recordBits = record.split(" ");
                currentGuard = Integer.parseInt(recordBits[3].substring(1));
                if(!guards.containsKey(currentGuard)){
                    guards.put(currentGuard, new int[60]);
                    Arrays.fill(guards.get(currentGuard), 0);
                }
            } else if(record.contains("asleep")){
                startMin = min;
            } else if(record.contains("wake")){
                for(int i = startMin; i < min; i++) {
                    guards.get(currentGuard)[i]++;
                }
            }
        }

        List<Integer> keys = new ArrayList<>(guards.keySet());
        int bestGuard = keys.get(0);
        int bestTotalSleep = 0;
        for(int guard: keys) {
            int totalSleep = Arrays.stream(guards.get(guard)).filter(x -> x > 0).sum();
            if(totalSleep > bestTotalSleep) {
                bestTotalSleep = totalSleep;
                bestGuard = guard;
            }
        }
        int bestNo = 0;
        int bestMin = 0;
        for(int i = 0; i < 60; i++){
            if(guards.get(bestGuard)[i] > bestNo) {
                bestNo = guards.get(bestGuard)[i];
                bestMin = i;
            }
        }
        return "Best guard: " + bestGuard + "\nMinute: " + bestMin + "\nanswer = " + (bestGuard * bestMin);
    }

    public String part2() {
        List<Integer> keys = new ArrayList<>(guards.keySet());
        int bestGuard = keys.get(0);
        int bestMinute = 0;
        for (int id : keys) {
            int minute = Arrays.stream(guards.get(id)).max().orElse(-1);
            if(minute > bestMinute) {
                bestMinute = minute;
                bestGuard = id;
            }
        }

        int bestTime = 0;
        for(int i = 0; i < 60; i++) {
            if(guards.get(bestGuard)[i] == bestMinute) {
                bestTime = i;
            }
        }
        return "Best guard: " + bestGuard + "\nMinute: " + bestTime + "\nanswer = " + (bestGuard * bestTime);
    }
}
