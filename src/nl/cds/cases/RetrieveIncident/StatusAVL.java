package nl.cds.cases.RetrieveIncident;

import nl.cds.data.DataStore;
import nl.cds.model.AvlTree;
import nl.cds.model.Case;
import nl.cds.model.records.Incident;

/**
 * A case for storing and retrieving incidents using an AVL Tree
 */
public class StatusAVL extends Case {
    private final String caseID;

    public StatusAVL(String title, DataStore dataStore, String caseID) {
        super(title, dataStore);
        this.caseID = caseID;
    }

    @Override
    protected boolean run(int dataAmount) {
        Incident[] incidents = dataStore.getIncidents(dataAmount);
        AvlTree<Incident> avlTree = new AvlTree<>();
        for (Incident incident : incidents) {
            avlTree.add(incident);
        }

        Incident foundIncident = avlTree.find(new Incident(caseID, null, null, 0));
        return foundIncident != null;
    }
}
