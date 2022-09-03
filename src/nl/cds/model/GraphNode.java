package nl.cds.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A model class to store data in a Graph node (Vertex)
 * @param <T> Data object that implements the {@link Comparable} interface
 */
public class GraphNode<T extends Comparable<T>>{
    private final T value;
    private int distance = Integer.MAX_VALUE;
    private final Map<GraphNode<T>,Integer> adjacentNodes;
    private GraphNode<T> shortestPath;

    public GraphNode(T value) {
        adjacentNodes = new HashMap<>();
        this.value = value;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public T getNodeValue() {
        return value;
    }

    public int getDistance() {
        return distance;
    }

    public Map<GraphNode<T>, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addAdjacentNode(GraphNode<T> node, int weight){
        adjacentNodes.put(node,weight);
    }

    public GraphNode<T> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(GraphNode<T> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
