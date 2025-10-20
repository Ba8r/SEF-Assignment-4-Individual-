package flight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class FlightSearch {
   private String  departureDate;
   private String  departureAirportCode;
   private boolean emergencyRowSeating;
   private String  returnDate;
   private String  destinationAirportCode; 
   private String  seatingClass;
   private int     adultPassengerCount;
   private int     childPassengerCount;
   private int     infantPassengerCount;

   public boolean runFlightSearch(String departureDate,    String departureAirportCode,   boolean emergencyRowSeating, 
                                  String returnDate,       String destinationAirportCode, String seatingClass, 
                                  int adultPassengerCount, int childPassengerCount,       int infantPassengerCount) {
      boolean valid = true;

      //TODO: Validate all the provided parameters.
      //if the search parameters meets the given conditions, 
      //   the function should initialise all the class attributes and return true.
      //else 
      //   the function should return false
      
      if((adultPassengerCount + childPassengerCount + infantPassengerCount) < 1 || (adultPassengerCount + childPassengerCount + infantPassengerCount) > 9) {
    	  valid = false;
      }
      
      if((childPassengerCount > 0) && ((emergencyRowSeating == true) || (seatingClass.equals("first")))) {
    	  valid = false;
      }
      
      if((infantPassengerCount > 0) && ((emergencyRowSeating == true) || (seatingClass.equals("business")))) {
    	  valid = false;
      }
      
      if((childPassengerCount > 0) && (adultPassengerCount <= 0)) {
    	  valid = false;
      }
      
      else if(childPassengerCount > (2 * adultPassengerCount)) {
		  valid = false;
	  }
      
      if((infantPassengerCount > adultPassengerCount)) {
    	  valid = false;
      }
      
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
      currentDate.format(dateFormat);

      LocalDate formattedDepartureDate = null;

      try {
          formattedDepartureDate = LocalDate.parse(departureDate, dateFormat);
      }
      catch (Exception e) {
          valid = false;
      }
      
      if(formattedDepartureDate != null && formattedDepartureDate.isBefore(currentDate)) {
    	  valid = false;
      }

      LocalDate departureFormatTest = null;
      LocalDate returnFormatTest = null;

      try {
          departureFormatTest = LocalDate.parse(departureDate, dateFormat);
      }
      catch(Exception e) {
          valid = false;
      }

      try {
          returnFormatTest = LocalDate.parse(returnDate, dateFormat);
      }
      catch(Exception e) {
          valid = false;
      }

      if (departureFormatTest == null || returnFormatTest == null) {
          valid = false;
      }

      if (returnFormatTest == null || departureFormatTest == null) {
          valid = false;
      }

      else if(departureFormatTest.isAfter(returnFormatTest)) {
          valid = false;
      }

      if(!(seatingClass.equals("economy") || seatingClass.equals("premium economy") || seatingClass.equals("business") || seatingClass.equals("first"))) {
          valid = false;
      }

      if ((seatingClass.equals("premium economy") || seatingClass.equals("business") || seatingClass.equals("first")) && (emergencyRowSeating == true)) {
          valid = false;
      }

      if (!(departureAirportCode.equals("syd") || departureAirportCode.equals("mel") || departureAirportCode.equals("lax") || departureAirportCode.equals("cdg") || departureAirportCode.equals("del") ||  departureAirportCode.equals("pvg") ||  departureAirportCode.equals("doh"))) {
          valid = false;
      }

       if (!(destinationAirportCode.equals("syd") || destinationAirportCode.equals("mel") || destinationAirportCode.equals("lax") || destinationAirportCode.equals("cdg") || destinationAirportCode.equals("del") ||  destinationAirportCode.equals("pvg") ||  destinationAirportCode.equals("doh"))) {
           valid = false;
       }

       if (departureAirportCode.equals(destinationAirportCode)) {
           valid = false;
       }
      
      return valid;
   }
}