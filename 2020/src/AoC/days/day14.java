package AoC.days;

import AoC.Day;

import java.util.HashMap;
import java.util.Map;

public class day14 extends Day {
    public day14(String fileStr) {
        super(fileStr);
    }

    public long part1() {
        String bitmask ="", value;
        long memLocation;
        Map<Long, Long> memory = new HashMap<>();
        for (String program: input) {
            if (program.startsWith("mask = ")){
                bitmask = program.substring(7);
            } else {
                value = String.format("%36s", Integer.toBinaryString(Integer.parseInt(program.substring(program.indexOf('=') + 2)))).replace(' ','0');

                memLocation = Long.parseLong(program.substring(program.indexOf('[')+1, program.indexOf(']')));

                for (int i = 0;  i < bitmask.length(); i++) {
                    if (bitmask.charAt(i) != 'X'){
                        value =  value.substring(0,i) + bitmask.charAt(i) + value.substring(i+1);
                    }
                }

                memory.put(memLocation, Long.parseLong(value,2));
            }
        }
        long sum = 0;
        for (Long key: memory.keySet()){
            sum += memory.get(key);
        }
        return sum;
    }

    public long part2() {
        String bitmask ="", value;
        Map<Long, Long> memory = new HashMap<>();
        String memLocation;
        for (String program: input) {
            if (program.startsWith("mask = ")){
                bitmask = program.substring(7);
            } else {
                value = String.format("%36s", Integer.toBinaryString(Integer.parseInt(program.substring(program.indexOf('=') + 2)))).replace(' ','0');

                memLocation = String.format("%36s", Long.toBinaryString(Long.parseLong(program.substring(program.indexOf('[')+1, program.indexOf(']'))))).replace(' ','0');

                StringBuilder x = new StringBuilder();
                for (int i = 0; i < bitmask.length(); i++) {
                    char bit = bitmask.charAt(i);
                    if (bit == 'X' || bit == '1') {
                        x.append(bit);
                    } else {
                        x.append(memLocation.charAt(i));
                    }
                }
                memLocation = x.toString();

                long[] addrs = getMemLocations(memLocation);

                for (long i: addrs) {
                    memory.put(i, Long.parseLong(value, 2));
                }
            }
        }

        long result = 0;
        for (long i: memory.values()){
            result += i;
        }
        return result;
    }

    public long[] getMemLocations(String memLocation) {
        int count = 0;
        for(int i = 0; i < memLocation.length(); i++){
            if (memLocation.charAt(i) == 'X'){
                count++;
            }
        }
        long possibleVals = (long) Math.pow(2,count);
        long[] result = new long[(int)possibleVals];

        for(int i = 0; i < possibleVals; i++){
            String binary = String.format("%"+count+"s", Long.toBinaryString(i)).replace(' ','0');

            StringBuilder res = new StringBuilder();
            int k = 0;
            for (int j = 0; j < memLocation.length(); j++){
                if (memLocation.charAt(j) == 'X'){
                    res.append(binary.charAt(k));
                    k++;
                } else {
                    res.append(memLocation.charAt(j));
                }
            }
            result[i] = Long.parseLong(res.toString(), 2);
        }
        return result;
    }
}
