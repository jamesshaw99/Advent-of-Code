import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day3 {
    public static void main(String[] args) {
        List<String> map = new ArrayList<String>();
        File file = new File("inputs/day3.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                map.add(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        System.out.println("Part1");
        int x = 0, y = 0, noTrees = 0, maxX = map.get(0).length();
        while(y < map.size()){
            if(map.get(y).charAt(x) == '#'){
                noTrees++;
            }
            x+=3;
            y++;
            if(x >= maxX){
                x -= maxX;
            }
        }
        System.out.println("Number of trees passed: " + noTrees);

        System.out.println("");

        System.out.println("Part 2");
        Integer[][] Routes = {{1,1},{3,1},{5,1},{7,1},{1,2}};
        long total = 0;
        for(Integer[] row: Routes){
            int routeX = row[0], routeY = row[1];
            x = 0;
            y = 0;
            noTrees = 0;
            while(y < map.size()){
                if(map.get(y).charAt(x) == '#'){
                    noTrees++;
                }
                x += routeX;
                y += routeY;
                if(x >= maxX){
                    x -= maxX;
                }
            }
            if (total > 0){
                total = total * noTrees;
            } else {
                total = noTrees;
            }
        }
        System.out.println("number of trees passed on all routes, multiplied together: " + total);
    }
}
