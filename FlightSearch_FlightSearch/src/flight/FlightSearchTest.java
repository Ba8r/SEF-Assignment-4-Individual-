package flight;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FlightSearchTest {

    // We will format dates like day/month/year (e.g., 20/10/2025)
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    
    // Wrong format on purpose (MM/dd/yyyy) to test failures
    private static DateTimeFormatter wrongDateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    
    // Dashed format on purpose (dd-MM-yyyy) to test failures
    private static DateTimeFormatter dashedDateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    
    // Today’s date string in the correct format
    private static String currentDate = LocalDate.now().format(dateFormat);
    
    // Tomorrow’s date string in the correct format
    private static String returnDate = LocalDate.now().plusDays(1).format(dateFormat);

    @Test
    void validInputsOne() {
        // Valid case: economy allows emergency row, same-day return is okay, 1 adult
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch(currentDate, "mel", true, currentDate, "pvg", "economy", 1, 0, 0));
    }

    @Test
    void validInputsTwo() {
        // Valid case: premium economy, return is tomorrow, 1 adult
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "lax", "premium economy", 1, 0, 0));
    }

    @Test
    void validInputsThree() {
        // Valid case: 2 adults and 4 children (max 2 kids per adult), economy
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch(currentDate, "mel", false, returnDate, "cdg", "economy", 2, 4, 0));
    }

    @Test
    void validInputsFour() {
        // Valid leap year case: 29/02/2028 is valid, return is next day
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch("29/02/2028", "del", false, "01/03/2028", "doh", "economy", 1, 0, 0));
    }

    @Test
    void zeroPassengers() {
        // Should fail: total passengers = 0 (not allowed)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 0, 0, 0));
    }

    @Test
    void moreThanNinePassengers() {
        // Should fail: total passengers = 10 (more than 9)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 7, 2, 1));
    }

    @Test
    void negativePassengers() {
        // Should fail: negative adult count
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", -1, 0, 0));
    }

    @Test
    void childWithEmergencySeat() {
        // Should fail: child + emergency row is not allowed
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "economy", 1, 1, 0));
    }

    @Test
    void childWithFirstClass() {
        // Should fail: children not allowed in first class
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "first", 1, 1, 0));
    }

    @Test
    void infantWithEmergencySeat() {
        // Should fail: infant + emergency row is not allowed
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "economy", 1, 0, 1));
    }

    @Test
    void infantWithBusinessClass() {
        // Should fail: infants not allowed in business class
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "business", 1, 0, 1));
    }

    @Test
    void oneChildNoAdults() {
        // Should fail: child present but no adult booked
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 0, 1, 0));
    }

    @Test
    void threeChildrenOneAdult() {
        // Should fail: more than 2 children per 1 adult (here it’s 3)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 1, 3, 0));
    }

    @Test
    void oneInfantNoAdults() {
        // Should fail: infant present but no adult booked
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 0, 0, 1));
    }

    @Test
    void departureDateInPast() {
        // Should fail: departure is yesterday (in the past)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(LocalDate.now().minusDays(1).format(dateFormat), "syd", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void wrongDepartureDateFormat() {
        // Should fail: departure date has wrong format (MM/dd/yyyy)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(LocalDate.now().format(wrongDateFormat), "syd", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void wrongReturnDateFormat() {
        // Should fail: return date has wrong format (MM/dd/yyyy)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, LocalDate.now().plusDays(1).format(wrongDateFormat), "mel", "economy", 1, 0, 0));
    }

    @Test
    void dashedDepartureDate() {
        // Should fail: departure date uses dashes (dd-MM-yyyy), not allowed
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(LocalDate.now().format(dashedDateFormat), "syd", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void dashedDestinationDate() {
        // Should fail: return date uses dashes (dd-MM-yyyy), not allowed
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, LocalDate.now().plusDays(1).format(dashedDateFormat), "mel", "economy", 1, 0, 0));
    }

    @Test
    void returnDateBeforeDeparture() {
        // Should fail: return is before departure (yesterday)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, LocalDate.now().minusDays(1).format(dateFormat), "mel", "economy", 1, 0, 0));
    }

    @Test
    void seatClassDoesntExist() {
        // Should fail: seating class is not one of the allowed ones
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "nonExistent", 1, 0, 0));
    }

    @Test
    void firstClassEmergencySeat() {
        // Should fail: emergency row with first class (only economy can have emergency row)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "first", 1, 0, 0));
    }

    @Test
    void businessClassEmergencySeat() {
        // Should fail: emergency row with business class (only economy can have emergency row)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "business", 1, 0, 0));
    }

    @Test
    void premiumEconomyClassEmergencySeat() {
        // Should fail: emergency row with premium economy (only economy can have emergency row)
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "premium economy", 1, 0, 0));
    }

    @Test
    void wrongDepartureAirportCode() {
        // Should fail: departure airport code is not in the allowed list
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "wrong", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void wrongDestinationAirportCode() {
        // Should fail: destination airport code is not in the allowed list
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "wrong", "economy", 1, 0, 0));
    }

    @Test
    void sameDepartureAndDestinationAirportCode() {
        // Should fail: departure and destination cannot be the same
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "syd", "economy", 1, 0, 0));
    }

}
