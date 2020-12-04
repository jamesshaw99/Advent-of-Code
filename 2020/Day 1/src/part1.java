import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class part1 {
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

        Collections.sort(list);

        System.out.println("Part 1");

        int l, r;
        l = 0;
        r = list.size() - 1;
        while(l < r){
            if (list.get(l) + list.get(r) == 2020) {
                System.out.println(list.get(l));
                System.out.println(list.get(r));
                System.out.println(list.get(l) * list.get(r));
                break;
            } else if(list.get(l) + list.get(r) < 2020){
                l++;
            } else {
                r--;
            }
        }

        System.out.println("Part 2");

        for(int i = 0; i < list.size() - 2; i++) {
            for(int j = i + 1; j < list.size() - 1; j++) {
                for(int k = j + 1; k < list.size(); k++) {
                    if(list.get(i) + list.get(j) + list.get(k) == 2020){
                        System.out.println("Numbers are: " + list.get(i) + ", " + list.get(j) + ", " + list.get(k));
                        System.out.println(list.get(i) * list.get(j) * list.get(k));
                        return;
                    }
                }
            }
        }
    }
}
