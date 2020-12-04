import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day1 {
    public static void main(String[] args) {
        List<String> data = new ArrayList<String>();
        File file = new File("data.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                data.add(text);
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

        int passportNo = 0;
        List<Map<String, String>> passports = new ArrayList<>();
        Map<String, String> passport = new HashMap<>();
        for (String line: data){
            if (line != null && !line.isEmpty()){
                String[] parts = line.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    String[] part = parts[i].split(":");
                    passport.put(part[0], part[1]);
                }
            } else {
                passports.add(passport);
                passport = new HashMap<>();
            }
        }
        passports.add(passport);

        int validPassports = 0;
        String[] reqFields = "byr,iyr,eyr,hgt,hcl,ecl,pid".split(",");
        for (Map<String, String> passportData : passports) {
            boolean valid = true;
            for (String field : reqFields) {
                String result = passportData.get(field);
                if(result == null) {
                    valid = false;
                } else if(field.equals("byr") && (Integer.parseInt(result) < 1920 || Integer.parseInt(result) > 2002)){
                    valid = false;
                } else if(field.equals("iyr") && (Integer.parseInt(result) < 2010 || Integer.parseInt(result) > 2020)){
                    valid = false;
                } else if(field.equals("eyr") && (Integer.parseInt(result) < 2020 || Integer.parseInt(result) > 2030)){
                    valid = false;
                } else if(field.equals("hgt")){
                    if (!(result.endsWith("cm") || result.endsWith("in"))) {
                        valid = false;
                    } else {
                        int length = Integer.parseInt(result.substring(0,result.length()-2));
                        if((result.endsWith("cm") && (length < 150 || length > 193)) || (result.endsWith("in") && (length < 59 || length > 76))){
                            valid = false;
                        }
                    }
                } else if(field.equals("hcl")){
                    Pattern pattern = Pattern.compile("#[0-9a-fA-F]{6}$");
                    Matcher matcher = pattern.matcher(result);
                    if (!matcher.find()){
                        valid = false;
                    }
                } else if (field.equals("ecl")){
                    List<String> eyeColours = Arrays.asList("amb","blu","brn","gry","grn","hzl","oth");
                    if(!eyeColours.contains(result)){
                        valid = false;
                    }
                } else if (field.equals("pid")){
                    Pattern pattern = Pattern.compile("^[0-9]{9}$");
                    Matcher matcher = pattern.matcher(result);
                    if (!matcher.find()){
                        valid = false;
                    }
                }
            }
            if(valid){
                validPassports++;
            }
        }
        System.out.println("Number of valid passports: " + validPassports);
    }
}
