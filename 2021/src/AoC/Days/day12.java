package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class day12 extends Day {
    private final Map<String, Cave> caveMap = new HashMap<>();
    private Cave start = null;
    private Cave end = null;

    public day12(String fileStr) {
        super(fileStr);
        for(String line: input) {
            addData(line.split("-"));
        }
    }

    public String part1() {
        return String.format("Total number of paths: %d", walk(new ArrayList<>(), start));
    }

    public String part2() {
        return String.format("Total number of paths: %d", walkWithRepeat(new ArrayList<>(), start, false));
    }

    private void addData(String[] nodes) {
        Cave firstCave = createCave(nodes[0]);
        Cave secondCave = createCave(nodes[1]);
        firstCave.addCave(secondCave);
        secondCave.addCave(firstCave);

        if(nodes[0].equals("start") && start == null){
            start = firstCave;
        } else if (nodes[1].equals("start") && start == null){
            start = secondCave;
        }

        if(nodes[1].equals("end") && end == null) {
            end = secondCave;
        }
    }

    private Cave createCave(String value){
        if(caveMap.containsKey(value)){
            return caveMap.get(value);
        } else {
            Cave cave;
            if(value.equals(value.toLowerCase())){
                cave = new SmallCave(value);
            } else {
                cave = new BigCave(value);
            }
            caveMap.put(value, cave);
            return cave;
        }
    }

    private int walk(List<Cave> visited, Cave current) {
        if(current.getValue().equals("end")) {
            return 1;
        } else if(current.isSmall() && visited.contains(current)) {
            return 0;
        }

        visited.add(current);
        int sum = 0;
        for(Cave cave: current.getLinked()){
            List<Cave> newlyVisited = new ArrayList<>(visited);
            sum += walk(newlyVisited, cave);
        }
        return sum;
    }

    private int walkWithRepeat(List<Cave> visited, Cave current, boolean usedDouble) {
        if(current.getValue().equals("end")){
            return 1;
        } else if (current.isSmall() && visited.contains(current) && usedDouble || (visited.contains(current) && current.getValue().equals("start"))){
            return 0;
        } else if (current.isSmall() && visited.contains(current) && !current.getValue().equals("start")){
            usedDouble = true;
        }

        visited.add(current);
        int sum = 0;
        for(Cave cave: current.getLinked()) {
            List<Cave> newlyVisited = new ArrayList<>(visited);
            sum += walkWithRepeat(newlyVisited, cave, usedDouble);
        }
        return sum;
    }

    private static class Cave {
        protected final String value;
        protected final Map<String, Cave> links;
        protected boolean multipleVisits;

        public Cave(String value){
            this.value = value;
            links = new HashMap<>();
        }

        public boolean isSmall() {
            return !multipleVisits;
        }

        public String getValue() {
            return this.value;
        }

        public void addCave(Cave cave) {
            if(!links.containsKey(cave.getValue())){
                links.put(cave.getValue(), cave);
            }
        }

        public Collection<Cave> getLinked() {
            return this.links.values();
        }
    }

    private static class BigCave extends Cave {
        public BigCave(String value){
            super(value);
            this.multipleVisits = true;
        }
    }

    private static class SmallCave extends Cave {
        public SmallCave(String value) {
            super(value);
            this.multipleVisits = false;
        }
    }
}
