package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;

public class day10 extends Day {
    List<Point> points = new ArrayList<>();
    int minX = Integer.MIN_VALUE, maxX = Integer.MAX_VALUE, minY = Integer.MIN_VALUE, maxY = Integer.MAX_VALUE, xDiff = Integer.MAX_VALUE, yDiff = Integer.MAX_VALUE, seconds = 0;
    boolean first = true;

    public day10(String fileStr) {
        super(fileStr);
        for(String point: input) {
            this.points.add(new Point(point));
        }

        do {
            if(this.first) {
                this.first = false;
            } else {
                this.xDiff = this.maxX - this.minX;
                this.yDiff = this.maxY - this.minY;
            }

            this.minX = Integer.MAX_VALUE;
            this.maxX = Integer.MIN_VALUE;
            this.minY = Integer.MAX_VALUE;
            this.maxY = Integer.MIN_VALUE;

            for(Point point: this.points) {
                point.timeForward();

                if(point.getX() < this.minX) {
                    this.minX = point.getX();
                }
                if (point.getX() > this.maxX) {
                    this.maxX = point.getX();
                }
                if (point.getY() < this.minY) {
                    this.minY = point.getY();
                }
                if (point.getY() > this.maxY) {
                    this.maxY = point.getY();
                }
            }
            this.seconds++;
        } while((this.maxX - this.minX) < this.xDiff && (this.maxY - this.minY) < this.yDiff);

        this.minX = Integer.MAX_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.minY = Integer.MAX_VALUE;
        this.maxY = Integer.MIN_VALUE;
        for (Point point : this.points) {
            if (point.getX() < this.minX) {
                this.minX = point.getX();
            }
            if (point.getX() > this.maxX) {
                this.maxX = point.getX();
            }

            if (point.getY() < this.minY) {
                this.minY = point.getY();
            }
            if (point.getY() > this.maxY) {
                this.maxY = point.getY();
            }

            point.timeReverse();
        }
        this.seconds--;
    }

    public String part1() {
        String result = "";
        for(int y = this.minY; y <= this.maxY; y++){
            for(int x = this.minX; x <= this.maxY; x++){
                boolean found = false;
                for(Point point: points) {
                    if(point.atPos(x, y)){
                        found = true;
                        break;
                    }
                }

                if(found){
                    result += "#";
                } else {
                    result += ".";
                }
            }
            result += "\n";
        }
        return result;
    }

    public String part2() {
        return "Number of seconds: " + this.seconds;
    }

    class Point {
        private int x, y;
        private final int vx, vy;

        public Point(String data){
            String parts[] = data.split("<");
            String positions[] = parts[1].substring(0, parts[1].indexOf(">")).split(",");
            this.x = Integer.parseInt(positions[0].trim());
            this.y = Integer.parseInt(positions[1].trim());
            String velocities[] = parts[2].substring(0, parts[2].length() - 1).split(",");
            this.vx = Integer.parseInt(velocities[0].trim());
            this.vy = Integer.parseInt(velocities[1].trim());
        }

        public void timeForward() {
            this.x += this.vx;
            this.y += this.vy;
        }

        public void timeReverse() {
            this.x -= this.vx;
            this.y -= this.vy;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public boolean atPos(int x, int y) {
            return this.x == x && this.y == y;
        }
    }
}

