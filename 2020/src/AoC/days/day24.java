package AoC.days;

import AoC.Day;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day24 extends Day {
    Map<String, Point> coords = new HashMap<>();
    List<Point> tiles = new ArrayList<>();

    public day24(String fileStr) {
        super(fileStr);
        coords.put("ne", new Point(1, 1));
        coords.put("nw", new Point(-1, 1));
        coords.put("se", new Point(1, -1));
        coords.put("sw", new Point(-1, -1));
        coords.put("e", new Point(2, 0));
        coords.put("w", new Point(-2, 0));
    }

    public int part1() {
        for (String tile: input) {
            Point location = new Point(0,0);
            for (int i = 0; i < tile.length(); i++) {
                switch (tile.charAt(i)) {
                    case 'e':
                        location.translate(coords.get("e").x, coords.get("e").y);
                        break;
                    case 'w':
                        location.translate(coords.get("w").x, coords.get("w").y);
                        break;
                    case 's':
                        if (tile.charAt(i + 1) == 'e') {
                            location.translate(coords.get("se").x, coords.get("se").y);
                        } else {
                            location.translate(coords.get("sw").x, coords.get("sw").y);
                        }
                        i++;
                        break;
                    case 'n':
                        if (tile.charAt(i + 1) == 'e') {
                            location.translate(coords.get("ne").x, coords.get("ne").y);
                        } else {
                            location.translate(coords.get("nw").x, coords.get("nw").y);
                        }
                        i++;
                        break;
                }
            }
            if (tiles.contains(location)){
                tiles.remove(location);
            } else {
                tiles.add(location);
            }
        }
        return tiles.size();
    }

    public String part2() {
        return "";
    }
}
