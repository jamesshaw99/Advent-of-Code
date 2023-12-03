package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day3 extends Day {
    private final List<PartNo> partNos = new ArrayList<>();
    private final List<Symbol> symbols = new ArrayList<>();

    public Day3(String fileStr) {
        super(fileStr);
        processInput();
    }

    private void processInput() {
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            PartNo partNo = null;

            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);

                if (Character.isDigit(character)) {
                    if (partNo == null) {
                        partNo = new PartNo(x, y, Character.toString(character));
                    } else {
                        partNo.appendNumber(Character.toString(character));
                    }
                } else {
                    if (partNo != null) {
                        partNos.add(partNo);
                        partNo = null;
                    }
                    if (character != '.') {
                        symbols.add(new Symbol(x, y, Character.toString(character)));
                    }
                }
            }

            if (partNo != null) {
                partNos.add(partNo);
            }
        }
    }

    public String part1() {
        int total = partNos.stream()
                .filter(partNo -> symbols.stream().anyMatch(partNo::hasSymbol))
                .mapToInt(PartNo::getNumberAsInt)
                .sum();
        return "Sum of part numbers: " + total;
    }

    public String part2() {
        int total = symbols.stream()
                .filter(symbol -> "*".equals(symbol.getVal()))
                .flatMapToInt(symbol -> {
                    if(partNos.stream()
                            .filter(partNo -> partNo.hasSymbol(symbol))
                            .mapToInt(PartNo::getNumberAsInt).count() != 1) {
                        return partNos.stream()
                                .filter(partNo -> partNo.hasSymbol(symbol))
                                .mapToInt(PartNo::getNumberAsInt)
                                .reduce((first, second) -> first * second)
                                .stream();
                    }
                    return null;
                })
                .sum();
        return "Sum of the gear ratios: " + total;
    }

    private static class PartNo {
        private final int x, y;
        private String number = "";

        public PartNo(int x, int y, String no) {
            this.x = x;
            this.y = y;
            this.number = no;
        }

        public void appendNumber(String no) {
            this.number += no;
        }

        public boolean hasSymbol(Symbol symbol) {
            for(int i = 0; i < number.length(); i++){
                if(Math.hypot(this.x + i - symbol.getX(), this.y - symbol.getY()) < 2){
                    return true;
                }
            }
            return false;
        }

        public int getNumberAsInt() {
            return Integer.parseInt(number);
        }
    }

    private static class Symbol {
        private final int x, y;
        private final String val;

        public Symbol(int x, int y, String val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getVal() {
            return val;
        }
    }
}
