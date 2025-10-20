package flight;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	
    	FlightSearch flightSearch = new FlightSearch();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    	Scanner scanner = new Scanner(System.in);
    	LocalDate formattedDepartureDate = null;
    	LocalDate formattedReturnDate = null;
    	boolean valid = false;
    	LocalDate currentDate = LocalDate.now();
    	
    	System.out.println("Welcome to the flights search sytem.");
    	System.out.println("Please enter a valid present or future departure date in (dd/MM/yyyy) format: ");
    	String departureDate = scanner.nextLine();
    	
    	while(!valid) {
	    	try {
	            formattedDepartureDate = LocalDate.parse(departureDate, dateFormat);
	            if (formattedDepartureDate.isEqual(currentDate) || formattedDepartureDate.isAfter(currentDate)) {
	            	valid = true;
	            }
	            
	            else {
	            	valid = false;
	            	System.out.println("Invalid date, please enter a valid present or future departure date in (dd/MM/yyyy) format: ");
		            departureDate = scanner.nextLine();
	            }
	            valid = true;
	        }
	        catch (Exception e) {
	            valid = false;
	            System.out.println("Invalid date, please enter a valid departure date in (dd/MM/yyyy) format: ");
	            departureDate = scanner.nextLine();
	        }
    	}
    	
    	valid = false;
    	
    	System.out.println("Please enter a valid departure airport code (syd, mel, lax, cdg, del, pvg, doh): ");
    	String departureAirportCode = scanner.nextLine();
    	
    	while(!valid) {
	    	if (departureAirportCode.equals("syd") || departureAirportCode.equals("mel") || departureAirportCode.equals("lax") || departureAirportCode.equals("cdg") || departureAirportCode.equals("del") || departureAirportCode.equals("pvg") || departureAirportCode.equals("doh")) {
	    		valid = true;
	    	}
	    	else {
	    		System.out.println("Invalid airport code, please enter a valid airport code (syd, mel, lax, cdg, del, pvg, doh): ");
	            departureAirportCode = scanner.nextLine();
	    	}
    	}
    	
    	valid = false;
    	
    	System.out.println("Please enter a valid return date (must be on or after departure date) in (dd/MM/yyyy) format: ");
    	String returnDate = scanner.nextLine();
    	
    	while(!valid) {
	    	try {
	            formattedReturnDate = LocalDate.parse(returnDate, dateFormat);
	            if (formattedReturnDate.isAfter(currentDate) || formattedReturnDate.isEqual(currentDate)) {
	            	valid = true;
	            }
	            
	            else {
	            	valid = false;
	            	System.out.println("Invalid date, please enter a valid return date (must be on or after departure date) in (dd/MM/yyyy) format: ");
		            returnDate = scanner.nextLine();
	            }
	            valid = true;
	        }
	        catch (Exception e) {
	            valid = false;
	            System.out.println("Invalid date, please enter a valid return date (must be on or after departure date) in (dd/MM/yyyy) format: ");
	            returnDate = scanner.nextLine();
	        }
    	}
    	
    	valid = false;
    	
    	System.out.println("Please enter a valid departure airport code (syd, mel, lax, cdg, del, pvg, doh): ");
    	String destinationAirportCode = scanner.nextLine();
    	
    	while(!valid) {
	    	if (destinationAirportCode.equals("syd") || destinationAirportCode.equals("mel") || destinationAirportCode.equals("lax") || destinationAirportCode.equals("cdg") || destinationAirportCode.equals("del") || destinationAirportCode.equals("pvg") || destinationAirportCode.equals("doh")) {
	    		if (destinationAirportCode != departureAirportCode) {
	    			valid = true;
	    		}
	    		
	    		else {
	    			System.out.println("Invalid airport code, destination and departure airport codes can't be the same.");
	    			System.out.println("Please enter a valid airport code (syd, mel, lax, cdg, del, pvg, doh): ");
		            destinationAirportCode = scanner.nextLine();
	    		}
	    		
	    	}
	    	else {
	    		System.out.println("Invalid airport code, please enter a valid airport code (syd, mel, lax, cdg, del, pvg, doh): ");
	            destinationAirportCode = scanner.nextLine();
	    	}
    	}
    	
    	valid = false;
    	
    	System.out.println("Please enter a valid seating class (economy, premium economy, business, first): ");
    	String seatingClass = scanner.nextLine();
    	
    	while(!valid) {
	    	if (seatingClass.equals("economy") || seatingClass.equals("premium economy") || seatingClass.equals("business") || seatingClass.equals("first")) {
	    		valid = true;
	    	}
	    	else {
	    		System.out.println("Invalid seating class, please enter a valid airport code (economy, premium economy, business, first): ");
	            seatingClass = scanner.nextLine();
	    	}
    	}
    	
    	valid = false;
    	
    	int intAdultsCount = 0;
    	System.out.println("Please enter the adults count (at least 1) (one integer only): ");
    	String adultsCount = scanner.next();
    	
    	while(!valid) {
    		
    		for (int i = 0; i < adultsCount.length(); ++i) {
                if (!Character.isDigit(adultsCount.charAt(i))) {
                	System.out.println("Invalid integer, please enter a valid adults count (at least 1) (one integer only): ");
                	adultsCount = scanner.next();
                	break;
                }
                else {
                	continue;
                }
            }
    		
    		try {
    		   intAdultsCount = Integer.parseInt(adultsCount);
    		}
    		catch (NumberFormatException e) {
    		   intAdultsCount = -1;
    		}
    		
    		if (intAdultsCount <= 0) {
    			System.out.println("Invalid integer, please enter a valid adults count (at least 1) (one integer only): ");
	            adultsCount = scanner.next();
    		}
    		
    		else {
    			valid = true;
    		}
    		
        }
    	
    	valid = false;
    	
		int intChildrenCount = 0;
    	if (intAdultsCount > 0) {
	    	System.out.println("Please enter the children count (one integer only): ");
	    	String childrenCount = scanner.next();
	    	
	    	while(!valid) {
	    		
	    		for (int i = 0; i < childrenCount.length(); ++i) {
	                if (!Character.isDigit(childrenCount.charAt(i))) {
	                	System.out.println("Invalid integer, please enter a valid children count (one integer only): ");
	                	childrenCount = scanner.next();
	                	break;
	                }
	                else {
	                	continue;
	                }
	            }
	    		
	    		try {
	    		   intChildrenCount = Integer.parseInt(childrenCount);
	    		}
	    		catch (NumberFormatException e) {
	    		   intChildrenCount = -1;
	    		}
	    		
	    		if (intChildrenCount < 0) {
	    			System.out.println("Invalid integer, please enter a valid children count (one integer only): ");
		            childrenCount = scanner.next();
	    		}
	    		
	    		else if (intChildrenCount > (2*intAdultsCount)) {
	    			System.out.println("Not enough adults for the chosen children amount, please keep it max 2 children per adult: ");
		            childrenCount = scanner.next();
	    		}
	    		
	    		else {
	    			valid = true;
	    		}
	    		
	        }
    	}
    	
    	valid = false;
    	
    	int intInfantCount = 0;
    	if (intAdultsCount > 0) {
	    	System.out.println("Please enter the infant count (one integer only): ");
	    	String infantCount = scanner.next();
	    	
	    	while(!valid) {
	    		
	    		for (int i = 0; i < infantCount.length(); ++i) {
	                if (!Character.isDigit(infantCount.charAt(i))) {
	                	System.out.println("Invalid integer, please enter a valid infant count (one integer only): ");
	                	infantCount = scanner.next();
	                	break;
	                }
	                else {
	                	continue;
	                }
	            }
	    		
	    		try {
	    		   intInfantCount = Integer.parseInt(infantCount);
	    		}
	    		catch (NumberFormatException e) {
	    		   intInfantCount = -1;
	    		}
	    		
	    		if (intInfantCount < 0) {
	    			System.out.println("Invalid integer, please enter a valid infant count (one integer only): ");
		            infantCount = scanner.next();
	    		}
	    		
	    		else if (intInfantCount > intAdultsCount) {
	    			System.out.println("Not enough adults for the chosen infant amount, please keep it one infant per adult: ");
		            infantCount = scanner.next();
	    		}
	    		
	    		else {
	    			valid = true;
	    		}
	    		
	        }
    	}
    	
    	if (flightSearch.runFlightSearch(departureDate, departureAirportCode, false, returnDate, destinationAirportCode, seatingClass, intAdultsCount, intChildrenCount, intInfantCount)) {
    		System.out.println("Search successful!");
    	}
    	

    }
}