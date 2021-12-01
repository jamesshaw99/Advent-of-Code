package AoC.Days;

import AoC.Helpers.Day;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class day15 extends Day {
    private int[][] grid;
    private Unit[][] unitMap;
    private List<Unit> units;
    private int rounds;

    public day15(String fileStr) {
        super(fileStr);
        reset();
    }

    public String part1() {
        int result = 0;
        while (result == 0) {
            result = round();
        }
        return String.valueOf(result);
    }

    public String part2() {
        AtomicInteger boost = new AtomicInteger(4);
        while (true) {
            reset();
            int elves = (int) units.stream()
                    .filter(Unit::isElf)
                    .count();
            rounds = 0;
            units.stream()
                    .filter(Unit::isElf)
                    .forEach(x -> x.attack = boost.get());
            int result = 0;
            while (result == 0) {
                result = round();
            }
            if (units.stream()
                    .filter(Unit::isElf)
                    .filter(Unit::isAlive)
                    .count() == elves) {
                return String.valueOf(result);
            }
            boost.incrementAndGet();
        }
    }

    public void reset(){
        grid = new int[input.size()][input.get(0).length()];
        unitMap = new Unit[input.size()][input.get(0).length()];
        units = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (c == 'G' || c == 'E') {
                    Unit unit = new Unit();
                    unitMap[j][i] = unit;
                    unit.position = new Point(j, i);
                    unit.elf = c == 'E';
                    units.add(unit);
                }
                grid[j][i] = c;
            }
        }
    }

    class Unit {
        int attack = 3, HP = 200;
        boolean elf, alive = true;
        Point position;

        int getX() { return this.position.x; }
        int getY() { return this.position.y; }
        int getHP() { return this.HP; }
        boolean isAlive() { return this.alive; }
        boolean isElf() { return this.elf; }

        boolean turn() {
            if(alive) {
                List<Unit> enemies = getEnemies(this);
                if(enemies.size() == 0){
                    return true;
                }
                Unit enemy = getAdjacentEnemy(this);
                if(enemy == null) {
                    move(enemies);
                    enemy = getAdjacentEnemy(this);
                    if(enemy == null) {
                        return false;
                    }
                }
                attack(enemy);
            }
            return false;
        }

        void move(List<Unit> enemies) {
            Point m = bestMove(this, enemies);
            if(m != null) {
                grid[this.getX()][this.getY()] = '.';
                unitMap[this.getX()][this.getY()] = null;
                this.position = m;
                grid[this.getX()][this.getY()] = this.elf ? 'E' : 'G';
                unitMap[this.getX()][this.getY()] = this;
            }
        }

        void attack(Unit enemy) {
            enemy.HP -= this.attack;
            if(enemy.HP <= 0) {
                enemy.alive = false;
                unitMap[enemy.getX()][enemy.getY()] = null;
                grid[enemy.getX()][enemy.getY()] = ',';
            }
        }
    }

    private List<Unit> getEnemies(Unit n) {
        return units.stream().filter(x -> x.elf != n.elf).filter(Unit::isAlive).collect(Collectors.toList());
    }

    private Point bestMove(Unit u, List<Unit> enemies) {
        List<List<Point>> paths = new ArrayList<>();

        for(Unit enemy: enemies) {
            for(Point each: getAdjacent(enemy.position)) {
                List<Point> path = bfs(u.position, each);
                if(!path.isEmpty()){
                    paths.add(path);
                }
            }
        }

        if(paths.size() == 0){
            return null;
        }

        int min = paths.stream().mapToInt(List::size).min().orElse(0);

        if(min == 0) {
            return null;
        }

        Point target = paths.stream().filter(x -> x.size() == min).map(x -> x.get(x.size() - 1)).min(comparing(Point::getY).thenComparing(Point::getX)).get();

        paths = new ArrayList<>();
        List<Point> adj = getAdjacent(u.position);
        for(Point each: adj){
            List<Point> path = bfs(target, each);
            if(!path.isEmpty()){
                paths.add(path);
            }
        }

        int min2 = paths.stream().mapToInt(List::size).min().getAsInt();

        return paths.stream().filter(x -> x.size() == min2).map(x -> x.get(x.size() - 1)).min(comparing(Point::getY).thenComparing(Point::getX)).get();
    }

    private List<Point> bfs(Point start, Point target) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited= new HashSet<>();
        Map<Point, Point> parents = new HashMap<>();

        queue.add(start);

        while(!queue.isEmpty()){
            Point curr = queue.poll();
            if(curr.equals(target)) {
                List<Point> path = new ArrayList<>();
                curr = target;
                while(!curr.equals(start)){
                    path.add(curr);
                    curr = parents.get(curr);
                }
                path.add(start);
                Collections.reverse(path);
                return path;
            }

            for(Point adj : getAdjacent(curr)) {
                if(!visited.contains(adj)){
                    queue.add(adj);
                    parents.put(adj, curr);
                    visited.add(adj);
                }
            }
        }
        return new ArrayList<>();
    }

    private void sortReadingOrder() {
        units.sort(comparing(Unit::getY).thenComparing(Unit::getX));
    }

    private List<Point> getAdjacent(Point n) {
        List<Point> adj = new ArrayList<>();
        for(Direction dir: Direction.values()){
            int nx = (int) (n.getX() + dir.getDx());
            int ny = (int) (n.getY() + dir.getDy());
            if(grid[nx][ny] == '.') {
                adj.add(new Point(nx, ny));
            }
        }
        return adj;
    }

    private Unit getAdjacentEnemy(Unit u) {
        List<Unit> adj = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            int nx = (int) (u.position.getX() + dir.getDx());
            int ny = (int) (u.position.getY() + dir.getDy());
            if (unitMap[nx][ny] != null && unitMap[nx][ny].elf == !u.elf) {
                adj.add(unitMap[nx][ny]);
            }
        }
        if (adj.size() == 0) return null;
        if (adj.size() == 1) return adj.get(0);
        return adj.stream().min(comparing(Unit::getHP).thenComparing(Unit::getY).thenComparing(Unit::getX)).get();
    }

    private int round() {
        sortReadingOrder();
        for (Unit each : units) {
            if (each.alive) {
                boolean done = each.turn();
                if (done) {
                    return rounds * units.stream().filter(Unit::isAlive).mapToInt(Unit::getHP).sum();
                }
            }
        }
        rounds++;
        return 0;
    }

    enum Direction {
        UP, RIGHT, DOWN, LEFT;

        public int getDx() {
            switch (this) {
                case RIGHT:
                    return 1;
                case LEFT:
                    return -1;
            }
            return 0;
        }

        public int getDy() {
            switch (this) {
                case DOWN:
                    return 1;
                case UP:
                    return -1;
            }
            return 0;
        }
    }
}
