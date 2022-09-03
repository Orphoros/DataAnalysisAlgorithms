package nl.cds.cases.RetrieveIncident;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.records.Incident;

/**
 * A case for retrieving incidents from an unsorted list using Linear Search
 */
public class StatusUnsortedLinear extends Case {
    private final String caseID;

    public StatusUnsortedLinear(String title, DataStore dataStore, String caseID) {
        super(title, dataStore);
        this.caseID = caseID;
    }

    @Override
    protected boolean run(int dataAmount) {
        Incident[] unsortedIncidents = dataStore.getIncidents(dataAmount);

        //Linear search
        int index = 0;
        //Loop through each element of the array until we find the search for element
        while (index < unsortedIncidents.length && !unsortedIncidents[index].id().equals(caseID)){
            index++;
        }
        //The index didn't reach the end of the array, meaning it found the searched for item and exited the loop
        return index < unsortedIncidents.length;
    }
}
