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

      // Condition 1: Total passengers must be between 1 and 9
      if((adultPassengerCount + childPassengerCount + infantPassengerCount) < 1 || (adultPassengerCount + childPassengerCount + infantPassengerCount) > 9) {
    	  valid = false;
      }
      
      // Condition 2: Children can't be in emergency rows or first class
      if((childPassengerCount > 0) && ((emergencyRowSeating == true) || (seatingClass.equals("first")))) {
    	  valid = false;
      }
      
      // Condition 3: Infants can't be in emergency rows or business class
      if((infantPassengerCount > 0) && ((emergencyRowSeating == true) || (seatingClass.equals("business")))) {
    	  valid = false;
      }
      
      // Condition 4 (part 1): If there are children, there must be at least one adult
      if((childPassengerCount > 0) && (adultPassengerCount <= 0)) {
    	  valid = false;
      }
      // Condition 4 (part 2): Max two children per adult
      else if(childPassengerCount > (2 * adultPassengerCount)) {
		  valid = false;
	  }
      
      // Condition 5: One infant per adult (so infants can't exceed adults)
      if((infantPassengerCount > adultPassengerCount)) {
    	  valid = false;
      }
      
      // Prepare strict date parsing (DD/MM/YYYY) for Conditions 6–8
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
      currentDate.format(dateFormat); // note: this line formats today but doesn’t store it (not harmful)

      LocalDate formattedDepartureDate = null;

      // Condition 7 (departure): Strict format and a real calendar date
      try {
          formattedDepartureDate = LocalDate.parse(departureDate, dateFormat);
      }
      catch (Exception e) {
          valid = false;
      }
      
      // Condition 6: Departure cannot be in the past
      if(formattedDepartureDate != null && formattedDepartureDate.isBefore(currentDate)) {
    	  valid = false;
      }

      // Condition 7 (both dates): Strict format and real calendar dates
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

      // Condition 8: Return date cannot be before departure date (same day is okay)
      if (returnFormatTest == null || departureFormatTest == null) {
          valid = false;
      }
      else if(departureFormatTest.isAfter(returnFormatTest)) {
          valid = false;
      }

      // Condition 9: Seating class must be one of the allowed values
      if(!(seatingClass.equals("economy") || seatingClass.equals("premium economy") || seatingClass.equals("business") || seatingClass.equals("first"))) {
          valid = false;
      }

      // Condition 10: Only economy can have emergency row seating
      if ((seatingClass.equals("premium economy") || seatingClass.equals("business") || seatingClass.equals("first")) && (emergencyRowSeating == true)) {
          valid = false;
      }

      // Condition 11 (part 1): Departure airport must be from the allowed list
      if (!(departureAirportCode.equals("syd") || departureAirportCode.equals("mel") || departureAirportCode.equals("lax") || departureAirportCode.equals("cdg") || departureAirportCode.equals("del") ||  departureAirportCode.equals("pvg") ||  departureAirportCode.equals("doh"))) {
          valid = false;
      }

      // Condition 11 (part 2): Destination airport must be from the allowed list
       if (!(destinationAirportCode.equals("syd") || destinationAirportCode.equals("mel") || destinationAirportCode.equals("lax") || destinationAirportCode.equals("cdg") || destinationAirportCode.equals("del") ||  destinationAirportCode.equals("pvg") ||  destinationAirportCode.equals("doh"))) {
           valid = false;
       }

       // Condition 11 (part 3): Departure and destination can't be the same
       if (departureAirportCode.equals(destinationAirportCode)) {
           valid = false;
       }
       
       // If all conditions are met, the class variables are assigned
       if(valid == true) {
           this.departureDate = departureDate;
           this.departureAirportCode = departureAirportCode;
           this.emergencyRowSeating = emergencyRowSeating;
           this.returnDate = returnDate;
           this.destinationAirportCode = destinationAirportCode;
           this.seatingClass = seatingClass;
           this.adultPassengerCount = adultPassengerCount;
           this.childPassengerCount = childPassengerCount;
           this.infantPassengerCount = infantPassengerCount;
       }
      
      // NOTE for the assignment (important for marks):
      // The task says: if EVERYTHING is valid, you should set the class attributes to these values and return true.
      // If anything is invalid, you should NOT change the attributes and return false.
      // At the moment, this method only returns true/false and never sets the attributes.
      // To meet the requirement, add an "if (valid) { this.departureDate = departureDate; ... }" block before returning.
      
      return valid;
   }
}
