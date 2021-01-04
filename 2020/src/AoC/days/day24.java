package AoC.days;

import AoC.Day;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class day24 extends Day {
    private Map<String, Point> coords = new HashMap<>();
    private Set<Point> blackTiles = new HashSet<>();

    public day24(String fileStr) {
        super(fileStr);
        coords.put("ne", new Point(1, 2));
        coords.put("nw", new Point(-1, 2));
        coords.put("se", new Point(1, -2));
        coords.put("sw", new Point(-1, -2));
        coords.put("e", new Point(2, 0));
        coords.put("w", new Point(-2, 0));
    }

    public int part1() {
        for(String line: input) {
            int x = 0, y = 0,  i = 0;
            while (i < line.length()){
                for(String key: coords.keySet()){
                    if (line.startsWith(key, i)){
                        x += coords.get(key).x;
                        y += coords.get(key).y;
                        i += key.length();
                        break;
                    }
                }
            }
            Point tile = new Point(x, y);
            if (blackTiles.contains(tile)){
                blackTiles.remove(tile);
            } else {
                blackTiles.add(tile);
            }
        }
        return blackTiles.size();
    }

    public int part2() {
        for (int i = 0; i < 100; i++) {
            Set<Point> toDiscard = new HashSet<>();
            Set<Point> toAdd = new HashSet<>();
            Map<Point, Integer> seenWhiteTiles = new HashMap<>();
            for (Point tile: blackTiles) {
                int count = 0;
                for(String key: coords.keySet()){
                    Point point = new Point(tile.x + coords.get(key).x, tile.y + coords.get(key).y);
                    if (blackTiles.contains(point)){
                        count++;
                    } else {
                        if (seenWhiteTiles.containsKey(point)){
                            seenWhiteTiles.put(point, seenWhiteTiles.get(point)+1);
                        } else {
                            seenWhiteTiles.put(point, 1);
                        }
                    }
                }
                if (count == 0 || count > 2) {
                    toDiscard.add(tile);
                }
            }
            for (Point point: seenWhiteTiles.keySet()){
                if (seenWhiteTiles.get(point) == 2) {
                    toAdd.add(point);
                }
            }
            blackTiles.removeAll(toDiscard);
            blackTiles.addAll(toAdd);
        }
        return blackTiles.size();
    }
}
