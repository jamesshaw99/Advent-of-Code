import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class day6 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        File file = new File("inputs/day6.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;

            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.add(""); //blank line to ensure last group is caught

        System.out.println("Part 1");
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        List<Character> seenChars = new ArrayList<>();
        for (String line: list){
            if (!line.isEmpty()){
                for (int i = 0; i < line.length(); i++) {
                    if(!seenChars.contains(line.charAt(i))) {
                        seenChars.add(line.charAt(i));
                        count++;
                    }
                }
            } else {
                counts.add(count);
                count = 0;
                seenChars = new ArrayList<>();
            }
        }
        System.out.println("Total of counts: " + counts.stream().mapToInt(Integer::intValue).sum());

        System.out.print("\n");

        System.out.println("Part 2");
        counts = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for(String line: list) {
            if(!line.isEmpty()){
                answers.add(line);
            } else {
                Set<Character> commonChars = convertStringToSetOfChars(answers.get(0));
                answers.stream().skip(1).forEach(s -> commonChars.retainAll(convertStringToSetOfChars(s)));

                counts.add(commonChars.size());
                answers = new ArrayList<>();
            }
        }
        System.out.println("Total of counts: " + counts.stream().mapToInt(Integer::intValue).sum());
    }

    public static Set<Character> convertStringToSetOfChars(String string) {
        if (string == null || string.isEmpty()){
            return Collections.emptySet();
        }

        Set<Character> set = new HashSet<>(string.length() + 10);
        for (char c: string.toCharArray()){
            set.add(c);
        }
        return set;
    }
}
