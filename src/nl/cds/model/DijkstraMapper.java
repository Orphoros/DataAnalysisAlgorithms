package nl.cds.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class to map a graph using Dijkstra's Algorithm by calculating the shortest path to every node from the base node
 * @param <T> A data object that implements the {@link Comparable} interface
 */
public class DijkstraMapper<T extends Comparable<T>> {

    /**
     * Performs the mapping process
     *
     * @param graph The graph being mapped
     * @param value The value of the start node
     */
    public void mapGraph(Graph<T> graph, T value){
        GraphNode<T> source = graph.findValue(value);
        HashSet<GraphNode<T>> checkedNodes = new HashSet<>();
        HashSet<GraphNode<T>> uncheckedNodes = new HashSet<>();
        source.setDistance(0);
        uncheckedNodes.add(source);

        while (uncheckedNodes.size() != 0) { //Loop until not all nodes are checked
            GraphNode<T> currentNode = findLowestDistNode(uncheckedNodes); //Find the node with the lowest distance from unchecked nodes
            assert (currentNode != null);
            uncheckedNodes.remove(currentNode); //Set lowest found node as checked
            for (Map.Entry<GraphNode<T>, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) { //Loop through a node's adjacency list and find the one with the lowest distance to source node
                GraphNode<T> adjacentNode = adjacencyPair.getKey();
                int edgeWeight = adjacencyPair.getValue();
                if (!checkedNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    uncheckedNodes.add(adjacentNode);
                }
            }
            checkedNodes.add(currentNode); //Set lowest found node as checked
        }
    }

    private GraphNode<T> findLowestDistNode(Set<GraphNode<T>> unsettledNodes) {
        GraphNode<T> lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE; //Set the distance to infinity
        for (GraphNode<T> node: unsettledNodes) { //Loop through all the unchecked nodes to find the lowest distant one
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) { //Check if the current node's distance is lower than our current one
                lowestDistance = nodeDistance;
                lowestDistanceNode = node; //Due to the infinity distance set, a node will always be set
            }
        }
        assert (lowestDistanceNode != null);
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(GraphNode<T> evaluationNode, int weight, GraphNode<T> sourceNode) {
        int sourceDistance = sourceNode.getDistance();
        if (sourceDistance + weight < evaluationNode.getDistance()) { //Only update distance if the weight is smaller than its current one
            evaluationNode.setDistance(sourceDistance + weight); //Add up the source distance with its own weight
            evaluationNode.setShortestPath(sourceNode);
        }
    }
}
