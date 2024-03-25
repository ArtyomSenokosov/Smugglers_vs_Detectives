package main.java.detect;

import main.java.detect.algorithms.Algorithm;
import main.java.detect.component.Node;
import main.java.detect.reader.Reader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader reader = new Reader();

        int numNodes = reader.nextInt();
        int numConnections = reader.nextInt();
        int numNodesPack = reader.nextInt();
        int numConnectionsPack = reader.nextInt();

        Node[] graph = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            graph[i] = new Node(i);
        }

        for (int i = 0; i < numConnections; i++) {
            int source = reader.nextInt();
            int destination = reader.nextInt();
            graph[source].getNeighbours().add(graph[destination]);
            graph[destination].getNeighbours().add(graph[source]);
        }

        Algorithm algorithm = new Algorithm(graph, numNodesPack, numConnectionsPack);
        algorithm.calculateSubset();
        algorithm.getIsomorphismsPacks();
    }
}