import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        File file = new File("inputs/day1.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;

            while ((text = reader.readLine()) != null) {
                list.add(Integer.parseInt(text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Part 1");
        long totalFuel = 0;
        for (Integer integer : list) {
            totalFuel += (Math.floor(integer / 3.0) - 2);
        }
        System.out.println("Total fuel: " + totalFuel);

        System.out.println("");

        System.out.println("Part 2");
        totalFuel = 0;
        for (Integer integer : list) {
            int reqFuel = ((int) Math.floor(integer / 3.0) - 2);
            while (reqFuel > 0) {
                totalFuel += reqFuel;
                reqFuel = ((int) Math.floor(reqFuel / 3.0) - 2);
            }
        }
        System.out.println("Total fuel: " + totalFuel);
    }
}