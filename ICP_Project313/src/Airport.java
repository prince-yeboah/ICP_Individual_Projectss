/**
 * Prince Sefa Yebaoh
 * ICP Individual Project
 */


public class Airport {
    private int id;
	private String city;
	private String country;
	private double latitude;
	private double longitude;


    /**
     * A constructor
     * @param id
     * @param name
     * @param city
     * @param country
     * @param iATACode
     * @param iCAOCode
     * @param latitude
     * @param longitude
     * @param altitude
     * @param timezone
     * @param dST
     * @param tzDBTimezone
     * @param type
     * @param dataSource
     */
	public Airport(int id, String name, String city, String country, String iATACode, String iCAOCode, double latitude,
				   double longitude, double altitude, double timezone, String dST, String tzDBTimezone, String type,
				   String dataSource) {
		this.id = id;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	/*
	 * Getters methods to access the necessary columns needed for execution
	 */
	public int getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
}