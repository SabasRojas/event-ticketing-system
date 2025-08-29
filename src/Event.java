/**
 * Event: Used to store all information related to any event
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */
import java.util.HashMap;
import java.util.Set;

public abstract class Event{
    
    //Basic event info
    private int eventID;
    private String eventType;
    private String eventName;
    private String eventDate;
    private String venueType;
    private String eventTime;
    private Venue venue;
    private boolean hasFireworks;
    private double fireworksCost;

    //Ticket tier prices
    private double vipPrice;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
    private double gaPrice;

    //Percentage of tickets
    private double vipPct;
    private double goldPct;
    private double silverPct;
    private double bronzePct;
    private double gaPct;
    private double resPct;
    private double unavailablePct;

    //Sold tickets
    private int soldVIP;
    private int soldGld;
    private int soldSilver;
    private int soldBrz;
    private int soldGA;

    //Remaining tickets
    private double remVIPTickets;
    private double remGoldTickets;
    private double remSilverTickets;
    private double remBronzeTickets;
    private double remGaTickets;
    private double remReservedTickets;

    // Revenue:
    private int totalSoldSeats;
    private double totalExpectedRevenue;
    private double taxCollected;
    private double totalAmntDiscounted;

    // Fees:
    private double convenienceFee;
    private double serviceFee;
    private double charityFee;
    private double totalFees;

    // Cancelled Tickets:
    private int cancelledTickets;
    private int cancelledVIP;
    private int cancelledGold;
    private int cancelledSilver;
    private int cancelledBronze;
    private int cancelledGA;

    // Default Constructor.
    public Event(){}

    //Constructor for fireworks
    public Event(boolean hasFireworksIn, double fireworksCostIn){
        this.hasFireworks = false;
        this.fireworksCost = 0;
    }

    // Constructor for CSV file.
    public Event(int eventIDIn, String eventNameIn, String eventTypeIn, String eventTimeIn, Venue venueIn,
                String venueTypeIn, double vipPriceIn, double goldPriceIn, double silverPriceIn, double bronzePriceIn,
                double gaPriceIn, double vipPctIn, double silverPctIn, double goldPctIn, double bronzePCTIn,
                double remVipIn, double remGoldIn, double remSilverIn, double remBronzeIn, double remGaIn,
                double generalPCTIn, double remResIn, double discountAmountIn){
                    
                this.eventID = eventIDIn;
                this.eventName = eventNameIn;
                this.eventType = eventTypeIn;
                this.eventTime = eventTimeIn;
                this.venue = venueIn;
                this.venueType = venueTypeIn;
                
                this.vipPrice = vipPriceIn;
                this.goldPrice = goldPriceIn;
                this.silverPrice = silverPriceIn;
                this.bronzePrice = bronzePriceIn;
                this.gaPrice = gaPriceIn;
                
                this.vipPct = vipPctIn;
                this.goldPct = goldPctIn;
                this.silverPct = silverPctIn;
                this.bronzePct = bronzePCTIn;
                this.remVIPTickets = remVipIn;
                this.remGoldTickets = remGoldIn;
                this.remSilverTickets = remSilverIn;
                this.remBronzeTickets = remBronzeIn;
                this.remGaTickets = remGaIn;
                this.gaPct = generalPCTIn;
                this.remReservedTickets = remResIn;
                this.totalAmntDiscounted = discountAmountIn;

                this.hasFireworks = false;
                this.fireworksCost = 0;
    }

