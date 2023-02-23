import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("+-----------------------------------------+");
        System.out.println("|  WELCOME TO EVENT REGISTRATION SYSTEM   |");
        System.out.println("+-----------------------------------------+");

        System.out.println("PLEASE LOGIN TO CONTINUE - ");
        Operations o = new Operations();

        try {
            if (o.login()) {
                do {
                    System.out.println("1. EVENTS");
                    System.out.println("2. ATTENDEES");
                    System.out.println("3. EXIT");
                    try {
                        switch (sc.nextInt()) {
                            case 1:
                                o.getEventMenu();
                                break;
                            case 2:
                                o.getAttendeeMenu();
                                break;
                            case 3:
                                System.out.println("THANKS VISIT AGAIN.");
                                return;
                            default:
                                System.out.println("ERROR : INVALID CHOICE. PLEASE SELECT THE OPTION BETWEEN 1 TO 4.");
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR : INVALID INPUT." + e.getMessage());
                        break;
                    }
                } while (true);
            } else {
                System.out.println("ERROR : LOGIN FAILURE! INVALID CREDENTIALS.");
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}