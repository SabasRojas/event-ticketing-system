/**
 * VenueFactory: Design patten used to create venue objects
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class VenueFactory{
    
    // CONSTRUCTOR:
    public VenueFactory(){}

    /**
     * @param eventType
     * @return Event of specific type
     */
    public static Venue createVenue(String venueType){
        if(venueType.equalsIgnoreCase("Stadium")){
            return new Stadium();
        }else if(venueType.equalsIgnoreCase("Arena")){
            return new Arena();
        }else if(venueType.equalsIgnoreCase("Auditorium")){
            return new Auditorium();
        }else{
            return new OpenAir();
        }
    }
}