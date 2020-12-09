package AoC.days;

import AoC.Day;

public class day3 extends Day {
    public day3(String fileStr) {
        super(fileStr);
    }

    public int part1() {
        int x = 0, y = 0, noTrees = 0, maxX = input.get(0).length();
        while (y < input.size()) {
            if (input.get(y).charAt(x) == '#') {
                noTrees++;
            }
            x += 3;
            y++;
            if (x >= maxX) {
                x -= maxX;
            }
        }
        return noTrees;
    }

    public long part2() {
        int x, y, noTrees, maxX = input.get(0).length();
        Integer[][] Routes = {{1,1},{3,1},{5,1},{7,1},{1,2}};
        long total = 0;
        for(Integer[] row: Routes){
            int routeX = row[0], routeY = row[1];
            x = 0;
            y = 0;
            noTrees = 0;
            while(y < input.size()){
                if(input.get(y).charAt(x) == '#'){
                    noTrees++;
                }
                x += routeX;
                y += routeY;
                if(x >= maxX){
                    x -= maxX;
                }
            }
            if (total > 0){
                total = total * noTrees;
            } else {
                total = noTrees;
            }
        }
        return total;
    }
}
