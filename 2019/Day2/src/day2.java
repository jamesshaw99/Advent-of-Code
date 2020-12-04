import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day2 {
    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<Integer>();
        File file = new File("data.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                dataList.add(Integer.parseInt(text));
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

        List<Integer> list = new ArrayList<Integer>(dataList);
        List<Integer> list2 = new ArrayList<Integer>(dataList);

        System.out.println("Part1");
        list.set(1, 12);
        list.set(2, 2);
        for (int i = 0; i < list.size(); i++) {
            if(i % 4 == 0){
                if(list.get(i) == 1){
                    list.set(list.get((i+3)), list.get(list.get((i+1))) + list.get(list.get((i+2))));
                } else if(list.get(i) == 2) {
                    list.set(list.get((i+3)), list.get(list.get((i+1))) * list.get(list.get((i+2))));
                } else if(list.get(i) == 99){
                    break;
                }
            }
        }
        System.out.println(list.get(0));

        System.out.println("");

        System.out.println("Part2");
        for (int k = 0; k < 100; k++){
            for (int j = 0; j < 100; j++){
                list2 = new ArrayList<Integer>(dataList);
                list2.set(1, k);
                list2.set(2, j);
                for (int i = 0; i < list2.size(); i++) {
                    if(i % 4 == 0){
                        if(list2.get((i+3)) < list2.size()) {
                            if (list2.get(i) == 1) {
                                list2.set(list2.get((i + 3)), list2.get(list2.get((i + 1))) + list2.get(list2.get((i + 2))));
                            } else if (list.get(i) == 2) {
                                list2.set(list2.get((i + 3)), list2.get(list2.get((i + 1))) * list2.get(list2.get((i + 2))));
                            } else if (list2.get(i) == 99) {
                                break;
                            }
                        }
                    }
                }
                if(list2.get(0) == 19690720){
                    System.out.println("noun: " + k);
                    System.out.println("verb: " + j);
                    break;
                }
            }
        }
    }
}
