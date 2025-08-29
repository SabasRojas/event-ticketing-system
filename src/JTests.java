import org.junit.*;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;

public class JTests{

    @Test
    // Test Purchase and TicketMinerCompany objects.
    public void testZero(){
        // Use TicketMinerCompany to create instances of company and track fees.
        TicketMinerCompany testTicketMiner = new TicketMinerCompany();

        // Use Customer to create instances of customers.
        Customer testCustomer = new Customer(90, "Jorge", "Sandoval", 
        7500.63, 1, false, "sandovaljorge", "Fun!23");
        

        // Use Purchase to create an instance of a purchase.
        Purchase testPurchase = new Purchase("Concert", "Drake Live", "3/11/24", 
        "VIP", 1, 7148.51, 69, 7060.25);
        // $32.61- service fee.
        // $48.92- charity fee.

        // Test generation of ticket history:
        testTicketMiner.generateTickets(testCustomer, testPurchase);

        // Expected cost before fees with tax- 7060.25:
        assertEquals(7060.25, testPurchase.getPriceBeforeFees(), 0.01);
        
        // Expected cost after fees with tax- 7152.03
        assertEquals(7148.51, testPurchase.getPriceAfterFees(), 0.01);

        // Assert that the customer's ticket history is updated with the testPurchase
        assertEquals(1, testCustomer.getPurchaseHistory().size());

        // Assert the details of the generated ticket history match the purchase
        Purchase historyEntry = testCustomer.getPurchaseHistory().get(0);
        assertEquals("Concert", historyEntry.getEventType());
        assertEquals("Drake Live", historyEntry.getEventName());
        assertEquals("3/11/24", historyEntry.getEventDate());
        assertEquals("VIP", historyEntry.getTicketType());
        assertEquals(1, historyEntry.getNumberOfTickets());
        assertEquals(7148.51, historyEntry.getPriceAfterFees(), 0.01);
        assertEquals(69, historyEntry.getConfirmationNumber());
        assertEquals(7060.25, historyEntry.getPriceBeforeFees(), 0.01);
    }

    @Test
    // Test cancellation of ticket.
    public void testOne(){
        // Use Purchase to create an instance of a purchase.
        Purchase purchase = new Purchase("Sport", "UTEP Football 1", "2023-12-01",
                "VIP", 2, 138.11, 420, 63.0);
        // $0.63- Service fee.
        // $0.95- charity fee.

        // Store the original number of tickets.
        int testOriginalNumberTickets = purchase.getNumberOfTickets();

        // Cancel one ticket by reducing the number directly. (Simulate that customer cancels one ticket)
        purchase.setNumberOfTickets(testOriginalNumberTickets - 1);

        // Check if the number of tickets is reduced by 1 after cancellation.
        assertEquals(testOriginalNumberTickets - 1, purchase.getNumberOfTickets());
    }

    /*
     * Tests from PA4:
     */
    
    @Test
    public void testTwo(){
        // Use Customer to create instances of customers.
        Customer testCustomer = new Customer(90, "Jorge", "Sandoval", 
        5030.63, 0, false, "sandovaljorge", "Fun!23");

        Customer testCustomer2 = new Customer(77, "Christian", "Odom", 
        4909.24, 0, false, "odomchristian", "Fun!23");

        Customer testCustomer3 = new Customer(63, "Alan", "Holguin",
        3741.58, 0, true, "holguinalan", "Fun!23");

        Customer testCustomer4 = new Customer(87, "Sabas", "Rojas",
        4067.74, 0, false, "rojassabas", "Fun!23");

        //Create hashmap.
        HashMap<Integer, Customer> custMap = new HashMap<>();

        //Insert customer into hashmap.
        custMap.put(1, testCustomer);
        custMap.put(2, testCustomer2);
        custMap.put(3, testCustomer3);
        custMap.put(4, testCustomer4);

        // Test if customer is found using getCustomer method. 
        Customer foundCustomer = testCustomer.getCustomer(new String[]{"Jorge", "Sandoval"}, custMap);
        Customer foundCustomer2 = testCustomer2.getCustomer(new String[]{"Christian", "Odom"}, custMap);
        Customer foundCustomer3 = testCustomer3.getCustomer(new String[]{"Alan", "Holguin"}, custMap);
        Customer foundCustomer4 = testCustomer4.getCustomer(new String[]{"Sabas", "Rojas"}, custMap);

        /*
         * Test different Customer functions.
         * All tests pass since expected matches actual value.
         */
        assertEquals("Jorge", foundCustomer.getFirstName());
        assertEquals("Sandoval", foundCustomer.getLastName());
        assertEquals("odomchristian", foundCustomer2.getUserName());
        assertEquals(4909.24, foundCustomer2.getMoneyAvail(), 0.001);
        assertEquals(63, foundCustomer3.getCustID());
        assertEquals(false, foundCustomer4.getMinerMember());
    }

    @Test
    public void testThree(){
        // Use EventFactory to create instances of events since Event class is abstract.
        Event testEvent = EventFactory.creatEvent("Concert");
        testEvent.setVipPrice(100.00);

        Event testEvent2 = EventFactory.creatEvent("Sport");
        testEvent2.setHasFireworks(true);

        Event testEvent3 = EventFactory.creatEvent("Festival");
        testEvent3.setGoldPrice(500.00);
        testEvent3.setEventDate("12/13/2024");
        testEvent3.setEventTime("9:30 PM");

        /*  Create variables that use the Calculator class to calculate testEvents prices and tickets purchased.
            calcsubTotal modified from previous testing to consider "feesIn" for method signature.
        */
        double result = Calculator.calcSubtotal(3, "vip", testEvent, false, 10.0);
        double result2 = Calculator.calcSubtotal(5, "gold", testEvent3, true, 10.0);

        /*
         * Test different Event functions.
         * Explanation for first assert passing: amount expected is correct. (3 tickets for VIP tier * 100 for cost = 300.0 + 10.0 = 310.0).
         * Explanation for third assert passing: amount expected is correct. (5 tickets for GOLD tier * 500 for cost = 2250.0 + 10.0 = 2260.0)
         */ 
        assertEquals(310.0, result, 0.001);
        assertEquals(true, testEvent2.hasFireworks());
        assertEquals(2260.0, result2, 0.001);
        assertEquals("12/13/2024", testEvent3.getEventDate());
        assertEquals("9:30 PM", testEvent3.getEventTime());
    }

    @Test
    public void testF(){
        Venue testVenue = VenueFactory.createVenue("Stadium");
        testVenue.setName("AT&T Stadium");
        testVenue.setCapacity(20000);
        testVenue.setCostToRent(100000);
        
        /* 
         * Test different Venue functions.
        */
        assertEquals("AT&T Stadium", testVenue.getName());
        assertEquals(20000, testVenue.getCapacity());
        assertEquals(100000, testVenue.getCostToRent(), 0.001);
    }

}