package nl.cds.cases.CableRoute;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.Graph;
import nl.cds.model.GraphNode;
import nl.cds.model.PrimMapper;

import java.util.Map;
import java.util.Stack;

/**
 * Case for plotting a cable path originating from the base office through all cities using Prim's Algorithm
 */
public class PrimRoutePlot extends Case{
    private int totalLength;
    private Stack<Map.Entry<GraphNode<String>, GraphNode<String>>> routePlan;

    public PrimRoutePlot(String title, DataStore dataStore) {
        super(title, dataStore);
    }

    @Override
    public final Case start(){
        long start = System.nanoTime();
        run(-1);
        measuredTime = System.nanoTime() - start;
        return this;
    }

    @Override
    protected boolean run(int amountOfDataToUse) {
        Graph<String> graph = dataStore.getCitiesKilometerWeight();
        PrimMapper<String> mapper = new PrimMapper<>(graph,"devter");
        mapper.mapGraph();
        totalLength = mapper.getTotalCost();
        routePlan = mapper.getNodePath();
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\u001B[34m> Time required to finish case '")
                .append(title)
                .append("':\n");
        builder.append("Measured time to complete: ").append(measuredTime).append(" ns (Cable route)\n");
        builder.append(System.lineSeparator())
                .append("> Planned connections:\n");
        for (Map.Entry<GraphNode<String>, GraphNode<String>> entry : routePlan){
            if (entry.getKey().getNodeValue().equals(entry.getValue().getNodeValue())) continue;
            builder.append(entry.getValue().getNodeValue()).append(" -> ").append(entry.getKey().getNodeValue()).append("\n");
        }

        builder.append("\n\n> Total cable length:")
                .append(System.lineSeparator())
                .append(totalLength)
                .append(" km needed");
        builder.append("\u001B[0m");

        return builder.toString();
    }
}
