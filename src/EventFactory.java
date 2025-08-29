/**
 * EventFactory: Design pattern used to create event objects
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class EventFactory{

    // CONSTRUCTOR:
    public EventFactory(){}

    /**
     * @param eventType
     * @return Event of specific type
     */
    public static Event creatEvent(String eventType){
        if(eventType.equalsIgnoreCase("Sport")){
            return new Sport();
        }
        else if(eventType.equalsIgnoreCase("Concert")){
            return new Concert();
        }
        else{
            return new Festival();
        }
    }
}