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

    public <T> T part1() throws Exception {
        return (T)"Not completed yet";
    }

    public <T> T part2() throws Exception {
        return (T)"Not completed yet";
    }
}