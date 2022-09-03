package nl.cds.tools;

/**
 * An implementation of the QuickSort algorithm
 * @param <T> Data object that implements the {@link Comparable} interface
 */
public class QuickSort<T extends Comparable<T>> {
    /**
     * Recursively sort above and below a partition
     * @param dataset The dataset being sorted
     * @param low First array index to sort from
     * @param high Last array index to sort to
     */
    public void sort(T[] dataset, int low, int high){
        if (high >= 0 && low < high) {
            int partition = partition(dataset, low, high);
            sort(dataset, low, partition-1); //Recursively sort below partition
            sort(dataset, partition + 1, high); //Recursively sort above partition
        }
    }

    /**
     * Place pivot and sort values around it
     * @param dataset The dataset being sorted
     * @param low First array index to sort from
     * @param high Last array index to sort to
     * @return The index at which to partition the dataset for further sorting
     */
    private int partition(T[] dataset, int low, int high) {
        assert (low != high);
        assert (high < dataset.length);
        T pivot = dataset[high]; //Highest element as pivot

        int i = low - 1; //Lowest element
        for (int j = low; j <= high-1; j++) {
            if(dataset[j].compareTo(pivot) < 0){
                T temp = dataset[++i]; //Get data from the lowest element and increment the lowest element index
                //Swap elements
                dataset[i] = dataset[j];
                dataset[j] = temp;
            }
        }

        //Swap highest and lowest elements
        T temp = dataset[i+1];
        dataset[i+1] = dataset[high];
        dataset[high] = temp;

        return ++i;
    }
}
