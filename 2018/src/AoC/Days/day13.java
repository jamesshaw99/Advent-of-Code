package AoC.Days;

import AoC.Helpers.Day;

import java.awt.*;
import java.util.HashMap;
import java.util.stream.Collectors;

public class day13 extends Day {
    char[][] tracks;
    HashMap<Point, Cart> carts;

    public day13(String fileStr) {
        super(fileStr);
        reset();
    }

    public void reset() {
        int maxLen = input.stream().mapToInt(String::length).max().orElse(0);
        tracks = new char[input.size()][maxLen];
        carts = new HashMap<>();
        for(int y = 0; y < input.size(); y++) {
            for(int x = 0; x < input.get(y).length(); x++) {
                tracks[y][x] = input.get(y).charAt(x);
                if(tracks[y][x] == '^' || tracks[y][x] == 'v' || tracks[y][x] == '<' || tracks[y][x] == '>') {
                    Cart c = new Cart(tracks[y][x], x, y);
                    carts.put(c.p, c);
                }
            }
        }
    }

    public String part1() {
        return run(true);
    }

    public String part2() {
        reset();
        return run(false);
    }

    private String run(boolean part1) {
        boolean ok = true;
        int count = 0;

        while(ok) {
            /*if(count % 100 == 0) {
                System.out.println("Step "+count+", carts "+carts.size());
            }*/

            for(int y = 0; y < tracks.length && ok; y++){
                for(int x = 0; x < tracks[0].length && ok; x++){
                    if(tracks[y][x] == '^' || tracks[y][x] == 'v' || tracks[y][x] == '<' || tracks[y][x] == '>') {
                        Point loc = new Point(x, y);
                        Cart c = carts.get(loc);
                        carts.remove(loc);
                        if(c == null){
                            System.out.println("ERROR");
                        } else {
                            c.move(tracks);
                        }
                        Cart hit = carts.get(c.p);
                        if(hit != null) {
                            if(part1) {
                                return "Collision at " + c.p.x + "," + c.p.y + " During step " + count;
                            } else {
                                carts.remove(c.p);
                                tracks[hit.p.y][hit.p.x] = hit.track;
                            }
                        } else {
                            carts.put(c.p, c);
                        }
                    }
                }
            }

            for(Cart c: carts.values()) {
                c.moveTwo(tracks);
            }

            count++;

            if(!part1 && carts.size() == 1){
                return "Last cart is " + carts.values().iterator().next().toString() + " at step " + count;
            }
        }
        return null;
    }

    enum Dir {
        UP, RIGHT, DOWN, LEFT;

        public Dir turnRight() {
            return Dir.values()[(this.ordinal() + 1) % Dir.values().length ];
        }

        public Dir turnLeft() {
            return Dir.values()[(this.ordinal() + 3) % Dir.values().length ];
        }

        public Point move(Point p) {
            switch(this) {
                case UP:
                    p.y--;
                    break;
                case DOWN:
                    p.y++;
                    break;
                case RIGHT:
                    p.x++;
                    break;
                case LEFT:
                    p.x--;
                    break;
            }
            return p;
        }

        public char getCart() {
            switch(this) {
                case UP:
                    return '^';
                case DOWN:
                    return 'v';
                case RIGHT:
                    return '>';
                case LEFT:
                    return '<';
            }
            return '#';
        }
    }

    enum Turn {
        LEFT, STRAIGHT, RIGHT;

        public Dir getNewDir(Dir dir) {
            switch(this){
                case LEFT:
                    return dir.turnLeft();
                case RIGHT:
                    return dir.turnRight();
                default:
                    return dir;
            }
        }

        public Turn nextTurn() {
            return Turn.values()[(this.ordinal() + 1) % Turn.values().length];
        }
    }

    class Cart {
        Point p;
        Dir dir;
        Turn turn;
        char track;

        public String toString() {
            return "Cart at "+p.x+","+p.y+" facing "+dir.name()+" on "+track+" making next turn "+turn.name();
        }

        public Cart (char c, int x, int y) {
            p = new Point(x, y);
            turn = Turn.LEFT;
            switch(c) {
                case '^':
                    dir = Dir.UP;
                    track = '|';
                    break;
                case 'v':
                    dir = Dir.DOWN;
                    track = '|';
                    break;
                case '>':
                    dir = Dir.RIGHT;
                    track = '-';
                    break;
                case '<':
                    dir = Dir.LEFT;
                    track = '-';
            }
        }

        public void move(char[][] tracks) {
            tracks[p.y][p.x] = track;
            p = dir.move(p);
            track = tracks[p.y][p.x];

            if(track == '/') {
                if(dir == Dir.RIGHT || dir == Dir.LEFT){
                    dir = dir.turnLeft();
                } else {
                    dir = dir.turnRight();
                }
            } else if(track == '\\') {
                if(dir == Dir.RIGHT || dir == Dir.LEFT){
                    dir = dir.turnRight();
                } else {
                    dir = dir.turnLeft();
                }
            } else if(track == '+') {
                dir = turn.getNewDir(dir);
                turn = turn.nextTurn();
            }
        }

        public void moveTwo(char[][] tracks) {
            tracks[p.y][p.x] = dir.getCart();
        }
    }
}
