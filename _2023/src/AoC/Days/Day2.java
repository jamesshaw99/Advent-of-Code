package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 extends Day {
    List<Game> games = new ArrayList<>();

    public Day2(String fileStr) {
        super(fileStr);
        for (String line : input) {
            String[] split = line.split(":");
            int gameNo = Integer.parseInt(split[0].replace("Game ", ""));
            Game game = new Game(gameNo);
            for (String hand : split[1].split(";")) {
                game.update(extractColorCount(hand, "red"), extractColorCount(hand, "green"), extractColorCount(hand, "blue"));
            }
            games.add(game);
        }
    }

    public String part1() {
        int idSum = games.stream()
                .filter(game -> game.getRed() <= 12 && game.getGreen() <= 13 && game.getBlue() <= 14)
                .mapToInt(Game::getGameNo)
                .sum();
        return "Sum of possible games: " + idSum;
    }

    public String part2() {
        int power = games.stream().mapToInt(Game::getPower).sum();
        return "Sum of each games power: " + power;
    }

    private static int extractColorCount(String input, String color) {
        Pattern pattern = Pattern.compile("(\\d+)\\s+" + color);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    private static class Game {
        private final int gameNo;
        private int red, blue, green;

        public Game(int gameNo) {
            this.gameNo = gameNo;
        }

        public void update(int newRed, int newGreen, int newBlue) {
            this.red = Math.max(this.red, newRed);
            this.green = Math.max(this.green, newGreen);
            this.blue = Math.max(this.blue, newBlue);
        }

        public int getGameNo() {
            return gameNo;
        }

        public int getRed() {
            return red;
        }

        public int getBlue() {
            return blue;
        }

        public int getGreen() {
            return green;
        }

        public int getPower() {
            return red * green * blue;
        }
    }
}