    // Constructor if fees columns are needed for CSV:
    public Event(int eventIDIn, String eventNameIn, String eventTypeIn, String eventTimeIn, Venue venueIn,
                String venueTypeIn, double vipPriceIn, double goldPriceIn, double silverPriceIn, double bronzePriceIn,
                double gaPriceIn, double vipPctIn, double silverPctIn, double goldPctIn, double bronzePCTIn,
                double remVipIn, double remGoldIn, double remSilverIn, double remBronzeIn, double remGaIn,
                double generalPCTIn, double remResIn, double discountAmountIn, double convenienceFeeIn, double serviceFeeIn, double charityFeeIn, double totalFeesIn){
                    
                this.eventID = eventIDIn;
                this.eventName = eventNameIn;
                this.eventType = eventTypeIn;
                this.eventTime = eventTimeIn;
                this.venue = venueIn;
                this.venueType = venueTypeIn;
                
                this.vipPrice = vipPriceIn;
                this.goldPrice = goldPriceIn;
                this.silverPrice = silverPriceIn;
                this.bronzePrice = bronzePriceIn;
                this.gaPrice = gaPriceIn;
                
                this.vipPct = vipPctIn;
                this.goldPct = goldPctIn;
                this.silverPct = silverPctIn;
                this.bronzePct = bronzePCTIn;
                this.remVIPTickets = remVipIn;
                this.remGoldTickets = remGoldIn;
                this.remSilverTickets = remSilverIn;
                this.remBronzeTickets = remBronzeIn;
                this.remGaTickets = remGaIn;
                this.gaPct = generalPCTIn;
                this.remReservedTickets = remResIn;
                this.totalAmntDiscounted = discountAmountIn;

                this.hasFireworks = false;
                this.fireworksCost = 0;

                this.convenienceFee = convenienceFeeIn;
                this.serviceFee = serviceFeeIn;
                this.charityFee = charityFeeIn;
                this.totalFees = totalFeesIn;
    }

    //Setters and Getters
    /**
     *
     * @return event id
     */
    public int getEventID() {
        return this.eventID;
    }

    /**
     *
     * @param eventID
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    /**
     *
     * @return event type
     */
    public String getEventType() {
        return this.eventType;
    }

    /**
     *
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     *
     * @return event name
     */
    public String getEventName() {
        return this.eventName;
    }

    /**
     *
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     *
     * @return
     */
    public String getEventDate() {
        return this.eventDate;
    }

    /**
     *
     * @param eventDate
     */
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    /**
     *
     * @return venue type
     */
    public String getVenueType() {
        return this.venueType;
    }

    /**
     *
     * @param venueType
     */
    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    /**
     *
     * @return event time
     */
    public String getEventTime() {
        return this.eventTime;
    }

    /**
     *
     * @param eventTime
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     *
     * @return vip ticket price
     */
    public double getVipPrice() {
        return this.vipPrice;
    }

    /**
     *
     * @param vipPrice
     */
    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    /**
     *
     * @return gold ticket price
     */
    public double getGoldPrice() {
        return this.goldPrice;
    }

    /**
     *
     * @param goldPrice
     */
    public void setGoldPrice(double goldPrice) {
        this.goldPrice = goldPrice;
    }

    /**
     *
     * @return silver ticket price
     */
    public double getSilverPrice() {
        return this.silverPrice;
    }

    /**
     *
     * @param silverPrice
     */
    public void setSilverPrice(double silverPrice) {
        this.silverPrice = silverPrice;
    }

    /**
     *
     * @return bronze ticket price
     */
    public double getBronzePrice() {
        return this.bronzePrice;
    }

    /**
     *
     * @param bronzePrice
     */
    public void setBronzePrice(double bronzePrice) {
        this.bronzePrice = bronzePrice;
    }

    /**
     *
     * @return ga ticket price
     */
    public double getGaPrice() {
        return this.gaPrice;
    }

    /**
     *
     * @param gaPrice
     */
    public void setGaPrice(double gaPrice) {
        this.gaPrice = gaPrice;
    }

    /**
     *
     * @return vip ticket percentage
     */
    public double getVipPct() {
        return this.vipPct;
    }

    /**
     *
     * @param vipPct
     */
    public void setVipPct(double vipPct) {
        this.vipPct = vipPct;
    }

    /**
     *
     * @return gold ticket percentage
     */
    public double getGoldPct() {
        return this.goldPct;
    }

    /**
     *
     * @param goldPct
     */
    public void setGoldPct(double goldPct) {
        this.goldPct = goldPct;
    }

    /**
     *
     * @return silver ticket percentage
     */
    public double getSilverPct() {
        return this.silverPct;
    }

