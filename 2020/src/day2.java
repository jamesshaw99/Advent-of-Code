import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day2 {
    public static void main(String[] args) {
        String fileName= "inputs/day2.csv";
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

        System.out.println("Part 1");
        // the following code lets you iterate through the 2-dimensional array
        int noValid = 0;
        for(List<String> line: lines) {
            int columnNo = 1;
            String value1 = "", value2 = "";
            char searchChar = ' ';
            int charCount = 0;
            for (String value: line) {
                if (columnNo == 1){
                    value1 = value.substring(0, value.indexOf("-"));
                    value2 = value.substring(value.indexOf("-") + 1);
                } else if (columnNo == 2) {
                    searchChar = value.charAt(0);
                } else if(columnNo == 3) {
                    for (int i = 0; i < value.length(); i++){
                        if (value.charAt(i) == searchChar){
                            charCount++;
                        }
                    }
                    if (Integer.parseInt(value1) <= charCount && charCount <= Integer.parseInt(value2)) {
                        noValid++;
                    }
                }
                columnNo++;
            }
        }
        System.out.println("Number of valid passwords = " + noValid);

        System.out.print("\n");

        System.out.println("Part 2");
        int noValid2 = 0;
        for(List<String> line: lines) {
            int columnNo = 1;
            String value1 = "", value2 = "";
            char searchChar = ' ';
            for (String value: line) {
                if (columnNo == 1){
                    value1 = value.substring(0, value.indexOf("-"));
                    value2 = value.substring(value.indexOf("-") + 1);
                } else if (columnNo == 2) {
                    searchChar = value.charAt(0);
                } else if(columnNo == 3) {
                    if (value.charAt(Integer.parseInt(value1) - 1) == searchChar ^ value.charAt(Integer.parseInt(value2) - 1) == searchChar) {
                        noValid2++;
                    }
                }
                columnNo++;
            }
        }
        System.out.println("Number of valid passwords = " + noValid2);
    }
}
