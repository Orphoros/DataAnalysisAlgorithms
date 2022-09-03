package nl.cds.cases.RetrieveIncident;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.records.Incident;
import nl.cds.tools.QuickSort;

/**
 * A case for retrieving incidents from a sorted list using Linear Search
 */
public class StatusSortedLinear extends Case {
    private final String caseID;

    public StatusSortedLinear(String title, DataStore dataStore, String caseID) {
        super(title, dataStore);
        this.caseID = caseID;
    }

    @Override
    protected boolean run(int dataAmount) {
        QuickSort<Incident> sorter = new QuickSort<>();
        Incident[] sortedIncidents = dataStore.getIncidents(dataAmount);
        sorter.sort(sortedIncidents,0, sortedIncidents.length-1);
        int id = Integer.parseInt(caseID.substring(1));

        //Linear search
        int index = 0;
        //We loop through the elements until we reach the end or the currently viewed element is less than what we need
        while (index < sortedIncidents.length && Integer.parseInt(sortedIncidents[index].id().substring(1)) < id){
            //If the currently viewed element is grater than what we need, it means the list doesn't contain it as the list is sorted
            index++;
        }
        return index < sortedIncidents.length && Integer.parseInt(sortedIncidents[index].id().substring(1)) == id;
    }
}
