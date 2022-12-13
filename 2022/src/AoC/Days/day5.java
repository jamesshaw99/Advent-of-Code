package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day5 extends Day {
    String[] stacks = {"","",""};
    List<String> instructionsList = new ArrayList<>();
    public day5(String fileStr) {
        super(fileStr);

        boolean instructions = false;
        for (String line: input) {
            if (line.equals("")){
                instructions = true;
                continue;
            }
            if(instructions){
                instructionsList.add(line);
            }
            else {
                for (int i = 1; i < line.length(); i += 4) {
                    String crate = String.valueOf(line.charAt(i));
                    System.out.println(crate);
                    if(!crate.equals(" ") && !crate.matches("-?\\d+(\\.\\d+)?")) {
                        stacks[i / 4] += crate;
                    }
                }
            }
        }
    }

}
