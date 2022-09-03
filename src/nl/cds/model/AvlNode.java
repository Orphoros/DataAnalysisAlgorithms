package nl.cds.model;

/**
 * A model class for storing data in an AVL Tree node
 * @param <T> A data object that implements the {@link Comparable} interface
 */
public class AvlNode<T extends Comparable<T>> {
    public AvlNode<T> leftNode, rightNode;
    public final T value;
    private int height;

    public AvlNode(T value) {
        this.value = value;
        this.height = 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        assert (height > -1);
        this.height = height;
    }
}
