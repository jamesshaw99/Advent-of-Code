import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class day7 {
    public static void main(String[] args) {
        List<String> rules = new ArrayList<>();
        File file = new File("inputs/day7.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;

            while ((text = reader.readLine()) != null) {
                rules.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<String>> targetToContents = new HashMap<>();
        List<String> bags = new ArrayList<>();
        for(String rule: rules) {
            String target = rule.substring(0, rule.indexOf("bags contain") - 1);
            List<String> targetContains = Arrays.stream(rule.substring(rule.indexOf("bags contain") + 13).replace(".","").replaceAll(" bag[s]?","").split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            targetToContents.put(target, targetContains);
            if (!target.equals("shiny gold"))
                bags.add(target);
        }

        int containsGold = 0;
        for (String bag: bags){
            Map<String, Integer> contents = getBagContents(targetToContents, bag, false);
            if (contents.containsKey("shiny gold"))
                containsGold++;
        }
        System.out.println(containsGold + " bags eventually contain at least one shiny gold");

        System.out.print("\n");

        System.out.println("Part 2");
        Map<String, Integer> contents = getBagContents(targetToContents, "shiny gold", true);
        int totalBags = 0;
        for (String key: contents.keySet()){
            totalBags += contents.get(key);
        }
        System.out.println("Total number of bags: " + totalBags);
    }

    public static Map<String, Integer> getBagContents(Map<String, List<String>> contentMap, String bag, boolean getValue) {
        Map<String, Integer> uniqueBags = new HashMap<>();
        Map<String, Integer> result = getBagContents(contentMap, bag, uniqueBags, getValue);
        return result;
    }

    public static Map<String, Integer> getBagContents(Map<String, List<String>> contentMap, String bag, Map<String, Integer> uniqueBags, boolean getValue) {
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
