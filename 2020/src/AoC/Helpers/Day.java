package AoC.Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day {
    protected List<String> input = new ArrayList<>();

    public Day (String fileStr) {
        File file = new File(fileStr);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;

            while ((text = reader.readLine()) != null) {
                input.add(text);
            }
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
