package AoC.days;

import AoC.helpers.Day;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class day22 extends Day {
    ArrayList<Integer> deck1 = new ArrayList<>();
    public day22(String fileStr) {
        super(fileStr);
        for(int i = 0; i < 10007; i++) {
            this.deck1.add(i);
        }
    }

    public String part1() {
        List<Integer> newDeck = new ArrayList<>(this.deck1);
        for(String line: input) {
            newDeck = processTechnique(line, newDeck);
        }
        return "Value at position 2019: " + newDeck.indexOf(2019);
    }

    public String part2() {
        long len = 119315717514047L,
            pos = 2020,
            times = 101741582076661L%(len-1);
        int t = 0;
        while(true) {
            long oldpos = pos;
            //new_pos = a * old_pos + b;
            BigInteger a = BigInteger.ONE;
            BigInteger b = BigInteger.ZERO;
            for (String line : input) {
                if(line.equals("deal into new stack")){
                    pos = len - 1 - pos;
                    a = a.multiply(BigInteger.valueOf(-1));
                    b = b.multiply(BigInteger.valueOf(-1)).add(BigInteger.valueOf(len - 1));
                } else if(line.startsWith("deal with increment ")){
                    long n = Long.parseLong(line.split(" ")[3]);
                    long ninv = modInverse(n, len);
                    a = a.multiply(BigInteger.valueOf(ninv));
                    b = b.multiply(BigInteger.valueOf(ninv));
                    pos = (ninv*pos)%len;
                } else if(line.startsWith("cut")){
                    long n = Long.parseLong(line.split(" ")[1]);
                    if (n < 0) {
                        n = len - Math.abs(n);
                    }
                    pos = (pos + len - n) % len;
                }
            }
            t++;
            System.out.println(t + ", " + a + ", " + b + ", " + pos);
            if(t == times) {
                break;
            }
            if(pos == 2020){
                System.out.println(t);
                break;
            }
        }
        return "" + t;
    }

    /*private long processPart2(String line, long len, long pos, int t){
        if(line.equals("deal into new stack")){
            pos = len - 1 - pos;
            a = -a,
        } else if(line.startsWith("deal with increment ")){
            long n = Long.parseLong(line.split(" ")[3]);
            pos = (n*pos)%len;
        } else if(line.startsWith("cut")){
            long n = Long.parseLong(line.split(" ")[1]);
            if (n < 0) {
                n = len - Math.abs(n);
            }
            if (pos < n) {
                assert t==0;
                pos = (len - n) + pos;
            } else {
                pos -= n;
            }
        }
        return pos;
    }*/

    static long modInverse(long a, long m)
    {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    private List<Integer> processTechnique(String line, List<Integer> deck) {
        List<Integer> newDeck = new ArrayList<>();
        if(line.equals("deal into new stack")){
            newDeck = newStack(deck);
        } else if(line.startsWith("deal with increment ")){
            int n = Integer.parseInt(line.split(" ")[3]);
            newDeck = dealWithIncrementN(deck, n);
        } else if(line.startsWith("cut")){
            int n = Integer.parseInt(line.split(" ")[1]);
            newDeck = cutN(deck, n);
        }
        return newDeck;
    }

    private List<Integer> newStack(List<Integer> oldStack) {
        List<Integer> newStack = new ArrayList<>();
        for(int i = oldStack.size() - 1; i >= 0; i--) {
            newStack.add(oldStack.get(i));
        }
        return newStack;
    }

    private List<Integer> cutN(List<Integer> oldDeck, int n) {
        List<Integer> newDeck = new ArrayList<>(oldDeck);
        if(n >= 0) {
            for (int i = 0; i < n; i++) {
                newDeck.add(newDeck.remove(0));
            }
        } else {
            for (int i = 0; i < Math.abs(n); i++) {
                newDeck.add(0, newDeck.remove(newDeck.size() - 1));
            }
        }
        return newDeck;
    }

    private List<Integer> dealWithIncrementN(List<Integer> oldDeck, int n){
        List<Integer> newDeck = new ArrayList<>(oldDeck);
        for (int i = 0; i < oldDeck.size(); i++) {
            int index = (i * n) % oldDeck.size();
            newDeck.set(index, oldDeck.get(i));
        }
        return newDeck;
    }
}
