package AoC.Days;

import AoC.Helpers.Day;

import java.awt.*;
import java.util.HashMap;

public class day6 extends Day {
    HashMap<Integer, Point> points = new HashMap<>();
    HashMap<Integer, Integer> regions = new HashMap<Integer, Integer>();
    int maxx = 0,
        maxy = 0,
        count = 0;
    int[][] grid;
    public day6(String fileStr) {
        super(fileStr);

        for(String coord: input) {
            String s[] = coord.trim().split(", ");
            int x = Integer.parseInt(s[0]),
                    y = Integer.parseInt(s[1]);
            points.put(count, new Point(x, y));
            count++;
            if(x > maxx) {
                maxx = x;
            }
            if(y> maxy) {
                maxy = y;
            }
        }
        grid = new int[maxx + 1][maxy + 1];
    }

    public String part1() {
        for(int x = 0; x <= maxx; x++){
            for(int y = 0; y <= maxy; y++){
                int best = maxx + maxy,
                    bestnum = -1;

                for (int i = 0; i < count; i++) {
                    Point p = points.get(i);

                    int dist = Math.abs(x - p.x) + Math.abs(y - p.y);
                    if (dist < best) {
                        best = dist;
                        bestnum = i;
                    } else if (dist == best) {
                        bestnum = -1;
                    }
                }

                grid[x][y] = bestnum;
                Integer total = regions.get(bestnum);
                if (total == null) {
                    total = 1;
                } else {
                    total = total + 1;
                }
                regions.put(bestnum, total);

            }
        }

        for (int x = 0; x <= maxx; x++) {
            int bad = grid[x][0];
            regions.remove(bad);
            bad = grid[x][maxy];
            regions.remove(bad);
        }
        for (int y = 0; y <= maxy; y++) {
            int bad = grid[0][y];
            regions.remove(bad);
            bad = grid[maxx][y];
            regions.remove(bad);
        }

        // find biggest
        int biggest = 0;
        for (int size : regions.values()) {
            if (size > biggest) {
                biggest = size;
            }
        }

        return "Biggest: " + biggest;
    }

    public String part2() {
        int inarea = 0;

        for (int x = 0; x <= maxx; x++) {
            for (int y = 0; y <= maxy; y++) {

                int size = 0;
                for (int i = 0; i < count; i++) {
                    Point p = points.get(i);
                    int dist = Math.abs(x - p.x) + Math.abs(y - p.y);
                    size += dist;
                }

                if (size < 10000) {
                    inarea++;
                }

            }
        }

        return "Area Size: " + inarea;
    }
}
