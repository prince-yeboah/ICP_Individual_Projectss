/**
 * Prince Sefa Yebaoh
 * ICP Individual Project
 */


/**
 * 
 */
public class Airline {
    private int id;
	private String country;

/**
 * A constructor
 * @param id
 * @param name
 * @param alias
 * @param iATACode
 * @param iCAOCode
 * @param callSign
 * @param country
 * @param active
 */
	public Airline(int id, String name, String alias, String iATACode, String iCAOCode, String callSign, String country,
				   String active) {
		this.id = id;
		this.country = country;
	}


    /**
     * Accessor methods used to get the required values in the Airline Dataset
     * @return
     */
	public int getId() {
		return id;
	}

	public String getCountry() {
		return country;
	}

}