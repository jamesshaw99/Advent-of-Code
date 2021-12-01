package AoC.Days;

import AoC.Helpers.Day;

public class day14 extends Day {
    int noRecipes;

    public day14(String fileStr) {
        super(fileStr);
        noRecipes = Integer.parseInt(input.get(0));
    }

    public String part1() {
        return "Scores of ten recipes after goal: " + makeRecipes(noRecipes).substring(noRecipes);
    }

    public String part2() {
        return "Number of recipes to get to input sequence: " + makeRecipes(21000000).indexOf(input.get(0));
    }

    private StringBuilder makeRecipes(int noRecipes) {
        StringBuilder recipeScores = new StringBuilder();
        int aScore = 3,
                aPos = 0,
                bScore = 7,
                bPos = 1;

        recipeScores.append(aScore);
        recipeScores.append(bScore);

        while(recipeScores.length() < noRecipes + 10){
            int sum = aScore + bScore;
            if (sum > 9) {
                recipeScores.append(sum / 10);
                recipeScores.append(sum % 10);
            } else {
                recipeScores.append(sum);
            }

            aPos = (aPos + (1 + aScore)) % recipeScores.length();
            bPos = (bPos + (1 + bScore)) % recipeScores.length();

            aScore = recipeScores.charAt(aPos) - '0';
            bScore = recipeScores.charAt(bPos) - '0';
        }

        return recipeScores;
    }
}
