/**
 * Prince Sefa Yebaoh
 * ICP Individual Project
 */


/**
 * 
 */
public class Route {
    private String airlineCode;
	private String sourceAirportCode;
	private String sourceAirportID;
	private String destinationAirportCode;
	private String destinationAirportID;
	private int stops;

    /**
     * Constructor
     * @param airlineCode
     * @param airlineID
     * @param sourceAirportCode
     * @param sourceAirportID
     * @param destinationAirportCode
     * @param destinationAirportID
     * @param codeShare
     * @param stops
     * @param equipment
     */
	public Route(String airlineCode, String airlineID, String sourceAirportCode, String sourceAirportID,
				 String destinationAirportCode, String destinationAirportID, String codeShare, int stops, String equipment) {
		this.airlineCode = airlineCode;
		this.sourceAirportCode = sourceAirportCode;
		this.sourceAirportID = sourceAirportID;
		this.destinationAirportCode = destinationAirportCode;
		this.destinationAirportID = destinationAirportID;
		this.stops = stops;
	}


    /**
     * Accessor methods to get the necessary values in the Route dataset.
     * @return
     */
	public String getAirlineCode() {
		return airlineCode;
	}

	public String getSourceAirportCode() {
		return sourceAirportCode;
	}

	public String getSourceAirportID() {
		return sourceAirportID;
	}

	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}

	public String getDestinationAirportID() {
		return destinationAirportID;
	}

	public int getStops() {
		return stops;
	}
}