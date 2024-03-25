package main.java.detect.algorithms;

import main.java.detect.component.Node;
import main.java.detect.component.Pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Algorithm {

    private final Node[] graph;
    private final ArrayList<Pack> packs;

    private final int numNodesPack;
    private final int numConnectionsPack;

    public Algorithm(Node[] graph, int numNodesPack, int numConnectionsPack) {
        this.graph = graph;
        this.numNodesPack = numNodesPack;
        this.numConnectionsPack = numConnectionsPack;
        this.packs = new ArrayList<>();
    }

    public void calculateSubset() {
        calculateSubset(graph, 0, new Node[numNodesPack], 0);
    }

    private void calculateSubset(Node[] set, int iEnd, Node[] result, int currentIndex) {
        if (currentIndex == numNodesPack) {
            createSubGraph(result);
            return;
        }
        for (int i = iEnd; i < graph.length; i++) {
            result[currentIndex] = set[i];
            calculateSubset(set, i + 1, result, currentIndex + 1);
        }
    }

    public void getIsomorphismsPacks() {
        for (int i = 0; i < packs.size(); i++) {
            for (int j = i + 1; j < packs.size(); j++) {
                Pack pack1 = packs.get(i);
                Pack pack2 = packs.get(j);
                if (checkIsomorphism(pack1, pack2)) {
                    for (int k = 0; k < numNodesPack; k++) {
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(pack1.getNodes()[k].getIndex()));
                        System.out.print(stringBuilder.append(" "));
                    }
                    for (int k = 0; k < numNodesPack - 1; k++) {
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(pack2.getNodes()[k].getIndex()));
                        System.out.print(stringBuilder.append(" "));
                    }
                    System.out.print(pack2.getNodes()[numNodesPack - 1].getIndex());
                    System.out.println();
                }
            }
        }
    }

    private void createSubGraph(Node[] nodes) {
        Pack packNode = new Pack(numNodesPack);

        int allDegrees = 0;

        for (int i = 0; i < numNodesPack; i++) {
            Node node = nodes[i];
            int current = degreeInPack(nodes, node);
            if (current == 0)
                return;
            allDegrees += current;
            node.getDegrees().put(packNode, current);
            packNode.getNodes()[i] = node;
        }
        if (allDegrees / 2 == numConnectionsPack) {
            packs.add(packNode);
        }
    }

    private int degreeInPack(Node[] pack, Node n) {
        int degree = 0;
        for (Node node : pack) {
            if (node.getNeighbours().contains(n))
                degree++;
        }
        return degree;
    }

    private boolean isInPack(Node[] pack, Node n) {
        for (int i = 0; i < numNodesPack; i++) {
            if (pack[i] == n) return true;
        }
        return false;
    }

    private boolean checkIsomorphism(Pack pack1, Pack pack2) {
        for (int i = 0; i < numNodesPack; i++) {
            for (int j = 0; j < numNodesPack; j++) {
                if (pack1.getNodes()[j].getIndex() == pack2.getNodes()[i].getIndex())
                    return false;
            }
        }
        HashMap<Integer, ArrayList<ArrayList<Integer>>> graph1 = new HashMap<>();

        for (Node node : pack1.getNodes()) {
            Integer nodeDegree = node.getDegrees().get(pack1);
            ArrayList<Integer> degrees = new ArrayList<>();
            for (Node nodeN : node.getNeighbours()) {
                if (isInPack(pack1.getNodes(), nodeN)) {
                    degrees.add(degreeInPack(pack1.getNodes(), nodeN));
                }
            }
            Collections.sort(degrees);

            ArrayList<ArrayList<Integer>> graphDegrees = graph1.get(nodeDegree);
            if (graphDegrees == null)
                graph1.put(nodeDegree, new ArrayList<>());
            graphDegrees = graph1.get(nodeDegree);
            graphDegrees.add(degrees);
            graph1.put(nodeDegree, graphDegrees);
        }

        for (Node node : pack2.getNodes()) {
            Integer nodeDegree = node.getDegrees().get(pack2);
            ArrayList<Integer> degrees = new ArrayList<>();
            for (Node nodeN : node.getNeighbours()) {
                if (isInPack(pack2.getNodes(), nodeN)) {
                    degrees.add(degreeInPack(pack2.getNodes(), nodeN));
                }
            }
            Collections.sort(degrees);

            ArrayList<ArrayList<Integer>> graphDegrees = graph1.get(nodeDegree);
            if (graphDegrees == null) return false;
            boolean removed = false;
            for (int i = graphDegrees.size() - 1; i >= 0; i--) {
                if (graphDegrees.get(i).equals(degrees)) {
                    graphDegrees.remove(i);
                    removed = true;
                    break;
                }
            }
            if (!removed) return false;
        }
        return true;
    }
}