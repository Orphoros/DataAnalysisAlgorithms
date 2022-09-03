package nl.cds.cases.RouteToCity;

import nl.cds.data.DataStore;
import nl.cds.model.Case;
import nl.cds.model.DijkstraMapper;
import nl.cds.model.GraphNode;

import java.util.ArrayList;

/**
 * A case for plotting a route from the base office to a specified city using Dijkstra's Algorithm
 */
public class DijkstraRoutePlot extends Case {
    private final GraphNode<String> destination;
    private boolean planningSucceeded;

    private int summedWeight;
    private final ArrayList<String> routePlan;

    public DijkstraRoutePlot(String title, DataStore dataStore, GraphNode<String> destinationCity) {
        super(title, dataStore);
        this.destination = destinationCity;
        this.routePlan = new ArrayList<>();
    }

    @Override
    public final Case start(){
        long start = System.nanoTime();
        planningSucceeded = run(-1);
        measuredTime = System.nanoTime() - start;
        return this;
    }

    @Override
    protected boolean run(int ignored) {
        DijkstraMapper<String> mapper = new DijkstraMapper<>();
        mapper.mapGraph(dataStore.getCitiesHourWeight(),"devter"); //Devter is where the provider is located
        //Traverse graph to plot route from destination to source
        GraphNode<String> currentNode = destination;
        summedWeight = currentNode.getDistance();
        routePlan.add(currentNode.getNodeValue());
        while(!currentNode.getNodeValue().equals("devter")){
            routePlan.add(currentNode.getShortestPath().getNodeValue());
            currentNode = currentNode.getShortestPath();
        }
        return summedWeight > 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\u001B[34m> Time required to finish case '")
                .append(title)
                .append("':\n");

        if(planningSucceeded){
            builder.append("Measured time to complete: ").append(measuredTime).append(" ns (Route planned successfully)\n");
            builder.append(System.lineSeparator())
                    .append("> Planned route:\n");
            for(int i = routePlan.size() - 1; i > 0 ; i--) builder.append(routePlan.get(i)).append(" -> ");
            builder.append(routePlan.get(0));

            builder.append("\n\n> To complete this route:")
                    .append(System.lineSeparator())
                    .append(summedWeight)
                    .append(" km in total is needed");

        }
        else builder.append("COULD NOT PLAN A ROUTE TO DESTINATION!\n");

        builder.append("\u001B[0m");

        return builder.toString();
    }
}
