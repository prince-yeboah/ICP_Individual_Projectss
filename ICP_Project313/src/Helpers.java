import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;





/**
 * A helper class the contains all the necessary methods to help execute the program as well as the main method at the button
 * of the code
 */
public class Helpers {

    private final ArrayList<Airport> arrayListofAirports;
	private final ArrayList<Route> arrayListofRoutes;
	private HashMap<Airport, ArrayList<Airport>> aNodes;
	
	public Helpers(ArrayList<Airport> airports, ArrayList<Route> routes) {
		this.arrayListofAirports = airports;
		this.arrayListofRoutes = routes;
		the_a_Nodes();
	}
	
	public Airport findAirportById(int id) {
		for( Airport Node : arrayListofAirports ) {
			if( Node.getId() == id ) {
				return Node;
			}
		}
		return null;
	}

	private void the_a_Nodes() {
		aNodes = new HashMap<>();

		for(Airport node : arrayListofAirports ) {
			aNodes.put(node, new ArrayList<>());
		}

		for( Route edge : arrayListofRoutes ) {
			Airport node = findAirportById(Integer.parseInt(edge.getSourceAirportID()));
			if( node==null ) {
				continue;
			}
			if( findAirportById(Integer.parseInt(edge.getDestinationAirportID())) != null ) {
				aNodes.get(node).add(findAirportById(Integer.parseInt(edge.getDestinationAirportID())));
			}
		}
	}
	
	private Route getRoute(Airport startDestination, Airport endDestination) {
		for( Route edge : arrayListofRoutes ) {
			if( edge.getSourceAirportID().equals("" + startDestination.getId()) && edge.getDestinationAirportID().equals("" + endDestination.getId())) {
				return edge;
			}
		}
		return null;
	}
	
	// to find the path between startDestination and endDestination airport
	public ArrayList<Route> routeTaken(Airport startDestination, Airport endDestination) {
		ArrayList<Route> route = new ArrayList<>();
		
		// to indicate if a Node is explored
		// all arrayListofAirports are unexplored right now
		HashMap<Airport, Boolean> explored = new HashMap<>();
		// to store shortest distances
		HashMap<Airport, Integer> shortestDistances = new HashMap<>();
		for( Airport Node : arrayListofAirports ) {
			explored.put(Node, false);
			shortestDistances.put(Node, Integer.MAX_VALUE);
		}
		// Storing the parent of each node or airport
		HashMap<Airport, Airport> parent = new HashMap<>();
		shortestDistances.put(startDestination, 0);
		parent.put(startDestination, null);
		
		for( Airport readLine : arrayListofAirports) {
			
			// finding the nearest node
			Airport nearestNode = null;
			int shortestDistance = Integer.MAX_VALUE;
			for( Airport airport : arrayListofAirports ) {
				if( !explored.get(airport) && shortestDistances.get(airport)<shortestDistance ) { 
					nearestNode = airport;
					shortestDistance = shortestDistances.get(airport);
				}
			}
			
			explored.put(nearestNode, true);
			
			if(null == aNodes.get(nearestNode)) {
				continue;
			}
			
			// updating through iteration of the  distances of adjacent nodes
			for( Airport v : aNodes.get(nearestNode) ) {
				if( (shortestDistance + Haversince(nearestNode, v)) < shortestDistances.get(v) ) {
					parent.put(v, nearestNode);
					shortestDistances.put(v, shortestDistance + Haversince(nearestNode, v));
				}
			}
		}
		
		Airport currentDestination = endDestination;
		while( parent.get(currentDestination) != null ) {
			route.add(0, getRoute(parent.get(currentDestination), currentDestination));
			currentDestination = parent.get(currentDestination);
		}
		
		return route;
	}
    

