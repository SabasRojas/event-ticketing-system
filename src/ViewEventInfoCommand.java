/**
 * ViewEventInfoCommand: Class that is used to search for event. 
                        Is used by Command class (data pattern)
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

import java.util.HashMap;

class ViewEventInfoCommand extends MainMenu implements Command {

    private HashMap<Integer, Event> eventStorage;
    private int eventId; // Change the type from Event to int
    private String eventName; // Change the type from Event to String

    public ViewEventInfoCommand(HashMap<Integer, Event> eventStorage, int eventId) {
        this.eventStorage = eventStorage;
        this.eventId = eventId;
    }

    public ViewEventInfoCommand(HashMap<Integer, Event> eventStorage, String eventName) {
        this.eventStorage = eventStorage;
        this.eventName = eventName;
    }

    @Override
    public void execute() {
        if (eventId != 0) {
            executeByEventId();
        } else if (eventName != null) {
            executeByEventName();
        } else {
            System.out.println("Invalid command parameters.");
        }
    }

    private void executeByEventId() {
        Event foundEvent = eventStorage.get(eventId);
        if (foundEvent != null) {
            System.out.println("----------------------------");
            foundEvent.adminPrintEventInfo();
        } else {
            System.out.println("Event not found.");
        }
    }

    private void executeByEventName() {
        Event foundEvent = null;
        for (Event event : eventStorage.values()) {
            if (event.getEventName().equalsIgnoreCase(eventName)) {
                foundEvent = event;
                break;
            }
        }
        if (foundEvent != null) {
            System.out.println("----------------------------");
            foundEvent.adminPrintEventInfo();
        } else {
            System.out.println("Event not found.");
        }
    }
}
