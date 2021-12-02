package AoC.Days;

import AoC.Helpers.Day;

public class day2 extends Day {
    public day2(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        int horizontal = 0, depth = 0;
        for(String command: input){
            String[] commands = command.split(" ");
            int value = Integer.parseInt(commands[1]);
            switch(commands[0]) {
                case "forward":
                    horizontal += value;
                    break;
                case "down":
                    depth += value;
                    break;
                case "up":
                    depth -= value;
                    break;
            }
        }
        return String.format("Horizontal: %1$s, depth: %2$s, total: %3$s", horizontal, depth, horizontal*depth);
    }

    public String part2() {
        int horizontal = 0, depth = 0, aim = 0;
        for(String command: input) {
            String[] commands = command.split(" ");
            int value = Integer.parseInt(commands[1]);
            switch(commands[0]) {
                case "forward":
                    horizontal += value;
                    depth += value*aim;
                    break;
                case "down":
                    aim += value;
                    break;
                case "up":
                    aim -= value;
                    break;
            }
        }
        return String.format("Horizontal: %1$s, depth: %2$s, total: %3$s", horizontal, depth, horizontal*depth);
    }
}
