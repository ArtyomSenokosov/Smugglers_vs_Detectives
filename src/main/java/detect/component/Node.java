package main.java.detect.component;

import java.util.*;

public class Node {

    private final int index;
    private final ArrayList<Node> neighbours;
    private final HashMap<Pack, Integer> degrees;

    public Node(int index) {
        this.index = index;
        neighbours = new ArrayList<>();
        degrees = new HashMap<>();
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public HashMap<Pack, Integer> getDegrees() {
        return degrees;
    }
}