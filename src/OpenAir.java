/**
 * OpenAir: Class that holds OpenAir information. Uses super()
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class OpenAir extends Venue{

    // Default constructor:
    public OpenAir(){}

    // Constructor that uses super():
    public OpenAir(String nameIn, String typeIn, int capacityIn, double costIn){
        super(nameIn, typeIn, capacityIn, costIn);
    }
}