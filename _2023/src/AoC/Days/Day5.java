package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends Day {
    long[] seeds;
    List<long[]>[] maps;

    public Day5(String fileStr) {

        super(fileStr);
        parseInput();
    }

    public String part1() {
        long location = Long.MAX_VALUE;
        for (long seed : seeds) {
            location = Math.min(location, calculateLocation(seed));
        }
        return "Lowest location: " + location;
    }

    public String part2() {
        long location = Long.MAX_VALUE;
        for (int i = 0; i < seeds.length; i=i+2) {
            for(long j = 0; j <= seeds[i+1]; j++){
                location = Math.min(location, calculateLocation(seeds[i]+j));
            }
        }
        return "Lowest location: " + location;
    }

    private long calculateLocation(long seed) {
        long location = seed;

        for (int j = 0; j < 7; j++) {
            List<long[]> map = maps[j];
            for (long[] values : map) {
                long minValue = values[1];
                long maxValue = minValue + values[2];
                if (location >= minValue && location < maxValue) {
                    location = values[0] + (location - minValue);
                    break;
                }
            }
        }
        return location;
    }

    private void parseInput() {
        maps = new List[7]; // Initialize an array to store maps for each section

        int section = 0;
        for (String line : input) {
            if (line.isBlank()){
                section++;
                continue;
            }
            if(line.contains("map")) {
                continue;
            }

            if (section == 0) {
                line = line.substring(7);
                seeds = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            } else {
                long[] values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
                maps[section - 1] = maps[section - 1] != null ? maps[section - 1] : new ArrayList<>();
                maps[section - 1].add(values);
            }
        }
    }
}