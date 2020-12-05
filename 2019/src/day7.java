import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class day7 {
    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<Integer>();
        File file = new File("inputs/day7test.txt");
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

        List<List<Integer>> inputs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++){
                if (j != i) {
                    for (int k = 0; k < 5; k++) {
                        if (k != i && k != j) {
                            for (int l = 0; l < 5; l++) {
                                if(l != i && l != j && l !=k) {
                                    for (int m = 0; m < 5; m++){
                                        if(m != i && m != j && m != k && m != l) {
                                            inputs.add(Arrays.asList(i,j,k,l,m));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        List<Integer> output = new ArrayList<Integer>();
        List<Integer> finalOutput = new ArrayList<Integer>();
        for (List<Integer> phaseSettings: inputs){
            int input = 0;
            for (int i = 0; i < 5; i++){
                List<Integer> result = new ArrayList<Integer>(doIntcode(dataList, phaseSettings.get(i), input));
                System.out.println(result);
                input = result.get(0);
                output.add(input);
            }
            finalOutput.add(Collections.max(output));
        }
        System.out.println(Collections.max(finalOutput));


    }

    public static List<Integer> doIntcode(List<Integer> list, int input1, int input2){
        List<Integer> outputs = new ArrayList<Integer>();
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
                //Scanner num = new Scanner(System.in);
                System.out.print("Inputs: ");
                if (input1 != -1){
                    list.set(list.get(i+1), input1);
                    System.out.println(input1);
                    input1 = -1;
                } else {
                    list.set(list.get(i+1), input2);
                    System.out.println(input2);
                }
                i += 2;
            } else if(opcode == 4) {
                outputs.add(list.get(param1Mode == 0 ? list.get((i+1)) : i+1));
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
            } else {
                if(outputs.size() == 0) {
                    outputs.add(0);
                }
                break;
            }
        }
        return outputs;
    }
}
