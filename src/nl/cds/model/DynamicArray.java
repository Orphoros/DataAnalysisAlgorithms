package nl.cds.model;

import java.util.Arrays;
import java.util.Iterator;

/**
 * DynamicArray data structure makes an array that has dynamically growing size.
 * @param <T> Data that the dynamic array will hold
 */
public class DynamicArray<T> implements Iterable<T> {
    private static final int INITIAL_CAPACITY = 5; //Set an initial array size. It's small so if only a few elements are added it won't reserve too much space
    private int size = 0;
    private Object[] array;

    /**
     * Create a new dynamic array with variable length
     */
    public DynamicArray() {
        array = new Object[INITIAL_CAPACITY];
    }

    /**
     * Add a new element to the dynamic array. Duplicates are allowed
     * @param element Element to add
     */
    public void add(T element){
        if (size == array.length) increaseCapacity(); //If array is full, double the array length
        array[size++] = element; //If array is not full, add the element
    }

    /**
     * Get an element under an index
     * @param index Index of the element to get
     * @return The found element
     */
    @SuppressWarnings("unchecked")
    public T get(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds of size " + index);
        }
        return (T) array[index]; // return value on index.
    }

    /**
     * Deletes an element at an index
     * @param index Index of the element to delete
     * @return The deleted element
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds of size " + index);

        Object removedElement = array[index];
        if (size - 1 - index >= 0) System.arraycopy(array, index + 1, array, index, size - 1 - index);

        size--;

        return (T) removedElement;
    }

    public int size(){
        return size;
    }

    private void increaseCapacity() {
        int newIncreasedCapacity = array.length * 2; //Whenever our array is filled, double the array size to hold 2x as many elements
        array = Arrays.copyOf(array, newIncreasedCapacity); //Copy the original array's contents into the newly created array with the increased size
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < size; i++) builder.append(array[i]).append(System.lineSeparator());
        return builder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DynamicArrayIterator();
    }

    private class DynamicArrayIterator implements Iterator<T> {
        private int position = 0;

        public boolean hasNext() {
            return position < size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            if (this.hasNext()) return (T) array[position++];
            else return null;
        }
    }
}
