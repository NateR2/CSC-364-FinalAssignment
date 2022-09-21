package assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class TripTester {
    // user inputs for the source and destination
    static int startCityIndex;
    static int endCityIndex;
    // array of vertices
    static String[] cities = {
            "Seattle",  // 0
            "San Francisco",// 1
            "Los Angeles",  // 2
            "Denver",  // 3
            "Kansas City",  // 4
            "Chicago",  // 5
            "Boston",  // 6
            "New York",  // 7
            "Atlanta",  // 8
            "Miami",  // 9
            "Dallas",  // 10
            "Houston" };  // 11
    // array of weighted edges [source][dest][weight]
    static int[][] connections = {
            { 0, 1, 807 }, { 0, 3, 1331 }, { 0, 5, 2097 },
            { 1, 0, 807 }, { 1, 2, 381 }, { 1, 3, 1267 },
            { 2, 1, 381 }, { 2, 3, 1015 }, { 2, 4, 1663 }, { 2, 10, 1435 },
            { 3, 0, 1331 }, { 3, 1, 1267 }, { 3, 2, 1015 }, { 3, 4, 599 },
            { 3, 5, 1003 },
            { 4, 2, 1663 }, { 4, 3, 599 }, { 4, 5, 533 }, { 4, 7, 1260 },
            { 4, 8, 864 }, { 4, 10, 496 },
            { 5, 0, 2097 }, { 5, 3, 1003 }, { 5, 4, 533 }, { 5, 6, 983 },
            { 5, 7, 787 },
            { 6, 5, 983 }, { 6, 7, 214 },
            { 7, 4, 1260 }, { 7, 5, 787 }, { 7, 6, 214 }, { 7, 8, 888 },
            { 8, 4, 864 }, { 8, 7, 888 }, { 8, 9, 661 }, { 8, 10, 781 }, { 8,
            11, 810 },
            { 9, 8, 661 }, { 9, 11, 1187 },
            { 10, 2, 1435 }, { 10, 4, 496 }, { 10, 8, 781 }, { 10, 11, 239 },
            { 11, 8, 810 }, { 11, 9, 1187 }, { 11, 10, 239 } };
    public static void main(String[] args) {
// print out list of cities with index values
        System.out.println("List of Cities");
        for (int i = 0; i < cities.length; i++)
            System.out.println("[" + i + "] " + cities[i]);
        userPrompt();
        RoundTripPlanner roundTripPlanner = new RoundTripPlanner(cities, connections, startCityIndex, endCityIndex);
        roundTripPlanner.printRoundTrip();
        verifyRoundTrip(roundTripPlanner);
    }
    static void userPrompt() {
        Scanner scanner = new Scanner(System.in);
        boolean firstInputDone = false;
        boolean secondInputDone = false;
        while (!firstInputDone || !secondInputDone) {
            try {
// parse user input as startCityIndex integer
                if (!firstInputDone) {
                    System.out.print("Select your starting city: (0 - 11): ");
                    startCityIndex = Integer.parseInt(scanner.next());
                    if (startCityIndex < 0 || startCityIndex > 11)
                        throw new Exception("Start city needs to be between 0 - 11");
                                firstInputDone = true;
                }
// parse user input as endCityIndex integer
                if (!secondInputDone) {
                    System.out.print("Select your destination city: (0 - 11): ");
                    endCityIndex = Integer.parseInt(scanner.next());
                    if (endCityIndex < 0 || endCityIndex > 11)
                        throw new Exception("Destination city needs to be between 0 - 11");
                                secondInputDone = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid entry. Please enter an integer between 0 - 11");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        scanner.close();
    }
    private static void verifyRoundTrip(RoundTripPlanner roundTripPlanner) {
        List<String> forwardRouteCopy = new ArrayList<String>(roundTripPlanner.getForwardRoute());
        List<String> returnRouteCopy = new ArrayList<String>(roundTripPlanner.getReturnRoute());
// Removing common elements; Must make size=2; source + dest
        forwardRouteCopy.retainAll(roundTripPlanner.getReturnRoute());
// Deleting common elements; Must make size > 0
        returnRouteCopy.removeAll(forwardRouteCopy);
        if (forwardRouteCopy.size() == 2
                && returnRouteCopy.size() != 0
                && forwardRouteCopy.get(0).equals(cities[startCityIndex])
                && forwardRouteCopy.get(1).equals(cities[endCityIndex])) {
            System.out.println("Roundtrip planning successful!");
            if (roundTripPlanner.getForwardRoute().size() == 2 || roundTripPlanner.getReturnRoute().size() == 2)
                System.out.println("Be a little more adventurous! Travel further!");
        } else
            System.out.println("Roundtrip planning not successful!");
    }
}
