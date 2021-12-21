package AoC.Days;

import AoC.Helpers.Day;

public class day11 extends Day {
    private int[][] octopuses = new int[10][10];
    private int flashes = 0;

    public day11(String fileStr) {
        super(fileStr);
        for(int y = 0; y < input.size(); y++){
            for (int x = 0; x < input.get(y).length(); x++){
                octopuses[y][x] = Integer.parseInt(String.valueOf(input.get(y).charAt(x)));
            }
        }
    }

    public String part1(){
        for(int i = 0; i < 100; i++){
            step();
        }
        return String.format("Total number of flashes after 100 steps: %d", flashes);
    }

    public String part2(){
        int step = 100;
        while(!allFlash()){
            step();
            step++;
        }
        return String.format("All flash after %d steps", step);
    }

    private void step() {
        for(int y = 0; y < octopuses.length; y++) {
            for (int x = 0; x < octopuses[y].length; x++) {
                octopuses[y][x]++;
            }
        }
        tenCheck();
    }

    private void tenCheck() {
        boolean flash = false;
        for(int y = 0; y < octopuses.length; y++) {
            for (int x = 0; x < octopuses[y].length; x++) {
                if(octopuses[y][x] > 9){
                    octopuses[y][x] = 0;
                    flash = true;
                    flashes++;
                    incrementNeighbours(y, x);
                }
            }
        }
        if(flash)
            tenCheck();
    }

    private void incrementNeighbours(int y, int x) {
        for (int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0){
                    continue;
                }
                int newX = x+i, newY = y+j;
                if(newY >= 0 && newX >= 0 && newY < octopuses.length && newX < octopuses[0].length && octopuses[newY][newX] != 0){
                    octopuses[newY][newX]++;
                }
            }
        }
    }

    private void print(int i){
        System.out.printf("After step %d:\n", i+1);

        for (int[] octopus : octopuses) {
            for (int j : octopus) {
                System.out.print(j);
            }
            System.out.print("\n");
        }


        System.out.print("\n");
    }

    private boolean allFlash() {
        boolean all = true;
        outer:
        for(int y = 0; y < octopuses.length; y++) {
            for (int x = 0; x < octopuses[y].length; x++) {
                if(octopuses[y][x] != 0) {
                    all = false;
                    break outer;
                }
            }
        }
        return all;
    }
}
