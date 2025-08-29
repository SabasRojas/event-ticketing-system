/**
 * Auditorium: Class that holds auditorium information. Uses super()
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class Auditorium extends Venue{
    
    // Default constructor:
    public Auditorium(){}

    // Constructor that uses super():
    public Auditorium(String nameIn, String typeIn, int capacityIn, double costIn){
        super(nameIn, typeIn, capacityIn, costIn);
    }


}