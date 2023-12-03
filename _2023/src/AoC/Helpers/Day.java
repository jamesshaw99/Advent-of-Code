package AoC.Helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day {
    protected List<String> input;

    public Day(String fileStr) {
        try {
            input = Files.readAllLines(Path.of(fileStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String part1() throws Exception {
        return "Not completed yet";
    }

    public String part2() throws Exception {
        return "Not completed yet";
    }
}