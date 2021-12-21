package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;

public class day10 extends Day {
    private String OPEN = "([{<", CLOSE = ")]}>";
    private final Map<Character, Long> syntaxCheckValueScores = new HashMap<>();
    private final Map<Character, Character> chunkMap = new HashMap<>();
    private final Map<Character, Long> autocompleteValueScores = new HashMap<>();
    private List<Stack<Character>> incompleteLines = new ArrayList<>();

    public day10(String fileStr) {
        super(fileStr);
        syntaxCheckValueScores.put(')', 3L);
        syntaxCheckValueScores.put(']', 57L);
        syntaxCheckValueScores.put('}', 1197L);
        syntaxCheckValueScores.put('>', 25137L);
        chunkMap.put('(', ')');
        chunkMap.put('[', ']');
        chunkMap.put('{', '}');
        chunkMap.put('<', '>');
        autocompleteValueScores.put(')', 1L);
        autocompleteValueScores.put(']', 2L);
        autocompleteValueScores.put('}', 3L);
        autocompleteValueScores.put('>', 4L);
    }

    public String part1() {
        long points = 0L;
        start:
        for(String line: input){
            Stack<Character> stack = new Stack<>();
            for (char c: line.toCharArray()){
                if(chunkMap.containsKey(c)){
                    stack.push(c);
                } else {
                    Character open = stack.pop();
                    if (!chunkMap.get(open).equals(c)){
                        points += syntaxCheckValueScores.get(c);
                        continue start;
                    }
                }
            }
            incompleteLines.add(stack);
        }
        return String.format("Syntax error score: %d", points);
    }

    public String part2() {
        List<Long> scores = new ArrayList<>();
        for (Stack<Character> line: incompleteLines) {
            long points = 0L;
            while(!line.isEmpty()){
                char open = line.pop();
                char close = chunkMap.get(open);
                points *= 5;
                points += autocompleteValueScores.get(close);
            }
            scores.add(points);
        }
        Collections.sort(scores);
        return String.format("Middle score: %d", scores.get(scores.size()/2));
    }
}
