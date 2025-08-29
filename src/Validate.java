/**
 * Validate: Used to validate customer login credentials
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

import java.util.HashMap;

class Validate{

    public static boolean isValidCustomer(HashMap<Integer, Customer> customerMapIn, String name[], Log log){
    

        //Iterate through customers until matching first and last name found
        for (Customer cust : customerMapIn.values()) {

            //If matching first and last name, return true
            if (cust.getFirstName().equalsIgnoreCase(name[0]) && cust.getLastName().equalsIgnoreCase(name[1])) {
                
                //Log customer found
                log.logActivity("Login successful for customer: "
                                + cust.getFirstName() + " " + cust.getLastName());
                
                return true;
            }
        }
        log.logActivity("Attempted login. Customer not found.");
        return false;
    }

    /**
     * @param credIn
     * @param custIn
     * @return boolean
     * @brief iterate through customer hashmap to check if login credentials match customer
     * Written by: Christian Odom
     */
    public static boolean isValidLogin(String[] credIn, Customer custIn){
        try{
            if(custIn.getUserName().equalsIgnoreCase(credIn[0]) && custIn.getPassword().equals(credIn[1])){
                return true;
            }
            else{
                return false;
            }
        }
        catch(NullPointerException e){
            e.getMessage();
        }
        return false;
    }

    public static boolean validateCustomerFunds(Customer cust, double totalCost){
        
        if (cust.getMoneyAvail() >= totalCost) {
            return true;
        }

        return false;
    }

    /**
     * @param input
     * @return Returns true if the input string represents a valid date, otherwise returns false
     * Written by: Christian Odom and Alan Holguin
     */
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

    /**
     * @param time
     * @return Returns true if the input string time matches the valid time format, otherwise it returns false
     * Written by: Christian Odom and Alan Holguin
     */
    public static boolean isValidTimeFormat(String time) {
        return time.matches("^(1[0-2]|0[1-9]):[0-5][0-9] (AM|PM)$");
    }
}