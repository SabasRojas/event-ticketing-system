/**
 * Customer: Used to store all information related to customer
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Customer{

    // Basic customer info:
    private int custID;
    private String firstName;
    private String lastName;

    // Account customer info:
    private String userName;
    private String password;
    private double moneyAvail;
    private int concertsPurchased;
    private boolean minerMember;
    private int ticketsPurchased;
    private double moneySaved;
    private ArrayList<Purchase> purchaseHistory;

   //Invoice:
    private Invoice invoice;

    // Default constructor.
    public Customer(){}

    // Constructor for CSV file.
    /**
     * @param firstNameIn
     * @param lastNameIn
     * @param userNameIn
     * @param passwordIn
     * @param moneyAvailIn
     * @param concertsPurchasedIn
     * @param minerMemberIn
     */
    public Customer(int custID, String firstNameIn, String lastNameIn, double moneyAvailIn,
                    int concertsPurchasedIn,boolean minerMemberIn,String userNameIn, String passwordIn){
        this.custID = custID;
        this.firstName = firstNameIn;
        this.lastName = lastNameIn;
        this.userName = userNameIn;
        this.password = passwordIn;
        this.moneyAvail = moneyAvailIn;
        this.concertsPurchased = concertsPurchasedIn;
        this.minerMember = minerMemberIn;
        this.purchaseHistory = new ArrayList<Purchase>();
    }

    // Setters:
    /**
     *
     * @param custID
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     *
     * @param firstNameIn
     */
    public void setFirstName(String firstNameIn){
        this.firstName = firstNameIn;
    }

    /**
     *
     * @param lastNameIn
     */
    public void setLastName(String lastNameIn){
        this.lastName = lastNameIn;
    }

    /**
     *
     * @param userNameIn
     */
    public void setUserName(String userNameIn) {
        this.userName = userNameIn;
    }

    /**
     *
     * @param passwordIn
     */
    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }

    /**
     *
     * @param moneyAvailIn
     */
    public void setMoneyAvail(Double moneyAvailIn) {
        this.moneyAvail = moneyAvailIn;
    }

    /**
     *
     * @param concertsPurchasedIn
     */
    public void setConcertsPurchased(int concertsPurchasedIn, Log log) {
        this.concertsPurchased += concertsPurchasedIn;
    }

    /**
     *
     * @param minerMember
     */
    public void setMinerMember(boolean minerMember) {
        this.minerMember = minerMember;
    }

    /**
     *
     * @return
     */
    public boolean isMinerMember() {
        return this.minerMember;
    }

    /**
     *
     * @param ticketsPurchasedIn
     */
    public void setTicketsPurchased(int ticketsPurchasedIn){
        this.ticketsPurchased = ticketsPurchasedIn;
    }

    /**
     *
     * @param invoiceIn
     */
    public void setInvoice(Invoice invoiceIn) {
        this.invoice = invoiceIn;
    }

    /**
     *
     * @param moneySavedIn
     */
    public void setMoneySaved(double moneySavedIn){
        this.moneySaved += Math.round( moneySavedIn * 100.0) / 100.0;
    }

    /**
     *
     * @param purchaseIn
     */
    public void setPurchaseHistory(Purchase purchaseIn) {
        this.purchaseHistory.add(purchaseIn);
    }

    // Getters:
    /**
     *
     * @return first name of customer
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     *
     * @return last name of customer
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     *
     * @return customer username
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     *
     * @return customer password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @return current customer balance
     */
    public Double getMoneyAvail(){
        return this.moneyAvail;
    }

    /**
     *
     * @return total event purchased
     */
    public int getConcertsPurchased() {
        return this.concertsPurchased;
    }

    /**
     *
     * @return total number of tickets purchased
     */
    public int getTicketsPurchased(){
        return this.ticketsPurchased;
    }

    /**
     *
     * @return customer invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     *
     * @return customer id
     */
    public int getCustID() {
        return this.custID;
    }

    /**
     *
     * @return customer membership status
     */
    public boolean getMinerMember() {
        return this.minerMember;
    }

    /**
     *
     * @return array list of customer purchases
     */
    public ArrayList<Purchase> getPurchaseHistory() {
        return this.purchaseHistory;
    }

    public void removePurchase(int indexIn){
        this.purchaseHistory.remove(indexIn);
    }


    /**
     *
     * @return total amount of money saved with membership
     */
    public double getMoneySaved(){
        return this.moneySaved;
    }

    // METHODS:

        /**
     * @param totalPriceIn
     * @param customerIn
     * @brief deducts total price of tickets from customer balance
     * Written by: Christian Odom
     */
    public void deductFunds(double totalPriceIn){
        double newBalance = Math.round((this.getMoneyAvail() - totalPriceIn) * 100.0) / 100.0;
        this.setMoneyAvail(newBalance);
    }

    public void addFunds(double totalPriceIn){
        double newBalance = Math.round((this.getMoneyAvail() + totalPriceIn) * 100.0) / 100.0;
        this.setMoneyAvail(newBalance);
    }

    /**
     * @param custName
     * @param custMapIn
     * @return customer
     * @brief retrieve customer after login
     * Written by: Jorge Sandoval
     * Edited by: Christian Odom
     */
    public Customer getCustomer(String custName[], HashMap<Integer, Customer> custMapIn){
        for(int i = 1; i <= custMapIn.size(); i++){
            Customer customer = custMapIn.get(i);
            if(customer.getFirstName().equalsIgnoreCase(custName[0]) && customer.getLastName().equalsIgnoreCase(custName[1])){
                return customer;
            }
        }
        return null;
    }
    
    /**
     * @param numEventsPurchased
     * @param log
     * @brief update number of events customer has purchased tickets to
     * Written by: Jorge Sandoval
     * Edited by: Christian Odom
     */
    public void setNumEventsPurchased(int numEventsPurchased, Log log){
        this.ticketsPurchased += numEventsPurchased;
        log.logActivity("Updated number of events purchased "
                        + this.getTicketsPurchased());
    }
}