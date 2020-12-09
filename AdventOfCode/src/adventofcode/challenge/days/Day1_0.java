package adventofcode.challenge.days;

import adventofcode.AdventOfCode;
import adventofcode.challenge.Challenge;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class Day1_0 extends Challenge {
    public Day1_0(String id, File data) {
        super(id, data);
    }

    @Override
    public void run() {
        List<Integer> list = new ArrayList<>();
        for(String s: getLines()) list.add(Integer.valueOf(s));
        Collections.sort(list);
        int l, r;
        l = 0;
        r = list.size() - 1;
        while(l < r){
            if (list.get(l) + list.get(r) == 2020) {
                AdventOfCode.LOGGER.log(Level.INFO, "{0}", (list.get(l) * list.get(r)));
                break;
            } else if(list.get(l) + list.get(r) < 2020){
                l++;
            } else {
                r--;
            }
        }
    }
}
