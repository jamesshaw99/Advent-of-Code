import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day3 {

    public static int[] getDir(char c) {
        switch(c){
            case 'U':
                return new int[] {0,1};
            case 'D':
                return new int[] {0,-1};
            case 'L':
                return new int[] {-1,0};
            case 'R':
                return new int[] {1,0};
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String fileName= "inputs/day3.csv";
        File file= new File(fileName);

        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Map<String, Integer> wire = new HashMap<String, Integer>();
        String[] input = lines.get(0).toArray(new String[0]);

        int closestDistance = Integer.MAX_VALUE;
        int shortestWire = Integer.MAX_VALUE;

        int x = 0, y = 0, d = 0;

        for (int i = 0; i < input.length; i++) {
            int[] dir = getDir(input[i].charAt(0));
            int len = Integer.parseInt(input[i].substring(1));
            for (int j = 0; j < len; j++) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                wire.put(newX + "_" + newY, ++d);
                x = newX;
                y = newY;
            }
        }

        input = lines.get(1).toArray(new String[0]);
        x = y = d = 0;

        for(int i = 0; i < input.length; i++) {
            int[] dir = getDir(input[i].charAt(0));
            int len = Integer.parseInt(input[i].substring(1));
            for (int j = 0; j < len; j++) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                d++;

                if(wire.containsKey(newX + "_" + newY)) {
                    closestDistance = Math.min(closestDistance, (int)Math.abs(newX) + (int)Math.abs(newY));
                    shortestWire = Math.min(shortestWire, wire.get(newX + "_" + newY) + d);
                }
                x = newX;
                y = newY;
            }
        }

        System.out.println("Closest Distance: " + closestDistance);
        System.out.println("Shortest Wire: " + shortestWire);
    }
}
