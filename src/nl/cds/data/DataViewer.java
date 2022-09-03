package nl.cds.data;

import nl.cds.cases.CableRoute.PrimRoutePlot;
import nl.cds.cases.MostCommonIncident.ComIncLinear;
import nl.cds.cases.MostCommonIncident.ComIncSorted;
import nl.cds.cases.RetrieveIncident.StatusAVL;
import nl.cds.cases.RetrieveIncident.StatusBinary;
import nl.cds.cases.RetrieveIncident.StatusSortedLinear;
import nl.cds.cases.RetrieveIncident.StatusUnsortedLinear;
import nl.cds.cases.RouteToCity.DijkstraRoutePlot;
import nl.cds.model.GraphNode;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for interacting with the user and running selected cases
 */
public class DataViewer {
    DataStore dataStore;
    Scanner scanner;

    public DataViewer() {
        dataStore = new DataStore();
        scanner = new Scanner(System.in);
        readMenu();
    }

    public void printMenu(){
        System.out.println("""
                --- Telecom Data Viewer ---
                /// Please make a selection ///
                
                1) Check repair status
                2) Show most common type of incident
                3) Plan efficient route to a city from the office
                4) Plan efficient route for cable from the office through all cities
                0) Exit
                
                \\/\\/\\/ More options to come /\\/\\/\\
                """);
    }

    private void readMenu() {
        int choice = -1;
        Pattern pattern = Pattern.compile("^[0-4]$");
        do{
            printMenu();
            System.out.print("\n>");

            String input = scanner.next();
            Matcher matcher = pattern.matcher(input);

            if(!matcher.find()) {
                System.out.println("Please enter a number between 0 and 4!\n");
                continue;
            }

            assert (input.length() == 1);

            choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> checkRepair();
                case 2 -> showMostCommonIncident();
                case 3 -> plotRoute();
                case 4 -> plotCable();
                case 0 -> {}
                default -> System.out.println("!!! Unknown option !!!\n");
            }
        } while (choice != 0);
    }

    private void checkRepair(){
        String id;
        Pattern pattern = Pattern.compile("^I[1-9][0-9]$");
        boolean matchFound;
        do {
            System.out.print("Enter Incident ID | > ");
            id = scanner.next();
            Matcher matcher = pattern.matcher(id);
            matchFound = matcher.find();

            if(!matchFound) System.out.println("Invalid input, please try again!");
        } while (!matchFound);

        assert (id.charAt(0) == 'I');

        StatusBinary checkSortedRepair = new StatusBinary("Find repair by id in sorted list by binary search",dataStore,id);
        StatusUnsortedLinear checkUnsortedRepair = new StatusUnsortedLinear("Find repair by id in unsorted list by linear search", dataStore, id);
        StatusSortedLinear checkSortedRepairLinear = new StatusSortedLinear("Find repair by id in sorted list by linear search", dataStore, id);
        StatusAVL checkRepairAVL = new StatusAVL("Find repair by id in AVL Tree", dataStore, id);

        System.out.println(checkSortedRepair.start());
        System.out.println(checkUnsortedRepair.start());
        System.out.println(checkSortedRepairLinear.start());
        System.out.println(checkRepairAVL.start());

        awaitEnterPress();
    }

    private void showMostCommonIncident(){
        ComIncLinear mostCommonIncidentTypeLinear = new ComIncLinear("Find most common incident in list by linear search",dataStore);
        ComIncSorted mostCommonIncidentTypeSorted = new ComIncSorted("Find most common incident in list by sorting",dataStore);
        System.out.println(mostCommonIncidentTypeLinear.start());
        System.out.println(mostCommonIncidentTypeSorted.start());
        awaitEnterPress();
    }

    private void plotRoute(){
        String inputCityName;
        GraphNode<String> city;
        do{
            System.out.print("Enter destination city: ");
            inputCityName = scanner.next();
            assert (!inputCityName.isEmpty() && !inputCityName.isBlank());
            city = dataStore.getCitiesHourWeight().findValue(inputCityName);
            if(city == null) System.out.println("City doesn't exist, please try again!");
        }while(city == null);
        DijkstraRoutePlot findMostEffRoutToCity = new DijkstraRoutePlot("Find most efficient route from base to a city",dataStore, city);
        System.out.println(findMostEffRoutToCity.start());
        awaitEnterPress();
    }

    private void plotCable(){
        PrimRoutePlot findRouteAllCities = new PrimRoutePlot("Find most efficient route from base through all cities", dataStore);
        System.out.println(findRouteAllCities.start());
        awaitEnterPress();
    }

    private void awaitEnterPress(){
        System.out.println("Press ENTER to continue");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
