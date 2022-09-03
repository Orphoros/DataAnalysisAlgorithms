package nl.cds.model;

import java.util.*;

/**
 * A class to map a graph using Prim's Algorithm by calculating the shortest path that passes through every node once from the base node
 * @param <T> A data object that implements the {@link Comparable} interface
 */
public class PrimMapper<T extends Comparable<T>> {
    private final Graph<T> graph;
    private final T start;
    private final Stack<Map.Entry<GraphNode<T>, GraphNode<T>>> nodePath; //Node, Parent
    private final PriorityQueue<Map.Entry<GraphNode<T>, Map.Entry<GraphNode<T>, Integer>>> queue; //Parent, (Child, Weight)
    private int totalCost = 0;

    /**
     * Initializes the mapper
     * @param graph The graph being mapped
     * @param start The value of the start node
     */
    public PrimMapper(Graph<T> graph, T start) {
        this.graph = graph;
        this.start = start;
        this.nodePath = new Stack<>();
        Comparator<Map.Entry<GraphNode<T>, Map.Entry<GraphNode<T>, Integer>>> queueComparator = Comparator.comparingInt(entry -> entry.getValue().getValue());
        this.queue = new PriorityQueue<>(queueComparator);
    }

    /**
     * Maps the graph
     * Enumerates the current node's neighbors and visits the one with the cheapest (shortest) connection.
     * If a node has more expensive connections than the previous one, return to the last node and continue from there
     * The process repeats until all nodes have been visited once
     */
    public void mapGraph(){
        GraphNode<T> startingNode = graph.findValue(start);
        assert (startingNode != null);
        queue.add(new AbstractMap.SimpleEntry<>(startingNode,new AbstractMap.SimpleEntry<>(startingNode,0))); //Start from staring node
        assert !queue.isEmpty();
        while (!queue.isEmpty()){
            Map.Entry<GraphNode<T>, Map.Entry<GraphNode<T>, Integer>> connection = queue.poll(); //Get connection from queue. Via sorting this is always the cheapest
            boolean connectionOnStack = false;
            for (Map.Entry<GraphNode<T>, GraphNode<T>> element : nodePath) //Have we been to the node this connection leads to?
                if (element.getKey().getNodeValue().equals(connection.getValue().getKey().getNodeValue())) {
                    connectionOnStack = true;
                    break;
                }
            if (!connectionOnStack){ //New node discovered
                totalCost+=connection.getValue().getValue();
                nodePath.push(new AbstractMap.SimpleEntry<>(connection.getValue().getKey(),connection.getKey())); //Add traversal cost and push to path
                for (Map.Entry<GraphNode<T>,Integer> entry : connection.getValue().getKey().getAdjacentNodes().entrySet()){ //Enumerate all neighbors
                    connectionOnStack = false;
                    for (Map.Entry<GraphNode<T>, GraphNode<T>> element : nodePath) //Have we seen this neighbor?
                        if (element.getKey().getNodeValue().equals(entry.getKey().getNodeValue())) {
                            connectionOnStack = true;
                            break;
                        }
                    if (!connectionOnStack) { //New neighbor discovered
                        Map.Entry<GraphNode<T>, Map.Entry<GraphNode<T>, Integer>> newConnection = new AbstractMap.SimpleEntry<>(connection.getValue().getKey(),entry);
                        queue.add(newConnection); //Add connection to queue, auto-sorting the queue
                    }
                }
            }
        }
    }

    public Stack<Map.Entry<GraphNode<T>, GraphNode<T>>> getNodePath() {
        return nodePath;
    }

    public int getTotalCost() {
        return totalCost;
    }
}
