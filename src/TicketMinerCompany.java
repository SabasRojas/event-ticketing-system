/**
 * TicketMinerCompany: Class that is used to keep track of fees collected from transactions.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;

public class TicketMinerCompany {
    
    private double serviceFeeProfit;
    private double convenienceFeeProfit;
    private double charityFeeCollected;

    TicketMinerCompany(){
        
        this.serviceFeeProfit = 0;
        this.convenienceFeeProfit = 0;
        this.charityFeeCollected = 0;
    }

    public double getServiceFeeProfit() {
        return this.serviceFeeProfit;
    }

    public void setServiceFeeProfit(double serviceFeeProfitIn) {
        this.serviceFeeProfit += serviceFeeProfitIn;
    }

    public double getConvenienceFeeProfit() {
        return this.convenienceFeeProfit;
    }

    public void setConvenienceFeeProfit(double convenienceFeeProfitIn) {
        this.convenienceFeeProfit += convenienceFeeProfitIn;
    }

    public double getCharityFeeCollected() {
        return this.charityFeeCollected;
    }

    public void setCharityFeeCollected(double charityFeeCollectedIn) {
        this.charityFeeCollected += charityFeeCollectedIn;
    }

    public void generateTickets(Customer customerIn, Purchase purchaseIn){
        try {

            String firstName = customerIn.getFirstName();
            String lastName = customerIn.getLastName();
            String fileName = firstName + "-" + lastName + "-" + "ticket.txt";
            ArrayList<Purchase> purchaseHistory = customerIn.getPurchaseHistory();
            String outputFilePath = "";

            //Relative output file path depends on OS type
            String osType = System.getProperty("os.name");

            //Replace 'Mac OS X' if needed
            if (osType.equalsIgnoreCase("Mac OS X")){
                outputFilePath = "pa5-jorge_christian_alan_sabas/tickets/" + fileName;
            }
            else{
                outputFilePath = "tickets/" + fileName;
            }
            File file = new File(outputFilePath);
            Formatter fm = new Formatter(file);

            String dashedLine = "-----------------------------------------------------------------------------------------------------%n";

            String eventName = purchaseIn.getEventName();
            String eventDate = purchaseIn.getEventDate();
            String ticketType = purchaseIn.getTicketType();
            String total = String.valueOf(purchaseIn.getPriceAfterFees());
            String confirmation = String.valueOf(purchaseIn.getConfirmationNumber());
            int numTickets = purchaseIn.getNumberOfTickets();
                
            for(int i = 0; i < numTickets; i++){

                fm.format(dashedLine);
                fm.format("|%-22s ______ _       __         __   __  ___ _%-36s|%n", " ", " ");
                fm.format("|%-22s/_  __/(_)____ / /__ ___  / /_ /  |/  /(_)___  ___  ____%-21s|%n", " ", " ");
                fm.format("|%-22s / /  / // __//  '_// -_)/ __// /|_/ // // _ \\/ -_)/ __/%-21s|%n", " ", " ");
                fm.format("|%-22s/_/  /_/ \\__//_/\\_\\ \\__/ \\__//_/  /_//_//_//_/\\__//_/%-24s|%n", " ", " ");
                fm.format(dashedLine);
                fm.format("|%-45sADMINT ONE%-44s|%n", " ", " ");
                fm.format(dashedLine);
                fm.format("| %-25s  %-15s  %-15s  %-20s  $ %-12s |%n", eventName, eventDate,
                                ticketType, confirmation, total);
                fm.format(dashedLine);
                fm.format("%n");
                
                /* Add purchase to customer's transaction history only if it doesn't already exist.
                *  needed for J-Unit testZero
                */
                if (!customerIn.getPurchaseHistory().contains(purchaseIn)) {
                    customerIn.setPurchaseHistory(purchaseIn);
                }
            }
        
            fm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}