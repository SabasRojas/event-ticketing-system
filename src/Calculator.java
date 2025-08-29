/**
 * Calculator: Used to calculate event information such as remaining tickets, etc.
 *             Also used to calculated customer purchase total, tax, etc.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class Calculator {
    
    // CONSTRUCTOR:
    public Calculator(){}

    // METHODS:

    public static double calcFees(TicketMinerCompany ticketMinerIn, int numTicketsIn, String ticketTierIn, Event eventIn){

        double convenienceFee = 2.50;
        double serviceFeePct = 0.005;
        double charityFeePct = 0.0075;
        
        double totalFees = 0;
        
        //fees = {convenience fee, service fee, charity fee}
        double fees[] = new double[2];
        
        switch(ticketTierIn){

            case "ga":
                fees[0] = Math.round(100.0 * (eventIn.getGaPrice() * serviceFeePct * numTicketsIn)) / 100.0;
                fees[1] = Math.round(100.0 * (eventIn.getGaPrice() * charityFeePct * numTicketsIn)) / 100.0;
                totalFees = fees[0] + fees[1] + convenienceFee;
                break;

            case "bronze":
                fees[0] = Math.round(100.0 * (eventIn.getBronzePrice() * serviceFeePct * numTicketsIn)) / 100.0;
                fees[1] = Math.round(100.0 * (eventIn.getBronzePrice() * charityFeePct * numTicketsIn)) / 100.0;
                totalFees = fees[0] + fees[1] + convenienceFee;
                break;

            case "silver":
                fees[0] = Math.round(100.0 * (eventIn.getSilverPrice() * serviceFeePct * numTicketsIn)) / 100.0;
                fees[1] = Math.round(100.0 * (eventIn.getSilverPrice() * charityFeePct * numTicketsIn)) / 100.0;
                totalFees = fees[0] + fees[1] + convenienceFee;
                break;

            case "gold":
                fees[0] = Math.round(100.0 * (eventIn.getGoldPrice() * serviceFeePct * numTicketsIn)) / 100.0;
                fees[1] = Math.round(100.0 * (eventIn.getGoldPrice() * charityFeePct * numTicketsIn)) / 100.0;
                totalFees = fees[0] + fees[1] + convenienceFee;
                break;

            case "vip":
                fees[0] = Math.round(100.0 * (eventIn.getVipPrice() * serviceFeePct * numTicketsIn)) / 100.0;
                fees[1] = Math.round(100.0 * (eventIn.getVipPrice() * charityFeePct * numTicketsIn)) / 100.0;
                totalFees = fees[0] + fees[1];
                break;
        }

        //Update TicketMiner fees collected
        ticketMinerIn.setConvenienceFeeProfit(convenienceFee);
        ticketMinerIn.setServiceFeeProfit(fees[0]);
        ticketMinerIn.setCharityFeeCollected(fees[1]);

        eventIn.setConvenienceFee(convenienceFee);
        eventIn.setServiceFee(fees[0]);
        eventIn.setCharityFee(fees[1]);
        
        return totalFees;
    }

    /**
     * @param numTicketsIn
     * @param ticketTierIn
     * @param eventIn
     * @return total price
     * @brief calculate total price for tickets purchased by customer
     * Written by: Christian Odom
     * Edited by: Jorge Sandoval
     */
    public static double calcSubtotal(int numTicketsIn, String ticketTierIn, Event eventIn, boolean membershipIn, double feesIn){
        double total = 0;

        switch(ticketTierIn){
            case "ga":
                total = numTicketsIn * eventIn.getGaPrice();
                break;

            case "bronze":
                total = numTicketsIn * eventIn.getBronzePrice();
                break;

            case "silver":
                total = numTicketsIn * eventIn.getSilverPrice();
                break;

            case "gold":
                total = numTicketsIn * eventIn.getGoldPrice();
                break;

            case "vip":
                total = numTicketsIn * eventIn.getVipPrice();
                break;
        }

        //If customer has membership, 10% discount
        if(membershipIn){
            return (Math.round( (0.9 * total) * 100.0) / 100.0) + feesIn;
        }
        else{
            return (Math.round( (total) * 100.0) / 100.0) + feesIn;
        }
    }

    /**
     *
     * @param subtotal
     * @return tax amount
     * Written by: Christian Odom
     */
    public static double calcTax(double subtotal){
        return Math.round( (subtotal * (8.25 / 100.0)) * 100.0)  / 100.0;
    }

    /**
     *
     * @param subtotal
     * @param tax
     * @return subtotal + tax
     * Written by: Christian Odom
     */
    public static double calcGrandTotal(double subtotal, double tax){
        return Math.round( ((subtotal + tax) * 100.0)) / 100.0;
    }
    
    /**
     * @param capacityIn
     * @param pctIn
     * @return remaining tickets
     * @brief generic method used to calculate remaining tickets
     * Written by: Christian Odom
     */
    public static double calcRemTickets(double capacityIn, double pctIn){
        return Math.round((pctIn / 100) * capacityIn);
    }

    /**
     * @param eventIn
     * @return total number of tickets sold
     * @brief calculate total number of seats sold
     * Written by: Christian Odom
     */
    public int calcTotalSeatsSold(Event eventIn){
        int totalTicketsSold = eventIn.getSoldVIP() + eventIn.getSoldGld() +
                                eventIn.getSoldGld() + eventIn.getSoldBrz() +
                                eventIn.getSoldGA();


        return Math.round(totalTicketsSold);
    }

    /**
     * @param eventIn
     * @param seatType
     * @return total number of seats sold
     * @brief returns total number of ticket type(VIP, Gold, etc.) sold.
     * Written by: Christian Odom
     */
    public double calcSeatsSoldByType(Event eventIn, String seatType) {
        int capacity = eventIn.getVenue().getCapacity();
        double soldSeats = 0;
    
        switch (seatType.toLowerCase()) {
            case "vip":
                soldSeats = Math.round((capacity * (eventIn.getVipPct() / 100))) - eventIn.getRemVIPTickets();
                break;
    
            case "gold":
                soldSeats = Math.round((capacity * (eventIn.getGoldPct() / 100))) - eventIn.getRemGoldTickets();
                break;
    
            case "silver":
                soldSeats = Math.round((capacity * (eventIn.getSilverPct() / 100))) - eventIn.getRemSilverTickets();
                break;
    
            case "bronze":
                soldSeats = Math.round((capacity * (eventIn.getBronzePct() / 100))) - eventIn.getRemBronzeTickets();
                break;
    
            case "ga":
                soldSeats = Math.round((capacity * (eventIn.getGaPct() / 100))) - eventIn.getRemGaTickets();
                break;
        }
    
        // Adjust for cancelled tickets
        soldSeats -= eventIn.getCancelledTickets(seatType);
        return Math.max(0, soldSeats);
    }
    
    

    /**
     * @param eventIn
     * @param seatType
     * @return total revenue based on ticket type
     * Written by: Christian Odom
     */
    public double calcRevenue(Event eventIn, String seatType){
        double capacity = eventIn.getVenue().getCapacity();

        //Calculate current total revenue based on ticket type
        switch(seatType){
            case "VIP":
                return Math.round((Math.round((capacity * (eventIn.getVipPct()/100)))- eventIn.getRemVIPTickets()) * eventIn.getVipPrice());

            case "Gold":
                return Math.round((Math.round((capacity * (eventIn.getGoldPct()/100))) - eventIn.getRemGoldTickets()) * eventIn.getGoldPrice());
            
            case "Silver":
                return Math.round((Math.round((capacity * (eventIn.getSilverPct()/100))) - eventIn.getRemSilverTickets()) * eventIn.getSilverPrice());
            
            case "Bronze":
                return Math.round((Math.round((capacity * (eventIn.getBronzePct()/100))) - eventIn.getRemBronzeTickets()) * eventIn.getBronzePrice());
            
            case "GA":
                return Math.round((Math.round((capacity * (eventIn.getGaPct()/100))) - eventIn.getRemGaTickets()) * eventIn.getGaPrice());
        }
        return 0;
    }

    /**
     * @param eventIn
     * @return total revenue for all ticket types
     * @brief calculates total revenue by summing VIP revenue, Gold revenue, etc.
     * Written by: Christian Odom
     */
    public double calcRevenue(Event eventIn){
        double total = 0;
        
        total += ((eventIn.getVenue().getCapacity() * (eventIn.getVipPct()/100)) - eventIn.getRemVIPTickets()) * eventIn.getVipPrice();
        total += ((eventIn.getVenue().getCapacity() * (eventIn.getGoldPct()/100)) - eventIn.getRemGoldTickets()) * eventIn.getGoldPrice();
        total += ((eventIn.getVenue().getCapacity() * (eventIn.getSilverPct()/100)) - eventIn.getRemSilverTickets()) * eventIn.getSilverPrice();
        total += ((eventIn.getVenue().getCapacity() * (eventIn.getBronzePct()/100)) - eventIn.getRemBronzeTickets()) * eventIn.getBronzePrice();
        total += ((eventIn.getVenue().getCapacity() * (eventIn.getGaPct()/100)) - eventIn.getRemGaTickets()) * eventIn.getGaPrice();

        return total;
    }

    /**
     * @param eventIn
     * @return expected revenue if all tickets are sold
     * @brief calculate total expected revenue if all tickets sold
     * Written by: Christian Odom
     */
    public double calcExpectedRevenue(Event eventIn){
        //ER = (total vip * vip cost) + (total gold * gold cost) + ...
        double totalExpected = 0;

        totalExpected += (eventIn.getVenue().getCapacity() * (eventIn.getVipPct()/100)) * eventIn.getVipPrice();
        totalExpected += (eventIn.getVenue().getCapacity() * (eventIn.getGoldPct()/100)) * eventIn.getGoldPrice();
        totalExpected += (eventIn.getVenue().getCapacity() * (eventIn.getSilverPct()/100)) * eventIn.getSilverPrice();
        totalExpected += (eventIn.getVenue().getCapacity() * (eventIn.getBronzePct()/100)) * eventIn.getBronzePrice();
        totalExpected += (eventIn.getVenue().getCapacity() * (eventIn.getGaPct()/100)) * eventIn.getGaPrice();

        return totalExpected;
    }

    /**
     * @param eventIn
     * @return actual revenue of all tickets sold
     * @brief calculate actual revenue based on current amount of tickets sold
     * Written by: Christian Odom
     */
    public double calcActualRevenue(Event eventIn){

        //AR = (total vip sold * vip cost) + (total gold sold * gold cost) + ...
        double actualRevenue = 0;
        int capacity = eventIn.getVenue().getCapacity();
        double subTotal = calcRevenue(eventIn);

        actualRevenue += ((capacity * (eventIn.getVipPct()/100)) - eventIn.getRemVIPTickets()) * eventIn.getVipPrice();
        actualRevenue += ((capacity * (eventIn.getGoldPct()/100)) - eventIn.getRemGoldTickets()) * eventIn.getGoldPrice();
        actualRevenue += ((capacity * (eventIn.getSilverPct()/100)) - eventIn.getRemSilverTickets()) * eventIn.getSilverPrice();
        actualRevenue += ((capacity * (eventIn.getBronzePct()/100)) - eventIn.getRemBronzeTickets()) * eventIn.getBronzePrice();
        actualRevenue += ((capacity * (eventIn.getGaPct()/100)) - eventIn.getRemGaTickets()) * eventIn.getGaPrice();
        subTotal += eventIn.getConvenienceFee();
        subTotal += eventIn.getServiceFee();
        subTotal += eventIn.getCharityFee();
  
        return actualRevenue;
    }

    /**
     *
     * @param subTotal
     * @return amount saved with membership
     * @brief calculate the total amount of money saved after membership applied
     * Written by: Jorge Sandoval
     * Editied by: Christian Odom
     */
    public static double calculateTotalSavedWithMembership(double subTotal, double grandTotal, Customer customer) {
        // Calculate the discount amount
        double discountAmount = grandTotal - subTotal;
        
        // Update the money saved property of the customer
        customer.setMoneySaved(discountAmount);
        
        // Return the discount amount
        return ( Math.round(((subTotal / 0.9) - subTotal) * 100.0) / 100.0 );
        
    }

}
