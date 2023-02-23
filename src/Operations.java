import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Operations {
    private final String file_credentials = "src/credentials.txt";

    public boolean login() {
        boolean status = false;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("USERNAME - ");
            String username = sc.next();
            System.out.print("PASSWORD - ");
            String password = sc.next();
            File file = new File(file_credentials);
            Scanner reader = new Scanner(file);
            if (reader.hasNext()) {
                String[] credentials = reader.nextLine().split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    status = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public void getAttendeeMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        AttendeeOperations attendeeOperations = new AttendeeOperations();
        while (true) {
            System.out.println("1. REGISTER AN ATTENDEE");
            System.out.println("2. SEARCH AN ATTENDEE");
            System.out.println("3. DELETE AN ATTENDEE");
            System.out.println("4. BACK TO MAIN MENU");

            switch (sc.nextInt()) {
                case 1:
                    attendeeOperations.registerAttendee();
                    break;
                case 2:
                    attendeeOperations.searchAttendee();
                    break;
                case 3:
                    attendeeOperations.deleteAttendee();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("NOT A VALID CHOICE");
            }
        }
    }

    public void getEventMenu() throws ParseException, IOException {
        Scanner sc = new Scanner(System.in);
        EventOperations eventOperations = new EventOperations();
        while (true) {
            System.out.println("1. REGISTER AN EVENT");
            System.out.println("2. CANCEL AN EVENT");
            System.out.println("3. DELETE AN EVENT");
            System.out.println("4. DISPLAY EVENTS");
            System.out.println("5. RESET DATA");
            System.out.println("6. BACK TO MAIN MENU");

            switch (sc.nextInt()) {
                case 1:
                    eventOperations.registerEvent();
                    break;
                case 2:
                    eventOperations.cancelEvent();
                    break;
                case 3:
                    eventOperations.deleteEvent();
                    break;
                case 4:
                    eventOperations.getAllEvents();
                    break;
                case 5:
                    eventOperations.resetData();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("NOT A VALID CHOICE");
            }
        }
    }

    public void getVenueMenu() {
        Scanner sc = new Scanner(System.in);
        VenueOperations venueOperations = new VenueOperations();
        while (true) {
            System.out.println("1. ADD VENUE");
            System.out.println("2. DELETE VENUE");
            System.out.println("3. DISPLAY ALL VENUES");
            System.out.println("4. BACK TO MAIN MENU");

            switch (sc.nextInt()) {
                case 1:
                    venueOperations.addVenue();
                    break;
                case 2:
                    venueOperations.deleteVenue();
                    break;
                case 3:
                    venueOperations.getAllVenues();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("NOT A VALID CHOICE");
            }
        }
    }
}
