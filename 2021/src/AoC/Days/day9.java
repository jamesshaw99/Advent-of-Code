package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class day9 extends Day {
    private int[][] heightmap;
    private ArrayList<Integer> nums = new ArrayList<>();

    public day9(String fileStr) {
        super(fileStr);
        heightmap = new int[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++){
            for(int x = 0; x < input.get(y).length(); x++){
                heightmap[y][x] = Integer.parseInt(String.valueOf(input.get(y).charAt(x)));
            }
        }
    }

    public String part1() {
        int risksum  = 0;

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                int num = heightmap[y][x];
                if (y == 0 && x == 0) {
                    if (heightmap[y][x + 1] > num && heightmap[y + 1][x] > num) {
                        risksum  += (1 + num);
                    }
                } else if (y == 0 && x == heightmap[y].length - 1) {
                    if (heightmap[y][x - 1] > num && heightmap[y + 1][x] > num) {
                        risksum  += (1 + num);
                    }
                } else if (y == heightmap.length - 1 && x == 0) {
                    if (heightmap[y][x + 1] > num && heightmap[y - 1][x] > num) {
                        risksum  += (1 + num);
                    }
                } else if (y == heightmap.length - 1 && x == heightmap[y].length - 1) {
                    if (heightmap[y][x - 1] > num && heightmap[y - 1][x] > num) {
                        risksum  += (1 + num);
                    }
                } else if (y == 0) {
                    if (heightmap[y][x - 1] > num && heightmap[y + 1][x] > num
                            && heightmap[y][x + 1] > num) {
                        risksum  += (1 + num);
                    }
                } else if (y == heightmap.length - 1) {
                    if (heightmap[y][x - 1] > num && heightmap[y - 1][x] > num
                            && heightmap[y][x + 1] > num) {
                        risksum  += (1 + num);
                    }
                } else if (x == 0) {
                    if (heightmap[y + 1][x] > num && heightmap[y - 1][x] > num
                            && heightmap[y][x + 1] > num) {
                        risksum  += (1 + num);
                    }
                } else if (x == heightmap.length - 1) {
                    if (heightmap[y + 1][x] > num && heightmap[y - 1][x] > num
                            && heightmap[y][x - 1] > num) {
                        risksum  += (1 + num);
                    }
                } else {
                    if (heightmap[y + 1][x] > num && heightmap[y - 1][x] > num
                            && heightmap[y][x - 1] > num && heightmap[y][x + 1] > num) {
                        risksum  += (1 + num);
                    }
                }
            }
        }
        return String.format("Total risk: %d", risksum);
    }

    public String part2() {
        for (int y = 0; y < input.size(); y++){
            for(int x = 0; x < input.get(y).length(); x++){
                heightmap[y][x] = Integer.parseInt(String.valueOf(input.get(y).charAt(x)));
            }
        }
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                if (y == 0 && x == 0) {
                    if (heightmap[y][x + 1] > heightmap[y][x] && heightmap[y + 1][x] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (y == 0 && x == heightmap[y].length - 1) {
                    if (heightmap[y][x - 1] > heightmap[y][x] && heightmap[y + 1][x] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (y == heightmap.length - 1 && x == 0) {
                    if (heightmap[y][x + 1] > heightmap[y][x] && heightmap[y - 1][x] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (y == heightmap.length - 1 && x == heightmap[y].length - 1) {
                    if (heightmap[y][x - 1] > heightmap[y][x] && heightmap[y - 1][x] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (y == 0) {
                    if (heightmap[y][x - 1] > heightmap[y][x] && heightmap[y + 1][x] > heightmap[y][x]
                            && heightmap[y][x + 1] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (y == heightmap.length - 1) {
                    if (heightmap[y][x - 1] > heightmap[y][x] && heightmap[y - 1][x] > heightmap[y][x]
                            && heightmap[y][x + 1] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (x == 0) {
                    if (heightmap[y + 1][x] > heightmap[y][x] && heightmap[y - 1][x] > heightmap[y][x]
                            && heightmap[y][x + 1] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else if (x == heightmap.length - 1) {
                    if (heightmap[y + 1][x] > heightmap[y][x] && heightmap[y - 1][x] > heightmap[y][x]
                            && heightmap[y][x - 1] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                } else {
                    if (heightmap[y + 1][x] > heightmap[y][x] && heightmap[y - 1][x] > heightmap[y][x]
                            && heightmap[y][x - 1] > heightmap[y][x] && heightmap[y][x + 1] > heightmap[y][x]) {
                        findbasin(y, x, heightmap);
                    }
                }
            }
        }
        nums.sort(Collections.reverseOrder());

        return String.format("product of the three largest basins: %d", nums.get(0) * nums.get(1) * nums.get(2));
    }

    private void findbasin(int origY, int origX, int[][] heightMap) {
        Stack<String> s = new Stack<String>();
        Set<String> set = new HashSet<String>();
        int x, y, count;
        s.push((origY + "," + origX));
        set.add((origY + "," + origX));
        count = 0;
        while (s.size() > 0) {
            String pop = s.pop();
            x = Integer.parseInt(pop.substring(0, pop.indexOf(",")));
            y = Integer.parseInt(pop.substring(pop.indexOf(",") + 1));
            if (y - 1 >= 0 && heightMap[x][y - 1] != 9) {
                int size = set.size();
                set.add((x) + "," + (y - 1));
                if (size != set.size()) {
                    s.push((x) + "," + (y - 1));
                }
            }
            if (y + 1 < heightMap[x].length && heightMap[x][y + 1] != 9) {
                int size = set.size();
                set.add((x) + "," + (y + 1));
                if (size != set.size()) {
                    s.push((x) + "," + (y + 1));
                }
            }
            if (x - 1 >= 0 && heightMap[x - 1][y] != 9) {
                int size = set.size();
                set.add((x - 1) + "," + (y));
                if (size != set.size()) {
                    s.push((x - 1) + "," + (y));
                }
            }
            if (x + 1 < heightMap.length && heightMap[x + 1][y] != 9) {
                int size = set.size();
                set.add((x + 1) + "," + (y));
                if (size != set.size()) {
                    s.push((x + 1) + "," + (y));
                }
            }
            if (heightMap[x][y] == 9) {
                count--;
            }
            count++;
        }
        nums.add(count);
    }
}
