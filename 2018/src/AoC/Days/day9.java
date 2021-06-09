package AoC.Days;

import AoC.Helpers.Day;

import java.util.Arrays;

public class day9 extends Day {
    private final int players, last_marble;

    public day9(String fileStr) {
        super(fileStr);
        players = Integer.parseInt(input.get(0).split(" ")[0]);
        last_marble = Integer.parseInt(input.get(0).split(" ")[6]);
    }

    public String part1() {
        return "Winning Elf's score: " + String.valueOf(playGame(players, last_marble));
    }

    public String part2() {
        return "Winning Elf's score: " + String.valueOf(playGame(players, last_marble*100));
    }

    private long playGame(int players, int times) {
        Marble current = new Marble(0);
        long[] scores = new long[players];

        for(int i = 1; i <= times; i++) {
            if(i % 23 == 0) {
                for(int j = 0; j < 7; j++){
                    current = current.previous;
                }
                scores[i % players] += i + current.removeMarble();
                current = current.next;
            } else {
                current = current.next.insertAfter(i);
            }
        }
        return Arrays.stream(scores).max().getAsLong();
    }

    class Marble {
        Marble previous, next;
        private int value;

        public Marble(int value) {
            this.value = value;
            if(value == 0) {
                previous = this;
                next = this;
            }
        }

        public Marble insertAfter(int value) {
            Marble marble = new Marble(value);
            marble.previous = this;
            marble.next = this.next;
            this.next.previous = marble;
            this.next = marble;
            return marble;
        }

        public int removeMarble() {
            this.previous.next = this.next;
            this.next.previous = this.previous;
            return this.value;
        }
    }
}


