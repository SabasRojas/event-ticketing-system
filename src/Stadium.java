/**
 * Stadium: Class that holds type of venue information. Uses super()
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class Stadium extends Venue{
    
    // Default Constructor:
    public Stadium(){}

    // Constructor that uses super():
    public Stadium(String nameIn, String typeIn, int capacityIn, double costIn){
        super(nameIn, typeIn, capacityIn, costIn);
    }
}