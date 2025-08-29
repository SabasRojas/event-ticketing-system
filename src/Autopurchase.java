/**
 * Autopurchase: Class that reads CSV files for autopurchases.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

class Autopurchase{

    private String autoPurchaseFilePath;

    //Default constructor
    Autopurchase(){}

    Autopurchase(String inputPathIn){
        this.autoPurchaseFilePath = inputPathIn;
    }

    //Setters and Getters
    public String getAutoPurchaseFilePath() {
        return this.autoPurchaseFilePath;
    }

    public void setAutoPurchaseFilePath(String autoPurchaseFilePath) {
        this.autoPurchaseFilePath = autoPurchaseFilePath;
    }

    public void runAutoPurchase(HashMap<Integer, Customer> custMapIn, HashMap<Integer, Event> eventMapIn, TicketMinerCompany ticketMinerIn, Scanner sc, Log log){
        
        boolean isValid = false;
        do{
            try{
                //Open file and read with Scanner
                File file = new File(this.autoPurchaseFilePath);
                Scanner apScanner = new Scanner(file);

                //Read the header row to get the column names (assuming it's present)
                String headerLine = apScanner.nextLine();
                String[] header = headerLine.split(",");

                Customer customer = new Customer();
                
                while(apScanner.hasNext()){

                    //Read next line and split by comma
                    String line = apScanner.nextLine();
                    String[] token = line.split(",");

                    //Store array into human readable variable names
                    String firstName = token[0];
                    String lastName = token[1];
                    String action = token[2];
                    int eventId = Integer.valueOf(token[3]);
                    String eventName = token[4];
                    int ticketQuantity = Integer.valueOf(token[5]);
                    String ticketType = MainMenu.ticketTierSelAuto(token[6]);
                    
                    //Store custome name in array to pass to isValidCustomer function
                    String[] flname = {firstName, lastName};
                    
                    //Validate that customer exists in system
                    boolean isValidCustomer = Validate.isValidCustomer(custMapIn, flname, log);

                    if(isValidCustomer){

                        customer = customer.getCustomer(flname, custMapIn);
                        Event event = eventMapIn.get(eventId);

                        // Get customer membership status then calculate subtotal, tax, and grand total
                        boolean membershipStatus = customer.getMinerMember();

                        //charityServiceFees = {convenience fee, service fee, charity fee}
                        double totalFees = Calculator.calcFees(ticketMinerIn, ticketQuantity, ticketType, event);
                        event.setTotalFees(totalFees);

                        double subtotal = Calculator.calcSubtotal(ticketQuantity, ticketType, event, membershipStatus, totalFees);
                        double tax = Calculator.calcTax(subtotal);
                        double grandTotal = Calculator.calcGrandTotal(subtotal, tax);
                        
                        //Money saved with membership
                        double moneySaved = 0;

                        //Only calculate  and update money saved column if member is customer, otherwise no need
                        if(membershipStatus){
                            log.logActivity("TicketMiner Membership Discout applied.");
                            moneySaved = Calculator.calculateTotalSavedWithMembership(subtotal, grandTotal, customer);
                            customer.setMoneySaved(moneySaved);
                            event.setTotalAmntDiscounted(moneySaved);
                        }

                        //Verify if the customer has enough money to complete purchase
                        boolean hasFunds = Validate.validateCustomerFunds(customer, grandTotal);

                        if(hasFunds){

                            // Create invoice for customer
                            Invoice invoice = new Invoice();
                            customer.setInvoice(invoice);
                            customer.getInvoice().setTicketTier(ticketType);

                            // Generate unique confirmation number
                            int confirmationNumber = Math.abs(customer.hashCode() + event.hashCode());

                            int idSel = event.getEventID();

                            // Remove total from user balance and update balance
                            customer.deductFunds(grandTotal);

                            // Update concerts purchased
                            customer.setConcertsPurchased(1, log);
                            log.logActivity("UPDATED: Number of concerts purchased for: "
                                                + event.getEventName());

                            // Decrease remaining # tickets for ticket tier
                            eventMapIn.get(idSel).setRemainingGeneric(ticketQuantity, ticketType,"purchase", log);

                            // Update tax collected for event
                            eventMapIn.get(idSel).setTaxCollected(tax);

                            // Log successful customer purchase
                            String logOutput = "Success: " + customer.getFirstName() + " " + customer.getLastName()
                                    + " purchased " + ticketQuantity + " ticket(s). Total: $"
                                    + grandTotal;
                            log.logActivity(logOutput);

                            // Create an invoice of transaction
                            customer.getInvoice().createInvoice(customer.getFirstName() + " " + customer.getLastName(),
                                    eventMapIn.get(idSel).getEventDate(), eventMapIn.get(idSel).getEventTime(), grandTotal,
                                    ticketType, ticketQuantity,confirmationNumber, log);
                            customer.setInvoice(invoice);

                            //Create customer purchase and add to customer purchase history
                            Purchase purchase = new Purchase(event.getEventType(), event.getEventName(), event.getEventDate(), ticketType,
                            ticketQuantity, grandTotal, confirmationNumber, subtotal - totalFees);
                    
                    
                    customer.setPurchaseHistory(purchase);
                        }
                        else {
                            log.logActivity("ERROR: Transaction failed. Insufficient funds.");
                        }
                    }
                    else{
                        log.logActivity("Failed customer login: " + firstName + " " + lastName);
                    }
                }
                isValid = true;
            }
            catch(FileNotFoundException e){
                System.out.println("No event file found...");
                System.out.println("Please enter the path to the customer file: ");

                //Update filepath location
                this.autoPurchaseFilePath = sc.nextLine();

                log.logActivity("ERROR: Unable to locate autopurchase file.");
            }
        }while(!isValid);
    }
}