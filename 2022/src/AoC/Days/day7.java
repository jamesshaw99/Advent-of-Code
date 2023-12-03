package AoC.Days;

import AoC.Helpers.Day;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class day7 extends Day {
    HashMap<String, Dir> dirs;
    Dir root;
    public day7(String fileStr) {
        super(fileStr);
    }

    public String part1() throws Exception {
        run();
        int sum = 0;
        for(String key: dirs.keySet()){
            Dir dir = dirs.get(key);
            if(dir.size <= 100000){
                sum += dir.size;
            }
        }
        return "sum of sub 100000 directories: " + sum;
    }

    public String part2() throws Exception {
        run();
        int required = dirs.get("/root").size - 40000000;
        LinkedList<Dir> candidates = new LinkedList<>();

        for(String key: dirs.keySet()){
            Dir dir = dirs.get(key);
            if(dir.size > required){
                candidates.add(dir);
            }
        }
        Collections.sort(candidates);

        return "Size of smallest directory to delete: " + candidates.getFirst().size;
    }

    private void run() {
        dirs = new HashMap<>();
        root = new Dir("root");

        dirs.put("/root", root);

        Dir parent = root;
        String path = "/root";
        for(String command: input) {
            String[] cmdParts = command.split(" ");
            if(cmdParts[0].equals("$")){
                if(cmdParts[1].equals("cd")){
                    if(cmdParts[2].equals("/")){
                        parent = root;
                        path = "/root";
                    } else if(cmdParts[2].equals("..")){
                        path = path.substring(0, path.lastIndexOf("/"+parent.name));
                        parent = parent.parent;
                    } else {
                        String name = cmdParts[2];
                        path += "/"+name;
                        if(dirs.containsKey(path)){
                            parent = dirs.get(path);
                        } else {
                            Dir newParent = new Dir(name, parent);
                            parent = newParent;
                            dirs.put(path, newParent);
                        }
                    }
                } else if (cmdParts[1].equals("ls")) {
                    continue;
                }
            } else if(cmdParts[0].equals("dir")){
                continue;
            } else {
                Dir tempDir = parent;
                String tempPath = path;
                while(tempDir != null){
                    tempDir.size += Integer.parseInt(cmdParts[0]);
                    dirs.put(tempPath, tempDir);
                    tempPath = tempPath.substring(0, tempPath.lastIndexOf("/" + tempDir.name));
                    tempDir = tempDir.parent;
                    if(tempDir != null) {
                    }
                }
            }
        }
    }

    class Dir implements Comparable<Dir> {
        public String name;
        public int size = 0;
        public Dir parent;

        public Dir(String name){
            this.name = name;
        }

        public Dir(String name, Dir parent){
            this.name = name;
            this.parent = parent;
        }

        public int compareTo(Dir d) {
            if (size > d.size) {
                return 1;
            }
            else if (size == d.size) {
                return 0;
            }
            else {
                return -1;
            }
        }
    }
}
