import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        File file = new File("data.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                list.add(Integer.parseInt(text));
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

        System.out.println("Part 1");
        long totalFuel = 0;
        for(int i = 0; i < list.size(); i++){
            totalFuel += (Math.floor(list.get(i) / 3.0) - 2);
        }
        System.out.println("Total fuel: " + totalFuel);

        System.out.println("");

        System.out.println("Part 2");
        totalFuel = 0;
        for(int i = 0; i < list.size(); i++){
            int reqFuel = ((int)Math.floor(list.get(i) / 3.0) - 2);
            while(reqFuel > 0) {
                totalFuel += reqFuel;
                reqFuel = ((int)Math.floor(reqFuel / 3.0) - 2);
            }
        }
        System.out.println("Total fuel: " + totalFuel);
    }
}
