package nl.cds.cases.MostCommonIncident;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.records.Incident;

import java.util.HashMap;
import java.util.Map;

/**
 * Case for finding the most common incident in an unsorted list of incidents
 */
public class ComIncLinear extends Case {

    public ComIncLinear(String title, DataStore dataStore) {
        super(title, dataStore);
    }

    @Override
    protected boolean run(int dataAmount) {
        int mostCommonValue = 0;
        Incident[] incidents = dataStore.getIncidents(dataAmount);
        HashMap<String, Integer> frequencyLookupTable = new HashMap<>();
        for (Incident i : incidents) {
            if(!frequencyLookupTable.containsKey(i.category())){
                frequencyLookupTable.put(i.category(), 1);
            }else{
                frequencyLookupTable.put(i.category(), frequencyLookupTable.get(i.category())+1);
            }
        }
        assert (frequencyLookupTable.size() == 4); //Incidents file only have 4 types in total
        for (Map.Entry<String, Integer> entry : frequencyLookupTable.entrySet()) if (entry.getValue() > mostCommonValue){
            mostCommonValue = entry.getValue();
        }

        return true;
    }
}
