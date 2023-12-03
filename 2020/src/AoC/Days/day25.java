package AoC.Days;

import AoC.Helpers.Day;

public class day25 extends Day {
    private int card, door, subjectNo = 1;
    private boolean test = true;
    public day25(String fileStr) {
        super(fileStr);
        if(test){
            card = 5764801;
            door = 17807724;
        } else {
            card = Integer.parseInt(input.get(0));
            door = Integer.parseInt(input.get(1));

        }
    }

    public Long part1() {
        return getEncryptionKey(card, getLoopSize(door));
    }

    public String part2() {
        return "I Won";
    }

    private int getLoopSize(int n) {
        int current = 1, i = 0;
        while (current != n) {
            current *= 7;
            current %= 20201227;
            i++;
        }
        return i;
    }

    private long getEncryptionKey(int key1, int loopSize2) {
        long n = 1;
        for (int i = 0; i < loopSize2; i++) {
            n *= key1;
            n %= 20201227;
        }
        return n;
    }
}
