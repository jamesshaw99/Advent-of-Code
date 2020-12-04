public class day4 {
    public static boolean rule1(String value){
        if(value.length() == 6){
            return true;
        } else {
            return false;
        }
    }

    public static boolean rule2(String value) {
        char x = value.charAt(0);
        for (int i = 1; i < value.length(); i++){
            if (value.charAt(i) == x){
                return true;
            }
            x = value.charAt(i);
        }
        return false;
    }

    public static boolean rule3(String value) {
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) < value.charAt(i-1)){
                return false;
            }
        }
        return true;
    }

    public static boolean rule4(String value) {
        for (int i = 0; i < value.length(); i++){
            char someChar = value.charAt(i);
            int count = 0;
            for (int j = 0; j < value.length(); j++){
                if (value.charAt(j) == someChar) {
                    count++;
                }
            }
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Part 1");
        int count = 0;
        for (int i = 231832; i <= 767346; i++){
            String value = Integer.toString(i);
            if (rule1(value) && rule2(value) && rule3(value)){
                count++;
            }
        }

        System.out.println("Acceptable passwords: " + count);

        System.out.println("");

        System.out.println("Part 2");
        count = 0;
        for (int i = 231832; i <= 767346; i++){
            String value = Integer.toString(i);
            if (rule1(value) && rule2(value) && rule3(value) && rule4(value)){
                count++;
            }
        }

        System.out.println("Acceptable passwords: " + count);
    }
}
