package AoC.Days;

import AoC.Helpers.Day;

import java.awt.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class day13 extends Day {
    LinkedList<String> instructions;
    HashSet<Point> points;
    public day13(String fileStr) {
        super(fileStr);

        instructions = new LinkedList<>();
        points = new HashSet<>();
        boolean isInstructions = false;

        for(String line: input){
            if(line.isBlank()){
                isInstructions = true;
                continue;
            }
            if(isInstructions){
                instructions.add(line);
            } else {
                String[] parts = line.split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        }
    }

    public String part1() throws Exception {
        LinkedList<Point> tempPoints = new LinkedList<>();
        char axis = instructions.get(0).charAt(11);
        int foldPoint = Integer.parseInt(instructions.get(0).substring(13));
        for(Point point : points){
            if(axis == 'y'){
                if(point.y < foldPoint){
                    tempPoints.add(point);
                } else {
                    point.y = foldPoint - (point.y - foldPoint);
                    tempPoints.add(point);
                }
            }
        }

        return "Visible dots after one fold: " + tempPoints.size();
    }

    public String part2() throws Exception {
        return super.part2();
    }

}
