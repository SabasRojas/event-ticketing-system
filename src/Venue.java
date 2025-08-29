/**
 * Venue: Handles all information related to venue objects
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */
public abstract class Venue {

    //ATTRIBUTES
    private String name;
    private int capacity;
    private double costToRent;
    private String venueType;

    //Default constructor:
    public Venue(){}

    //Constructor for venue in CSV file.
    public Venue(String nameIn, String typeIn, int capacityIn, Double costIn){
        this.name = nameIn;
        this.capacity = capacityIn;
        this.costToRent = costIn;
        this.venueType = typeIn;
    }

    // SETTERS:

    /**
     *@param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @param costToRent
     */
    public void setCostToRent(int costToRent) {
        this.costToRent = costToRent;
    }

    /**
     * @param venueType
     */
    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    // GETTERS:

    /**
     * @return venue name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return venue capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @return cost of venue
     */
    public double getCostToRent() {
        return this.costToRent;
    }

    /**
     * @return venue type
     */
    public String getVenueType() {
        return this.venueType;
    }

}