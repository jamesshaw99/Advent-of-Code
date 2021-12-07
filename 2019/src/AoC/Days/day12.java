package AoC.Days;

import AoC.helpers.Day;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class day12 extends Day {
    List<Moon> moons = new ArrayList<>();
    List<Moon> moonsStart;
    public day12(String fileStr) {
        super(fileStr);
        for (String coordinates: input) {
            String[] rawCoor = coordinates.substring(1, coordinates.length()-1).split(",");
            for (int i = 0; i < rawCoor.length; i++){
                rawCoor[i] = rawCoor[i].substring(rawCoor[i].indexOf('=')+1);
            }
            moons.add(new Moon(rawCoor[0], rawCoor[1], rawCoor[2]));
        }
        moonsStart = moons.stream().map(Moon::new).collect(toList());
    }

    public String part1() {
        int steps = 1000;

        for (int s = 0; s < steps; s++) {
            step();
        }
        int sum = 0;
        for (Moon moon : moons) {
            int pot = Math.abs(moon.posX) + Math.abs(moon.posY) + Math.abs(moon.posZ);
            int kin = Math.abs(moon.velX) + Math.abs(moon.velY) + Math.abs(moon.velZ);
            sum += pot*kin;
        }

        return "Total Energy after 1000 steps: " + sum;
    }

    public String part2() {

        int steps = 1000;
        int x = 0, y = 0, z = 0;
        while(x == 0 || y == 0 || z == 0) {
            step();
            steps++;
            if(x == 0 && isEqual('x')){
                x = steps;
            } else if (y == 0 && isEqual('y')){
                y = steps;
            } else if (z == 0 && isEqual('z')){
                z = steps;
            }
        }
        return "Steps: " + lowestCommonMultiple(lowestCommonMultiple(x,y),z);
    }

    private long lowestCommonMultiple(long a, long b) {
        return (a * b) / greatestCommonDenominator(a, b);
    }

    private long greatestCommonDenominator(long a, long b) {
        if (a == 0){
            return b;
        } else {
            return greatestCommonDenominator(b % a, a);
        }
    }

    private boolean isEqual(char axis) {
        for(int i = 0; i < moons.size(); i++) {
            if (axis == 'x' && moons.get(i).posX != moonsStart.get(i).posX){
                return false;
            } else if (axis == 'y' && moons.get(i).posY != moonsStart.get(i).posY){
                return false;
            } else if (axis == 'z' && moons.get(i).posZ != moonsStart.get(i).posZ){
                return false;
            }
        }

        for (Moon moon: moons){
            if(axis == 'x' && moon.velX != 0){
                return false;
            } else if (axis == 'y' && moon.velY != 0) {
                return false;
            } else if (axis == 'z' && moon.velZ != 0) {
                return false;
            }
        }
        return true;
    }

    private void step() {
        for (int i = 0; i < moons.size(); i++) {
            Moon moon1 = moons.get(i);
            for (int j = i+1; j < moons.size(); j++) {
                int x1 = 0, y1 = 0, z1 = 0,
                        x2 = 0, y2 = 0, z2 = 0;
                Moon moon2 = moons.get(j);
                if (moon1.posX < moon2.posX) {
                    x1++;
                    x2--;
                } else if (moon1.posX > moon2.posX) {
                    x1--;
                    x2++;
                }
                if (moon1.posY < moon2.posY) {
                    y1++;
                    y2--;
                } else if (moon1.posY > moon2.posY) {
                    y1--;
                    y2++;
                }
                if (moon1.posZ < moon2.posZ) {
                    z1++;
                    z2--;
                } else if (moon1.posZ > moon2.posZ) {
                    z1--;
                    z2++;
                }

                moon1.changeVel(x1, y1, z1);
                moon2.changeVel(x2, y2, z2);
            }
        }
        for (Moon moon : moons) {
            moon.move();
        }
    }

    private static class Moon {
        private int posX, posY, posZ, velX = 0, velY = 0, velZ = 0;

        public Moon (String x, String y, String z) {
            this.posX = Integer.parseInt(x);
            this.posY = Integer.parseInt(y);
            this.posZ = Integer.parseInt(z);
        }

        public Moon(Moon moon) {
            this.posX = moon.posX;
            this.posY = moon.posY;
            this.posZ = moon.posZ;
        }

        public void changeVel (int x, int y, int z){
            this.velX += x;
            this.velY += y;
            this.velZ += z;
        }

        public void move() {
            posX += velX;
            posY += velY;
            posZ += velZ;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Moon)){
                return false;
            }
            Moon moon = (Moon) obj;
            return moon.posX == posX && moon.posY == posY && moon.posZ == posZ;
        }
    }
}
