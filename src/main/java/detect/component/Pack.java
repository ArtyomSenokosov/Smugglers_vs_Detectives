package main.java.detect.component;

public class Pack {

    private final Node[] nodes;

    public Pack(int size) {
        nodes = new Node[size];
    }

    public Node[] getNodes() {
        return nodes;
    }
}