    /**
     *
     * @param silverPct
     */
    public void setSilverPct(double silverPct) {
        this.silverPct = silverPct;
    }

    /**
     *
     * @return bronze ticket percentage
     */
    public double getBronzePct() {
        return this.bronzePct;
    }

    /**
     *
     * @param bronzePct
     */
    public void setBronzePct(double bronzePct) {
        this.bronzePct = bronzePct;
    }

    /**
     *
     * @return ga ticket percentage
     */
    public double getGaPct() {
        return this.gaPct;
    }

    /**
     *
     * @param gaPct
     */
    public void setGaPct(double gaPct) {
        this.gaPct = gaPct;
    }

    /**
     *
     * @return number of vip tickets sold
     */
    public int getSoldVIP() {
        return this.soldVIP;
    }

    /**
     *
     * @param soldVIP
     */
    public void setSoldVIP(int soldVIP) {
        this.soldVIP += soldVIP;
    }

    /**
     *
     * @return number of gold tickets sold
     */
    public int getSoldGld() {
        return this.soldGld;
    }

    /**
     *
     * @param soldGld
     */
    public void setSoldGld(int soldGld) {
        this.soldGld += soldGld;
    }

    public void setSoldSilver(int soldSilver){
        this.soldSilver += soldSilver;
    }

    public int getSoldSilver(){
        return this.soldSilver;
    }

    /**
     *
     * @return number of bronze tickets sold
     */
    public int getSoldBrz() {
        return this.soldBrz;
    }

    /**
     *
     * @param soldBrz
     */
    public void setSoldBrz(int soldBrz) {
        this.soldBrz += soldBrz;
    }

    /**
     *
     * @return number of ga tickets sold
     */
    public int getSoldGA() {
        return this.soldGA;
    }

    /**
     *
     * @param soldGA
     */
    public void setSoldGA(int soldGA) {
        this.soldGA += soldGA;
    }

    /**
     *
     * @return total number of seats sold
     */
    public int getTotalSoldSeats() {
        return this.totalSoldSeats;
    }

    /**
     *
     * @param totalSoldSeats
     */
    public void setTotalSoldSeats(int totalSoldSeats) {
        this.totalSoldSeats += totalSoldSeats;
    }

    /**
     * @param totalSoldSeats
     * @param userChoice
     * change amount of total tickets for Admin if customer cancels ticket. 
     */
    public void setTotalSoldSeats(int totalSoldSeats, String purchase, Log log){

        if(purchase == "Cancel"){
            this.totalSoldSeats -= totalSoldSeats;
        }else{
            this.totalSoldSeats += totalSoldSeats;
        }
    }

    /**
     *
     * @return revenue if all tickets sold
     */
    public double getTotalExpectedRevenue() {
        return this.totalExpectedRevenue;
    }

    /**
     *
     * @param totalExpectedRevenue
     */
    public void setTotalExpectedRevenue(double totalExpectedRevenue) {
        this.totalExpectedRevenue = totalExpectedRevenue;
    }

    /**
     *
     * @return event venue
     */
    public Venue getVenue() {
        return this.venue;
    }

    /**
     *
     * @param venue
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     *
     * @return remaining vip tickets
     */
    public double getRemVIPTickets() {
        return this.remVIPTickets;
    }

    /**
     *
     * @param remVIPTickets
     */
    public void setRemVIPTickets(double remVIPTickets) {
        this.remVIPTickets = remVIPTickets;
    }

    /**
     *
     * @return remainign gold tickets
     */
    public double getRemGoldTickets() {
        return this.remGoldTickets;
    }

    /**
     *
     * @param remGoldTickets
     */
    public void setRemGoldTickets(double remGoldTickets) {
        this.remGoldTickets = remGoldTickets;
    }

    /**
     *
     * @return remaining silver tickets
     */
    public double getRemSilverTickets() {
        return this.remSilverTickets;
    }

    /**
     *
     * @param remSilverTickets
     */
    public void setRemSilverTickets(double remSilverTickets) {
        this.remSilverTickets = remSilverTickets;
    }

