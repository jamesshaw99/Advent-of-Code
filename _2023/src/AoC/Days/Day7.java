package AoC.Days;

import AoC.Helpers.Day;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 extends Day {
    List<Hand> hands = new ArrayList<>();
    public Day7(String fileStr) {
        super(fileStr);
        for (String line : input) {
            String[] parts = line.split(" ");
            String handString = parts[0];
            int bid = Integer.parseInt(parts[1]);
            hands.add(new Hand(handString, bid));
        }
    }

    public String part1() {
        return "Total Winnings: " + calculateWinnings();
    }

    public String part2() {
        hands.forEach(Hand::updateType);
        return "Total Winnings: " + calculateWinnings();
    }

    public int calculateWinnings(){
        Collections.sort(hands);
        int totalWinnings = 0;

        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            totalWinnings += (i + 1) * hand.getBid();
        }
        return totalWinnings;
    }

    private class Hand implements Comparable<Hand> {
        private final String handString;
        private final int bid;
        private int type;
        private String cards = "23456789TJQKA";
        private HandsRecognizer handsRecognizer = new HandsRecognizer();

        public Hand(String handString, int bid) {
            this.handString = handString;
            this.bid = bid;
            this.type = handsRecognizer.recognizePokerHand(handString.split(""));
        }

        public void updateType() {
            cards = "J23456789TQKA";
            this.type = handsRecognizer.recognizePokerHand(handString.split(""), true);
        }

        public int getBid() {
            return bid;
        }

        @Override
        public int compareTo(Hand other) {
            if (this.type != other.type) {
                return Integer.compare(this.type, other.type);
            } else {
                for (int i = 0; i < handString.length(); i++) {
                    if (handString.charAt(i) != other.handString.charAt(i)) {
                        return Integer.compare(cards.indexOf(handString.charAt(i)), cards.indexOf(other.handString.charAt(i)));
                    }
                }
                return 0;
            }
        }
    }

    private class HandsRecognizer {
        boolean part2 = false;

        public int recognizePokerHand(String[] hand, boolean part2){
            this.part2 = part2;
            return recognizePokerHand(hand);
        }

        public int recognizePokerHand(String[] hand) {
            Arrays.sort(hand, Comparator.comparing(s -> s.charAt(0)));

            if (hasNOfAKind(hand, 5)) return 6;
            else if (hasNOfAKind(hand, 4)) return 5;
            else if (isFullHouse(hand)) return 4;
            else if (hasNOfAKind(hand, 3)) return 3;
            else if (isTwoPair(hand)) return 2;
            else return hasNOfAKind(hand, 2) ? 1 : 0;
        }

        private boolean isFullHouse(String[] hand) {
            return hasNOfAKind(hand, 3) && hasNOfAKind(hand, 2);
        }

        private boolean isTwoPair(String[] hand) {
            int pairCount = 0;
            for (int i = 1; i < hand.length; i++) {
                if (hand[i].charAt(0) == hand[i - 1].charAt(0)) pairCount++;
            }
            return pairCount == 2;
        }

        private boolean hasNOfAKind(String[] hand, int n) {
            if(part2){
                String mostCommonString = Arrays.stream(hand)
                        .filter(s -> !s.equals("J"))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .max(Map.Entry.<String, Long>comparingByValue()
                                .thenComparingInt(entry -> "J23456789TQKA".indexOf(String.valueOf(entry.getKey()))))
                        .map(Map.Entry::getKey)
                        .orElse("J");

                hand = Arrays.stream(hand)
                        .map(s -> s.equals("J") ? mostCommonString : s)
                        .toArray(String[]::new);
            }
            for(String card : hand){
                if(Collections.frequency(List.of(hand), card) == n){
                    return true;
                }
            }
            return false;
        }
    }
}
