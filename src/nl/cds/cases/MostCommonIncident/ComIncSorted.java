package nl.cds.cases.MostCommonIncident;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.records.Incident;
import nl.cds.tools.QuickSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Case for finding the most common incident in a sorted list of incidents
 */
public class ComIncSorted extends Case {
    private ArrayList<Map.Entry<String, Integer>> sortedMapByValue;

    public ComIncSorted(String title, DataStore dataStore) {
        super(title, dataStore);
    }

    @Override
    protected boolean run(int dataAmount) {
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

        Integer[] sortedFrequencies = frequencyLookupTable.values().toArray(new Integer[0]);
        QuickSort<Integer> sorter = new QuickSort<>();
        sorter.sort(sortedFrequencies,0, sortedFrequencies.length-1);

        sortedMapByValue = new ArrayList<>();
        for(int freq : sortedFrequencies){
            frequencyLookupTable.forEach((k,v) -> {
                if(v == freq) sortedMapByValue.add(Map.entry(k,v));
            });
        }

        //Check if the list is sorted correctly
        assert (sortedMapByValue.get(sortedMapByValue.size()-1).getValue() > sortedMapByValue.get(sortedMapByValue.size()-2).getValue());

        return true;
    }
}
