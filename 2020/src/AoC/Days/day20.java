package AoC.Days;

import AoC.Day;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day20 extends Day {
    private final Pattern REGEX_TILEID = Pattern.compile("Tile ([0-9]+):");
    private final Pattern REGEX_TILECONTENT = Pattern.compile("^([.#]+)$");

    private char[][] finalImage;
    private List<List<Integer>> tileIDs = new ArrayList<>();
    private Map<Integer,Tile> tiles = new HashMap<>();
    public day20(String fileStr) {
        super(fileStr);
        int tileId = 0;
        List<String> tile = new ArrayList<>();
        for (String x: input){
            if(x.isEmpty()){
                tiles.put(tileId, new Tile(tileId, tile));
                tile = new ArrayList<>();
                continue;
            }
            Matcher matcher = REGEX_TILEID.matcher(x);
            if (matcher.matches()){
                tileId = Integer.parseInt(matcher.group(1));
            }
            Matcher matcher2 = REGEX_TILECONTENT.matcher(x);
            if(matcher2.matches()) {
                tile.add(x);
            }
        }
        tiles.put(tileId, new Tile(tileId, tile));

        for(Tile i: tiles.values()) {
            for (Tile j: tiles.values()) {
                if (i != j) {
                    i.compare(j);
                }
            }
        }
    }

    public long part1() {
        long result = 1;
        for (Tile tile: tiles.values()) {
            int count = 0;
            if (tile.topTile == 0) count++;
            if (tile.bottomTile == 0) count++;
            if (tile.leftTile == 0) count++;
            if (tile.rightTile == 0) count++;

            if(count == 2) {
                result *= tile.id;
            }
        }
        return result;
    }

    public int part2() {
        for (Tile tile: tiles.values()) {
            int count = 0;
            if (tile.topTile == 0) count++;
            if (tile.leftTile == 0) count++;

            if(count == 2) {
                int ListID = tile.id;
                while(ListID != 0) {
                    List<Integer> IDs = new ArrayList<>();
                    IDs.add(ListID);
                    int nextId = getTileRight(ListID);

                    while (tiles.get(nextId).rightTile != 0) {
                        IDs.add(nextId);
                        nextId = getTileRight(nextId);
                    }
                    IDs.add(nextId);
                    tileIDs.add(IDs);
                    Tile newTile = tiles.get(tiles.get(ListID).bottomTile);
                    if (newTile == null) break;
                    while(newTile.topTile != ListID) {
                        newTile.rotate();
                    }
                    if(!newTile.top.equals(tiles.get(ListID).bottom)){
                        newTile.flip(0);
                    }
                    ListID = tiles.get(ListID).bottomTile;
                }
                break;
            }
        }
        stitchFinalImage();
        Tile completedImage = new Tile(0, finalImage);
        for (int i = 0; i < 16; i++){
            if (i == 4 || i == 12){
                completedImage.flip(1);
            }else if (i == 8){
                completedImage.flip(0);
            }
            completedImage.findMonsters();
            completedImage.rotate();
        }

        int count = 0;
        for(int i = 0; i < completedImage.content.length; i++) {
            for(int j = 0; j < completedImage.content[i].length; j++) {
                if (completedImage.content[i][j] == '#'){
                    count++;
                }
            }
        }

        return count;
    }

    private int getTileRight(int id) {
        Tile tile1 = tiles.get(id);
        Tile tile2 = tiles.get(tile1.rightTile);
        while(tile2.leftTile != tile1.id) {
            tile2.rotate();
        }
        if (!tile2.left.equals(tile1.right)) {
            tile2.flip(1);
        }
        return tile2.id;
    }

    private void stitchFinalImage() {
        int height = 8 * tileIDs.get(0).size();

        finalImage = new char[height][height];

        int ri = 0; // row of image
        int ci = 0; // col of image
        for (int i = 0; i < tileIDs.size(); i++) {
            for (int j = 0; j < tileIDs.get(0).size(); j++) {
                Tile t = tiles.get(tileIDs.get(i).get(j)); // current tile we are copying into image
                char[][] g = t.content;
                // using the coordinates of our puzzle piece, calculate where to begin placing
                // chars in image array
                ri = i * 8;
                ci = j * 8;
                for (int r = 1; r <= g.length - 2; r++) {
                    for (int c = 1; c <= g[0].length - 2; c++) {
                        finalImage[ri][ci] = g[r][c];
                        ci++;
                    }
                    ri++; // go down one row
                    ci = j * 8; // go back to starting column
                }
            }
        }
    }



    private class Tile{
        private final int id;
        private char[][] content;
        private String top = "", bottom = "", left = "", right = "";
        private int topTile = 0, bottomTile = 0, leftTile = 0, rightTile = 0;

        public Tile(int id, List<String> content) {
            this.id = id;
            this.content = new char[content.size()][content.get(0).length()];
            for (int i = 0; i < content.size(); i++){
                this.content[i] = content.get(i).toCharArray();
            }
            this.top = content.get(0);
            this.bottom = content.get(content.size()-1);
            for(String row: content) {
                this.left += row.charAt(0);
                this.right += row.charAt(row.length()-1);
            }
        }

        public Tile(int id, char[][] content) {
            this.id = id;
            this.content = content;
        }

        public void print() {
            System.out.format("Tile %d:\n", id);
            for (char[] chars : content) {
                for (char aChar : chars) {
                    System.out.print(aChar);
                }
                System.out.print("\n");
            }
            System.out.print("\n");
            System.out.format("top = %d, bottom = %d, left = %d, right = %d\n", topTile, bottomTile, leftTile, rightTile);
        }

        public void rotate() {
            int rows = this.content.length;
            int cols = this.content[0].length;

            char[][] rotatedArr = new char[cols][rows];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    rotatedArr[c][rows-1-r] = this.content[r][c];
                }
            }
            this.content = rotatedArr;

            String temp = this.top;
            this.top = new StringBuilder(this.left).reverse().toString();
            this.left = this.bottom;
            this.bottom = new StringBuilder(this.right).reverse().toString();
            this.right = temp;

            int temp2 = this.topTile;
            this.topTile = this.leftTile;
            this.leftTile = this.bottomTile;
            this.bottomTile = this.rightTile;
            this.rightTile = temp2;
        }

        public void flip(int axis) {
            int rows = this.content.length;
            int cols = this.content[0].length;

            char[][] flippedArr = new char[rows][cols];
            if (axis == 0) {
                for (int r = 0; r < rows; r++) {
                    for(int c = 0; c < cols; c++) {
                        flippedArr[r][cols-c-1] = this.content[r][c];
                    }
                }
                String temp = this.left;
                this.left = this.right;
                this.right = temp;
                this.top = new StringBuilder(this.top).reverse().toString();
                this.bottom = new StringBuilder(this.bottom).reverse().toString();

                int temp2 = this.leftTile;
                this.leftTile = this.rightTile;
                this.rightTile = temp2;
            } else if (axis == 1) {
                for (int r = 0; r < rows; r++) {
                    for(int c = 0; c < cols; c++) {
                        flippedArr[rows-r-1][c] = this.content[r][c];
                    }
                }
                String temp = this.top;
                this.top = this.bottom;
                this.bottom = temp;
                this.left = new StringBuilder(this.left).reverse().toString();
                this.right = new StringBuilder(this.right).reverse().toString();

                int temp2 = this.topTile;
                this.topTile = this.bottomTile;
                this.bottomTile = temp2;
            }

            this.content = flippedArr;
        }

        public void compare(Tile other) {
            if(oneOfEquals(other, this.top)){
                this.topTile = other.id;
            }else if(oneOfEquals(other, this.bottom)){
                this.bottomTile = other.id;
            }else if(oneOfEquals(other, this.left)){
                this.leftTile = other.id;
            }else if(oneOfEquals(other, this.right)){
                this.rightTile = other.id;
            }
        }

        private boolean oneOfEquals(Tile other, String Expected){
            String t = other.top, b = other.bottom, l = other.left, r = other.right;
            return Expected.equals(t) || Expected.equals(new StringBuilder(t).reverse().toString())
                || Expected.equals(b) || Expected.equals(new StringBuilder(b).reverse().toString())
                || Expected.equals(l) || Expected.equals(new StringBuilder(l).reverse().toString())
                || Expected.equals(r) || Expected.equals(new StringBuilder(r).reverse().toString());
        }

        private void findMonsters() {
            for (int row = 0; row< content.length; row++){
                for (int col = 0; col < content[0].length; col++){
                    if (col + 19 < content[0].length && row + 2 < content.length){
                        if(content[row][col + 18] == '#' && content[row + 1][col] == '#' && content[row + 1][col + 5] == '#'
                                && content[row + 1][col + 6] == '#' && content[row + 1][col + 11] == '#' && content[row + 1][col + 12] == '#'
                                && content[row + 1][col + 17] == '#' && content[row + 1][col + 18] == '#' && content[row + 1][col + 19] == '#'
                                && content[row + 2][col + 1] == '#' && content[row + 2][col + 4] == '#' && content[row + 2][col + 7] == '#'
                                && content[row + 2][col + 10] == '#' && content[row + 2][col + 13] == '#' && content[row + 2][col + 16] == '#') {

                            content[row][col + 18] = 'O';
                            content[row + 1][col] = 'O';
                            content[row + 1][col + 5] = 'O';
                            content[row + 1][col + 6] = 'O';
                            content[row + 1][col + 11] = 'O';
                            content[row + 1][col + 12] = 'O';
                            content[row + 1][col + 17] = 'O';
                            content[row + 1][col + 18] = 'O';
                            content[row + 1][col + 19] = 'O';
                            content[row + 2][col + 1] = 'O';
                            content[row + 2][col + 4] = 'O';
                            content[row + 2][col + 7] = 'O';
                            content[row + 2][col + 10] = 'O';
                            content[row + 2][col + 13] = 'O';
                            content[row + 2][col + 16] = 'O';
                        }
                    }
                }
            }
        }
    }
}
