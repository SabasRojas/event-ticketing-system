/**
 * Purchase: Class that holds purchase details for a transaction.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

class Purchase{
    

    private String eventType;
    private String eventName;
    private String eventDate;
    private String ticketType;
    private int numberOfTickets;
    private double priceBeforeFees;
    private double priceAfterFees;
    private int confirmationNumber;

    Purchase(){

    }

    Purchase(String eventTypeIn, String eventNameIn, String eventDateIn, String ticketTypeIn,
            int numberOfTicketsIn, double priceAfterFeesIn, int confirmationIn, double priceBeforeFeesIn){

            this.eventType = eventTypeIn;
            this.eventName = eventNameIn;
            this.eventDate = eventDateIn;
            this.ticketType = ticketTypeIn;
            this.numberOfTickets = numberOfTicketsIn;
            this.priceAfterFees = priceAfterFeesIn;
            this.confirmationNumber = confirmationIn;
            this.priceBeforeFees = priceBeforeFeesIn;
        }

    public double getPriceBeforeFees() {
        return this.priceBeforeFees;
    }

    public void setPriceBeforeFees(double priceBeforeFees) {
        this.priceBeforeFees = priceBeforeFees;
    }

    public double getPriceAfterFees() {
        return this.priceAfterFees;
    }

    public void setPriceAfterFees(double priceAfterFees) {
        this.priceAfterFees = priceAfterFees;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTicketType() {
        return this.ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getNumberOfTickets() {
        return this.numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getpriceAfterFees() {
        return this.priceAfterFees;
    }

    public void setpriceAfterFees(double priceAfterFees) {
        this.priceAfterFees = priceAfterFees;
    }

    public int getConfirmationNumber() {
        return this.confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

}