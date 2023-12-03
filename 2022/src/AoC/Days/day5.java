package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class day5 extends Day {
    List<ArrayDeque<String>> stacks;
    List<String> instructionsList;
    public day5(String fileStr) {
        super(fileStr);

    }

    public String part1() throws Exception {
        reset();
        return move(true);
    }

    public String part2() throws Exception {
        reset();
        return move(false);
    }

    private String move(boolean moveOne){
        for(String instruction : instructionsList){
            String[] line = instruction.split(" ");
            int noBoxes = Integer.parseInt(line[1]),
                from = Integer.parseInt(line[3]),
                to = Integer.parseInt(line[5]);
            if(moveOne) {
                for (int i = 0; i < noBoxes; i++) {
                    String box = stacks.get(from - 1).removeLast();
                    stacks.get(to - 1).add(box);
                }
            } else {
                String[] boxes = new String[noBoxes];
                for (int i = 0; i < noBoxes; i++) {
                    boxes[i] = stacks.get(from - 1).removeLast();
                }
                for (int i = noBoxes; i > 0; i--) {
                    stacks.get(to - 1).add(boxes[i-1]);
                }
            }
        }

        StringBuilder res = new StringBuilder();
        for(ArrayDeque<String> stack: stacks){
            res.append(stack.removeLast());
        }

        return "Top crates: " + res;
    }

    public void reset(){
        int noStacks = input.get(input.indexOf("")-1).replaceAll(" ", "").length();

        instructionsList = new ArrayList<>();
        stacks = new ArrayList<>();

        for(int i = 0; i < noStacks; i++){
            stacks.add(new ArrayDeque<>());
        }

        boolean instructions = false;
        for (String line: input) {
            if (line.isEmpty()){
                instructions = true;
                continue;
            }
            if(instructions){
                instructionsList.add(line);
            }
            else {
                for (int i = 1; i < line.length(); i += 4) {
                    String crate = String.valueOf(line.charAt(i));
                    if(!crate.equals(" ") && !crate.matches("-?\\d+(\\.\\d+)?")) {
                        ArrayDeque<String> stack = stacks.get(i / 4);
                        stack.push(crate);
                    }
                }
            }
        }
    }
}
