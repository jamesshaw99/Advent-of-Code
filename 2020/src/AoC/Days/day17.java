package AoC.Days;

import AoC.Helpers.ConwayCubes;
import AoC.Helpers.Day;

public class day17 extends Day {
    private String[] lines;

    public day17(String fileStr) {
        super(fileStr);
        lines = input.toArray(new String[0]);
    }

    public Integer part1() {
        ConwayCubes conwayCubes = ConwayCubes.from(lines, 3);
        conwayCubes.transitionToNextState(6);
        return conwayCubes.activeCubesCount();
    }

    public Integer part2() {
        ConwayCubes conwayCubes = ConwayCubes.from(lines, 4);
        conwayCubes.transitionToNextState(6);
        return conwayCubes.activeCubesCount();
    }
}
