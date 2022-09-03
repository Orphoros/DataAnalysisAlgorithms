package nl.cds.model;

import nl.cds.data.DataStore;

import java.util.HashMap;
import java.util.Map;

/**
 * A base abstract class for building a case.
 * A case is used to benchmark a specific scenario
 */
public abstract class Case {
    protected final DataStore dataStore;
    protected final String title;
    protected long measuredTime;
    private int dataAmountToUse;
    private final Map<Integer, Long> measuredResults;

    public Case(String title, DataStore dataStore) {
        this.dataStore = dataStore;
        this.title = title;
        this.dataAmountToUse = 90;
        this.measuredResults = new HashMap<>();
    }

    /**
     * The execution time of the code in the run method will be measured
     */
    protected abstract boolean run(int amountOfDataToUse);


    /**
     * Method used to run the logic of the case.
     * By running the start method, the execution time of the run method will be calculated
     */
    public Case start(){
        for(int i = 0; i < 6; i++){
            long start = System.nanoTime();
            boolean didFind = run(dataAmountToUse);
            measuredTime = System.nanoTime() - start;
            measuredResults.put(dataAmountToUse, didFind ? measuredTime : null);
            dataAmountToUse = dataAmountToUse * 2;
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\u001B[34m> Time required to finish case '")
                .append(title)
                .append("':\n");

        measuredResults.forEach((dataAmount,time) -> {
            builder.append("\t-Measured time under ").append(dataAmount).append(" of data elements: ");
            if(time != null) builder.append(time).append(" ns (Data found)\n");
            else builder.append("DATA DOES NOT EXIST!\n");
        });

        builder.append("\u001B[0m");

        return builder.toString();
    }
}