    public int Haversince(Airport startDestination, Airport endDestination) {
		/*
		Inspiration from the Iddriss Raaj in the implementation of the Haversine formula
		*/
		double latitudeDistance = Math.toRadians((endDestination.getLatitude() - startDestination.getLatitude()));
        double longitudeDistance = Math.toRadians((endDestination.getLongitude() - startDestination.getLongitude()));
 
        double l1 = Math.toRadians((startDestination.getLatitude()));
        double l2 = Math.toRadians((endDestination.getLatitude()));
 
        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
				+ Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2)
				* Math.cos(l1) * Math.cos(l2);

		// radius of the earth
        double earthRadius = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
		return (int) (earthRadius * c);
	}
    

    // initializing the required files to string variables.
    //database dr
    //public static final String routeData = "/Users/yeboahprincee/Documents/ICP_Project313/src/routes.csv";
    public static final String routeData = "routes.csv";
	//public static final String airportData = "/Users/yeboahprincee/Documents/ICP_Project313/src/airports.csv";
    public static final String airportData = "airports.csv";
	//public static final String airlineData = "/Users/yeboahprincee/Documents/ICP_Project313/src/airlines.csv";
    public static final String airlineData = "airlines.csv";
    


// thea main static method to run the program
    public static void main(String[] args) {

		// String variable to hold the name of the input file.
		String fileName = "input.txt";

		// Creating an Array list for each file read
		ArrayList<Airport> airports = airportFile();
		airlinesCSV();
		ArrayList<Route> routes = routesCSV();

		// try to read city names from the file
		try {
			Scanner sc = new Scanner(new File(fileName));
			// get output file name
			String outputFile = getOutputFileName(fileName);
			PrintWriter printWriter = new PrintWriter(outputFile);

			// read start location and endDestination location
			String startLocation = sc.nextLine();
			String destinationLocation = sc.nextLine();

			// Location of the airports
			Airport startAirportDestination = airportUsed(startLocation, airports);
			Airport endAirportDestination = airportUsed(destinationLocation, airports);

			// if the start location is null it would print the following text
			if( startAirportDestination == null ) {
				printWriter.println("There is not an airport in " + startLocation);
			}
			// if the endDestination location is null it would print the following text
			else if( endAirportDestination == null ) {
				printWriter.println("Cannot find you endDestination Airport: " + destinationLocation);
			}
			else {
				// if both the start and the endDestination are not null, the route taken would be printed to the output file
				Helpers Helpers = new Helpers(airports, routes);
				// find path
				ArrayList<Route> path = Helpers.routeTaken(startAirportDestination, endAirportDestination);
				// print the route of the flights.
				int numAdditionalStops = 0;
				int totalNumberOfFlights=0;
				int totalDistance = 0;
				for( Route route : path ) {
					printWriter.println("\t" + (++totalNumberOfFlights) + ". " + route.getAirlineCode() + " from " + route.getSourceAirportCode() + " to " + route.getDestinationAirportCode() + " " + route.getStops() + " stops.");
					numAdditionalStops += route.getStops();
					totalDistance += Helpers.Haversince( Helpers.findAirportById(Integer.parseInt(route.getSourceAirportID())),
							Helpers.findAirportById(Integer.parseInt(route.getDestinationAirportID())));
				}
				printWriter.println("Total flights: " + totalNumberOfFlights);
				printWriter.println("Total additional stops: " + numAdditionalStops);
				printWriter.println("Total distance: " + totalDistance + "km.");
			}
            sc.close();
			printWriter.close();

			System.out.print("Your output file has been written.\n Please check the file with the name: " + outputFile);

		}
		catch(FileNotFoundException e) {
			System.out.println("This file is not found.");
		}
	}

	// Method toread airport data from file
	private static ArrayList<Airport> airportFile() {
		
		try {
			ArrayList<Airport> airports = new ArrayList<>();
			Scanner sc = new Scanner(new File(airportData));

			while( sc.hasNextLine() ) {

				// read every line in the airport file
				String everyline = sc.nextLine();

				// extracting the data from every line and placing them in the string stringEveryValue
				String[] stringEveryValue = everyline.split(",");
				if( stringEveryValue.length != 14 ) {
					continue;
				}
                // if any of the "stringEveryValue" lines has the value "\N" then move pass it and go to another row 
				boolean invalidData = false;
				for (String readLine : stringEveryValue) {
					if (readLine.equals("\\N")) {
						invalidData = true;
						break;
					}
				}
				if(invalidData) {
					continue;
				}
				Airport airport = new Airport(Integer.parseInt(stringEveryValue[0]), stringEveryValue[1], stringEveryValue[2], stringEveryValue[3], stringEveryValue[4], stringEveryValue[5],
						Double.parseDouble(stringEveryValue[6]), Double.parseDouble(stringEveryValue[7]), Double.parseDouble(stringEveryValue[8]), Double.parseDouble(stringEveryValue[9]),
						stringEveryValue[10], stringEveryValue[11], stringEveryValue[12], stringEveryValue[13]);
				airports.add(airport);
			}
			sc.close();
			return airports;
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot open input file " + airportData);
			return null;
		}
	}
	
	// the helper method to read airline data from file
	private static void airlinesCSV() {
		
		try {
			ArrayList<Airline> airlines = new ArrayList<>();
			Scanner sc = new Scanner(new File(airlineData));
			while( sc.hasNextLine() ) {
				String everyline = sc.nextLine();
				String[] AIR = everyline.split(",");

				// This code check the length of each everyline.
				// If the length of the everyline is not equal to 8, then there is some invalid data
				// If there is invalid data, skip past it because the reason for that data will be unknown and probably a typo.
				if( AIR.length != 8 ) {
					continue;
				}
				// if any of the AIR lines has this character: "\N", skip this row
				boolean invalidData = false;
				for (String readLine : AIR) {
					if (readLine.equals("\\N")) {
						invalidData = true;
						break;
					}
				}
				if( invalidData ) {
					continue;
				}
				// creating a new airline with the specific columns needed
				Airline airline = new Airline(Integer.parseInt(AIR[0]), AIR[1], AIR[2], AIR[3], AIR[4], AIR[5],
						AIR[6], AIR[7]);
				airlines.add(airline);
			}
			sc.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot open input file " + airlineData);
		}
	}
	
	// the helper method to read route data from file
	private static ArrayList<Route> routesCSV() {
		
		try {
			ArrayList<Route> routes = new ArrayList<>();
			Scanner sc = new Scanner(new File(routeData));
			while( sc.hasNextLine() ) {
				String everyline = sc.nextLine();
				String[] RT = everyline.split(",");

				// This code check the length of each everyline.
				// If the length of the everyline is not equal to 9, then there is some invalid data
				// If there is invalid data, skip past it because the reason for that data will be unknown and probably a typo.
				if( RT.length != 9 ) {
					continue;
				}

				// if any of the RT lines has this character: "\N", skip this row
				boolean invalidData = false;
				for (String readLine : RT) {
					if (readLine.equals("\\N")) {
						invalidData = true;
						break;
					}
				}
				if( invalidData ) {
					continue;
				}
				// creating a new route with the specific needed columns
				Route route = new Route(RT[0], RT[1], RT[2], RT[3], RT[4], RT[5],
						RT[6], Integer.parseInt(RT[7]), RT[8]);
				routes.add(route);
			}
			sc.close();
			return routes;
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot open input file " + routeData);
			return null;
		}
	}
	
	// This static method will get the name of the output file.
	private static String getOutputFileName(String fileName) {
		String name = fileName.substring(0, fileName.indexOf('.'));
		return name + "_output.txt";
	}
	
	// extracting the value of the city and country of a specific airport.
	private static Airport airportUsed(String readLine, ArrayList<Airport> airports) {
		String city = readLine.substring(0, readLine.indexOf(','));
		String country = readLine.substring(readLine.indexOf(',') + 1).trim();
		// using just the equals() method will not ignore cases so the equalsIgnoreCase() method was implemented.
		for( Airport airport : airports )
			if (airport.getCity().equalsIgnoreCase(city) && airport.getCountry().equalsIgnoreCase(country)) {
				return airport;
			}
		return null;
	}
}


    
