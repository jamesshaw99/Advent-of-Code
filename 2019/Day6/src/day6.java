import java.io.*;
import java.util.*;

public class day6 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        File file = new File("data.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        Map<String, Node> orbitsMap = new HashMap<>();
        orbitsMap.put("COM", new Node(null, "COM"));
        for (String line: list){
            String[] temp = line.split("\\)");
            Node node1 = findOrMakeNode(orbitsMap, temp[0]);
            Node node2 = findOrMakeNode(orbitsMap, temp[1]);
            node2.parent = node1;
        }

        System.out.println("Part 1");
        int totalOrbits = 0;
        for(String key: orbitsMap.keySet()){
            totalOrbits += orbitsMap.get(key).getDepth();
        }
        System.out.println("The total number of direct and indirect orbits is: " + totalOrbits);

        System.out.println("");

        System.out.println("Part 2");
        Node common = findCommonParent(orbitsMap, orbitsMap.get("YOU"), orbitsMap.get("SAN"));
        int distance = (orbitsMap.get("YOU").getDepth() - common.getDepth()) + (orbitsMap.get("SAN").getDepth() - common.getDepth()) - 2;
        System.out.println("Transfers required from YOU to SAN: " + distance);
    }

    public static Node findOrMakeNode(Map<String, Node> map, String name){
        if(!map.containsKey(name)) {
            map.put(name, new Node(null, name));
        }
        return map.get(name);
    }

    public static Node findCommonParent(Map<String, Node> map, Node node1, Node node2) {
        List<Node> node1Parents = node1.getAncestors();
        List<Node> node2Parents = node2.getAncestors();
        node1Parents.retainAll(node2Parents);

        Collections.sort(node1Parents);
        return node1Parents.get(0);
    }

    static class Node implements Comparable<Node> {
        private Node parent = null;
        private String name;

        public Node(Node parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        public boolean isRoot(){
            return this.parent == null;
        }

        public int getDepth() {
            return getAncestors().size();
        }

        public ArrayList<Node> getAncestors() {
            if (isRoot()){
                return new ArrayList<Node>();
            } else {
                ArrayList<Node> parents = new ArrayList<>();
                Node p = this.parent;
                while(p != null){
                    parents.add(p);
                    p = p.parent;
                }
                return parents;
            }
        }

        @Override
        public int compareTo(Node o) {
            if (this.getDepth() < o.getDepth()){
                return 1;
            } else{
                return 0;
            }
        }
    }
}


