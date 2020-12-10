package AoC.days;

import AoC.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day10 extends Day {
    private List<Integer> adapters = new ArrayList<>();
    private final int deviceJoltage;
    private int combinationCount = 0;
    public day10(String fileStr) {
        super(fileStr);
        for(String s: input) adapters.add(Integer.valueOf(s));
        adapters.add(0);
        Collections.sort(adapters);
        deviceJoltage = Collections.max(adapters) + 3;
        adapters.add(deviceJoltage);
    }

    public int part1() {
        int no1Jolt = 0, no3Jolt = 0, difference;
        for (int i = 1; i < adapters.size(); i++) {
            difference = adapters.get(i) - adapters.get(i - 1);
            if (difference == 1){
                no1Jolt++;
            } else if(difference == 3) {
                no3Jolt++;
            }
        }
        return no1Jolt * no3Jolt;
    }

    public Long part2() {
        int start = 0, end = deviceJoltage;
        Map<Integer, Adapter> adapterMap = adapters.stream().map(num -> new Adapter(num)).collect(Collectors.toMap(adapter -> adapter.jolts, adapter -> adapter));
        adapterMap.values().forEach(adapter -> adapter.setPossibleConnections(adapterMap));
        return adapterMap.get(0).getNumPossibilities();
    }

    private class Adapter {
        List<Adapter> possibleConnections = new ArrayList<>();
        Integer jolts;
        Long permutations;

        public Adapter(int jolts) {
            this.jolts = jolts;
        }

        private void setPossibleConnections(Map<Integer, Adapter> adapterMap) {
            IntStream.rangeClosed(1,3).forEach(index -> {
                if (adapterMap.containsKey(jolts + index)) {
                    possibleConnections.add(adapterMap.get(jolts + index));
                }
            });
        }
        public Long getNumPossibilities() {
            if(permutations != null){
                return permutations;
            }

            permutations = possibleConnections.stream().mapToLong(Adapter::getNumPossibilities).sum();

            if(possibleConnections.isEmpty()) {
                permutations = 1l;
            }

            return permutations;
        }
    }
}
