/**
 * AttributeFactory: Design pattern used to aid in dynamically reading event and customer files.
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */

public class AttributeFactory {
    
    //Default constructor
    public AttributeFactory(){}

    /**
     *
     * @param custIn
     * @param attributeIn
     * @param valueIn
     * @return customer with updated attribute
     * @brief set customer attributes
     * Written by: Christian Odom
     */
    public static Customer addCustomerAttribute(Customer custIn, String attributeIn, String valueIn){

        switch(attributeIn){
            
            case "ID":
                custIn.setCustID(Integer.valueOf(valueIn));
                break;

            case "First Name":
                custIn.setFirstName(valueIn);
                break;

            case "Last Name":
                custIn.setLastName(valueIn);
                break;
            
            case "Money Available":
                custIn.setMoneyAvail(Double.valueOf(valueIn));
                break;

            case "Tickets Purchased":
                custIn.setTicketsPurchased(Integer.valueOf(valueIn));
                break;
            
            case "TicketMiner Membership":
                custIn.setMinerMember(Boolean.valueOf(valueIn));
                break;
            
            case "Username":
                custIn.setUserName(valueIn);
                break;
            
            case "Password":
                custIn.setPassword(valueIn);
                break;
            
            default:
                break;
        }
        return custIn;
    }

    /**
     * @param eventIn
     * @param attribute
     * @param valueIn
     * @return event with updated attribute
     * @brief set event attributes
     * Written by: Christian Odom
     */
    public static Event addEventAttribute(Event eventIn, String attribute, String valueIn){

        switch(attribute){

            case "Gold Price":
                eventIn.setGoldPrice(Double.valueOf(valueIn));
                break;

            case "Name":
                eventIn.setEventName(valueIn);
                break;

            case "VIP Price":
                eventIn.setVipPrice(Double.valueOf(valueIn));
                break;

            case "Event Type":
                eventIn.setEventType(valueIn);
                break;

            case "Venue Type":
                eventIn.setVenueType(valueIn);
                break;

            case "Bronze Pct":
                eventIn.setBronzePct(Double.valueOf(valueIn));
                break;
            
            case "Event ID":
                eventIn.setEventID(Integer.valueOf(valueIn));
                break;

            case "Time":
                eventIn.setEventTime(valueIn);
                break;

            case "General Admission Pct":
                eventIn.setGaPct(Double.valueOf(valueIn));
                break;

            case "Silver Price":
                eventIn.setSilverPrice(Double.valueOf(valueIn));
                break;
            
            case "Date":
                eventIn.setEventDate(valueIn);
                break;

            case "Bronze Price":
                eventIn.setBronzePrice(Double.valueOf(valueIn));
                break;

            case "Fireworks Cost":
                //If Fireworks Cost column is empty, set to 0
                if(valueIn.equals("")){
                    eventIn.setFireworksCost(0);
                }
                else{
                    eventIn.setFireworksCost(Double.valueOf(valueIn));
                }
                break;

            case "General Admission Price":
                eventIn.setGaPrice(Double.valueOf(valueIn));
                break;

            case "Pct Seats Unavailable":
                eventIn.setUnavailablePct(Double.valueOf(valueIn));
                break;

            case "Capacity":
                eventIn.getVenue().setCapacity(Integer.valueOf(valueIn));
                break;

            case "Reserved Extra Pct":
                eventIn.setResPct(Double.valueOf(valueIn));
                break;
            
            case "Cost":
                eventIn.getVenue().setCostToRent(Integer.valueOf(valueIn));
                break;

            case "VIP Pct":
                eventIn.setVipPct(Double.valueOf(valueIn));
                break;

            case "Venue Name":
                eventIn.getVenue().setName(valueIn);
                break;

            case "Gold Pct":
                eventIn.setGoldPct(Double.valueOf(valueIn));
                break;

            case "Fireworks Planned":
                //If 'Fireworks Planned' column is empty or 'no', set to false
                if(valueIn.equalsIgnoreCase("no") | valueIn.equalsIgnoreCase("")){
                    eventIn.setHasFireworks(false);
                }
                else{
                    eventIn.setHasFireworks(true);
                }
                break;
            
            case "Silver Pct":
                eventIn.setSilverPct(Double.valueOf(valueIn));
                break;
            
            default:
                break;
        }
        return eventIn;
    }
}
