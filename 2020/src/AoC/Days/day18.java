package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;


public class day18 extends Day {
    public day18(String fileStr) {
        super(fileStr);
        for (String line: input) {
            line.replaceAll(" ", "");
        }
    }

    public Double part1() {
        List<Double> values = new ArrayList<>();
        for(String line: input) {
            values.add(doMath(line, true));
        }

        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    public Double part2() {
        List<Double> values = new ArrayList<>();
        for(String line: input) {
            values.add(doMath(line, false));
        }
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    public double doMath(String expression, boolean part1) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while(ch == ' ') nextChar();
                if(ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if(pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                if(part1) {
                    double x = parseFactor();
                    for (; ; ) {
                        if (eat('+')) x += parseFactor();
                        else if (eat('-')) x -= parseFactor();
                        else if (eat('*')) x *= parseFactor();
                        else if (eat('/')) x /= parseFactor();
                        else return x;
                    }
                } else {
                    double x = parseTerm();
                    for (; ; ) {
                        if (eat('*')) x *= parseTerm();
                        else if (eat('/')) x /= parseTerm();
                        else return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('+')) x += parseFactor();
                    else if (eat('-')) x -= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if(eat('+')) return parseFactor();
                if(eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if(eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}