    /**
     *
     * @return remaining bronze tickets
     */
    public double getRemBronzeTickets() {
        return this.remBronzeTickets;
    }

    /**
     *
     * @param remBronzeTickets
     */
    public void setRemBronzeTickets(double remBronzeTickets) {
        this.remBronzeTickets = remBronzeTickets;
    }

    /**
     *
     * @return remaining ga tickets
     */
    public double getRemGaTickets() {
        return Math.max(0, this.remGaTickets - this.cancelledGA);
    }

    /**
     *
     * @param remGaTickets
     */
    public void setRemGaTickets(double remGaTickets) {
        this.remGaTickets = remGaTickets;
    }

    /**
     *
     * @return remaining reserved tickets
     */
    public double getRemReservedTickets() {
        return this.remReservedTickets;
    }

    /**
     *
     * @param remReservedTickets
     */
    public void setRemReservedTickets(double remReservedTickets) {
        this.remReservedTickets = remReservedTickets;
    }

    /**
     *
     * @return reserved ticket percentage
     */
    public double getResPct() {
        return this.resPct;
    }

    /**
     *
     * @param resPct
     */
    public void setResPct(double resPct) {
        this.resPct = resPct;
    }

    /**
     *
     * @param hasFireworks
     */
    public void setHasFireworks(boolean hasFireworks) {
        this.hasFireworks = hasFireworks;
    }

    /**
     *
     * @return true or false
     */
    public boolean hasFireworks() {
        return this.hasFireworks;
    }

    /**
     *
     * @param fireworksCost
     */
    public void setFireworksCost(double fireworksCost) {
        this.fireworksCost = fireworksCost;
    }

    /**
     *
     * @return cost of fireworks
     */
    public double getFireworksCost() {
        return this.fireworksCost;
    }

    /**
     *
     * @return unavailable ticket percentage
     */
    public double getUnavailablePct() {
        return this.unavailablePct;
    }

    /**
     *
     * @param unavailablePct
     */
    public void setUnavailablePct(double unavailablePct) {
        this.unavailablePct = unavailablePct;
    }

    /**
     *
     * @return total tax collected for event
     */
    public double getTaxCollected() {
        return this.taxCollected;
    }

    /**
     *
     * @param taxCollected
     */
    public void setTaxCollected(double taxCollected) {
        this.taxCollected += taxCollected;
    }

    public double getTotalAmntDiscounted() {
        return this.totalAmntDiscounted;
    }

    public void setTotalAmntDiscounted(double totalAmntDiscounted) {
        this.totalAmntDiscounted += (double) Math.round(totalAmntDiscounted * 100.0) / 100.0;
    }

    public double getConvenienceFee() {
        return convenienceFee;
    }

    public void setConvenienceFee(double convenienceFee) {
        this.convenienceFee += convenienceFee;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee += serviceFee;
    }

    public double getCharityFee() {
        return charityFee;
    }

