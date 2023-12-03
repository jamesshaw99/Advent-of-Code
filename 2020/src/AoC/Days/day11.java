package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;

public class day11 extends Day {
    private List<String> seatsStart;
    private List<String> seatsEnd;
    public day11(String fileStr) {
        super(fileStr);
        seatsStart = new ArrayList<>(input);
    }

    public Integer part1() {
        seatsEnd = new ArrayList<>(seatsStart);
        int seatChanges = Integer.MAX_VALUE;
        int seats = 0;
        while (seatChanges > 0) {
            seatChanges = 0;
            for (int i = 0; i < seatsStart.size(); i++) {
                for (int j = 0; j < seatsStart.get(i).length(); j++) {
                    if (checkSeat(i, j)) {
                        seatChanges++;
                        if (seatsStart.get(i).charAt(j) == 'L') {
                            seatsEnd.set(i, seatsEnd.get(i).substring(0, j) + '#' + seatsEnd.get(i).substring(j + 1));
                        } else if (seatsStart.get(i).charAt(j) == '#') {
                            seatsEnd.set(i, seatsEnd.get(i).substring(0, j) + 'L' + seatsEnd.get(i).substring(j + 1));
                        }
                    }
                }
            }
            seatsStart = new ArrayList<>(seatsEnd);
        }
        for (String line: seatsStart){
            for (int i = 0; i < line.length(); i++){
                if (line.charAt(i) == '#'){
                    seats++;
                }
            }
        }

        return seats;
    }

    public Integer part2() {
        seatsStart = new ArrayList<>(input);
        seatsEnd = new ArrayList<>(seatsStart);
        int seatChanges = Integer.MAX_VALUE;
        int seats = 0;
        while (seatChanges > 0) {
            seatChanges = 0;
            for (int i = 0; i < seatsStart.size(); i++) {
                for (int j = 0; j < seatsStart.get(i).length(); j++) {
                    if (checkSeatPt2(i, j)) {
                        seatChanges++;
                        if (seatsStart.get(i).charAt(j) == 'L') {
                            seatsEnd.set(i, seatsEnd.get(i).substring(0, j) + '#' + seatsEnd.get(i).substring(j + 1));
                        } else if (seatsStart.get(i).charAt(j) == '#') {
                            seatsEnd.set(i, seatsEnd.get(i).substring(0, j) + 'L' + seatsEnd.get(i).substring(j + 1));
                        }
                    }
                }
            }
            seatsStart = new ArrayList<>(seatsEnd);
        }
        for (String line: seatsStart){
            for (int i = 0; i < line.length(); i++){
                if (line.charAt(i) == '#'){
                    seats++;
                }
            }
        }

        return seats;
    }

    public boolean checkSeat(int row, int col){
        char targetSeat = seatsStart.get(row).charAt(col);
        if (targetSeat == '.') return false;
        int count = targetSeat == 'L' ? 0 : -1;
        for (int i = row - 1; i <= row + 1; i++){
            if (i < 0 || i >= seatsStart.size()) continue;
            for (int j = col - 1; j <= col + 1; j++){
                if (j < 0 || j >= seatsStart.get(i).length()) continue;
                if (targetSeat == 'L' && seatsStart.get(i).charAt(j) == '#'){
                    count++;
                } else if (targetSeat == '#' && seatsStart.get(i).charAt(j) == '#') {
                    count++;
                }
            }
        }
        return (targetSeat == 'L' && count == 0) || (targetSeat == '#' && count >= 4);
    }

    public boolean checkSeatPt2(int row, int col) {

        char targetSeat = seatsStart.get(row).charAt(col), currentSeat;
        int count = 0;
        for (int i = row+1; i < seatsStart.size(); i++){
            currentSeat = seatsStart.get(i).charAt(col);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = row -1; i >= 0; i--){
            currentSeat = seatsStart.get(i).charAt(col);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = col + 1; i < seatsStart.get(row).length(); i++){
            currentSeat = seatsStart.get(row).charAt(i);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = col - 1; i >= 0; i--){
            currentSeat = seatsStart.get(row).charAt(i);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = row + 1, j = col + 1; i < seatsStart.size() && j < seatsStart.get(0).length(); i++, j++){
            currentSeat = seatsStart.get(i).charAt(j);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--){
            currentSeat = seatsStart.get(i).charAt(j);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = row + 1, j = col - 1; i < seatsStart.size() && j >= 0; i++, j--){
            currentSeat = seatsStart.get(i).charAt(j);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < seatsStart.get(0).length(); i--, j++){
            currentSeat = seatsStart.get(i).charAt(j);
            if (targetSeat == 'L' && currentSeat == '#'){
                count++;
            } else if (targetSeat == '#' && currentSeat == '#') {
                count++;
            }
            if(currentSeat != '.') break;
        }
        return (targetSeat == 'L' && count == 0) || (targetSeat == '#' && count >= 5);
    }
}
