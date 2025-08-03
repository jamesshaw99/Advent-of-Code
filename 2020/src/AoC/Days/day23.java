package AoC.Days;

import AoC.Helpers.Day;
import java.util.Arrays;

public class day23 extends Day {
    private final int[] inputs;
    public day23(String fileStr) {
        super(fileStr);
        inputs = Arrays.stream(input.get(0).split("")).mapToInt(Integer::parseInt).toArray();
    }

    public String part1() {
        Node currentCup = setup(inputs, 1);

        for (int i = 0; i < 100; i++) {
            Node p = removeNext3(currentCup);
            Node d = findDestinationCup(currentCup, p);
            placePickedUpCups(p, d);
            currentCup = currentCup.next;
        }

        Node tempCup = currentCup;
        do {
            tempCup = tempCup.next;
            if(tempCup.cup == 1) {
                tempCup = tempCup.next;
                break;
            }
        } while (tempCup != currentCup);

        Node lastCup = tempCup.prev;
        StringBuilder result = new StringBuilder();
        do {
            result.append(tempCup.cup);
            tempCup = tempCup.next;
        } while (tempCup != lastCup);

        return result.toString();
    }

    public Integer part2() {
        Node currentCup = setup(inputs, 2);

        Node oneCup = currentCup;
        while(oneCup.cup != 1) {
            oneCup = oneCup.next;
        }

        for (int i = 0; i < 10000000; i++) {
            Node p = removeNext3(currentCup);
            Node d = findDestinationCup(currentCup, p);
            placePickedUpCups(p,d);
            currentCup = currentCup.next;
        }

        return oneCup.next.cup * oneCup.next.next.cup;
    }

    public static void create(Node currentCup, int min, int max) {
        Node prevNode = currentCup.prev;

        for(int i = min; i <= max; i++) {
            Node tempCup = new Node(i);
            tempCup.prev = prevNode;
            prevNode.next = tempCup;
            if(i > 10) {
                tempCup.minus = prevNode;
            }
            prevNode = tempCup;
        }
        prevNode.next = currentCup;
        currentCup.prev = prevNode;
    }

    private static Node setup(int[] inputs, int part) {
        Node currentCup = new Node(inputs[0]);
        Node tempCup = currentCup;
        Node prevCup;
        for (int i = 1; i < inputs.length; i++) {
            tempCup.next = new Node(inputs[i]);
            prevCup = tempCup;
            tempCup = tempCup.next;
            tempCup.prev = prevCup;
        }
        tempCup.next = currentCup;
        currentCup.prev = tempCup;

        tempCup = currentCup;
        Node oneCup = null;
        do {
            if (tempCup.cup == 1) {
                oneCup = tempCup;
            } else {
                int minusCup = tempCup.cup - 1;
                Node t = tempCup.next;
                while(true) {
                    if(t.cup == minusCup) {
                        tempCup.minus = t;
                        break;
                    } else {
                        t = t.next;
                    }
                }
            }
            tempCup = tempCup.next;
        } while(tempCup != currentCup);

        tempCup = currentCup;
        while (tempCup.cup != 9) {
            tempCup = tempCup.next;
        }
        if (part == 1) {
            assert oneCup != null;
            oneCup.minus = tempCup;
        } else {

            Node lastCup = currentCup.prev;
            create(currentCup, 10, 1000000);
            assert oneCup != null;
            oneCup.minus = currentCup.prev;
            lastCup.next.minus = tempCup;
        }

        return currentCup;
    }

    public static void placePickedUpCups(Node p, Node d) {
        Node next = d.next;

        d.next = p;
        p.prev = d;

        next.prev = p.next.next;
        p.next.next.next = next;
    }

    public static Node findDestinationCup(Node currentCup, Node p) {
        Node dest = currentCup.minus;

        while (dest == p || dest == p.next || dest == p.next.next) {
            dest = dest.minus;
        }

        return dest;
    }

    public static Node removeNext3(Node currentCup) {
        Node p1 = currentCup.next;
        Node p3 = currentCup.next.next.next;
        Node l = p3.next;

        currentCup.next = l;
        l.prev = currentCup;

        p1.prev = null;
        p3.next = null;

        return p1;
    }

    public static class Node {
        public Node minus;
        public Node next;
        public Node prev;
        int cup;

        public Node(int cup) {
            this.cup = cup;
        }

        public String toString() {
            return String.valueOf(cup);
        }
    }
}