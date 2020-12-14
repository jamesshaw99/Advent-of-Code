package AoC.days;

import AoC.Day;

import java.util.*;

public class day13 extends Day {
    private final int earliestTimestamp;
    private final List<String> busIDs;
    public day13(String fileStr) {
        super(fileStr);
        earliestTimestamp = Integer.parseInt(input.get(0));
        busIDs = Arrays.asList(input.get(1).split(","));
    }

    public int part1() {
        int busID = 0, busTime = 0;
        for(String bus: busIDs){
            if (!bus.equals("x")){
                int busVal = Integer.parseInt(bus), busTimeTemp = busVal * ((earliestTimestamp-1) / busVal +1);
                if (busTime == 0 || busTimeTemp < busTime) {
                    busTime = busTimeTemp;
                    busID = busVal;
                }
            }
        }
        return busID * (busTime - earliestTimestamp);
    }

    public long part2() {
        List<Integer> n = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Long> Ns = new ArrayList<>();
        List<Integer> x = new ArrayList<>();
        long N = 1;
        for (int i = 0; i < busIDs.size(); i++) {
            if (!busIDs.get(i).equals("x")) {
                if(i==0) {
                    b.add(i);
                } else {
                    b.add((-1*i)+Integer.parseInt(busIDs.get(i)));
                }
                n.add(Integer.parseInt(busIDs.get(i)));
                N *= Integer.parseInt(busIDs.get(i));
            }
        }
        for (int i = 0; i < n.size(); i++) {
            Ns.add(N / n.get(i));
            x.add(getInverse(Ns.get(i), n.get(i)));
        }

        long sum = 0;
        for (int i = 0; i < n.size(); i++) {
            sum += b.get(i) * Ns.get(i) * x.get(i);
        }
        return sum % N;
    }

    public int getInverse(long N, int n) {
        int x = 1;
        while ((N*x) % n != 1) {
            x++;
        }
        return x;
    }
}
