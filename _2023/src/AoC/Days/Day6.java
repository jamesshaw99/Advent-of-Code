package AoC.Days;

import AoC.Helpers.Day;

import java.util.Arrays;

public class Day6 extends Day {
    long[] time;
    long[] distance;

    public Day6(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        time = Arrays.stream(input.get(0).replace("Time: ", "").trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
        distance = Arrays.stream(input.get(1).replace("Distance: ", "").trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
        long result = calculateWaysToBeatRecord(time, distance);

        return "Total number of ways to win: " + result;
    }

    public String part2() {
        time = Arrays.stream(input.get(0).replace("Time: ", "").replaceAll("\\s+", "").split(" ")).mapToLong(Long::parseLong).toArray();
        distance = Arrays.stream(input.get(1).replace("Distance: ", "").replaceAll("\\s+", "").split(" ")).mapToLong(Long::parseLong).toArray();
        long result = calculateWaysToBeatRecord(time, distance);

        return "Total number of ways to win: " + result;
    }

    private static long calculateWaysToBeatRecord(long[] time, long[] distance) {
        long totalWays = 1;

        for (int i = 0; i < time.length; i++) {
            long waysToBeatRecord = calculateWaysForRace(time[i], distance[i]);
            totalWays *= waysToBeatRecord;
        }

        return totalWays;
    }

    private static long calculateWaysForRace(long raceTime, long recordDistance) {
        long waysToBeatRecord = 0;

        for (long holdTime = 0; holdTime < raceTime; holdTime++) {
            long boatSpeed = holdTime;
            long remainingTime = raceTime - holdTime;
            long totalDistance = boatSpeed * remainingTime;

            if (totalDistance > recordDistance) {
                waysToBeatRecord++;
            }
        }

        return waysToBeatRecord;
    }
}
