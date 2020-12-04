import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day5 {
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
        //list.set(1, 12);
        //list.set(2, 2);
        int i = 0;
        while (i < list.size()) {
            int opcode = list.get(i) % 100;
            int param1Mode = 0, param2Mode = 0, param3Mode = 0;
            if (list.get(i) >= 100){
                param1Mode = (list.get(i) - opcode)/100%10;
            }
            if (list.get(i) >= 1000){
                param2Mode = (list.get(i) - opcode - (param1Mode*100))/1000 %10;
            }

            if(opcode == 1){
                list.set(list.get((i+3)) == 4 ? list.get((i+4)) : list.get((i+3)), list.get(param1Mode == 0 ? list.get((i+1)) : i+1) + list.get(param2Mode == 0 ? list.get((i+2)) : i+2));
                i += 4;
                if (list.get(i+3) == 4){
                    i++;
                }
            } else if(opcode == 2) {
                list.set(list.get((i+3)) == 4 ? list.get((i+4)) : list.get((i+3)), list.get(param1Mode == 0 ? list.get((i+1)) : i+1) * list.get(param2Mode == 0 ? list.get((i+2)) : i+2));
                i += 4;
                if (list.get(i+3) == 4){
                    i++;
                }
            } else if(opcode == 3) {
                Scanner num = new Scanner(System.in);
                System.out.print("Please provide the ID of the system to test: ");
                list.set(list.get(i+1), num.nextInt());
                i += 2;
            } else if(opcode == 4) {
                System.out.println(list.get(param1Mode == 0 ? list.get((i+1)) : i+1));
                i += 2;
            } else if(opcode == 5) {
                if(list.get(param1Mode == 0 ? list.get((i+1)) : i+1) != 0){
                    i = list.get(param2Mode == 0 ? list.get(i+2) : i+2);
                } else {
                    i += 3;
                }
            } else if(opcode == 6) {
                if(list.get(param1Mode == 0 ? list.get((i+1)) : i+1) == 0){
                    i = list.get(param2Mode == 0 ? list.get(i+2) : i+2);
                } else {
                    i += 3;
                }
            } else if(opcode == 7) {
                if (list.get(param1Mode == 0 ? list.get((i+1)) : i+1).compareTo(list.get(param2Mode == 0 ? list.get((i+2)) : i+2)) < 0){
                    list.set(list.get(i+3), 1);
                } else {
                    list.set(list.get(i+3), 0);
                }
                i += 4;
            } else if(opcode == 8) {
                if (list.get(param1Mode == 0 ? list.get((i + 1)) : i + 1).equals(list.get(param2Mode == 0 ? list.get((i + 2)) : i + 2))){
                    list.set(list.get(i+3), 1);
                } else {
                    list.set(list.get(i+3), 0);
                }
                i += 4;
            } else if(opcode == 99){
                break;
            }
        }
        //System.out.println(list.get(0));

        /*System.out.println("");

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
        }*/
    }
}
