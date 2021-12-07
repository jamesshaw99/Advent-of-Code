package AoC.Days;

import AoC.ConwayCubes;
import AoC.Day;

public class day17 extends Day {
    private String[] lines;

    public day17(String fileStr) {
        super(fileStr);
        lines = input.toArray(new String[0]);
    }

    public int part1() {
        ConwayCubes conwayCubes = ConwayCubes.from(lines, 3);
        conwayCubes.transitionToNextState(6);
        return conwayCubes.activeCubesCount();
    }

    public int part2() {
        ConwayCubes conwayCubes = ConwayCubes.from(lines, 4);
        conwayCubes.transitionToNextState(6);
        return conwayCubes.activeCubesCount();
    }
}
