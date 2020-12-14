package AoC.days;

import AoC.Day;

import java.util.ArrayList;
import java.util.List;

public class day8 extends Day {
    private Pair<String, Integer> result;

    public day8(String fileStr) {
        super(fileStr);
        result = run(input);
    }

    public int part1() {
        return result.acc;
    }

    public int part2() {
        while(result.msg.equals("INFINITE")){
            List<String> newInstructions = new ArrayList<>(input);
            for (int i = 0; i < newInstructions.size(); i++) {
                String op = newInstructions.get(i).substring(0, 3);
                int arg = Integer.parseInt(newInstructions.get(i).substring(4));
                if (op.equals("jmp")){
                    newInstructions.set(i, "nop " + arg);
                } else if(op.equals("nop")){
                    newInstructions.set(i, "jmp " + arg);
                }
                result = run(newInstructions);
                if (result.msg.equals("SUCCESS")){
                    break;
                } else {
                    newInstructions = new ArrayList<>(input);
                }
            }
        }
        return result.acc;
    }

    public static Pair run(List<String> instructions) {
        int accumulator = 0,
                i = 0;
        List<Integer> seenI = new ArrayList<>();
        Pair result;

        while(i < instructions.size()){
            seenI.add(i);
            String op = instructions.get(i).substring(0, 3);
            int arg = Integer.parseInt(instructions.get(i).substring(4));

            switch(op) {
                case "acc":
                    accumulator += arg;
                    break;
                case "jmp":
                    i += (arg -1);
                    break;
            }
            i++;
            if(seenI.contains(i)){
                result = new Pair("INFINITE", accumulator);
                return result;
            }
        }
        result = new Pair("SUCCESS", accumulator);
        return result;
    }

    public static class Pair<T, U> {
        public final T msg;
        public final U acc;

        public Pair(T msg, U acc) {
            this.msg= msg;
            this.acc= acc;
        }
    }
}
