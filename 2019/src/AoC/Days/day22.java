package AoC.Days;

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
        long n = 119315717514047L,
            c = 2020,
            a = 1,
            b = 0,
            la = 0,
            lb = 0;
        for (String line: input) {
            if(line.equals("deal into new stack")){
                la = -1;
                lb = -1;
            }else if(line.startsWith("deal with increment ")){
                la = Integer.parseInt(line.split(" ")[3]);
                lb = 0;
            } else if(line.startsWith("cut ")){
                la = 1;
                lb = -Integer.parseInt(line.split(" ")[1]);
            }
            a = (la * a) % n;
            b = (la * b + lb) % n;
        }

        long m = 101741582076661L;
        BigInteger ma = pow(a, m, n),
            mb = ma.subtract(BigInteger.ONE).multiply(BigInteger.valueOf(b)).multiply(inv(a-1, n)).mod(BigInteger.valueOf(n));

        return "Result at 2020: " + BigInteger.valueOf(c).subtract(mb).multiply(inv(ma, n)).mod(BigInteger.valueOf(n));
    }

    private BigInteger pow(long a, long b, long c){
        return BigInteger.valueOf(a).modPow(BigInteger.valueOf(b), BigInteger.valueOf(c));
    }

    private BigInteger inv(long a, long b) {
        return pow(a, b-2, b);
    }

    private BigInteger inv(BigInteger a, long b) {
        return a.modPow(BigInteger.valueOf(b-2), BigInteger.valueOf(b));
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
