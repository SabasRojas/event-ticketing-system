/**
 * CSVHandler: This class handles all functionality related to reading and writing
 *             a csv file.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class CSVHandler{
    private String customerPath;
    private String eventPath;
    private String outputCustomerPath;
    private String outputEventPath;
    private Event newEvent;
    // private HashMap<String, Integer> headerToIndex;

    // DEFAULT CONSTRUCTOR:
    public CSVHandler(){}

    /**
     * @param customerPathIn
     * @param eventPathIn
     * @brief initialize file paths for customer and event files
     */
    public CSVHandler(String customerPathIn, String eventPathIn, String outCustomePath, String outEventPath){
        this.customerPath = customerPathIn;
        this.eventPath = eventPathIn;
        this.outputCustomerPath = outCustomePath;
        this.outputEventPath = outEventPath;
    }

    // SETTERS:
    /**
     *
     * @param customerPath
     */
    public void setCustomerPath(String customerPath) {
        this.customerPath = customerPath;
    }

    /**
     * 
     * @param eventPath
     */
    public void setEventPath(String eventPath) {
        this.eventPath = eventPath;
    }

    // GETTERS:
    /**
     * 
     * @return customerPath
     */
    public String getCustomerPath() {
        return this.customerPath;
    }

    /**
     * 
     * @return eventPath
     */
    public String getEventPath() {
        return this.eventPath;
    }
    
    // READING METHODS:
    /**
     * @param customerFile
     * @return customer hashmap
     * Written by: Alan Holguin and Sabas Rojas
     */
    public HashMap<Integer, Customer> readCustomers(Scanner sc, Log log) {
        
        //Create empty hashmap to store customer data
        HashMap<Integer, Customer> customerStorage = new HashMap<>();

        boolean isValid = false;
        do{

            try {
                File file = new File(this.customerPath);
                Scanner scanner = new Scanner(file);

                //Read the header row to get the column names (assuming it's present)
                String headerLine = scanner.nextLine();
                String[] headers = headerLine.split(",");

                //Create a mapping of header names to their indices
                HashMap<String, Integer> headerToIndex = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    headerToIndex.put(headers[i].trim(), i);
                }

                while (scanner.hasNextLine()) {
                    String[] customerData = scanner.nextLine().split(",");

                    int customerId = Integer.parseInt(customerData[headerToIndex.get("ID")]);
                    String firstName = customerData[headerToIndex.get("First Name")];
                    String lastName = customerData[headerToIndex.get("Last Name")];
                    double moneyAvailable = Double.parseDouble(customerData[headerToIndex.get("Money Available")]);
                    int concertsAmount = Integer.parseInt(customerData[headerToIndex.get("Tickets Purchased")]);
                    boolean membership = Boolean.parseBoolean(customerData[headerToIndex.get("TicketMiner Membership")]);
                    String userName = customerData[headerToIndex.get("Username")];
                    String password = customerData[headerToIndex.get("Password")];

                    // Create a Customer object and put it in the HashMap
                    Customer myCustomer = new Customer(customerId, firstName, lastName, moneyAvailable,
                                                        concertsAmount, membership, userName, password);
                    customerStorage.put(customerId, myCustomer);
                    
                }
                scanner.close();
                isValid = true;
            } catch (Exception e) {
                System.out.println("No event file found...");
                System.out.println("Please enter the path to the customer file: ");

                //Update filepath location
                this.customerPath = sc.nextLine();
                System.out.println(this.customerPath);

                log.logActivity("ERROR: Issue occured while reading in customer file.");
            }
        }while(!isValid);

        return customerStorage;
    }

    /**
     * @param eventPath
     * @return eventMap
     * @brief dynamically read event file
     * Written by: Jorge Sandoval
     * Edited by: Christian Odom
     */
    public HashMap<Integer, Event> eventReader(Scanner sc,Log log){

        HashMap<Integer, Event> newEventMap = new HashMap<Integer, Event>();

        int largestEventID = 0;
        
        boolean isValid = false;
        do{
            try{
                //Open file and read with Scanner
                File file = new File(this.eventPath);
                Scanner eventScan = new Scanner(file);

                //Get header line
                String headerString = eventScan.nextLine();
                
                //Split header using ',' as a delimiter
                String splitHeader[] = headerString.split(",");

                //Create a hashmap to store column names and their location {columnNumber : columnName}
                HashMap<Integer, String>  columnLocationMap = new HashMap<Integer, String>();
                
                //Initialize variable to store location (column) of event type,
                int eventTypeColumn = 0;
                int venueTypeColumn = 0;

                //Iterate through header and store column locations
                for(int i = 0; i < splitHeader.length; i++){

                    //If 'Event Type' column is found, store index
                    if(splitHeader[i].equalsIgnoreCase("event type")){
                        eventTypeColumn = i;
                    }
                    //If 'Venue Type' column is found, store index
                    if(splitHeader[i].equalsIgnoreCase("venue type")){
                        eventTypeColumn = i;
                    }
                    //Store in hashmap: {columnNumber : columnName}
                    columnLocationMap.put(i, splitHeader[i]);
                }

                //Iterate through rest of lines in file
                while(eventScan.hasNextLine()){

                    // //Get line and split using comma
                    String line = eventScan.nextLine();
                    String[] data = line.split(",");

                    //Get 'Event Type' and create object of that type
                    String eventType = data[eventTypeColumn];
                    Event event = EventFactory.creatEvent(eventType);
                    
                    //Get 'Venue Type' and create object of that type
                    String venueType = data[venueTypeColumn];
                    Venue venue = VenueFactory.createVenue(venueType);

                    //Set venue attribute of event
                    event.setVenue(venue);

                    //Iterate through each column and add attribute
                    for(int i = 0; i < data.length; i++){
                        String attribute = columnLocationMap.get(i);
                        event = AttributeFactory.addEventAttribute(event,attribute, data[i]);
                    }

                    //Perform remaining ticket calculations
                    double remVip = Calculator.calcRemTickets(event.getVenue().getCapacity(), event.getVipPct());
                    double remGold = Calculator.calcRemTickets(event.getVenue().getCapacity(), event.getGoldPct());
                    double remSilver = Calculator.calcRemTickets(event.getVenue().getCapacity(), event.getSilverPct());
                    double remBronze = Calculator.calcRemTickets(event.getVenue().getCapacity(), event.getBronzePct());
                    double remGa = Calculator.calcRemTickets(event.getVenue().getCapacity(), event.getGaPct());
                    double remReserved = Calculator.calcRemTickets(event.getVenue().getCapacity(), event.getResPct());

                    //Set remaining ticket attributes
                    event.setRemVIPTickets(remVip);
                    event.setRemGoldTickets(remGold);
                    event.setRemSilverTickets(remSilver);
                    event.setRemBronzeTickets(remBronze);
                    event.setRemGaTickets(remGa);
                    event.setRemReservedTickets(remReserved);
                    event.setTotalAmntDiscounted(0);
                    //Add event to hashmap
                    newEventMap.put(event.getEventID(), event);

                }
                eventScan.close();
                isValid = true;

            } catch(FileNotFoundException e){

                System.out.println("No event file found...");
                System.out.println("Please enter the path to the event file: ");

                //Update filepath location
                this.eventPath = sc.nextLine();

                log.logActivity("ERROR: Issue occured while reading in event file.");
            }

        }while(!isValid);

        log.logActivity("SUCCESS: Read event file.");
        return newEventMap;
    }
    
    // WRITING METHODS:
    // This method writes a new customer list with updated values to a new CSV file.
    public void writeCustomerList(HashMap<Integer, Customer> customerStorage) {
        try {

            FileWriter writer = new FileWriter(this.outputCustomerPath);
            PrintWriter printWriter = new PrintWriter(writer);
    
            printWriter.println("ID,First Name,Last Name,Money Available,Concerts Purchased,"
                                + "TicketMiner Membership,Username,Password,Money Saved");
    
            for (Customer customer : customerStorage.values()) {
                printWriter.println(customer.getCustID() + "," + customer.getFirstName() + "," + customer.getLastName() + "," +
                        customer.getMoneyAvail() + "," + customer.getConcertsPurchased() + "," + customer.getMinerMember() + "," +
                        customer.getUserName() + "," + customer.getPassword() + "," + customer.getMoneySaved());
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
     * @param custMapIn
     * @param custTree
     * @brief Write an Event CSV file.
     * Written by: Jorge Sandoval
     * Edited by: Christian Odom
     */
    public void writeEventList(HashMap<Integer, Event> eventMapIn) {
        File file = new File(this.outputEventPath);
        
        // Clear existing content of the file
        try {
            Files.write(Paths.get(this.outputEventPath), "".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to clear CSV file. " + e.getMessage());
            return; // Return to avoid writing to the file if clearing fails
        }

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            String header = "Date,Cost,VIP Price,Silver Price,Bronze Price,Event Type,"
            +"Fireworks Cost,Silver Pct,General Admission Price,Name,Reserved Extra Pct,"
            +"Venue Name,Pct Seats Unavailable,Time,Venue Type,Capacity,Gold Price,VIP Pct,"
            +"Event ID,Gold Pct,Bronze Pct,General Admission Pct,Fireworks Planned, Total Amnt Discounted\n";


            fileWriter.write(header);

            for (HashMap.Entry<Integer, Event> entry : eventMapIn.entrySet()) {
                
                Event value = entry.getValue();
                StringBuilder line = new StringBuilder();

                line.append(value.getEventDate()).append(",");
                line.append(value.getVenue().getCostToRent() + ",");
                line.append(value.getVipPrice()).append(",");
                line.append(value.getSilverPrice()).append(",");
                line.append(value.getBronzePrice()).append(",");
                line.append(value.getEventType()).append(",");
                line.append(value.getFireworksCost() + ",");
                line.append(value.getSilverPct()).append(",");
                line.append(value.getGaPrice()).append(",");
                line.append(value.getEventName()).append(",");
                line.append(value.getResPct()).append(",");
                line.append(value.getVenue().getName() + ",");
                line.append(value.getUnavailablePct() + ",");
                line.append(value.getEventTime()).append(",");
                line.append(value.getVenueType()).append(",");
                line.append(value.getVenue().getCapacity() + ",");
                line.append(value.getGoldPrice()).append(",");
                line.append(value.getVipPct()).append(",");
                line.append(value.getEventID()).append(",");
                line.append(value.getGoldPct()).append(",");
                line.append(value.getBronzePct()).append(",");
                line.append(value.getGaPct()).append(",");
                line.append(value.hasFireworks() + ",");
                line.append(value.getTotalAmntDiscounted());
                
                
                //Write line to csv
                fileWriter.write(line.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Failed to write CSV file. " + e.getMessage());
        }
    }

    // New method to calculate the largest event ID from the CSV file

    public int getLargestEventIDFromCSV(Log log) {
        int largestEventID = 0;
    
        try {
            // Open the event file and read with a Scanner
            File file = new File(this.eventPath);
            Scanner eventScan = new Scanner(file);
    
            if (eventScan.hasNextLine()) {
                // Get header line and split it using a comma
                String headerString = eventScan.nextLine();
                String[] splitHeader = headerString.split(",");
    
                // Find the index of "Event ID" in the header
                int eventIDColumnIndex = -1;
                for (int i = 0; i < splitHeader.length; i++) {
                    if (splitHeader[i].trim().equalsIgnoreCase("Event ID")) {
                        eventIDColumnIndex = i;
                        break;
                    }
                }
    
                if (eventIDColumnIndex != -1) {
                    while (eventScan.hasNextLine()) {
                        String line = eventScan.nextLine();
                        String[] data = line.split(",");
                        
                        // Ensure that the data array is long enough
                        if (data.length > eventIDColumnIndex) {
                            int eventID = Integer.parseInt(data[eventIDColumnIndex]);
                            largestEventID = Math.max(largestEventID, eventID);
                        }
                    }
                } else {
                    System.err.println("Column 'Event ID' not found in the CSV header.");
                }
            }
            
            eventScan.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.logActivity("ERROR: Issue occurred while reading the event file.");
        }
    
        log.logActivity("SUCCESS: Read event file.");
        return largestEventID + 1; // Add one to the largest event ID to generate a new ID
    }
    
    public Event createNewEventFromConsole(Log log, HashMap<Integer, Event> eventMap, CSVHandler csvHandler) {
        Scanner scanner = new Scanner(System.in);
    
        // Auto-generate Event ID (largestEventID++)
        int eventID = csvHandler.getLargestEventIDFromCSV(log);
    
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
    
        System.out.print("Enter Event Date (MM/DD/YYYY): ");
        String eventDate = scanner.nextLine();
    
        // Validate the date format
        while (!CSVHandler.isValidDateFormat(eventDate)) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY format.");
            System.out.print("Enter Event Date (MM/DD/YYYY): ");
            eventDate = scanner.nextLine();
        }
    
        System.out.print("Enter Event Time (XX:XX AM/PM): ");
        String eventTime = scanner.nextLine();
    
        // Prompt the user to choose a venue from the list of available venues
        System.out.println("Select Venue:");
        System.out.println("1. Sun Bowl Stadium");
        System.out.println("2. Don Haskins Center");
        System.out.println("3. Magoffin Auditorium");
        System.out.println("4. San Jacinto Plaza");
        System.out.println("5. Centennial Plaza");
        int venueChoice = scanner.nextInt();
    
        // Determine the venue based on the user's choice
        Venue venue = null;
        switch (venueChoice) {
            case 1:
                venue = new Stadium();
                break;
            case 2:
                venue = new Arena();
                break;
            case 3:
                venue = new Auditorium();
                break;
            case 4:
                venue = new OpenAir();
                break;
            // Add more cases for other venues if needed
        }
    
        // Calculate the number of seats for each ticket level based on venue capacity
        int venueCapacity = venue.getCapacity();
        int vipSeats = (int) (0.05 * venueCapacity);
        int goldSeats = (int) (0.10 * venueCapacity);
        int silverSeats = (int) (0.15 * venueCapacity);
        int bronzeSeats = (int) (0.20 * venueCapacity);
        int gaSeats = (int) (0.45 * venueCapacity);
        int reservedSeats = (int) (0.05 * venueCapacity);
    
        // Set ticket prices programmatically
        double gaPrice = 4000.0;  // Max General Admission price
        double vipPrice = gaPrice * 5;
        double goldPrice = gaPrice * 3;
        double silverPrice = gaPrice * 2.5;
        double bronzePrice = gaPrice * 1.5;
    
        // Prompt the user to specify if the event will have fireworks
        System.out.print("Will the event have fireworks (yes/no): ");
        String hasFireworksInput = scanner.next();
        boolean hasFireworks = hasFireworksInput.equalsIgnoreCase("yes");
    
        double fireworksCost = 0.0;
    
        if (hasFireworks) {
            // Prompt the user to enter the fireworks cost
            System.out.print("Enter fireworks cost: $");
            fireworksCost = scanner.nextDouble();
        }
    
        // Create a new event using the EventFactory
        Event newEvent = EventFactory.creatEvent("Custom");
    
        if (newEvent != null) {
            newEvent.setEventID(eventID);
            newEvent.setEventName(eventName);
            newEvent.setEventDate(eventDate);
            newEvent.setEventTime(eventTime);
            newEvent.setVenue(venue);
            newEvent.setEventType("Custom");
            newEvent.setVipPrice(vipPrice);
            newEvent.setGoldPrice(goldPrice);
            newEvent.setSilverPrice(silverPrice);
            newEvent.setBronzePrice(bronzePrice);
            newEvent.setGaPrice(gaPrice);
            newEvent.setVipPct(vipSeats);
            newEvent.setGoldPct(goldSeats);
            newEvent.setSilverPct(silverSeats);
            newEvent.setBronzePct(bronzeSeats);
            newEvent.setGaPct(gaSeats);
            newEvent.setResPct(reservedSeats);
            newEvent.setTotalAmntDiscounted(0.0);
            newEvent.setHasFireworks(hasFireworks);
            newEvent.setFireworksCost(fireworksCost);
    
            // Add the new event to your data structure (e.g., a HashMap of events)
            eventMap.put(eventID, newEvent);
            
            // Call the method to write the updated event list to the CSV file
            csvHandler.writeEventList(eventMap);
        } else {
            System.out.println("Failed to create the event. Invalid event type.");
        }
    
        // Close the scanner
        scanner.close();
        return newEvent;
    }
    


    public static boolean isValidDateFormat(String input) {
        try {
            // Split the input by '/' and check the parts
            String[] parts = input.split("/");
            if (parts.length != 3) {
                return false;
            }

            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1000 || year > 9999) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    } 
    

    // public void createElectronicInvoiceSummary(){

    //     try {

    //         FileWriter writer = new FileWriter(this.outputCustomerPath);
    //         PrintWriter printWriter = new PrintWriter(writer);
    
    //         printWriter.println("ID,First Name,Last Name,Money Available,Concerts Purchased,"
    //                             + "TicketMiner Membership,Username,Password,Money Saved");
    
    //         for (Customer customer : customerStorage.values()) {
    //             printWriter.println(customer.getCustID() + "," + customer.getFirstName() + "," + customer.getLastName() + "," +
    //                     customer.getMoneyAvail() + "," + customer.getConcertsPurchased() + "," + customer.getMinerMember() + "," +
    //                     customer.getUserName() + "," + customer.getPassword() + "," + customer.getMoneySaved());
    //         }
    //         printWriter.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    // }
}