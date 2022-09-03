package nl.cds.model;

/**
 * An AVL Tree is a sorted, balanced tree data structure
 * @param <T> Value to store (Needs to implement Comparable)
 */
public class AvlTree<T extends Comparable<T>> {
    private AvlNode<T> rootNode;

    /**
     * Create a new, empty AVL Tree with one, empty root node
     */
    public AvlTree() {
        this.rootNode = null;
    }

    /**
     * Checks if the tree contains a given value
     * @param key The value to find in the tree
     * @return The found value. If not found, returns null
     */
    public T find(T key){
        AvlNode<T> foundNode = findValue(key);
        return foundNode ==  null ? null : foundNode.value;
    }

    /**
     * Add a new value to the AVL Tree.
     * <br><br>
     * After adding a new value, if a leaf becomes unbalanced, the tree will automatically
     * restructure its structure
     * @param value The value to add to the tree
     */
    public void add(T value){
        rootNode = insert(rootNode, value);
    }

    /**
     * Recursively searches through the tree to find an existing node that the new node can be assigned to
     * Upon successful creation, recursively returns to the top of the tree while balancing it
     * @param node The current node being checked. Updates recursively
     * @param value The value of the new node to be created
     * @return The node that was created or last balanced
     */
    private AvlNode<T> insert(AvlNode<T> node, T value) {
        if (node == null) { //Spot found
            return new AvlNode<>(value);
        } else if (node.value.compareTo(value) > 0) { //Turn right
            node.leftNode = insert(node.leftNode, value);
        } else if (node.value.compareTo(value) < 0) { //Turn left
            node.rightNode = insert(node.rightNode, value);
        } else {
            throw new IllegalArgumentException("Cannot insert a new node to the AVL Tree! The value already exists in the tree!");
        }
        return balance(node);
    }

    private AvlNode<T> findValue(T key) {
        AvlNode<T> current = rootNode;
        while (current != null) {
            if (current.value.compareTo(key) == 0) {
                break;
            }
            current = current.value.compareTo(key) < 0 ? current.rightNode : current.leftNode;
        }
        return current;
    }

    /**
     * Balances the tree based on the balancing root node's balance factor
     * Balance factors between -1 and +1 do not require balancing
     * Balance factors above +1 require a "Right-Left rotation" if the balance is shifted to the right child's left node, otherwise a "Left rotation" is performed
     * Balance factors below -1 require a "Left-Right rotation" if the balance is shifted to the left child's right node, otherwise a "Right rotation" is performed
     * @param balancingRootNode The node from which the balancing calculation should be conducted
     * @return The new balancing root node
     */
    private AvlNode<T> balance(AvlNode<T> balancingRootNode) {
        updateHeight(balancingRootNode);
        int balance = getBalance(balancingRootNode); // Get up-to-date height
        if (balance > 1) { //Unbalanced to the right
            if (height(balancingRootNode.rightNode.rightNode) <= height(balancingRootNode.rightNode.leftNode)) { //Is balance skewed to the right child's left node?
                balancingRootNode.rightNode = rotateRight(balancingRootNode.rightNode);
            }
            balancingRootNode = rotateLeft(balancingRootNode);
        } else if (balance < -1) { //Unbalanced to the left
            if (height(balancingRootNode.leftNode.leftNode) <= height(balancingRootNode.leftNode.rightNode)) { //Is balance skewed to the left child's right node?
                balancingRootNode.leftNode = rotateLeft(balancingRootNode.leftNode);
            }
            balancingRootNode = rotateRight(balancingRootNode);
        }
        return balancingRootNode;
    }

    /**
     * Preforms a node rotation to the left
     * @param rotationRootNode The node from which the balancing is being performed
     * @return The node that was previously on the root node's right and has now become its parent
     */
    private AvlNode<T> rotateLeft(AvlNode<T> rotationRootNode) {
        AvlNode<T> rotationRootRightNode = rotationRootNode.rightNode;
        AvlNode<T> rotationRootRightChildLeftNode = rotationRootRightNode.leftNode;
        rotationRootRightNode.leftNode = rotationRootNode;
        rotationRootNode.rightNode = rotationRootRightChildLeftNode;
        updateHeight(rotationRootNode);
        updateHeight(rotationRootRightNode);
        return rotationRootRightNode;
    }

    /**
     * Performs a node rotation to the right
     * @param rotationRootNode The node from which the balancing is being performed
     * @return The node that was previously on the root node's left and has now become its parent
     */
    private AvlNode<T> rotateRight(AvlNode<T> rotationRootNode) {
        AvlNode<T> rotationRootLeftNode = rotationRootNode.leftNode;
        AvlNode<T> rotationRootLeftChildRightNode = rotationRootLeftNode.rightNode;
        rotationRootLeftNode.rightNode = rotationRootNode;
        rotationRootNode.leftNode = rotationRootLeftChildRightNode;
        updateHeight(rotationRootNode);
        updateHeight(rotationRootLeftNode);
        return rotationRootLeftNode;
    }

    /**
     * Updates the node's height from the bottom of the graph
     * @param node The node to update height
     */
    private void updateHeight(AvlNode<T> node) {
        node.setHeight(1 + Math.max(height(node.leftNode), height(node.rightNode)));
    }

    private int height(AvlNode<T> node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * Calculate node balance based on the height of its children
     * @param node The node to check balance
     * @return The calculated balance
     */
    private int getBalance(AvlNode<T> node) {
        return (node == null) ? 0 : height(node.rightNode) - height(node.leftNode);
    }
}
