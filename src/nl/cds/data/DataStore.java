package nl.cds.data;

import nl.cds.model.Graph;
import nl.cds.model.GraphNode;
import nl.cds.model.records.Employee;
import nl.cds.model.records.Incident;
import nl.cds.model.records.Subscriber;
import nl.cds.tools.DataReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to read and store case data
 */
public class DataStore {

    private final ArrayList<Employee> employees;        //In random order
    private final ArrayList<Incident> incidents;        //In random order
    private final ArrayList<Subscriber> subscribers;    //In random order
    private final Graph<String> citiesKilometerWeight, citiesHourWeight;

    /**
     * Initializes and populates all datasets (Employees, Subscribers, Incidents, City connections by KM, City connections by Minutes)
     */
    public DataStore() {
        this.employees = new ArrayList<>();
        this.incidents = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.citiesHourWeight = new Graph<>();
        this.citiesKilometerWeight = new Graph<>();

        try {
            populateEmployees();
            populateSubscribers();
            populateIncidents();
            createKilometerGraph();
            createHourGraph();
        } catch (FileNotFoundException e) {
            System.err.println("Warning: Could not read in data from the CSV files");
        }
    }

    private void populateEmployees() throws FileNotFoundException {
        DataReader dataReader = new DataReader("./data/Employees.csv", ";");
        dataReader.readNextLine();
        while (dataReader.hasNext()){
            String[] lineData = dataReader.readNextLine();
            employees.add(new Employee(lineData[0],lineData[1], lineData[2]));
        }
        assert (!employees.isEmpty());
        Collections.shuffle(employees);
        dataReader.close();
    }

    private void populateSubscribers() throws FileNotFoundException {
        DataReader dataReader = new DataReader("./data/Subscribers.csv", ";");
        dataReader.readNextLine();
        while (dataReader.hasNext()){
            String[] lineData = dataReader.readNextLine();
            subscribers.add(new Subscriber(lineData[0],lineData[1], lineData[2]));
        }
        assert (!subscribers.isEmpty());
        Collections.shuffle(subscribers);
        dataReader.close();
    }

    private void populateIncidents() throws FileNotFoundException {
        DataReader dataReader = new DataReader("./data/Incidents.csv", ";");
        dataReader.readNextLine();
        while (dataReader.hasNext()){
            String[] lineData = dataReader.readNextLine();
            Incident tempIncident = new Incident(lineData[0], lineData[3], lineData[4], Integer.parseInt(lineData[5]));
            incidents.add(tempIncident);
        }
        assert (!incidents.isEmpty());
        Collections.shuffle(incidents);
        dataReader.close();
    }

    private void createKilometerGraph() throws FileNotFoundException {createGraph(citiesKilometerWeight,1);}
    private void createHourGraph() throws FileNotFoundException {createGraph(citiesHourWeight,2);}

    private void createGraph(Graph<String> destinationGraph, int weightIndex) throws FileNotFoundException {
        DataReader dataReader = new DataReader("./data/CountryMap.txt"," => ");
        while (dataReader.hasNext()){
            String[] lineData = dataReader.readNextLine();
            assert lineData.length > 0;
            GraphNode<String> sourceCity = null;
            for (String city : lineData) {
                assert (!city.contains("=>") && !city.contains(" "));
                Pattern pattern = Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
                Matcher matcher = pattern.matcher(city);
                boolean matchFound = matcher.find();
                if(matchFound) { //City with weight
                    assert (!matcher.group(weightIndex).isEmpty());
                    int weight = Integer.parseInt(matcher.group(weightIndex));
                    String cityName = city.replace(matcher.group(0), "");
                    pattern = Pattern.compile("^.*[\\)\\(\\,0-9]+.*$");
                    matcher = pattern.matcher(cityName);
                    matchFound = matcher.find();
                    assert (!matchFound);
                    assert (sourceCity != null);
                    sourceCity.addAdjacentNode(destinationGraph.addValue(cityName),weight);
                } else { //City without weight
                    pattern = Pattern.compile("^.*[\\)\\(\\,0-9]+.*$");
                    matcher = pattern.matcher(city);
                    matchFound = matcher.find();
                    assert (!matchFound);
                    sourceCity = destinationGraph.addValue(city);
                }
            }
        }
    }

    public Incident[] getIncidents(int maxNumber) {
        if(maxNumber > 90) return generateMoreIncidents(maxNumber - incidents.size()).clone();
        else return incidents.toArray(new Incident[0]);
    }

    private Incident[] generateMoreIncidents(int i) {
        assert (i > 0);
        Incident[] newAmountOfIncidents = new Incident[ i + incidents.size()];
        System.arraycopy(incidents.toArray(new Incident[0]),0,newAmountOfIncidents,0,incidents.size());
        for(int j = 0; j < i; j++){
            int index = j < 90 ? j : j - (90 * (j / 90));
            Incident inc = new Incident(
                    "I" + (j + incidents.size() + 10),
                    incidents.get(index).category(),
                    incidents.get(index).priority(),
                    incidents.get(index).resolveDuration());
            newAmountOfIncidents[j + incidents.size()] = inc;
        }
        assert (newAmountOfIncidents.length == (i + incidents.size()));
        return newAmountOfIncidents;
    }

    public Graph<String> getCitiesKilometerWeight() {
        return citiesKilometerWeight;
    }

    public Graph<String> getCitiesHourWeight() {
        return citiesHourWeight;
    }
}
