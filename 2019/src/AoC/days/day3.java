package AoC.days;

import AoC.helpers.Day;

import java.util.*;

public class day3 extends Day {
    private final List<List<String>> lines = new ArrayList<>();
    private int closestDistance = Integer.MAX_VALUE;
    private int shortestWire = Integer.MAX_VALUE;

    public day3(String fileStr) {
        super(fileStr);
        for (String line: input) {
            String[] values = line.split(",");
            lines.add(Arrays.asList(values));
        }
    }

    public static int[] getDir(char c) {
        switch(c){
            case 'U':
                return new int[] {0,1};
            case 'D':
                return new int[] {0,-1};
            case 'L':
                return new int[] {-1,0};
            case 'R':
                return new int[] {1,0};
        }
        return null;
    }

    public String part1() {
        Map<String, Integer> wire = new HashMap<>();
        String[] input = lines.get(0).toArray(new String[0]);

        int x = 0, y = 0, d = 0;

        for (String s : input) {
            int[] dir = getDir(s.charAt(0));
            int len = Integer.parseInt(s.substring(1));
            for (int j = 0; j < len; j++) {
                assert dir != null;
                int newX = x + dir[0];
                int newY = y + dir[1];
                wire.put(newX + "_" + newY, ++d);
                x = newX;
                y = newY;
            }
        }

        input = lines.get(1).toArray(new String[0]);
        x = y = d = 0;

        for (String s : input) {
            int[] dir = getDir(s.charAt(0));
            int len = Integer.parseInt(s.substring(1));
            for (int j = 0; j < len; j++) {
                assert dir != null;
                int newX = x + dir[0];
                int newY = y + dir[1];
                d++;

                if (wire.containsKey(newX + "_" + newY)) {
                    closestDistance = Math.min(closestDistance, Math.abs(newX) + Math.abs(newY));
                    shortestWire = Math.min(shortestWire, wire.get(newX + "_" + newY) + d);
                }
                x = newX;
                y = newY;
            }
        }

        return Integer.toString(closestDistance);
    }

    public String part2(){
        return Integer.toString(shortestWire);
    }
}
