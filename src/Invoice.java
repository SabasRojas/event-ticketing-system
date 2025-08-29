/**
 * Invoice: Class that holds all invoice details of purchase customer makes.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;

public class Invoice{

    private String customerName;
    private String eventTime;
    private String eventDate;
    private String ticketTier;
    private int ticketQuantity;
    private double totalPrice;
    private int confirmationNumber;
    private int eventId;

    //Default constructor
    public Invoice(){

    }

    //Constructor to create invoice.
    public Invoice(int ticketQuantity, double totalPrice, int confirmationNumber, int eventId){
        this.ticketQuantity = ticketQuantity;
        this.totalPrice = totalPrice;
        this.confirmationNumber = confirmationNumber;
        this.eventId = eventId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTicketTier() {
        return this.ticketTier;
    }

    public void setTicketTier(String ticketTier) {
        this.ticketTier = ticketTier;
    }


    public int getTicketQuantity() {
        return this.ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getConfirmationNumber() {
        return this.confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }


    // INVOICE METHODS:

    
    /**
     * @param totalPriceIn
     * @param customerIn
     * @brief deducts total price of tickets from customer balance
     * Written by: Christian Odom
     */
    public void deductFunds(double totalPriceIn, Customer customerIn){
        double newBalance = Math.round((customerIn.getMoneyAvail() - totalPriceIn) *100.0 / 100.0);
        customerIn.setMoneyAvail(newBalance);
    }

    public void addFunds(double totalPriceIn, Customer customerIn){
        double newBalance = Math.round((customerIn.getMoneyAvail() + totalPriceIn) * 100.0 / 100.0);
        customerIn.setMoneyAvail(newBalance);
    }

    /**
     * @param nameIn
     * @param timeIn
     * @param dateIn
     * @param totalPriceIn
     * @param ticketTypeIn
     * @param numTicketsIn
     * @param log
     * @brief create an invoice for the customer
     * Written by: Christian Odom
     * Edited by: Jorge Sandoval
     */
    public void createInvoice(String nameIn, String timeIn, String dateIn,
                                double totalPriceIn, String ticketTypeIn,
                                int numTicketsIn, int confirmationNum, Log log){

            this.customerName = nameIn;
            this.eventDate = dateIn;
            this.eventTime = timeIn;
            this.totalPrice = totalPriceIn;
            this.ticketTier = ticketTypeIn;
            this.ticketQuantity = numTicketsIn;
            this.confirmationNumber = confirmationNum;
            log.logActivity("Invoice created.");
    }

    /**
     * @param log
     * @brief display invoice to customer.
     * Written by: Chrisitan Odom
     * Edited by: Jorge Sandoval
     */
    public void printInvoice(Log log) {
        DecimalFormat f = new DecimalFormat("##.##");
        double totalWithFees = this.getTotalPrice();
        System.out.println(" ");
        System.out.printf("------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-38s INVOICE %-39s |%n", " ", " ");
        System.out.printf("------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-20s | %-8s | %-8s | %-6s | %-2s | %-8s | %-15s | %n", "NAME", "DATE", "TIME", "TIER", "QTY", "TOTAL", "CONFIRMATION #");
        System.out.printf("------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-20s | %-8s | %-8s | %-6s | %-2s | $%-8s | %-15s | %n", this.getCustomerName(), this.getEventTime(),
        this.getEventDate(), this.getTicketTier(), this.getTicketQuantity(),
                            f.format(Math.round(this.getTotalPrice() * 100.0) / 100.0), this.confirmationNumber);
        System.out.printf("------------------------------------------------------------------------------------------%n");
        log.logActivity("SUCCESSFUL PURCHASE: " + this.getCustomerName());
    }

    public static void invoiceSummaryWriter(Customer customerIn){
        try {

            //Create file name
            String firstName = customerIn.getFirstName();
            String lastName = customerIn.getLastName();
            String outputFile = "invoice-summary/" + firstName + "-" + lastName + "-InvoiceSummary.txt";
            ArrayList<Purchase> purchaseHistory = customerIn.getPurchaseHistory();

            File file = new File(outputFile);
            Formatter fm = new Formatter(file);

            /* Jorge's Macbook:
            String firstName = customerIn.getFirstName();
            String lastName = customerIn.getLastName();
            String outputFile = firstName + "-" + lastName + "-" + "ticket.txt";
            ArrayList<Purchase> purchaseHistory = customerIn.getPurchaseHistory();
            File file = new File("pa5-jorge_christian_alan_sabas/tickets"+outputFile);
            Formatter fm = new Formatter(file);
             */

            String dashedLine = "-----------------------------------------------------------------------------------------------------%n";
            
            fm.format(dashedLine);
            fm.format("|%-42sINVOICE SUMMARY%-42s|%n", " ", " ");
            fm.format(dashedLine);
            fm.format("|%-43s %s %s%-43s|%n", " ", customerIn.getFirstName(), customerIn.getLastName(), " ");
            fm.format(dashedLine);
            fm.format("| %-20s | %-10s | %-10s | %-10s | %-3s | %-15s | $ %-8s |%n", "Event Name", "Date", "Type",
                            "Tier", "Qty", "Confirmation #", "Total");
            fm.format(dashedLine);
                
            for(Purchase purchase : purchaseHistory){

                String eventName = purchase.getEventName();
                String eventDate = purchase.getEventDate();
                String eventType = purchase.getEventType();
                String ticketType = purchase.getTicketType();
                String total = String.valueOf(purchase.getPriceAfterFees());
                String ticketQuantity = String.valueOf(purchase.getNumberOfTickets());
                String confirmation = String.valueOf(purchase.getConfirmationNumber());

                fm.format("| %-20s | %-10s | %-10s | %-10s | %-3s | %-15s | $ %-8s |%n", eventName, eventDate, eventType,
                                ticketType, ticketQuantity, confirmation, total);
                fm.format(dashedLine);
            }
        
            fm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}