/**
 * RunTicket: Main class.
 * @author Jorge Sandoval
 * @author Christian Odom
 * @author Alan Holguin
 * @author Sabas Rojas
 * @since
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RunTicket {

    public static void main(String[] args) {

        // String nameOfOs = System.getProperty("os.name");
        // System.out.println(nameOfOs);

        // Create an instance of 'Scanner' for user input.
        Scanner sc = new Scanner(System.in);

        // Create an object of Log to log activity.
        String logFile = "log.txt";
        Log log = new Log(logFile);

        //Create an instance of Autopurchaser
        String autoCustListPath = "AutoPurchase1K.csv";
        Autopurchase autopurchase = new Autopurchase(autoCustListPath);

        // Create an instance of CSVHandler and Customer HashMap.
        String customerListPath = "CustomerListPA5.csv";
        String eventListPath = "EventListPA5.csv";
        String outputCustomerPath = "NewCustomerListPA5.csv";
        String outputEventPath = "NewEventListPA5.csv";

        CSVHandler csvh = new CSVHandler(customerListPath, eventListPath,
                                            outputCustomerPath, outputEventPath);

        //Create hashmaps to store event and customer data
        HashMap<Integer, Customer> custMap = csvh.readCustomers(sc,log);
        HashMap<Integer, Event> eventMap = csvh.eventReader(sc, log);

        // Create an empty hashmap to store events by type
        HashMap<Integer, Event> eventOfTypeMap = new HashMap<Integer, Event>();

        TicketMinerCompany ticketMiner = new TicketMinerCompany();

        // Create an instance of Menu to display prompts and get user input
        MainMenu menu = new MainMenu();

        // Run MainMenu loop.
        boolean runMain = true;
        while (runMain) {

            // Run main menu to user
            menu.startUpMenu();

            // Determine if user is customer or admin.
            String userOrAdmin = menu.customerAdminSel(sc, log);

            switch (userOrAdmin) {

                // Display Customer program
                case "customer":

                    //Get customer based on first and last name
                    Customer customer = menu.customerNameMenu(custMap, sc, log);
                    
                    //Validate customer login credentials
                    menu.customerUsernamePWMenu(customer, sc, log);

                    boolean runCustomer = true;
                    while (runCustomer) {
                        
                        //Determine if the customer would like to purchase or cancel purchase
                        String custSelection = menu.customerSelectionMenu(sc, log);

                        switch (custSelection) {

                            //Case "1": Purchase ticket
                            case "1":
                                //Get event type selection and get new map containing events by type
                                String eventType = menu.customerEventTypeMenu(sc);
                                eventOfTypeMap = Event.filterHashMapByType(eventMap, eventType);

                                //Display event choices
                                Event.displayEvents(eventOfTypeMap);

                                //Get user event selection
                                Event event = menu.customerEventSelectMenu(eventOfTypeMap, sc);

                                //Get user type of ticket to purchase
                                String ticketType = menu.ticketTierSel(sc);

                                //Get user amount of tickets to purchase
                                int numTickets = menu.numTicketsToPurchase(sc);

                                // Get customer membership status then calculate subtotal, tax, and grand total
                                boolean membershipStatus = customer.getMinerMember();

                                //charityServiceFees = {convenience fee, service fee, charity fee}
                                double totalFees = Calculator.calcFees(ticketMiner, numTickets, ticketType, event);
                                double subtotal = Calculator.calcSubtotal(numTickets, ticketType, event, membershipStatus, totalFees);

                                double tax = Calculator.calcTax(subtotal);
                                double grandTotal = Calculator.calcGrandTotal(subtotal, tax);
                                
                                //Money saved with membership
                                double moneySaved = 0;

                                //Only calculate  and update money saved column if member is customer, otherwise no need
                                if(membershipStatus){
                                    System.out.println("TicketMiner Membership Discout applied.");
                                    moneySaved = Calculator.calculateTotalSavedWithMembership(subtotal, grandTotal, customer);
                                    customer.setMoneySaved(moneySaved);
                                    event.setTotalAmntDiscounted(moneySaved);
                                    
                                }else{
                                    System.out.println("-------------------------------------------------------------");
                                    System.out.println("|   Next time save money with the TicketMiner membership!   |");
                                    System.out.println("-------------------------------------------------------------");
                                }

                                // Create invoice for customer
                                Invoice invoice = new Invoice();
                                customer.setInvoice(invoice);
                                customer.getInvoice().setTicketTier(ticketType);

                                // Checks if user has enough money to purchase number of tickets selected
                                boolean hasFunds = false;
                                hasFunds = Validate.validateCustomerFunds(customer, grandTotal);

                                // If the user has enough funds - update data, else return to main menu
                                if (hasFunds) {

                                    // Generate unique confirmation number
                                    int confirmationNumber = Math.abs(customer.hashCode() + event.hashCode());

                                    int idSel = event.getEventID();

                                    // Remove total from user balance and update balance
                                    customer.deductFunds(grandTotal);

                                    // Update concerts purchased
                                    customer.setConcertsPurchased(1, log);

                                    // Decrease remaining # tickets for ticket tier
                                    eventMap.get(idSel).setRemainingGeneric(numTickets, ticketType,"purchase", log);
                                    event.setTotalSoldSeats(numTickets, "purchase",log);

                                    // Update tax collected for event
                                    eventMap.get(idSel).setTaxCollected(tax);

                                    // Log successful customer purchase
                                    String logOutput = customer.getFirstName() + " " + customer.getLastName()
                                            + " purchased " + numTickets + " ticket(s). Total: $"
                                            + Math.round(grandTotal);
                                    log.logActivity(logOutput);

                                    // Create an invoice of transaction
                                    customer.getInvoice().createInvoice(customer.getFirstName() + " " + customer.getLastName(),
                                            eventMap.get(idSel).getEventDate(), eventMap.get(idSel).getEventTime(), grandTotal,
                                            ticketType, numTickets,confirmationNumber, log);
                                    customer.setInvoice(invoice);

                                    customer.getInvoice().printInvoice(log);

                                    //Create customer purchase and add to customer purchase history
                                    Purchase purchase = new Purchase(event.getEventType(), event.getEventName(), event.getEventDate(), ticketType,
                                                                        numTickets, grandTotal, confirmationNumber, subtotal - totalFees);
                                    
                                    customer.setPurchaseHistory(purchase);
                                    ticketMiner.generateTickets(customer, purchase);
                                } else {
                                    System.out.println("Insufficient funds.Returning to the main menu...");
                                }
                                
                                break;
                        
                            //Case "2": Cancel purchase
                            case "2":
                                
                                ArrayList<Purchase> purchaseHistory = customer.getPurchaseHistory();

                                if(purchaseHistory.isEmpty()){
                                    System.out.println("You do not have any transactions to cancel.");
                                }
                                else{
                                    //Display message and get user purchase to cancel
                                    int purchaseIndex = menu.getPurchaseToCancel(purchaseHistory, sc);
                                    Event eventToCancel = Event.findEventByName(purchaseHistory.get(purchaseIndex).getEventName(), eventMap);

                                    Purchase purchaseToCancel = purchaseHistory.get(purchaseIndex);
                                    int numberOfTicketsToCancel = purchaseToCancel.getNumberOfTickets();

                                    //1. Return money to customer's account( Only return ticket costs (do not return service/charity/convenience fees))
                                    customer.addFunds(purchaseHistory.get(purchaseIndex).getPriceBeforeFees());

                                    //2. Adjust event profit/seats available:
                                    // Call method to subtract the number of tickets for cancellation.
                                    eventToCancel.setTotalSoldSeats(numberOfTicketsToCancel, "Cancel", log);

                                    // Call method to subract the type of ticket from cancelled ticket that was purchased.
                                    eventToCancel.setRemainingGeneric(purchaseToCancel.getNumberOfTickets(), purchaseToCancel.getTicketType(), "cancel", log);


                                    //3. Remove purchase from purchase history
                                    customer.removePurchase(purchaseIndex);
                                    System.out.println("Purchase successfully cancelled.");
                                }

                                break;

                            case "3":
                                System.out.println();
                                System.out.println("Balance: $" + customer.getMoneyAvail());
                                break;

                            case "4":
                                runCustomer = false;
                                break;
                        }

                    }
                    break;

                // Run admin program
                case "admin":
                    boolean runAdmin = true;
                    while (runAdmin) {
                        menu.SysAdmnMenu();
                        Scanner scanny = new Scanner(System.in);
                        String adminChoice = scanny.nextLine().toUpperCase();
                
                        switch (adminChoice) {

                            case "EXIT":
                                runAdmin = false;
                                break;
                
                            case "1":
                                menu.inquireById(eventMap, sc, log); // Use the correct parameters
                                break;
                
                            case "2":
                                menu.inquireByName(eventMap, sc, log); // Use the correct parameters
                                break;
                
                            case "3":
                                menu.createEvent(eventMap, scanny, sc, log); // Use the correct parameters
                                break;
                
                            case "4":
                                menu.generateInvoice(custMap, sc, log); // Use the correct parameters
                                break;

                            case "5":
                                menu.computeTotalFeesForEvent(ticketMiner, eventMap, scanny, log);
                                break;

                            case "6":
                                menu.computeTotalFees(ticketMiner);
                                break;

                            case "7":
                                menu.cancelEvent(eventMap, custMap, ticketMiner, sc, log);
                                break;
                            default:
                                System.out.println("Invalid selection.");
                                System.out.println("Please enter 1, 2, 3, 4, 5, 6, 7 or type EXIT.");
                                break;
                        }
                    }
                    break;

                case "auto":
                    autopurchase.runAutoPurchase(custMap,eventMap, ticketMiner, sc, log);
                    break;

                case "EXIT":
                    //Write new customer and event csv files
                    csvh.writeCustomerList(custMap);
                    csvh.writeEventList(eventMap);
                    
                    System.out.println("Exiting. Goodbye.");
                    runMain = false;
                    break;
            }
        }
    }
}