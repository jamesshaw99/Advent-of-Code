package AoC.Days;

import AoC.Helpers.Day;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class Day10 extends Day {
    private char[][] grid;
    private boolean[][] visited;

    public Day10(String fileStr) {
        super(fileStr);
        initializeGrid();
    }

    private void initializeGrid() {
        int rows = input.size();
        int cols = input.get(0).length();
        grid = new char[rows][cols];
        visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            String rowString = input.get(i).trim();
            for (int j = 0; j < cols; j++) {
                grid[i][j] = rowString.charAt(j);
            }
        }
    }

    public String part1() {
        long steps = findFarthestPoint();
        return "Steps to farthest point: " + steps;
    }

    public String part2() {
        int enclosedTiles = countEnclosedTiles();
        return "Tiles enclosed by the loop: " + enclosedTiles;
    }

    public long findFarthestPoint() {
        long[][] distances = new long[grid.length][grid[0].length];
        int[] startCoords = findStartCoordinates();

        dfs(startCoords[0], startCoords[1], distances, 0);

        return getMaxDistance(distances)-1;
    }

    private long getMaxDistance(long[][] distances) {
        long maxDistance = 0;
        for (long[] distance : distances) {
            for (long j : distance) {
                if (j > maxDistance) {
                    maxDistance = j;
                }
            }
        }

        return maxDistance;
    }

    private int[] findStartCoordinates() {
        int[] startCoords = {-1, -1};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') {
                    startCoords[0] = i;
                    startCoords[1] = j;
                    break;
                }
            }
        }

        return startCoords;
    }

    private void dfs(int startRow, int startCol, long[][] distances, int curDistance) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{startRow, startCol, curDistance});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int row = current[0];
            int col = current[1];
            int distance = current[2];

            if (row < 0 || row >= numRows || col < 0 || col >= numCols || grid[row][col] == '.') {
                continue;
            }

            int currentDistance = getDistance(row, col, distance);

            if (distances[row][col] != 0 && distances[row][col] <= currentDistance) {
                continue;
            }

            distances[row][col] = currentDistance;
            visited[row][col] = true;

            if (isValidMove(1, row, col)) {
                stack.push(new int[]{row - 1, col, currentDistance}); // Up
            }
            if (isValidMove(2, row, col)) {
                stack.push(new int[]{row + 1, col, currentDistance}); // Down
            }
            if (isValidMove(3, row, col)) {
                stack.push(new int[]{row, col - 1, currentDistance}); // Left
            }
            if (isValidMove(4, row, col)) {
                stack.push(new int[]{row, col + 1, currentDistance}); // Right
            }
        }
    }

    private int getDistance(int row, int col, int curDistance) {
        int upDis = (isValidMove(1, row, col)) ? curDistance : 0;
        int downDis = (isValidMove(2, row, col)) ? curDistance : 0;
        int leftDis = (isValidMove(3, row, col)) ? curDistance : 0;
        int rightDis = (isValidMove(4, row, col)) ? curDistance : 0;

        return Math.max(Math.max(upDis, downDis), Math.max(leftDis, rightDis)) + 1;
    }

    private boolean isValidMove(int dir, int row, int col) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            return false;
        }

        char currentCell = grid[row][col];
        char neighborCell;

        switch (dir) {
            case 1:
                neighborCell = (row - 1 >= 0) ? grid[row - 1][col] : '.';
                return "S|JL".indexOf(currentCell) >= 0 && "|F7".indexOf(neighborCell) >= 0;
            case 2:
                neighborCell = (row + 1 < numRows) ? grid[row + 1][col] : '.';
                return "S|F7".indexOf(currentCell) >= 0 && "|LJ".indexOf(neighborCell) >= 0;
            case 3:
                neighborCell = (col - 1 >= 0) ? grid[row][col - 1] : '.';
                return "S-7J".indexOf(currentCell) >= 0 && "-LF".indexOf(neighborCell) >= 0;
            case 4:
                neighborCell = (col + 1 < numCols) ? grid[row][col + 1] : '.';
                return "S-LF".indexOf(currentCell) >= 0 && "-7J".indexOf(neighborCell) >= 0;
            default:
                return false;
        }
    }


    public int countEnclosedTiles() {
        int enclosedTiles = 0;

        for (int i = 0; i < grid.length; i++) {
            boolean inLoop = false;
            for (int j = 0; j < grid[0].length; j++) {
                inLoop = isCellInLoop(i, j, inLoop);

                if(inLoop && !visited[i][j]){
                    enclosedTiles++;
                }
            }
        }

        return enclosedTiles;
    }

    private boolean isCellInLoop(int row, int col, boolean current) {
        if (visited[row][col] && grid[row][col] == '|') {
            return !current;
        }
        if(visited[row][col] && grid[row][col] == '7'){
            for(int x = col; x > 0; x--){
                if(grid[row][x] == 'F'){
                    break;
                }
                if(grid[row][x] == 'L'){
                    return !current;
                }
            }
        }
        if(visited[row][col] && grid[row][col] == 'J'){
            for(int x = col; x > 0; x--){
                if(grid[row][x] == 'L'){
                    break;
                }
                if(grid[row][x] == 'F'){
                    return !current;
                }
            }
        }
        return current;
    }
}