    public void setCharityFee(double charityFee) {
        this.charityFee += charityFee;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(double totalFees) {
        this.totalFees += totalFees;
    }

    public void setCancelledTickets(int cancelledTicketsIn){
        this.cancelledTickets = cancelledTicketsIn;

    }

    public int getCancelledTickets(){
        return cancelledTickets;
    }

    public int getCancelledTickets(String ticketType) {
        switch (ticketType.toLowerCase()) {
            case "vip":
                return this.cancelledVIP;
            case "gold":
                return this.cancelledGold;
            case "silver":
                return this.cancelledSilver;
            case "bronze":
                return this.cancelledBronze;
            case "ga":
                return this.cancelledGA;
            default:
                return 0;
        }
    }
    

    // CUSTOMER METHODS:
    /**
     * @param mapIn
     * @param eventTypeIn
     * @return filteredEvents
     * Written by: Jorge Sandoval
     * Edited by: Christian Odom
     */
    public static HashMap<Integer, Event> filterHashMapByType(HashMap<Integer,Event> mapIn, String eventTypeIn){
        HashMap<Integer, Event> filteredEvents = new HashMap<>();

        for (Event event : mapIn.values()) {
            if (event.getEventType().equalsIgnoreCase(eventTypeIn)) {
                filteredEvents.put(event.getEventID(), event);
            }
        }
        return filteredEvents;
    }

    /**
     * @param eventMap
     * Written by: Jorge Sandoval
     * Edited by: Christian Odom
     */
    public static void displayEvents(HashMap<Integer, Event> eventMap){
        String dashLine = "---------------------------------------------------------------------%n";

        System.out.println("");
        System.out.printf(dashLine);
        System.out.printf("| %-27s  EVENT  %-29s |%n"," ", " ");
        System.out.printf(dashLine);

        System.out.printf("| %-6s | %-30s | %-10s | %-10s |%n", "ID",
                            "EVENT NAME", "DATE", "TIME");
        System.out.printf(dashLine);

        //Iterate through Hashmap and all events based on customer selection
        Set<Integer> keys = eventMap.keySet();
        for(int k: keys){
            System.out.printf("| %-6s | %-30s | %-10s | %-10s |%n", k,
                                eventMap.get(k).getEventName(),
                                eventMap.get(k).getEventDate(),
                                eventMap.get(k).getEventTime());
                                System.out.printf(dashLine);
        }
        System.out.println();
}






    /**
     * @param eventNameIn
     * @param eventListIn
     * @return event or null
     * @brief search for event by name
     * Written by: Jorge Sandoval
     */
    public static Event findEventByName(String eventNameIn, HashMap<Integer, Event> eventListIn){
        for (Event event : eventListIn.values()){
            if (event.getEventName().equalsIgnoreCase(eventNameIn)){
                return event;
            }
        }
        return null;
    }
    
    /**
     * @brief display ticket prices
     */
    public void displayTicketPrices() {
        System.out.println("VIP: $" + this.getVipPrice());
        System.out.println("Gold: $" + this.getGoldPrice());
        System.out.println("Silver: $" + this.getSilverPrice());
        System.out.println("Bronze: $" + this.getBronzePrice());
        System.out.println("GA: $" + this.getGaPrice());
        System.out.println();
    }

    // ADMIN METHODS:

    /**
     * @return void
     * @brief print all event info
     * Written by: Christian Odom
     */
    public void adminPrintEventInfo(){

        //Create an instance of Calculator to perform calculations for seats sold, etc.
        Calculator calc = new Calculator();
        double subtotal = calc.calcRevenue(this);
        double actualRevenue = calc.calcActualRevenue(this);

        String dashedLine = "------------------------------------------------";
        System.out.println(" ");
        System.out.println(dashedLine);
        System.out.printf("|%-14s%-18s%-14s|%n", " ", "Event Information", " ");
        System.out.println(dashedLine);
        System.out.printf("| %-9s%-35s|%n", "Event ID: ", this.getEventID());
        System.out.printf("| %-5s%-39s|%n", "Name: ", this.getEventName());
        System.out.printf("| %-5s%-39s|%n", "Date: ", this.getEventDate());
        System.out.printf("| %-5s%-39s|%n", "Time: ", this.getEventTime());
        System.out.printf("| %-13s%-32s|%n", "Event Type: ", this.getEventType());
        System.out.printf("| %-16s%-29s|%n", "Event Capacity: ", this.getVenue().getCapacity());
        System.out.printf("| %-19s%-26s|%n", "Total Seats Sold: ", this.getTotalSoldSeats());
        System.out.printf("| %-23s%-22s|%n", "Total VIP Seats Sold: ", calc.calcSeatsSoldByType(this, "VIP"));
        System.out.printf("| %-24s%-21s|%n", "Total Gold Seats Sold: ", calc.calcSeatsSoldByType(this, "Gold"));
        System.out.printf("| %-25s%-20s|%n", "Total Silver Seats Sold: ",  calc.calcSeatsSoldByType(this, "Silver"));
        System.out.printf("| %-25s%-20s|%n", "Total Bronze Seats Sold: ", calc.calcSeatsSoldByType(this, "Bronze"));
        System.out.printf("| %-22s%-23s|%n", "Total GA Seats Sold: ", calc.calcSeatsSoldByType(this, "GA"));
        System.out.printf("| %-33s%-12s|%n", "Total revenue for VIP tickets: $",  calc.calcRevenue(this, "VIP"));
        System.out.printf("| %-34s%-11s|%n", "Total revenue for Gold tickets: $",  calc.calcRevenue(this, "Gold"));
        System.out.printf("| %-36s%-9s|%n", "Total revenue for Silver tickets: $", calc.calcRevenue(this, "Silver"));
        System.out.printf("| %-36s%-9s|%n", "Total revenue for Bronze tickets: $", calc.calcRevenue(this, "Bronze")); 
        System.out.printf("| %-34s%-11s|%n", "Total revenue for GA tickets: $",calc.calcRevenue(this, "GA"));
        System.out.printf("| %-36s%-9s|%n", "Total revenue for all tickets: $", Math.round(calc.calcRevenue(this)));
        System.out.printf("| %-30s%-15s|%n", "Expected profit (Sell Out): $", String.format("%.0f",calc.calcExpectedRevenue(this)));
        System.out.printf("| %-17s%-28s|%n", "Actual Profit: $", Math.round(calc.calcActualRevenue(this)));
        System.out.printf("| %-32s%-13s|%n", "Total revenue for all tickets: $", Math.round(calc.calcRevenue(this)));
        System.out.printf("| %-19s%-26s|%n", "Convenience Fee: $", String.format("%.2f", this.getConvenienceFee()));
        System.out.printf("| %-15s%-30s|%n", "Service Fee: $", String.format("%.2f", this.getServiceFee()));
        System.out.printf("| %-15s%-30s|%n", "Charity Fee: $", String.format("%.2f", this.getCharityFee()));
        System.out.printf("| %-15s%-30s|%n", "Total Fees: $", String.format("%.2f", this.getTotalFees()));
        System.out.printf("| %-18s%-27s|%n",  "Actual Revenue: $", String.format("%.2f", calc.calcActualRevenue(this)));
        System.out.println(dashedLine);
    }

    /**
     * @param numTicketsIn
     * @param typeIn
     * @param log
     * @brief set remaining tickets after customer purchase.
     * Written by: Christian Odom
     */
    public void setRemainingGeneric(int numTicketsIn, String typeIn, String transactionType, Log log){
        
        double updatedTickets;

        switch(typeIn){
            case "ga":

                if(transactionType.equals("purchase")){

                    //For purchase, total amount of REMAINING tickets decreases
                    updatedTickets = this.getRemGaTickets() - numTicketsIn;
                    this.setRemGaTickets(updatedTickets);

                    //For purchase, total amount of SOLD tickets increases
                    this.setSoldGA(this.getSoldGA() + numTicketsIn);
                    log.logActivity("PURCHASE: Updated remaining GA tickets: " + this.getRemGaTickets());
                }
                else if(transactionType.equals("cancel")){

                    //For cancellation, total amount of remaining tickets increases
                    updatedTickets = this.getRemGaTickets() + numTicketsIn;
                    this.setRemGaTickets(updatedTickets);

                    //For cancellation, total amount of SOLD tickets decreases
                    this.setSoldGA(this.getSoldGA() - numTicketsIn);
                    log.logActivity("CANCELLATION: Updated remaining GA tickets: " + this.getRemGaTickets());
                }

                break;

            case "bronze":

                if(transactionType.equals("purchase")){

                    //For purchase, total amount of REMAINING tickets decreases
                    updatedTickets = this.getRemBronzeTickets() - numTicketsIn;
                    this.setRemBronzeTickets(updatedTickets);

                    //For purchase, total amount of SOLD tickets increases
                    this.setSoldBrz(this.getSoldBrz() + numTicketsIn);
                    log.logActivity("PURCHASE: Updated remaining Bronze tickets: " + this.getRemBronzeTickets());
                }
                else if(transactionType.equals("cancel")){

                    //For cancellation, total amount of remaining tickets increases
                    updatedTickets = this.getRemBronzeTickets() + numTicketsIn;
                    this.setRemBronzeTickets(updatedTickets);

                    //For cancellation, total amount of SOLD tickets decreases
                    this.setSoldBrz(this.getSoldBrz() - numTicketsIn);
                    log.logActivity("CANCELLATION: Updated remaining Bronze tickets: " + this.getRemBronzeTickets());
                }

                break;

            case "silver":

                if(transactionType.equals("purchase")){

                    //For purchase, total amount of REMAINING tickets decreases
                    updatedTickets = this.getRemSilverTickets() - numTicketsIn;
                    this.setRemSilverTickets(updatedTickets);

                    //For purchase, total amount of SOLD tickets increases
                    this.setSoldSilver(this.getSoldSilver() + numTicketsIn);
                    log.logActivity("PURCHASE: Updated remaining Silver tickets: " + this.getRemSilverTickets());
                }
                else if(transactionType.equals("cancel")){

                    //For cancellation, total amount of remaining tickets increases
                    updatedTickets = this.getRemSilverTickets() + numTicketsIn;
                    this.setRemSilverTickets(updatedTickets);

                    //For cancellation, total amount of SOLD tickets decreases
                    this.setSoldSilver(this.getSoldSilver() - numTicketsIn);
                    log.logActivity("CANCELLATION: Updated remaining Silver tickets: " + this.getRemSilverTickets());
                }

                break;

            case "gold":

                if(transactionType.equals("purchase")){

                    //For purchase, total amount of REMAINING tickets decreases
                    updatedTickets = this.getRemGoldTickets() - numTicketsIn;
                    this.setRemGoldTickets(updatedTickets);

                    //For purchase, total amount of SOLD tickets increases
                    this.setSoldGld(this.getSoldGld() + numTicketsIn);
                    log.logActivity("PURCHASE: Updated remaining Gold tickets: " + this.getRemGoldTickets());
                }
                else if(transactionType.equals("cancel")){

                    //For cancellation, total amount of remaining tickets increases
                    updatedTickets = this.getRemGoldTickets() + numTicketsIn;
                    this.setRemGoldTickets(updatedTickets);

                    //For cancellation, total amount of SOLD tickets decreases
                    this.setSoldGld(this.getSoldGld() - numTicketsIn);
                    log.logActivity("CANCELLATION: Updated remaining Gold tickets: " + this.getRemGoldTickets());
                }
                break;

            case "vip":

                if(transactionType.equals("purchase")){

                    //For purchase, total amount of REMAINING tickets decreases
                    updatedTickets = this.getRemVIPTickets() - numTicketsIn;
                    this.setRemVIPTickets(updatedTickets);

                    //For purchase, total amount of SOLD tickets increases
                    this.setSoldVIP(this.getSoldVIP() + numTicketsIn);
                    log.logActivity("PURCHASE: Updated remaining VIP tickets: " + this.getRemVIPTickets());
                }
                else if(transactionType.equals("cancel")){

                    //For cancellation, total amount of remaining tickets increases
                    updatedTickets = this.getRemVIPTickets() + numTicketsIn;
                    this.setRemVIPTickets(updatedTickets);

                    //For cancellation, total amount of SOLD tickets decreases
                    this.setSoldVIP(this.getSoldVIP() - numTicketsIn);
                    log.logActivity("CANCELLATION: Updated remaining VIP tickets: " + this.getRemVIPTickets());
                }
                break;
        }
    }

    /**
     * @param convenienceFee
     */
    public void addConvenienceFee(double convenienceFeeIn) {
        setConvenienceFee(getConvenienceFee() + convenienceFeeIn);
        setTotalFees(getTotalFees() + convenienceFeeIn);
    }

    public void addServiceFee(double serviceFeeIn) {
        setServiceFee(getServiceFee() + serviceFeeIn);
        setTotalFees(getTotalFees() + serviceFeeIn);
    }

    public void addCharityFee(double charityFeeIn) {
        setCharityFee(getCharityFee() + charityFeeIn);
        setTotalFees(getTotalFees() + charityFeeIn);
    }

}