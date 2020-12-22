package AoC.days;

import AoC.Day;

import java.util.*;

public class day22 extends Day {
    private final List<Integer> playerStart = new ArrayList<>();
    private final List<Integer> crabStart = new ArrayList<>();
    private int level = 0;

    public day22(String fileStr) {
        super(fileStr);
        String currentPlayer = "";
        for(String line: input) {
            if(line.equals("Player 1:")){
                currentPlayer = "player";
            } else if (line.equals("Player 2:")){
                currentPlayer = "crab";
            } else if(!line.isEmpty()){
                if (currentPlayer.equals("player")){
                    playerStart.add(Integer.parseInt(line));
                } else {
                    crabStart.add(Integer.parseInt(line));
                }
            }
        }
    }

    public int part1(boolean printResult) {
        List<Integer> player = new ArrayList<>(playerStart);
        List<Integer> crab = new ArrayList<>(crabStart);
        int round = 1;
        while(player.size() > 0 && crab.size() > 0) {
            if(printResult) {
                System.out.format("-- Round %d --\n", round);
                System.out.println("Player's deck: " + player.toString().replaceAll("\\[|\\]", ""));
                System.out.println("Crab's deck: " + crab.toString().replaceAll("\\[|\\]", ""));
            }
            int playerPlays = player.remove(0);
            int crabPlays = crab.remove(0);
            if(printResult) {
                System.out.println("Player plays: " + playerPlays);
                System.out.println("Crab plays: " + crabPlays);
            }
            if (playerPlays > crabPlays){
                if(printResult) System.out.println("Player wins the round!");
                player.add(playerPlays);
                player.add(crabPlays);
            } else {
                if(printResult) System.out.println("Crab wins the round!");
                crab.add(crabPlays);
                crab.add(playerPlays);
            }
            if(printResult) System.out.print("\n");
            round++;
        }

        if (printResult) {
            System.out.println("== Post-game results ==");
            System.out.println("Player's deck: " + player.toString().replaceAll("\\[|\\]", ""));
            System.out.println("Crab's deck: " + crab.toString().replaceAll("\\[|\\]", ""));
        }
        int score = 0;
        if (player.size() > 0) {
            for (int i = player.size(); i > 0; i--) {
                score += i * player.remove(0);
            }
            if(printResult) System.out.println("Player wins! Score: " + score);
        } else {
            for (int i = crab.size(); i > 0; i--) {
                score += i * crab.remove(0);
            }
            if(printResult) System.out.println("Crab wins! Score: " + score);
        }
        return score;
    }

    public int part2() {
        List<Integer> recursivePlayer = new ArrayList<>(playerStart);
        List<Integer> recursiveCrab = new ArrayList<>(crabStart);

        boolean playerWon = recursiveCombat(recursivePlayer, recursiveCrab);
        System.out.println(playerWon);

        int score = 0;
        if (playerWon) {
            for (int i = recursivePlayer.size(); i > 0; i--) {
                score += i * recursivePlayer.remove(0);
            }
        } else {
            for (int i = recursiveCrab.size(); i > 0; i--) {
                score += i * recursiveCrab.remove(0);
            }
        }
        return score;
    }


    private boolean recursiveCombat(List<Integer> recursivePlayer, List<Integer> recursiveCrab) {
        Map<String, String> prevDecks = new HashMap<>();

        while(recursivePlayer.size() > 0 && recursiveCrab.size() > 0) {
            String recursivePlayerStr = recursivePlayer.toString();
            String recursiveCrabStr = recursiveCrab.toString();
            if(prevDecks.containsKey(recursivePlayerStr) && prevDecks.get(recursivePlayerStr).equals(recursiveCrabStr)){
                System.out.println("Tie!");
                return true;
            }
            prevDecks.put(recursivePlayerStr, recursiveCrabStr);
            int playerPlays = recursivePlayer.remove(0);
            int crabPlays = recursiveCrab.remove(0);
            boolean playerWon = playerPlays > crabPlays;
            if(playerPlays <= recursivePlayer.size() && crabPlays <= recursiveCrab.size()) {
                playerWon = recursiveCombat(new ArrayList<>(recursivePlayer.subList(0, playerPlays)), new ArrayList<>(recursiveCrab.subList(0, crabPlays)));

            }
            if (playerWon) {
                recursivePlayer.add(playerPlays);
                recursivePlayer.add(crabPlays);
            } else {
                recursiveCrab.add(crabPlays);
                recursiveCrab.add(playerPlays);
            }
        }
        return recursivePlayer.size() > 0;
    }

}
