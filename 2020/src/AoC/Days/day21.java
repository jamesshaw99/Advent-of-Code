package AoC.Days;

import AoC.Day;

import java.util.*;

public class day21 extends Day {
    private HashMap<String, String> translations = new HashMap<>();
    public day21(String fileStr) {
        super(fileStr);
        HashSet<String> uniqueAllergens = new HashSet<>();
        HashSet<String> uniqueIngredients = new HashSet<>();
        for(String line: input) {
            String[] ingredients = line.substring(0, line.indexOf("(") - 1).split(" ");
            uniqueIngredients.addAll(Arrays.asList(ingredients));
            String[] allergens = line.substring(line.indexOf("contains") + 9, line.length() - 1).split(", ");
            uniqueAllergens.addAll(Arrays.asList(allergens));
        }

        HashMap<String, ArrayList<String>> possibleMatches = new HashMap<>();
        for(String allergen: uniqueAllergens) {
            ArrayList<String> temp = new ArrayList<>(uniqueIngredients);
            for (String line : input) {
                if (line.contains(" " + allergen)) {
                    String[] ingredients = line.substring(0, line.indexOf("(") - 1).split(" ");
                    ArrayList<String> other = new ArrayList<>(Arrays.asList(ingredients));
                    temp.retainAll(other);
                }
            }
            possibleMatches.put(allergen, temp);
        }

        while(!possibleMatches.isEmpty()) {
            Iterator<Map.Entry<String, ArrayList<String>>> iterator = possibleMatches.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, ArrayList<String>> entry = iterator.next();
                ArrayList<String> ingredients = entry.getValue();
                if (ingredients.size() == 1) {
                    String allergen = entry.getKey();
                    String ingredient = ingredients.get(0);
                    translations.put(allergen, ingredient);
                    iterator.remove();

                    for(ArrayList<String> list: possibleMatches.values()) {
                        list.remove(ingredient);
                    }
                }
            }
        }
    }

    public int part1() {

        int count = 0;
        for (String line : input) {
            String[] ingredients = line.substring(0, line.indexOf("(") - 1).split(" ");
            for (String ingredient : ingredients) {
                if (!translations.containsValue(ingredient)) {
                    count++;
                }
            }
        }

        return count;
    }

    public String part2() {
        String[] cat = new String[ translations.size()];
        int i = 0;
        for (Map.Entry<String, String> entry: translations.entrySet()) {
            cat[i] = entry.getKey() + "/" + entry.getValue();
            i++;
        }

        Arrays.sort(cat);

        StringBuilder cdil = new StringBuilder();
        for(String str: cat) {
            cdil.append(str.substring(str.indexOf("/") + 1)).append(",");
        }
        cdil = new StringBuilder(cdil.substring(0, cdil.length() - 1));
        return cdil.toString();
    }
}
