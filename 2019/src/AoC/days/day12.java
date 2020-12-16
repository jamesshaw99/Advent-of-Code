package AoC.days;

import AoC.Day;
import AoC.intcode.ProgramExecutor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class day12 extends Day {
    List<Moon> moons = new ArrayList<>();
    public day12(String fileStr) {
        super(fileStr);
        for (String coordinates: input) {
            String[] rawCoor = coordinates.substring(1, coordinates.length()-1).split(",");
            for (int i = 0; i < rawCoor.length; i++){
                rawCoor[i] = rawCoor[i].substring(rawCoor[i].indexOf('=')+1);
            }
            moons.add(new Moon(rawCoor[0], rawCoor[1],rawCoor[2]));
        }
    }

    public String part1() {
        int steps = 1000;

        List<Moon> moons1 = moons.stream().map(Moon::new).collect(toList());

        for (int s = 0; s < steps; s++) {

            for (int i = 0; i < moons1.size(); i++) {
                for (int j = 0; j < moons1.size(); j++) {
                    if (i != j) {
                        int x = 0, y = 0, z = 0;
                        Moon moon1 = moons1.get(i), moon2 = moons1.get(j);
                        if (moon1.posX < moon2.posX) {
                            x++;
                        } else if (moon1.posX > moon2.posX) {
                            x--;
                        }
                        if (moon1.posY < moon2.posY) {
                            y++;
                        } else if (moon1.posY > moon2.posY) {
                            y--;
                        }
                        if (moon1.posZ < moon2.posZ) {
                            z++;
                        } else if (moon1.posZ > moon2.posZ) {
                            z--;
                        }

                        moon1.changeVel(x, y, z);
                    }
                }
            }
            for (Moon moon : moons1) {
                moon.move();
            }
        }
        int sum = 0;
        for (Moon moon : moons1) {
            int pot = Math.abs(moon.posX) + Math.abs(moon.posY) + Math.abs(moon.posZ);
            int kin = Math.abs(moon.velX) + Math.abs(moon.velY) + Math.abs(moon.velZ);
            sum += pot*kin;
        }

        return "Total Energy after 1000 steps: " + sum;
    }

    public String part2() {
        boolean complete = false;
        List<Moon> moonsStart = moons.stream().map(Moon::new).collect(toList());

        long steps = 0;
        while(!complete) {
            for (int i = 0; i < moons.size(); i++) {
                for (int j = 0; j < moons.size(); j++) {
                    if (i != j) {
                        int x = 0, y = 0, z = 0;
                        Moon moon1 = moons.get(i), moon2 = moons.get(j);
                        if (moon1.posX < moon2.posX) {
                            x++;
                        } else if (moon1.posX > moon2.posX) {
                            x--;
                        }
                        if (moon1.posY < moon2.posY) {
                            y++;
                        } else if (moon1.posY > moon2.posY) {
                            y--;
                        }
                        if (moon1.posZ < moon2.posZ) {
                            z++;
                        } else if (moon1.posZ > moon2.posZ) {
                            z--;
                        }

                        moon1.changeVel(x, y, z);
                    }
                }
            }

            for (Moon moon : moons) {
                moon.move();
            }
            steps++;

            if (moons.get(0).posX == moonsStart.get(0).posX && moons.get(0).posY == moonsStart.get(0).posY && moons.get(0).posZ == moonsStart.get(0).posZ &&
                    moons.get(1).posX == moonsStart.get(1).posX && moons.get(1).posY == moonsStart.get(1).posY && moons.get(1).posZ == moonsStart.get(1).posZ &&
                    moons.get(2).posX == moonsStart.get(2).posX && moons.get(2).posY == moonsStart.get(2).posY && moons.get(2).posZ == moonsStart.get(2).posZ &&
                    moons.get(3).posX == moonsStart.get(3).posX && moons.get(3).posY == moonsStart.get(3).posY && moons.get(3).posZ == moonsStart.get(3).posZ) {
                complete = true;
            }
        }
        return "Steps: " + steps;
    }

    private class Moon {
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
    }
}
