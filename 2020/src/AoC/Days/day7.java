package AoC.Days;

import AoC.Day;

import java.util.*;
import java.util.stream.Collectors;

public class day7 extends Day {
    private final Map<String, List<String>> targetToContents = new HashMap<>();
    private final List<String> bags = new ArrayList<>();

    public day7(String fileStr) {
        super(fileStr);

        for(String rule: input) {
            String target = rule.substring(0, rule.indexOf("bags contain") - 1);
            List<String> targetContains = Arrays.stream(rule.substring(rule.indexOf("bags contain") + 13).replace(".","").replaceAll(" bag[s]?","").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            targetToContents.put(target, targetContains);
            if (!target.equals("shiny gold"))
                bags.add(target);
        }
    }

    public int part1() {
        int containsGold = 0;
        for (String bag : bags) {
            Map<String, Integer> contents = getBagContents(targetToContents, bag, false);
            if (contents.containsKey("shiny gold"))
                containsGold++;
        }
        return containsGold;
    }

    public int part2() {
        Map<String, Integer> contents = getBagContents(targetToContents, "shiny gold", true);
        int totalBags = 0;
        for (String key: contents.keySet()){
            totalBags += contents.get(key);
        }
        return totalBags;
    }

    public Map<String, Integer> getBagContents(Map<String, List<String>> contentMap, String bag, boolean getValue) {
        Map<String, Integer> uniqueBags = new HashMap<>();
        return getBagContents(contentMap, bag, uniqueBags, getValue);
    }

    public Map<String, Integer> getBagContents(Map<String, List<String>> contentMap, String bag, Map<String, Integer> uniqueBags, boolean getValue) {
        List<String> contents = contentMap.get(bag);
        if(!contents.get(0).equals("no other")) {
            for (String item : contents) {
                String bagColour = item.substring(item.indexOf(" ")).trim();
                int value = Integer.parseInt(item.substring(0, item.indexOf(" ")));
                if (!uniqueBags.containsKey(bagColour)) {
                    uniqueBags.put(bagColour, value);
                } else {
                    uniqueBags.put(bagColour, uniqueBags.get(bagColour) + value);
                }
                if(getValue) {
                    for (int i = 0; i < value; i++) {
                        uniqueBags = getBagContents(contentMap, bagColour, uniqueBags, true);
                    }
                } else
                    uniqueBags = getBagContents(contentMap, bagColour, uniqueBags, false);
            }
        }
        return uniqueBags;
    }
}
