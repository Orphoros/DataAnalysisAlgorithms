package nl.cds.cases.RetrieveIncident;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.records.Incident;
import nl.cds.tools.QuickSort;

/**
 * A case for retrieving incidents from a sorted list using Binary Search
 */
public class StatusBinary extends Case {
    private final String caseID;

    public StatusBinary(String title, DataStore dataStore, String caseID) {
        super(title, dataStore);
        this.caseID = caseID;
    }

    @Override
    protected boolean run(int dataAmount) {
        QuickSort<Incident> sorter = new QuickSort<>();
        Incident[] sortedIncidents = dataStore.getIncidents(dataAmount);
        sorter.sort(sortedIncidents,0, sortedIncidents.length-1);

        int left = 0; //By default, left is the first element
        int right = sortedIncidents.length - 1; //By default, right is the last element
        int middle = (left + right) / 2; //By default, middle is the element that is at the middle between left and right

        //Make a template incident. Only ID is given as that is the only data we need to find an incident.
        Incident testIncident = new Incident(caseID, null, null, 0);

        //Binary search
        while (sortedIncidents[middle].compareTo(testIncident) != 0 && left < right){
            //We haven't found the incident since left hasn't reached right and the current incident is not the searched one
            if (sortedIncidents[middle].compareTo(testIncident) < 0) left = middle + 1; //Searched incident is greater than middle, so left is set to the place of the middle
            else if(sortedIncidents[middle].compareTo(testIncident) > 0) right = middle - 1; //Searched incident is smaller than middle, so right is set to the place of the middle
            middle = (left + right) / 2; //Calculate new middle
        }
        //Incident is found
        return sortedIncidents[middle].compareTo(testIncident) == 0;
    }
}
