package assignment6;

import java.util.List;
public class RoundTripPlanner {
    // user inputs for the source and destination
    private int startCityIndex;
    private int endCityIndex;
    // Graph created using the following vertices and edges
    private WeightedGraph<String> flightNetwork;
    // array of vertices
    private String[] cities;
    // array of weighted edges [source][dest][weight]
    private int[][] connections;
    // forward and return route cities lists and cost of trip
    private List<String> forwardRoute;
    private double forwardRouteCost;
    private List<String> returnRoute;
    private double returnRouteCost;
    /*
     * Constructor:
     * - Assigns class variables
     * - Invokes generateRoundTrip() method
     */
    public RoundTripPlanner(String[] cities, int[][] connections, int
            startCityIndex, int endCityIndex) {
        this.cities= cities;
        this.connections=connections;
        this.startCityIndex=startCityIndex;
        this.endCityIndex=endCityIndex;
        generateRoundTrip();
    }
    /*
     * Round trip generator:
     * - Creates flight network graph
     * - Updates forward trip path variable and forward trip cost
     * - Performs necessary actions for return trip planning
     * - Updates return trip path variable and return trip cost
     */
    public void generateRoundTrip() {
        flightNetwork= new WeightedGraph<>(cities, connections); //create a weighted graph from cities and connections called flightNetwork
        WeightedGraph.ShortestPathTree shortestPath1 = flightNetwork.getShortestPath(startCityIndex); //Get the tree of shortest paths from starting index
        forwardRoute= shortestPath1.getPath(endCityIndex); //From the shortest path tree get the path from starting to end index
        forwardRouteCost= shortestPath1.getCost(endCityIndex); //Get the cost as well
        int temp3=endCityIndex; //temp3 and temp2 are used so proper transit cities are accessed
        int temp2;
        while(temp3!=startCityIndex) { //iterate till we are back at start
            temp2= shortestPath1.getParent(temp3); //set temp2 to parent of temp3
            for (Edge e : flightNetwork.neighbors.get(temp3)) { //set edge between temp3 and temp2 to max
                if(e.v == temp2) {
                    ((WeightedEdge) e).weight = Integer.MAX_VALUE;
                }
            }
            for (Edge e : flightNetwork.neighbors.get(temp2)) { //set all edges exiting temp2 to max
                ((WeightedEdge) e).weight = Integer.MAX_VALUE;
            }
            temp3=temp2;//set temp3 to temp2
        }
        WeightedGraph.ShortestPathTree shortestPath2 = flightNetwork.getShortestPath(endCityIndex);
        returnRoute= shortestPath2.getPath(startCityIndex);
        returnRouteCost= shortestPath2.getCost(startCityIndex);
    }
    /*
     * Trip viewer:
     * - prints forward trip in the format:
     * "Forward trip from A to B: A > P > Q > R > B"
     * - prints return trip in the same format:
     * "Return trip from B to A: B > S > T > U > A"
     * - prints the costs for the forward trip, return trip, and total trip in the
    format:
     *  "Forward route cost: 200.0"
     *  "Return route cost: 300.0"
     *  "Total trip cost: 500.0"
     */
    public void printRoundTrip() {
        //Output formatted
        String temp= forwardRoute.get(0);
        for(int i=1; i<forwardRoute.size(); i++) {
            temp += " > " + forwardRoute.get(i);
        }
        System.out.println("Forward trip from " + cities[startCityIndex] + " to " + cities[endCityIndex] + ": " + temp);
        String temp2= returnRoute.get(0);
        for(int i=1; i<returnRoute.size(); i++) {
            temp2 += " > " + returnRoute.get(i);
        }
        System.out.println("Return trip from " + cities[endCityIndex] + " to " + cities[startCityIndex] + ": " + temp2);
        System.out.println("Forward route cost: " + forwardRouteCost);
        System.out.println("Return route cost: " + returnRouteCost);
        System.out.println("Total trip cost: " + (forwardRouteCost+returnRouteCost));
    }
    // Returns the forwardRoute class variable
    public List<String> getForwardRoute() {
        return forwardRoute;
    }
    // Returns the returnRoute class variable
    public List<String> getReturnRoute() {
        return returnRoute;
    }
    // Returns the forwardRouteCost class variable
    public double getForwardRouteCost() {
        return forwardRouteCost;
    }
    // Returns the returnRouteCost class variable
    public double getReturnRouteCost() {
        return returnRouteCost;
    }
}

/*
Select your starting city: (0 - 11): 7
Select your destination city: (0 - 11): 10
Forward trip from New York to Dallas: New York > Atlanta > Dallas
Return trip from Dallas to New York: Dallas > Kansas City > New York
Forward route cost: 1669.0
Return route cost: 1756.0
Total trip cost: 3425.0
Roundtrip planning successful!

Select your starting city: (0 - 11): 3
Select your destination city: (0 - 11): 5
Forward trip from Denver to Chicago: Denver > Chicago
Return trip from Chicago to Denver: Chicago > Kansas City > Denver
Forward route cost: 1003.0
Return route cost: 1132.0
Total trip cost: 2135.0
Roundtrip planning successful!
Be a little more adventurous! Travel further!
 */