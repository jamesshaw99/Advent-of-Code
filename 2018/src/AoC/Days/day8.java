package AoC.Days;

import AoC.Helpers.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day8 extends Day {
    //String testInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
    List<Integer> data = new ArrayList<>();
    int total;
    Node root = new Node();

    public day8(String fileStr) {
        super(fileStr);
        Scanner scanner = new Scanner(input.get(0));
        while(scanner.hasNextInt()){
            data.add(scanner.nextInt());
        }
    }

    public String part1() {
        buildTree(0, root);
        return "Sum of MetaData: " + total;
    }

    public String part2() {
        return "Root value: " + root.value();
    }

    private int buildTree(int index, Node current) {
        int children = data.get(index++),
            metaData = data.get(index++);

        for(int i = 0; i < children; i++) {
            Node child = new Node();
            current.addChild(child);
            index = buildTree(index, child);
        }

        for(int i = 0; i < metaData; i++){
            current.addMetaData(data.get(index + i));
            total += data.get(index + i);
        }

        return index + metaData;
    }


}

class Node {
    private List<Node> children = new ArrayList<>();
    private List<Integer> metaData = new ArrayList<>();

    public int value() {
        if (children.size() == 0) {
            return metaData.stream().mapToInt(x -> x).sum();
        } else {
            int sum = 0;
            for(Integer meta: metaData) {
                if(meta <= children.size()){
                    Node child = children.get(meta - 1);
                    if(child != null) {
                        sum += child.value();
                    }
                }
            }
            return sum;
        }
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void addMetaData(Integer data) {
        this.metaData.add(data);
    }
}
