package AoC.Days;

import AoC.Day;

import java.util.*;

public class day22 extends Day {
    private final List<Integer> playerStart = new ArrayList<>();
    private final List<Integer> crabStart = new ArrayList<>();

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

    public int part1() {
        List<Integer> player = new ArrayList<>(playerStart);
        List<Integer> crab = new ArrayList<>(crabStart);
        while(player.size() > 0 && crab.size() > 0) {
            int playerPlays = player.remove(0);
            int crabPlays = crab.remove(0);
            if (playerPlays > crabPlays){
                player.add(playerPlays);
                player.add(crabPlays);
            } else {
                crab.add(crabPlays);
                crab.add(playerPlays);
            }
        }

        int score = 0;
        if (player.size() > 0) {
            for (int i = player.size(); i > 0; i--) {
                score += i * player.remove(0);
            }
        } else {
            for (int i = crab.size(); i > 0; i--) {
                score += i * crab.remove(0);
            }
        }
        return score;
    }

    public int part2() {
        List<Integer> recursivePlayer = new ArrayList<>(playerStart);
        List<Integer> recursiveCrab = new ArrayList<>(crabStart);
        List<Integer> cardsToCount = new ArrayList<>();

        boolean playerWon = recursiveCombat(recursivePlayer, recursiveCrab);

        int score = 0;
        if (playerWon) {
            cardsToCount.addAll(recursivePlayer);
        } else {
            cardsToCount.addAll(recursiveCrab);
        }
        for(int i = 0; i < cardsToCount.size(); i++)
        {
            score += cardsToCount.get(i) * (cardsToCount.size() - i);
        }
        return score;
    }


    private boolean recursiveCombat(List<Integer> playersDeck, List<Integer> crabsDeck) {
        Map<String, String> prevDecks = new HashMap<>();
        while(playersDeck.size() > 0 && crabsDeck.size() > 0)
        {
            String playerDeckStr = playersDeck.toString();
            String crabDeckStr = playersDeck.toString();
            if(prevDecks.containsKey(playerDeckStr) && prevDecks.get(playerDeckStr).equals(crabDeckStr))
                return true;

            prevDecks.put(playerDeckStr, crabDeckStr);
            int playerCard = playersDeck.remove(0);
            int crabsCard = crabsDeck.remove(0);
            boolean playerWon = playerCard > crabsCard;
            if(playerCard <= playersDeck.size() && crabsCard <= crabsDeck.size())
                playerWon = recursiveCombat(new ArrayList<>(playersDeck.subList(0, playerCard)), new ArrayList<>(crabsDeck.subList(0, crabsCard)));

            if(playerWon)
            {
                playersDeck.add(playerCard);
                playersDeck.add(crabsCard);
            }
            else
            {
                crabsDeck.add(crabsCard);
                crabsDeck.add(playerCard);
            }
        }

        return playersDeck.size() > 0;
    }

}
