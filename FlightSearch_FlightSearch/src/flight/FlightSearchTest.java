package flight;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FlightSearchTest {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static DateTimeFormatter wrongDateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    private static DateTimeFormatter dashedDateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private static String currentDate = LocalDate.now().format(dateFormat);
    private static String returnDate = LocalDate.now().plusDays(1).format(dateFormat);

    @Test
    void validInputsOne() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch(currentDate, "mel", true, currentDate, "pvg", "economy", 1, 0, 0));
    }

    @Test
    void validInputsTwo() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "lax", "premium economy", 1, 0, 0));
    }

    @Test
    void validInputsThree() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch(currentDate, "mel", false, returnDate, "cdg", "economy", 2, 4, 0));
    }

    @Test
    void validInputsFour() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(true, flightSearch.runFlightSearch("29/02/2028", "del", false, "01/03/2028", "doh", "economy", 1, 0, 0));
    }

    @Test
    void zeroPassengers() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 0, 0, 0));
    }

    @Test
    void moreThanNinePassengers() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 7, 2, 1));
    }

    @Test
    void negativePassengers() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", -1, 0, 0));
    }

    @Test
    void childWithEmergencySeat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "economy", 1, 1, 0));
    }

    @Test
    void childWithFirstClass() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "first", 1, 1, 0));
    }

    @Test
    void infantWithEmergencySeat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "economy", 1, 0, 1));
    }

    @Test
    void infantWithBusinessClass() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "business", 1, 0, 1));
    }

    @Test
    void oneChildNoAdults() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 0, 1, 0));
    }

    @Test
    void threeChildrenOneAdult() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 1, 3, 0));
    }

    @Test
    void oneInfantNoAdults() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "economy", 0, 0, 1));
    }

    @Test
    void departureDateInPast() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(LocalDate.now().minusDays(1).format(dateFormat), "syd", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void wrongDepartureDateFormat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(LocalDate.now().format(wrongDateFormat), "syd", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void wrongReturnDateFormat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, LocalDate.now().plusDays(1).format(wrongDateFormat), "mel", "economy", 1, 0, 0));
    }

    @Test
    void dashedDepartureDate() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(LocalDate.now().format(dashedDateFormat), "syd", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void dashedDestinationDate() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, LocalDate.now().plusDays(1).format(dashedDateFormat), "mel", "economy", 1, 0, 0));
    }

    @Test
    void returnDateBeforeDeparture() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, LocalDate.now().minusDays(1).format(dateFormat), "mel", "economy", 1, 0, 0));
    }

    @Test
    void seatClassDoesntExist() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "mel", "nonExistent", 1, 0, 0));
    }

    @Test
    void firstClassEmergencySeat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "first", 1, 0, 0));
    }

    @Test
    void businessClassEmergencySeat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "business", 1, 0, 0));
    }

    @Test
    void premiumEconomyClassEmergencySeat() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", true, returnDate, "mel", "premium economy", 1, 0, 0));
    }

    @Test
    void wrongDepartureAirportCode() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "wrong", false, returnDate, "mel", "economy", 1, 0, 0));
    }

    @Test
    void wrongDestinationAirportCode() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "wrong", "economy", 1, 0, 0));
    }

    @Test
    void sameDepartureAndDestinationAirportCode() {
        FlightSearch flightSearch = new FlightSearch();
        assertEquals(false, flightSearch.runFlightSearch(currentDate, "syd", false, returnDate, "syd", "economy", 1, 0, 0));
    }

}