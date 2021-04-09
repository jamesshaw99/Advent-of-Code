package AoC.Days;

import AoC.Helpers.Day;

public class day5 extends Day {
    public day5(String fileStr) {
        super(fileStr);
    }

    public String part1() {
        StringBuilder polymerRes = new StringBuilder(input.get(0));

        return "Final Polymer Length: " + collapse(polymerRes);
    }

    public String part2() {
        int minLen = Integer.MAX_VALUE;

        String[] pattern = new String[26];
        for (int i = 0; i < 26; i++) {
            pattern[i] = '[' + (Character.toString((char)(i + 'a'))) + (Character.toString((char)(i + 'A'))) + ']';
        }

        for (int i = 0; i < 26; i++) {
            String chain = input.get(0);
            chain = chain.replaceAll(pattern[i], "");
            int res = collapse(new StringBuilder(chain));
            if(res < minLen) {
                minLen = res;
            }
        }
        return "Min polymer length: " + minLen;
    }

    private int collapse(StringBuilder polymer) {
        boolean removed = true;

        while (removed) {
            for (int i = 0; i < polymer.length() - 1; i++) {
                if((polymer.charAt(i) ^ polymer.charAt(i+1)) == 32) {
                    polymer.delete(i, i+2);
                    removed = true;
                    break;
                }
                removed = false;
            }
        }

        return polymer.length();
    }
}
