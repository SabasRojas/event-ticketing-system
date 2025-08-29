/**
 * MainMenu: Used to present the user (customer or admin) with menus/prompts
 *           to purchase tickets or make inquiries.
 * @author Jorge Sandoval
 * @author Christian Odom
 * @author Alan Holguin
 * @author Sabas Rojas
 */

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MainMenu{

    // CONSTRUCTOR:
    public MainMenu(){}

    // METHODS:
    /**
     * @brief display admin/customer menu to user
     */
    public void startUpMenu(){
        //Prompt user with main menu
        System.out.println("");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("|              ______ _       __         __   __  ___ _                             |");
        System.out.println("|             /_  __/(_)____ / /__ ___  / /_ /  |/  /(_)___  ___  ____              |");
        System.out.println("|              / /  / // __//  '_// -_)/ __// /|_/ // // _ \\/ -_)/ __/              |");
        System.out.println("|             /_/  /_/ \\__//_/\\_\\ \\__/ \\__//_/  /_//_//_//_/\\__//_/                 |");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("|                                   Main Menu                                       |");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("| 1. Customer                                                                       |");
        System.out.println("| 2. System Administrator                                                           |");
        System.out.println("| 3. Autopurchaser                                                                  |");
        System.out.println("| EXIT: Exit                                                                        |");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.print("Enter your choice: ");

    }

    /**
     * @param sc
     * @param log
     * @return loginType
     * @brief get user selection if admin or customer
     * Written by: Christian Odom
     */
    public String customerAdminSel(Scanner sc, Log log){
        String loginType = "";
        String userIn;
        do{
            //Get user input
            userIn = sc.next();
            try
            {
                //If user enter "EXIT", quit program
                if(userIn.equalsIgnoreCase("EXIT")){
                    userIn = "0";
                    loginType = "EXIT";
                }
                //If user inputs unexpected selection, show error message
                else if(Integer.valueOf(userIn) > 3 | Integer.valueOf(userIn) < 0){
                    customerAdminMenuErr();
                }
                //Set loginMethod accordingly
                else if(Integer.valueOf(userIn) == 1){
                    loginType = "customer";
                }
                else if(Integer.valueOf(userIn) == 2){
                    loginType= "admin";
                    log.logActivity("SUCCESSFUL LOGIN: Admin");
                }
                else if(Integer.valueOf(userIn) == 3){
                    loginType = "auto";
                    log.logActivity("Running auto purchaser...");
                }
        }
        catch(NumberFormatException e)
        {
            customerAdminMenuErr();
            userIn = "-1";
        }
    }while(Integer.valueOf(userIn) > 3 | Integer.valueOf(userIn) < 0);

    return loginType;
    }

    /**
     * @brief print error message
     */
    public void customerAdminMenuErr(){
        System.out.println("Sorry, that is not a valid input.");
        System.out.println("Enter '1' to login as customer,");
        System.out.println("Enter '2' to login as admin.");
        System.out.println("Enter '3' to run autopurchaser.");
        System.out.println("Enter 'EXIT' to quit.");
        System.out.println();
    }

    // CUSTOMER METHODS:

    /**
     * @param customerMap
     * @param scan
     * @param log
     * @return customer
     * Written by: Alan Holguin and Sabas Rojas
     * Edited by: Christian Odom
     */
    public Customer customerNameMenu(HashMap<Integer,Customer> customerMap, Scanner scan, Log log){

        //Create customer to be returned
        Customer customer = new Customer();
        
        scan.nextLine();
        boolean userNameValid = false;
        do{
            //Prompt customer for first and last name
            System.out.println("");
            System.out.println("------------------------------------------------------");
            System.out.println("|                    Account Sign-In                 |");
            System.out.println("------------------------------------------------------");
            System.out.print("Please enter your first name: ");
            String fname = scan.nextLine();

            System.out.print("Please enter your last name: ");
            String lname = scan.nextLine();
            
            //Set customer ID to invalid ID
            boolean validId = false;
    
            //Iterate through customers until matching first and last name found
            for (Customer cust : customerMap.values()) {

                //If matching first and last name, return customer
                if (cust.getFirstName().equalsIgnoreCase(fname) && cust.getLastName().equalsIgnoreCase(lname)) {
                    
                    validId = true;
                    customer = cust;
                    
                    //Display success message and log activity
                    log.logActivity("Login successful for customer: "
                                    + customer.getFirstName() + " " + customer.getLastName());
                    
                    userNameValid = true;
                }
            }
            
            //If name not found during iteration, print error message
            if(!validId){
                System.out.println("Invalid name. Please try again...");
                System.out.println();
            }
            
        }while(!userNameValid);
        
        //Should never reach this line
        return customer;
    }


    public Customer customerNameMenuAuto(HashMap<Integer,Customer> customerMap,String[] flName, Log log){

        //Create customer to be returned
        Customer customer = new Customer();
        
        boolean userNameValid = false;
        do{
            String fname = flName[0];
            String lname = flName[1];
            
            //Set customer ID to invalid ID
            boolean validId = false;
    
            //Iterate through customers until matching first and last name found
            for (Customer cust : customerMap.values()) {

                //If matching first and last name, return customer
                if (cust.getFirstName().equalsIgnoreCase(fname) && cust.getLastName().equalsIgnoreCase(lname)) {
                    
                    validId = true;
                    customer = cust;
                    
                    //Log successful login
                    log.logActivity("Login successful for customer: "
                                    + customer.getFirstName() + " " + customer.getLastName());
                    
                    userNameValid = true;
                }
            }
            
            //If name not found during iteration, print error message
            if(!validId){
                System.out.println("Invalid name. Please try again...");
                System.out.println();
            }
            
        }while(!userNameValid);
        
        //Should never reach this line
        return customer;
    }
    /**
     * @param customer
     * @param scan
     * @param log
     * Written by: Alan Holguin and Sabas Rojas
     * Edited by: Christian Odom
     */
    public void customerUsernamePWMenu(Customer customer, Scanner scan, Log log){
        String loginCred[] = new String[2];
        boolean isValidLogin = false;

        do{
            System.out.print("Please enter your username: ");
            loginCred[0] = scan.nextLine();
            System.out.print("Please enter your password: ");
            loginCred[1] = scan.nextLine();

            //Check if user credentials are valid
            if (Validate.isValidLogin(loginCred, customer)) {
                
                //Display success message and log successfull login
                System.out.println("------------------------------------------------------");
                System.out.println("|                   Login Successful                 |");
                System.out.println("------------------------------------------------------");
                System.out.println();
                log.logActivity("Login successful for customer: " + customer.getFirstName() + " " + customer.getLastName());

                //Set flag
                isValidLogin = true;

            }else {
                System.out.println("Invalid username or password. Try again!");
            }

        }while(!isValidLogin);
    }

    public String customerSelectionMenu(Scanner sc, Log log){
        System.out.println("");
        System.out.println("------------------------------------------------------");
        System.out.println("|                    Customer Menu                   |");
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Purchase ticket                                 |");
        System.out.println("| 2. Cancel purchase                                 |");
        System.out.println("| 3. View account balance                            |");
        System.out.println("| 4. EXIT                                            |");
        System.out.println("------------------------------------------------------");
        System.out.print("Enter your choice: ");
    
        String selection;
    
        boolean isValid = false;
        do {
            selection = sc.next();
    
            try {
                int choice = Integer.parseInt(selection);
                if (choice > 0 && choice < 5) {
                    isValid = true;
                } else {
                    System.out.println("Sorry, that is not a valid choice.");
                    System.out.println("Enter '1' to purchase a ticket.");
                    System.out.println("Enter '2' to cancel a purchase.");
                    System.out.println("Enter '3' to view account balance.");
                    System.out.println("Enter '4' to exit.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter one of the number options.");
                System.out.println("Enter '1' to purchase a ticket.");
                System.out.println("Enter '2' to cancel a purchase.");
                System.out.println("Enter '3' to view account balance.");
                System.out.println("Enter '4' to exit.");

                selection = sc.nextLine();
            }
    
        } while (!isValid);
    
        return selection;
    }

    public int getPurchaseToCancel(ArrayList<Purchase> purchaseHistoryIn, Scanner sc){
        String dashedLine = "----------------------------------------------------------";
        System.out.println(" ");
        System.out.println(dashedLine);
        System.out.println("|                    Cancel Purchase                     |");
        System.out.println(dashedLine);
        System.out.printf("| %-3s| %-18s | %-6s | %-4s | %-12s |%n", "ID", "EVENT NAME","PRICE", "QTY", "CONF. #");
        System.out.println(dashedLine);
        for(Purchase purchase: purchaseHistoryIn){
            System.out.printf("| %-3s| %-18s | %-6s | %-4s | %-12s |%n", purchaseHistoryIn.indexOf(purchase), purchase.getEventName(),
                                purchase.getPriceAfterFees(),purchase.getNumberOfTickets(), purchase.getConfirmationNumber());
            System.out.println(dashedLine);
        }

        String purchaseId;
        boolean isValid = false;
        do{
            purchaseId = sc.next();

            if(Integer.valueOf(purchaseId) >= 0 & Integer.valueOf(purchaseId) < purchaseHistoryIn.size()){
                isValid = true;
            }
            else{
                System.out.println("Sorry, that is not a valid purchase ID. Please try again.");
            }

        }while(!isValid);

        return Integer.valueOf(purchaseId);
    }

    /**
     * @param sc
     * @return
     * Written by: Alan Holguin and Sabas Rojas
     * Edited by: Christian Odom
     */
    public String customerEventTypeMenu(Scanner sc){
            // Check for valid event types user wants to purchase tickets to:
            System.out.println("");
            System.out.println("------------------------------------------------------");
            System.out.println("|                    Event Types                     |");
            System.out.println("------------------------------------------------------");
            System.out.println("|- Sport                                             |");
            System.out.println("|- Concert                                           |");
            System.out.println("|- Festival                                          |");
            System.out.println("------------------------------------------------------");
            System.out.print("Type in your choice: ");
    
            String eventType = "";
            boolean isValidSel = false;
            do {
    
                // Get user event type selection
                eventType = sc.next();
    
                // If user enters a valid selection, display events of type
                if (eventType.equalsIgnoreCase("sport") || eventType.equalsIgnoreCase("concert") ||
                        eventType.equalsIgnoreCase("festival")) {
                    
                    //Convert user input to lowercase for predictability
                    eventType = eventType.toLowerCase();
                    
                    //Set valid selection flag
                    isValidSel = true;
                } else {
                    eventTypeSelecErr();
                }
            } while (!isValidSel);

            return eventType;
    }

    /**
     * @brief error catcher for event type.
     *
     * Written by: Christian Odom
     */
    public void eventTypeSelecErr(){
        System.out.println("Sorry, that is not a valid selection.");
        System.out.println("Enter 'sport' to view sports");
        System.out.println("Enter 'concert' to view concerts");
        System.out.println("Enter 'festival' to view festivals");
        System.out.println();
    }

    /**
     * @param eventMap
     * @param sc
     * @return
     * Written by: Jorge Sandoval
     */
    public Event customerEventSelectMenu(HashMap<Integer, Event> eventMap, Scanner sc){
        Event selectedEvent = null;
        boolean isValid = false;

        System.out.print("Please enter the event ID number: ");
        sc.nextLine();

        do{

            String eventId;
            eventId = sc.next();
            try{
                
                int eventKey = Integer.valueOf(eventId);
                
                if(eventMap.containsKey(eventKey)){
                    selectedEvent = eventMap.get(eventKey);
                    isValid = true;
                    
                    //Format menu and data
                    System.out.println(" ");
                    String dashLine = "------------------------------------%n";
                    System.out.printf(dashLine);
                    System.out.printf("|            TIER SELECT           |%n");
                    System.out.printf(dashLine);
                    System.out.printf("| %-8s | %-8s | %-10s |%n", "TIER",
                                    "PRICE","REMAINING");
                    System.out.printf(dashLine);
                    System.out.printf("| %-8s | %-8s | %-10s |%n", "GA",
                                    selectedEvent.getGaPrice(), selectedEvent.getRemGaTickets());
                                System.out.printf(dashLine);
                    System.out.printf("| %-8s | %-8s | %-10s |%n", "Bronze",
                                    selectedEvent.getBronzePrice(), selectedEvent.getRemBronzeTickets());
                                System.out.printf(dashLine);
                    System.out.printf("| %-8s | %-8s | %-10s |%n", "Silver",
                                    selectedEvent.getSilverPrice(), selectedEvent.getRemSilverTickets());
                                System.out.printf(dashLine);
                    System.out.printf("| %-8s | %-8s | %-10s |%n", "Gold",
                                    selectedEvent.getGoldPrice(), selectedEvent.getRemGoldTickets());
                                System.out.printf(dashLine);
                    System.out.printf("| %-8s | %-8s | %-10s |%n", "VIP",
                                    selectedEvent.getVipPrice(), selectedEvent.getRemVIPTickets());
                                System.out.printf(dashLine);

        System.out.println();
                }
                else{
                    System.out.println("Sorry, that is not a valid ID.");
                    System.out.println("Please try again.");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Sorry, that is not a valid selection.");
                System.out.println("Please enter an integer value.");
                eventId = sc.next();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Sorry, that is not a valid selection.");
                System.out.println("Please enter an integer value.");
                eventId = sc.next();
            }
        } while (!isValid);

        return selectedEvent;
    }

    /**
     * @param scan
     * @return ticket type
     * @brief get user ticket tier selection
     * Written by: Christian Odom
     * Edited by: Jorge Sandoval
     */
    public String ticketTierSel(Scanner sc){
        // Display message
        System.out.println("Please select a ticket tier to purchase.");
        System.out.println("Example: Type 'gold' to purchase Gold ticket(s).");

        String ticketTier = "";
        String userIn = "";
        boolean isValid = false;

        // Get user input
        userIn = sc.next().toLowerCase(); // In case customer enters a capital letter.
        System.out.println();
        do{
            try
            {
    

                switch(userIn){
                    case "ga":
                        ticketTier = userIn;
                        isValid = true;
                        break;
                        
                    case "bronze":
                        ticketTier = userIn;
                        isValid = true;
                        break;

                    case "silver":
                        ticketTier = userIn;
                        isValid = true;
                        break;

                    case "gold":
                        ticketTier = userIn;
                        isValid = true;
                        break;

                    case "vip":
                        ticketTier = userIn;
                        isValid = true;
                        break;
                    
                    default:
                        System.out.println("Sorry, that is not a valid selection.");
                        System.out.println("Enter: 'vip', 'gold', 'silver', etc.");
                        userIn = sc.next().toLowerCase();
                        break;
                }
            }
            catch(NullPointerException e)
            {
                System.out.println("Sorry, that is not a valid selection.");
                System.out.println("Please enter 'ga' to purchase GA ticket(s).");
                System.out.println("Please enter 'bronze' to purchase Bronze ticket(s).");
                System.out.println("Please enter 'silver' to purchase Silver ticket(s).");
                System.out.println("Please enter 'gold' to purchase Gold ticket(s).");
                System.out.println("Please enter 'vip' to purchase VIP ticket(s).");
                System.out.println();

                userIn = sc.next().toLowerCase();
            }
        }while(!isValid);
        
        return ticketTier;
    }

    public static String ticketTierSelAuto(String typeSelection){

        boolean isValid = false;
        do{
            try
            {
                
                // Get user input
                typeSelection = typeSelection.toLowerCase();

                switch(typeSelection){
                    case "general admission":
                        typeSelection = "ga";
                        isValid = true;
                        break;
                        
                    case "bronze":
                        typeSelection = "bronze";
                        isValid = true;
                        break;

                    case "silver":
                        typeSelection = "silver";
                        isValid = true;
                        break;

                    case "gold":
                        typeSelection = "gold";
                        isValid = true;
                        break;

                    case "vip":
                        typeSelection = "vip";
                        isValid = true;
                        break;
                    
                    default:
                        System.out.println("Sorry, that is not a valid selection.");
                        System.out.println("Enter: 'vip', 'gold', 'silver', etc.");
                        break;
                }
            }
            catch(NullPointerException e)
            {
                System.out.println("Sorry, that is not a valid selection.");
                System.out.println("Please enter 'ga' to purchase GA ticket(s).");
                System.out.println("Please enter 'bronze' to purchase Bronze ticket(s).");
                System.out.println("Please enter 'silver' to purchase Silver ticket(s).");
                System.out.println("Please enter 'gold' to purchase Gold ticket(s).");
                System.out.println("Please enter 'vip' to purchase VIP ticket(s).");
                System.out.println();
            }
        }while(!isValid);
        
        return typeSelection;
    }

    /**
     * @param scan
     * @return num tickets
     * @brief get amount of tickets to purchase
     *  Written by: Christian Odom
     *  Edited by: Jorge Sandoval
     */
    public int numTicketsToPurchase(Scanner scan){
        // Display message
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("|                            Ticket Checkout                               |");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("| How many tickets would you like to purchase?                             |");
        System.out.println("| Maximum of 6 tickets can be purchased be transaction.                    |");
        System.out.println("----------------------------------------------------------------------------");

        int userIn = 0;
        boolean isValid = false;
        do{
            try
            {
                // Get user input
                userIn = scan.nextInt();
                System.out.println();

                if(userIn <= 6 & userIn >= 1) isValid = true;

                else{
                    System.out.println("Sorry, that is not a valid amount.");
                    System.out.println("Please enter a value between 1 and 6.");
                    scan.nextLine();
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Sorry, that is not a valid selection.");
                System.out.println("Please enter a value between 1 and 6.");
                scan.nextLine();
            }
        }while(!isValid);
        
        return userIn;
    }

    /**
     * @param none
     * @return System administrator menu prints
     */
    public void SysAdmnMenu() {

        System.out.println(" ");
        System.out.println("------------------------------------------------------");
        System.out.println("|                      Admin Menu                    |");
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Inquire event by ID                             |");
        System.out.println("| 2. Inquire event by name                           |");
        System.out.println("| 3. Create new event                                |");
        System.out.println("| 4. Get invoice summary                             |");
        System.out.println("| 5. Get fees summary for one event by ID            |");
        System.out.println("| 6. Get fees summary for all events                 |");
        System.out.println("| 7. Cancel an event                                 |");
        System.out.println("| EXIT: Exit                                         |");
        System.out.println("------------------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    /**
     * @param scan
     * @param eventIn
     * @return a value 1-8 corresponding to inquiry selection
     * @brief diplay inquiry options
     *
     * Written by: Christian Odom
     */
    public int adminInquirySel(Scanner scan, Event eventIn){
        System.out.println(" ");
        System.out.println("------------------------------------------------------");
        System.out.println("|                     Event Inquiry                  |");
        System.out.println("------------------------------------------------------");
        System.out.println("| Select event information to view.                  |");
        System.out.println("| 1. View all event information                      |");
        System.out.println("| 2. View number of seats remaining (incl. reserved) |");
        System.out.println("| 3. View number of VIP seats remaining              |");
        System.out.println("| 4. View number of Gold seats remaining             |");
        System.out.println("| 5. View number of Silver seats remaining           |");
        System.out.println("| 6. View number of Bronze seats remaining           |");
        System.out.println("| 7. View number of GA seats remaining               |");
        System.out.println("| 8. View number of seats remaining (excl. reserved) |");
        System.out.println("| 9. View total tax collected for event              |");
        System.out.println("------------------------------------------------------");

        String userIn = "";
        boolean isValid = false;
        do{
            try{
                userIn = scan.next();

                if(Integer.valueOf(userIn) > 0 & Integer.valueOf(userIn) < 10){
                    isValid = true;
                }
                else{
                    System.out.println("ERROR: INVALID SELECTION.");
                    System.out.println("Example: Enter '7' to view remaining GA seats.");
                    System.out.println();
                    System.out.println("1. View all event information.");
                    System.out.println("2. View number of seats remaining. (including reserved)");
                    System.out.println("3. View number of VIP seats remaining.");
                    System.out.println("4. View number of Gold seats remaining.");
                    System.out.println("5. View numner of Silver seats remaining.");
                    System.out.println("6. View number of Bronze seats remaining.");
                    System.out.println("7. View number of GA seats remaining.");
                    System.out.println("8. View number of seats remaining. (excluding reserved)");
                    System.out.println("9. View total tax collected for event.");
                    System.out.println();
                }
            }
            catch(Exception e){
                System.out.println("ERROR: INVALID SELECTION");
            }
        }while(!isValid);

        return Integer.valueOf(userIn);
    }

    /**
     * @param inquiryIn
     * @param eventIn
     * @brief display inquiry based on user selection
     *
     * Written by: Christian Odom
     */
    public void displayInquiry(int inquiryIn, Event eventIn,  HashMap<Integer, Event> eventStorage){
        String dashLine = "-----------------------------------------------------------------%n";
        String smallDashLine = "--------------------------------------------------%n";
        switch(inquiryIn){

            case 1:
                //view all event info
                ViewEventInfoCommand viewEventInfoCommand = new ViewEventInfoCommand(eventStorage,eventIn.getEventID());
                viewEventInfoCommand.execute();
                System.out.println();
                System.out.println();
                break;

            case 2:
                //view number of seats remaining (including reserved)
                System.out.println(" ");
                System.out.printf(dashLine);
                System.out.printf("|%-25s Remaining Seats %-20s |%n", " ", " ");
                System.out.printf(dashLine);
                System.out.print("| Total number of seats remaining (including reserved): ");
                System.out.printf("%-2s |%n", eventIn.getRemVIPTickets() + eventIn.getRemGoldTickets() +
                    eventIn.getRemSilverTickets() + eventIn.getRemBronzeTickets() +
                    eventIn.getRemGaTickets() + eventIn.getRemReservedTickets(), " ", " ");
                System.out.printf(dashLine);
                System.out.println(" ");
                break;
            
            case 3:
                //View number of VIP seats remaining
                System.out.println(" ");
                System.out.printf(smallDashLine);
                System.out.printf("|%15s Remaining VIP %-16s|%n", " ", " ");
                System.out.printf(smallDashLine);
                System.out.print("| Total number of VIP tickets remaining: ");
                System.out.printf("%-2s | %n ", eventIn.getRemVIPTickets());
                System.out.printf(smallDashLine);
                System.out.println();
                break;
            
            case 4:
                //view number of Gold seats remaining
                System.out.println(" ");
                System.out.printf(smallDashLine);
                System.out.printf("|%15s Remaining Gold %-17s|%n", " ", " ");
                System.out.printf(smallDashLine);
                System.out.print("| Total number of Gold tickets remaining: ");
                System.out.printf("%-1s | %n ", eventIn.getRemGoldTickets());
                System.out.printf(smallDashLine);
                System.out.println();
                break;

            case 5:
                //view number of Silver seats remaining
                System.out.println(" ");
                System.out.printf(smallDashLine);
                System.out.printf("|%15s Remaining Silver %-17s|%n", " ", " ");
                System.out.printf(smallDashLine);
                System.out.print("| Total number of Silver tickets remaining: ");
                System.out.printf("%-2s | %n ", eventIn.getRemSilverTickets());
                System.out.printf(smallDashLine);
                System.out.println();
                break;

            case 6:
                //view number of bronze seats remaining
                System.out.println(" ");
                System.out.printf(smallDashLine);
                System.out.printf("|%15s Remaining Bronze %-17s|%n", " ", " ");
                System.out.printf(smallDashLine);
                System.out.print("| Total number of Bronze tickets remaining: ");
                System.out.printf("%-2s | %n ", eventIn.getRemBronzeTickets());
                System.out.printf(smallDashLine);
                System.out.println();
                break;

            case 7:
                //view number of GA seats remaining
                System.out.println(" ");
                System.out.printf(smallDashLine);
                System.out.printf("|%15s Remaining GA %-17s|%n", " ", " ");
                System.out.printf(smallDashLine);
                System.out.print("| Total number of GA tickets remaining: ");
                System.out.printf("%-2s | %n ", eventIn.getRemGaTickets());
                System.out.printf(smallDashLine);
                System.out.println();
                break;

            case 8:
                //view number of seats remaining (excluding reserved)
                System.out.println(" ");
                System.out.printf(dashLine);
                System.out.printf("|%-25s Remaining Seats  %-18s |%n", " ", " ");
                System.out.printf(dashLine);
                System.out.print("Total number of seats remaining (excluding reserved): ");
                System.out.printf("%-4s |%n", eventIn.getRemVIPTickets() + eventIn.getRemGoldTickets() +
                                    eventIn.getRemSilverTickets() + eventIn.getRemBronzeTickets() +
                                    eventIn.getRemGaTickets() + " ", " ");
                System.out.printf(dashLine);
                System.out.println(" ");
                break;

            case 9:
                System.out.println(eventIn.getTaxCollected());
                System.out.println();

            default:
                break;
            }
    }

    /**
     * @param sc
     * @return Y: inquire about another event, N: return to main menu
     * @brief determine if admin would like to make another inquiry or return to main menu
     *
     * Written by: Christian Odom
     */
    public String adminContinue(Scanner sc){
        System.out.println(" ");
        System.out.println("------------------------------------------------------");
        System.out.println("|                   Admin Selection                  |");
        System.out.println("------------------------------------------------------");
        System.out.println("| Would you like to inquire about another event?     |");
        System.out.println("| Y: Yes                                             |");
        System.out.println("| N: No                                              |");
        System.out.println("------------------------------------------------------");

        String userIn = "";
        boolean isValid = false;
        do{
            userIn = sc.next();

            if(userIn.equalsIgnoreCase("y") | userIn.equalsIgnoreCase("n")){
                isValid = true;
                return userIn;
            }
            else{
                System.out.println("Sorry, that is not a valid selection.");
                System.out.println("Type 'Y' to make another inquiry.");
                System.out.println("Enter 'N' to exit inquiry menu.");
            }
        }while(!isValid);

        return "";
    }
    




    public void inquireById(HashMap<Integer, Event> eventMapIn, Scanner scan, Log log) {
        boolean loopRunner = true;
    
        do {
            log.logActivity("Inquire by ID selected.");
            System.out.print("\nPlease enter the ID of the event: ");
    
            if (scan.hasNextInt()) {
                int adminEventId = scan.nextInt();
    
                // Check if the Event exists before using it
                Event eventById = eventMapIn.get(adminEventId);
                if (eventById != null) {
                    int inquirySelection = adminInquirySel(scan, eventById);
                    displayInquiry(inquirySelection, eventById, eventMapIn);
                } else {
                    System.out.println("Event not found.");
                }
    
                // Ask if the admin wants to inquire about another event
                String continueChoice = adminContinue(scan);
                if ("N".equalsIgnoreCase(continueChoice)) {
                    loopRunner = false;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid event ID.");
                scan.nextLine(); // Consume the invalid input
            }
        } while (loopRunner);
    }


    public void inquireByName(HashMap<Integer, Event> eventMapIn, Scanner scan, Log log) {
        boolean loopRunner = true;
        log.logActivity("Inquire by name selected.");
        System.out.print("\nPlease enter the name of the event: ");
        Scanner scanny = new Scanner(System.in);
        String adminEventName = scanny.nextLine();
    
        // Check if the Event exists before using it
        Event eventByName = Event.findEventByName(adminEventName, eventMapIn);
        if (eventByName != null) {
            int inquirySelection2 = adminInquirySel(scan, eventByName);
            displayInquiry(inquirySelection2, eventByName, eventMapIn);
        } else {
            System.out.println("\nEvent not found.");
        }
    
        // Ask if the admin wants to inquire about another event
        String continueChoice2;
        do {
            continueChoice2 = adminContinue(scan);
            if ("N".equalsIgnoreCase(continueChoice2)) {
                loopRunner = false;
            } else if (!"Y".equalsIgnoreCase(continueChoice2)) {
                System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
            }
        } while (!"Y".equalsIgnoreCase(continueChoice2) && !"N".equalsIgnoreCase(continueChoice2));
    }
    

    public void createEvent(HashMap<Integer, Event> eventMapIn, Scanner scanny, Scanner scan, Log log) {
        System.out.println("");
        System.out.println("------------------------------------------------");
        System.out.println("|               Event Creation                 |");
        System.out.println("------------------------------------------------");
        System.out.println("| What type of event would you like to create? |");
        System.out.println("|- Sport                                       |");
        System.out.println("|- Concert                                     |");
        System.out.println("|- Festival                                    |");
        System.out.println("------------------------------------------------");
    
        String eventType;
        boolean isValid = false;

        do {
            eventType = scanny.nextLine();
            eventType = eventType.toLowerCase();

            if (eventType.equals("sport") || eventType.equals("concert") || eventType.equals("festival")) {
                isValid = true;
            } else {
                System.out.println("Sorry, that is not a valid event type. Try again!");
            }
        } while (!isValid);

        Event eventToAdd = EventFactory.creatEvent(eventType);

        log.logActivity("Create new event selected");
        System.out.println("");
        System.out.println("------------------------------------------------");
        System.out.println("|                   Event Name                 |");
        System.out.println("------------------------------------------------");
        System.out.print("Enter the Event Name: ");
        String eventName = scanny.nextLine();
        System.out.println("------------------------------------------------");
        eventToAdd.setEventName(eventName);

        while (true) {
            System.out.println("------------------------------------------------");
            System.out.println("|                 Event Date                   |");
            System.out.println("------------------------------------------------");
            System.out.print("Enter a date in MM/DD/YYYY format: ");
            String date = scanny.nextLine();
            System.out.println("------------------------------------------------");

            if (Validate.isValidDateFormat(date)) {
                eventToAdd.setEventDate(date);
                break;
            } else {
                System.out.println("Invalid date format. Please use MM/DD/YYYY format.");
            }
        }

        String eventTime;
        boolean isValidTime = false;

        do {
            System.out.println("------------------------------------------------");
            System.out.println("|                 Event Time                   |");
            System.out.println("------------------------------------------------");
            System.out.print("Enter Event Time (XX:XX AM/PM): ");
            eventTime = scanny.nextLine();
            System.out.println("------------------------------------------------");

            if (Validate.isValidTimeFormat(eventTime)) {
                isValidTime = true;
            } else {
                System.out.println("Invalid time format. Please use XX:XX AM/PM format.");
            }
        } while (!isValidTime);

        eventToAdd.setEventTime(eventTime);

        int venueChoice;
        Venue venueToAdd = null;

        do{
            System.out.println("------------------------------------------------");
            System.out.println("|                 Event Venue                  |");
            System.out.println("------------------------------------------------");

            switch (eventType) {
                case "sport":
                    System.out.println("| Select the Venue:                            |");
                    System.out.println("| 1. Sun Bowl Stadium                          |");
                    System.out.println("| 2. Don Haskins Center                        |");
                    System.out.println("------------------------------------------------");
                    System.out.print("Enter Choice: ");
                    venueChoice = scanny.nextInt();

                    switch (venueChoice) {
                        case 1:
                            venueToAdd = VenueFactory.createVenue("Stadium");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Stadium");
                            eventToAdd.getVenue().setName("Sun Bowl Stadium");
                            eventToAdd.getVenue().setCapacity(58000);
                            eventToAdd.getVenue().setCostToRent(681500);
                            break;
                        case 2:
                            venueToAdd = VenueFactory.createVenue("Arena");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Arena");
                            eventToAdd.getVenue().setName("Don Haskins Center");
                            eventToAdd.getVenue().setCapacity(12800);
                            eventToAdd.getVenue().setCostToRent(150400);
                            break;
                        default:
                            System.out.println("Invalid venue choice. Please choose a valid option (1-2).");
                    }
                    break;

                case "festival":
                    System.out.println("| Select the Venue:                            |");
                    System.out.println("| 1. Centennial Plaza                          |");
                    System.out.println("| 2. Don Haskins Center                        |");
                    System.out.println("| 3. San Jacinto Plaza                         |");
                    System.out.println("------------------------------------------------");
                    System.out.print("Enter Choice: ");
                    venueChoice = scanny.nextInt();

                    switch (venueChoice) {
                        case 1:
                            venueToAdd = VenueFactory.createVenue("Open Air");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Open Air");
                            eventToAdd.getVenue().setName("Centennial Plaza");
                            eventToAdd.getVenue().setCapacity(5000);
                            eventToAdd.getVenue().setCostToRent(58750);
                            break;
                        case 2:
                            venueToAdd = VenueFactory.createVenue("Arena");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Arena");
                            eventToAdd.getVenue().setName("Don Haskins Center");
                            eventToAdd.getVenue().setCapacity(12800);
                            eventToAdd.getVenue().setCostToRent(150400);
                            break;
                        case 3:
                            venueToAdd = VenueFactory.createVenue("Open Air");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Open Air");
                            eventToAdd.getVenue().setName("San Jacinto Plaza");
                            eventToAdd.getVenue().setCapacity(15000);
                            eventToAdd.getVenue().setCostToRent(176250);
                            break;
                        default:
                            System.out.println("Invalid venue choice. Please choose a valid option (1-3).");
                    }
                    break;

                case "concert":
                    System.out.println("| Select the Venue:                            |");
                    System.out.println("| 1. Magoffin Auditorium                       |");
                    System.out.println("| 2. Sun Bowl Stadium                          |");
                    System.out.println("| 3. Don Haskins Center                        |");
                    System.out.println("------------------------------------------------");
                    System.out.print("Enter Choice: ");
                    venueChoice = scanny.nextInt();

                    switch (venueChoice) {
                        case 1:
                            venueToAdd = VenueFactory.createVenue("Auditorium");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Auditorium");
                            eventToAdd.getVenue().setName("Magoffin Auditorium");
                            eventToAdd.getVenue().setCapacity(1152);
                            eventToAdd.getVenue().setCostToRent(13536);
                            break;
                        case 2:
                            venueToAdd = VenueFactory.createVenue("Stadium");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Stadium");
                            eventToAdd.getVenue().setName("Sun Bowl Stadium");
                            eventToAdd.getVenue().setCapacity(58000);
                            eventToAdd.getVenue().setCostToRent(752000);
                            break;
                        case 3:
                            venueToAdd = VenueFactory.createVenue("Arena");
                            eventToAdd.setVenue(venueToAdd);
                            eventToAdd.setVenueType("Arena");
                            eventToAdd.getVenue().setName("Don Haskins Center");
                            eventToAdd.getVenue().setCapacity(12800);
                            eventToAdd.getVenue().setCostToRent(164500);
                            break;
                        default:
                            System.out.println("Invalid venue choice. Please choose a valid option (1-3).");
                    }
                    break;

                default:
                    System.out.println("Invalid event type.");
                    break;
            }

            int maxId = 0;
            for (Map.Entry<Integer, Event> entry : eventMapIn.entrySet()) {
                int key = entry.getKey();
                if (key > maxId) {
                    maxId = key;
                }
            }
            if (venueToAdd != null) {
                int venueCapacity = venueToAdd.getCapacity();
                double venueCost = venueToAdd.getCostToRent();

                // eventToAdd.setVenue(venueToAdd);
                eventToAdd.getVenue().setCostToRent((int)venueCost); // Set venue cost in your Event object

                eventToAdd.setEventID(maxId + 1); // You need to implement generateUniqueEventID() to generate a unique event ID
                eventToAdd.setEventName(eventName);

                eventToAdd.setEventType(eventType); // Set the event type
                eventToAdd.setVenue(venueToAdd);
                eventToAdd.getVenue().setCapacity(venueCapacity);

                // double totalCost = venueCost + (eventToAdd.hasFireworks() ? eventToAdd.getFireworksCost() : 0.0);
                // eventToAdd.getVenue().setTotalCost(totalCost);
            } else {
                System.out.println("Some fields in the event are null. Please ensure all fields are properly set.");
            }
        } while (venueToAdd == null);

        boolean validFireworksInput = false;
        do{
            System.out.println("------------------------------------------------");
            System.out.println("|                Event Fireworks               |");
            System.out.println("------------------------------------------------");
            System.out.print("Will the Event have fireworks? (yes/no):");
            String fireworksEvent = scanny.next();
            
            if (fireworksEvent.equalsIgnoreCase("yes")) {
                Boolean hasFireworks = true;
                eventToAdd.setHasFireworks(hasFireworks);

                System.out.print("Enter the cost of fireworks: ");
                while (!scanny.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a valid double value for the fireworks cost.");
                    System.out.print("Enter the cost of fireworks: ");
                    scanny.next(); // Consume the invalid input
                }
                double fireworksCost = scanny.nextDouble();
                eventToAdd.setFireworksCost(fireworksCost);
        
                
                validFireworksInput = true; // Exit the loop
            } else if (fireworksEvent.equalsIgnoreCase("no")) {
                Boolean hasFireworks = false;
                eventToAdd.setHasFireworks(hasFireworks);
                validFireworksInput = true; // Exit the loop
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        } while (!validFireworksInput);


            // Automatically compute the number of seats based on venue percentages
            int venueCapacity = venueToAdd.getCapacity();
            int vipSeats = (int) (0.5 * venueCapacity);
            int goldSeats = (int) (0.10 * venueCapacity);
            int silverSeats = (int) (0.15 * venueCapacity);
            int bronzeSeats = (int) (0.20 * venueCapacity);
            int gaSeats = (int) (0.45 * venueCapacity);
            int reservedSeats = (int) (0.05 * venueCapacity);
            int unavailableSeats = venueCapacity - (vipSeats + goldSeats + silverSeats + bronzeSeats + gaSeats + reservedSeats);

            eventToAdd.setVipPct(vipSeats);
            eventToAdd.setGoldPct(goldSeats);
            eventToAdd.setSilverPct(silverSeats);
            eventToAdd.setBronzePct(bronzeSeats);
            eventToAdd.setGaPct(gaSeats);
            eventToAdd.setResPct(reservedSeats);
            eventToAdd.setUnavailablePct(unavailableSeats);

            // Set ticket prices
            double gaPrice = Math.min(eventToAdd.getGaPrice(), 4000);
            System.out.println(gaPrice);
            double vipPrice = 5.0 * gaPrice;
            double goldPrice = 3.0 * gaPrice;
            double silverPrice = 2.5 * gaPrice;
            double bronzePrice = 1.5 * gaPrice;

            eventToAdd.setVipPrice(vipPrice);
            eventToAdd.setGoldPrice(goldPrice);
            eventToAdd.setSilverPrice(silverPrice);
            eventToAdd.setBronzePrice(bronzePrice);
            eventToAdd.setGaPrice(gaPrice);

            double venueCost = eventToAdd.getVenue().getCostToRent();

            double totalCost = venueCost + (eventToAdd.hasFireworks() ? eventToAdd.getFireworksCost() : 0.0);

            System.out.println("\n*************************************************");
            if (eventToAdd.getEventName() != null) {
                eventToAdd.adminPrintEventInfo();
                System.out.println("Venue: " + venueToAdd.getName());
                System.out.println("Venue Type: " + eventToAdd.getVenueType());
                System.out.println("Venue Capacity: " + venueCapacity);
                System.out.println("Event has fireworks: " + (eventToAdd.hasFireworks() ? "Yes" : "No"));
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                System.out.println("Fireworks Cost: " + currencyFormat.format(eventToAdd.getFireworksCost()));
                System.out.println("Total Cost (Venue Cost + fireworks): " + currencyFormat.format(totalCost));
                System.out.println("*************************************************\n");
            } else {
                System.out.println("Event not found.");
            }


        int maxId = 0;
        for (Map.Entry<Integer, Event> entry : eventMapIn.entrySet()) {
            int key = entry.getKey();
            if (key > maxId) {
                maxId = key;
            }
        }

        eventMapIn.put(maxId + 1, eventToAdd);
        System.out.println("Event sucessfully created.");
        // break;
    }

    public void generateInvoice(HashMap<Integer, Customer> customerMapIn, Scanner scan, Log log) {
        System.out.println("Please enter customer's name.");
        scan.nextLine();
        
        Customer customer = new Customer();
        boolean userNameValid = false;
        do{
            //Prompt customer for first and last name
            System.out.println(" ");
            System.out.println("------------------------------------------------");
            System.out.println("|               Invoice Summary                |");
            System.out.println("------------------------------------------------");
            System.out.print("Please enter customer's first name: ");
            String fname = scan.nextLine();
            System.out.print("Please enter customer's last name: ");
            String lname = scan.nextLine();
            
                //Set customer ID to invalid ID
                boolean validId = false;
                
                //Iterate through customers until matching first and last name found
                for (Customer cust : customerMapIn.values()) {

                    //If matching first and last name, return customer
                    if (cust.getFirstName().equalsIgnoreCase(fname) && cust.getLastName().equalsIgnoreCase(lname)) {
                        
                        validId = true;
                        customer = cust;
                        
                        //Display success message and log activity
                        System.out.println("-------------------------------------");
                        log.logActivity("Customer found: "
                                        + customer.getFirstName() + " " + customer.getLastName());
                        
                        userNameValid = true;
                    }
                }
                
                //If name not found during iteration, print error message
                if(!validId){
                    System.out.println("Invalid name. Please try again...");
                    System.out.println();
                }
            }while(!userNameValid);

            //Write invoice summary txt file
            Invoice.invoiceSummaryWriter(customer);
    }
    
    /**
     * @param ticketMinerCompany
     * @brief displays the total fees collected for all events
     *
     * Written by: Sabas Rojas
     */
    public void computeTotalFees(TicketMinerCompany ticketMinerCompany) {
        double totalServiceFee = ticketMinerCompany.getServiceFeeProfit();
        double totalConvenienceFee = ticketMinerCompany.getConvenienceFeeProfit();
        double totalCharityFee = ticketMinerCompany.getCharityFeeCollected();

        double totalFees = totalServiceFee + totalConvenienceFee + totalCharityFee;
        double roundedTotalFees = Math.round(totalFees * 100.0) / 100.0;

        System.out.println(" ");
        System.out.println("------------------------------------------------");
        System.out.println("|              Fees Event Summary              |");
        System.out.println("------------------------------------------------");
        System.out.println("| Total Service Fees: $" + totalServiceFee);
        System.out.println("| Total Convenience Fees: $" + totalConvenienceFee);
        System.out.println("| Total Charity Fees: $" + totalCharityFee);
        System.out.println("| Total Fees: $" + roundedTotalFees);
        System.out.println("------------------------------------------------");
    }

    /**
     * @param ticketMinerCompany
     * @param eventMapIn
     * @param scan
     * @brief Compute and print the amount of money gained by The TicketMiner Company for a selected event
     *
     * Written by: Sabas Rojas
     */
    public void computeTotalFeesForEvent(TicketMinerCompany ticketMinerCompany, HashMap<Integer, Event> eventMapIn, Scanner scan, Log log) {
 
        boolean loopRunner = true;
        log.logActivity("Compute total fees for a specific event selected");

        System.out.println("");

        Event.displayEvents(eventMapIn);
        do {
    
            // Ask the manager/admin to select an event
            int selectedEventId = -1;  // Default value for validation
            boolean isValidInput = false;
    
            while (!isValidInput) {
                try {
                    System.out.print("Please enter the ID of the event you want to compute fees for: ");
                    selectedEventId = Integer.parseInt(scan.nextLine());
    
                    if (eventMapIn.containsKey(selectedEventId)) {
                        isValidInput = true;
                    } else {
                        System.out.println("Invalid event ID. Please enter a valid ID.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }
    
            // Check if the selected event exists
            Event selectedEvent = eventMapIn.get(selectedEventId);
            if (selectedEvent != null) {
                calculateAndPrintEventFees(selectedEvent);
            } else {
                System.out.println("Event not found.");
            }
    
            // Ask if the manager/admin wants to compute fees for another event
            String continueChoice;
            do {
                System.out.print("Do you want to compute fees for another event? (Y/N): ");
                continueChoice = scan.nextLine();
                if ("N".equalsIgnoreCase(continueChoice)) {
                    loopRunner = false;
                } else if (!"Y".equalsIgnoreCase(continueChoice)) {
                    System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
                }
            } while (!"Y".equalsIgnoreCase(continueChoice) && !"N".equalsIgnoreCase(continueChoice));
    
        } while (loopRunner);
    }
    
    
    /**
     * @param ticketMinerCompany
     * @param event
     * @brief Calculate and print the fees for a specific event
     */
    private void calculateAndPrintEventFees(Event event) {
    
        // Calculate fees for the specific event
        double totalServiceFee = event.getServiceFee();
        double totalConvenienceFee = event.getConvenienceFee();
        double totalCharityFee = event.getCharityFee();
        double totalFees = totalServiceFee + totalConvenienceFee + totalCharityFee;

        String formattedServiceFee = String.format("%.2f", totalServiceFee);
        String formattedConvenienceFee = String.format("%.2f", totalConvenienceFee);
        String formattedCharityFee = String.format("%.2f", totalCharityFee);
        String formattedTotalFees = String.format("%.2f", totalFees);
        
        String dashLine = "----------------------------------%n";
        System.out.println();
        System.out.printf(dashLine);
        System.out.printf("|%-10sFees Summary%-10s|%n", " "," ");
        System.out.printf(dashLine);

        System.out.printf("| Event: %-24s|%n",  event.getEventName());
        System.out.printf("| Total Service Fees: $%-10s|%n", formattedServiceFee);
        System.out.printf("| Total Convenience Fees: $%-6s|%n", formattedConvenienceFee);
        System.out.printf("| Total Charity Fees: $%-10s|%n", formattedCharityFee);
        System.out.printf("| Total Fees: $%-18s|%n", formattedTotalFees);
        System.out.printf(dashLine);
        System.out.println();
    }

    /**
     * @param eventMap
     * @param custMap
     * @param ticketMiner
     * @param sc
     * @brief cancels/deletes the selected event from the system
     *
     * Written by: Sabas Rojas
     */
    public void cancelEvent(HashMap<Integer, Event> eventMap, HashMap<Integer, Customer> custMap, TicketMinerCompany ticketMiner, Scanner sc, Log log) {
        System.out.println(" ");
        System.out.println("------------------------------------------------");
        System.out.println("|               Cancel Event                   |");
        System.out.println("------------------------------------------------");
        System.out.print("| Enter the ID of the event to cancel: ");
        if (sc.hasNextInt()) {
            int eventId = sc.nextInt();
            System.out.println("------------------------------------------------");
            Event canceledEvent = eventMap.get(eventId);

            if (canceledEvent != null) {
                double totalRefund = 0;

                // Refund each customer who purchased tickets for the canceled event
                for (Customer customer : custMap.values()) {
                    Invoice invoice = customer.getInvoice();
                    if (invoice != null) {
                        double refundAmount = invoice.getTotalPrice();
                        totalRefund += refundAmount;

                        // Refund service fee to TicketMinerCompany
                        double serviceFee = canceledEvent.getServiceFee();
                        ticketMiner.setServiceFeeProfit(ticketMiner.getServiceFeeProfit() - serviceFee);

                        // Refund convenience fee to TicketMinerCompany
                        double convenienceFee = canceledEvent.getConvenienceFee();
                        ticketMiner.setConvenienceFeeProfit(ticketMiner.getConvenienceFeeProfit() - convenienceFee);

                        // Refund charity fee to TicketMinerCompany
                        double charityFee = canceledEvent.getCharityFee();
                        ticketMiner.setCharityFeeCollected(ticketMiner.getCharityFeeCollected() - charityFee);

                        //Return money to customer's account
                        customer.setMoneyAvail(customer.getMoneyAvail() + refundAmount);

                        // Clear purchase history for the canceled event
                        customer.getPurchaseHistory().clear(); 

                        log.logActivity("Event canceled: Refund $" + refundAmount + " to " + customer.getFirstName() + " " + customer.getLastName());
                    }
                }

                // Log the total refund amount and cancel the event
                log.logActivity("Event canceled: Total refund amount $" + totalRefund);
                eventMap.remove(eventId);
                System.out.println("Event canceled successfully. Total refund amount: $" + totalRefund);
            } else {
                System.out.println("Event not found.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid event ID.");
            sc.nextLine(); // Consume the invalid input
        }
    }

}