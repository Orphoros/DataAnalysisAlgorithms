package nl.cds.model;

public class Graph<T extends Comparable<T>> {
    private final DynamicArray<GraphNode<T>> nodes;

    /**
     * Creates a new, empty directed graph
     */
    public Graph() {
        this.nodes = new DynamicArray<>();
    }

    /**
     * Adds a new value in the form of a node to the graph
     * @param value Value to add
     */
    public GraphNode<T> addValue(T value){
        for (GraphNode<T> node : nodes){
            if (node.getNodeValue().equals(value)) return node;
        }
        GraphNode<T> newNode = new GraphNode<>(value);
        nodes.add(newNode);
        return newNode;
    }

    /**
     * Find a value in the map
     * @param value Value to find
     * @return The found node, else return null
     */
    public GraphNode<T> findValue(T value){
        for (GraphNode<T> node: nodes) if(node.getNodeValue().compareTo(value) == 0) return node;
        return null;
    }
}
