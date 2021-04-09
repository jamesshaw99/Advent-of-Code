package AoC.Days;

import AoC.Helpers.Day;

public class day3 extends Day {
    String[][] fabric = new String[1000][1000];
    public day3(String fileStr) {
        super(fileStr);
        for (int x = 0; x < 1000; x++){
            for (int y = 0; y < 1000; y++) {
                fabric[x][y] = ".";
            }
        }
    }

    public String part1() {
        int count = 0;
        for(String claimRaw: input) {
            String[] claim = claimRaw.replace(":", " ").split(" ");
            int id = Integer.parseInt(claim[0].replace("#", ""));
            String[] coord = claim[2].split(",");
            String[] size = claim[4].split("x");

            for(int x = Integer.parseInt(coord[0]); x < Integer.parseInt(coord[0]) + Integer.parseInt(size[0]); x++) {
                for (int y = Integer.parseInt(coord[1]); y < Integer.parseInt(coord[1]) + Integer.parseInt(size[1]); y++) {
                    if(fabric[x][y] == "."){
                        fabric[x][y] = String.valueOf(id);
                    } else if(fabric[x][y] != "X") {
                        count++;
                        fabric[x][y] = "X";
                    }
                }
            }
        }

        return "Total inches of overlapping fabric: " + count;
    }

    public String part2() {
        int finalID = 0;
        for(String claimRaw: input) {
            String[] claim = claimRaw.replace(":", " ").split(" ");
            int id = Integer.parseInt(claim[0].replace("#", ""));
            String[] coord = claim[2].split(",");
            String[] size = claim[4].split("x");

            int count = 0;
            for(int x = Integer.parseInt(coord[0]); x < Integer.parseInt(coord[0]) + Integer.parseInt(size[0]); x++) {
                for (int y = Integer.parseInt(coord[1]); y < Integer.parseInt(coord[1]) + Integer.parseInt(size[1]); y++) {
                    if(fabric[x][y] == "X"){
                        count++;
                    }
                }
            }
            if(count == 0){
                finalID = id;
                break;
            }
        }
        return "Only claim with no overlap: " + finalID;
    }
}
