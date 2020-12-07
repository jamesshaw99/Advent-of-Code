import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day5 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        File file = new File("data.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;

            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Part1");
        List<Integer> SeatIDs = new ArrayList<>();
        for (String line: list) {
            int rowNo = Integer.parseInt(line.substring(0,7).replaceAll("F", "0").replaceAll("B", "1"),2);
            int columnNo = Integer.parseInt(line.substring(7).replaceAll("R", "1").replaceAll("L", "0"),2);
            SeatIDs.add((rowNo * 8) + columnNo);
        }
        System.out.println("Largest seatID = " + Collections.max(SeatIDs));

        System.out.println("Part2");
        for(int i = Collections.min(SeatIDs); i < Collections.max(SeatIDs); i++){
            if(!SeatIDs.contains(i)){
                System.out.println("Your seatID = " + i);
            }
        }
    }
}