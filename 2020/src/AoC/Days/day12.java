package AoC.Days;

import AoC.Helpers.Day;

public class day12 extends Day {
    private int x = 0, y = 0, deg = 90,
                waypointX = 10, waypointY = 1;
    public day12(String fileStr) {
        super(fileStr);
    }

    public Integer part1() {
        for (String inst: input) {
            char direction = inst.charAt(0);
            int value = Integer.parseInt(inst.substring(1));
            if (direction == 'R'  || direction == 'L') {
                setDeg(direction, value);
            } else if (direction == 'F') {
                moveForward(value);
            } else {
                switch (direction) {
                    case 'N':
                        y += value;
                        break;
                    case 'S':
                        y -= value;
                        break;
                    case 'E':
                        x += value;
                        break;
                    case 'W':
                        x -= value;
                        break;
                }
            }
        }
        return Math.abs(x)+Math.abs(y);
    }

    public Integer part2() {
        x = 0;
        y = 0;
        deg = 90;

        for (String inst: input) {
            char direction = inst.charAt(0);
            int value = Integer.parseInt(inst.substring(1));
            if (direction == 'R'  || direction == 'L') {
                moveWaypoint(direction, value);
            } else if (direction == 'F') {
                moveForwardPt2(value);
            } else {
                switch (direction) {
                    case 'N':
                        waypointY += value;
                        break;
                    case 'S':
                        waypointY -= value;
                        break;
                    case 'E':
                        waypointX += value;
                        break;
                    case 'W':
                        waypointX -= value;
                        break;
                }
            }

        }
        return Math.abs(x)+Math.abs(y);
    }

    private void moveWaypoint(char direction, int value) {
        int temp;
        if (direction == 'L'){
            switch (value) {
                case 90:
                    temp = waypointX;
                    waypointX = waypointY * -1;
                    waypointY = temp;
                    break;
                case 180:
                    waypointX = waypointX * -1;
                    waypointY = waypointY * -1;
                    break;
                case 270:
                    temp = waypointY;
                    waypointY = waypointX * -1;
                    waypointX = temp;
            }
        } else if (direction == 'R'){
            switch (value) {
                case 90:
                    temp = waypointY;
                    waypointY = waypointX * -1;
                    waypointX = temp;
                    break;
                case 180:
                    waypointX = waypointX * -1;
                    waypointY = waypointY * -1;
                    break;
                case 270:
                    temp = waypointX;
                    waypointX = waypointY * -1;
                    waypointY = temp;
            }
        }
    }

    private void moveForwardPt2(int value) {
        for (int i = 0; i < value; i++) {
            x += waypointX;
            y += waypointY;
        }
    }

    public void setDeg(char direction, int value) {
        if (direction == 'L'){
            deg -= value;
        } else if (direction == 'R'){
            deg += value;
        }

        if (deg >= 360) {
            deg -= 360;
        } else if(deg < 0){
            deg += 360;
        }
    }

    public void moveForward(int value) {
        switch(deg){
            case 0:
                y += value;
                break;
            case 180:
                y -= value;
                break;
            case 90:
                x += value;
                break;
            case 270:
                x -= value;
                break;
            default:
                System.out.println(deg);
        }
    }
}
