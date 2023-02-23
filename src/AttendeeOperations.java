import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AttendeeOperations {
    EventOperations eventOperations = new EventOperations();
    private final String file_attendees = "src/attendees.txt";

    public void registerAttendee() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER THE DATE OF EVENT IN DD-MM-YYYY FORMAT - ");
        String eventDate = sc.next();
        Events bookedEvent = eventOperations.getBookedEvent(eventDate);
        if (bookedEvent == null) {
            System.out.println("ERROR : NO EVENTS BOOKED FOR THE GIVEN DATE.");
        } else if (bookedEvent.getEventStatus().equals("CANCELLED")) {
            System.out.println("ERROR : THE EVENT HAS BEEN CANCELLED.");
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("ENTER THE NAME OF ATTENDEE - ");
            String attendeeName = br.readLine();
            System.out.println("ENTER THE EMAIL OF ATTENDEE - ");
            String attendeeEmail = br.readLine();
            System.out.println("ENTER THE MOBILE NUMBER OF ATTENDEE - ");
            String attendeeMobileNo = br.readLine();
            List<Attendee> attendees = getAttendees(file_attendees);
            Attendee attendee;
            int id = 0;
            if (attendees.size() > 0)
                id = attendees.get(attendees.size() - 1).getAttendeeId();
            attendee = new Attendee(id + 1, attendeeName, attendeeEmail, attendeeMobileNo, bookedEvent.getEventId());
            attendees.add(attendee);
            File file = new File(file_attendees);
            FileWriter fw;
            try {
                fw = new FileWriter(file, true);
                fw.write(attendee.toString());
                fw.close();
                System.out.println("ATTENDEE HAS BEEN REGISTERED FOR " + bookedEvent.getEventName() + " SUCCESSFULLY.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void searchAttendee() throws IOException {
        while (true) {
            System.out.println("1. SEARCH BY NAME");
            System.out.println("2. SEARCH BY EMAIL");
            System.out.println("3. SEARCH BY MOBILE NO");
            System.out.println("4. SEARCH BY EVENT");
            System.out.println("5. BACK TO PREVIOUS MENU");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            Attendee a = null;
            switch (choice) {
                case 1:
                    a = searchByName();
                    if (a == null)
                        System.out.println("ATTENDEE NOT FOUND");
                    else {
                        System.out.println("================================================");
                        System.out.println("ATTENDEE NAME      - " + a.getAttendeeName());
                        System.out.println("ATTENDEE EMAIL     - " + a.getAttendeeEmail());
                        System.out.println("ATTENDEE MOBILE NO - " + a.getAttendeeMobileNo());
                        System.out.println("================================================");
                    }
                    break;
                case 2:
                    a = searchByEmail();
                    if (a == null)
                        System.out.println("ATTENDEE NOT FOUND");
                    else {
                        System.out.println("================================================");
                        System.out.println("ATTENDEE NAME      - " + a.getAttendeeName());
                        System.out.println("ATTENDEE EMAIL     - " + a.getAttendeeEmail());
                        System.out.println("ATTENDEE MOBILE NO - " + a.getAttendeeMobileNo());
                        System.out.println("================================================");
                    }
                    break;
                case 3:
                    a = searchByMobileNo();
                    if (a == null)
                        System.out.println("ATTENDEE NOT FOUND");
                    else {
                        System.out.println("================================================");
                        System.out.println("ATTENDEE NAME      - " + a.getAttendeeName());
                        System.out.println("ATTENDEE EMAIL     - " + a.getAttendeeEmail());
                        System.out.println("ATTENDEE MOBILE NO - " + a.getAttendeeMobileNo());
                        System.out.println("================================================");
                    }
                    break;
                case 4:
                    eventOperations.getAllEvents();
                    System.out.print("ENTER THE EVENT ID TO SEARCH THE ATTENDEE - ");
                    int eventId = sc.nextInt();
                    displayAttendees(getAttendeesByEvent(file_attendees, eventId));
                    break;
                case 5:
                    return;
                default:
                    System.out.println("INVALID INPUT");
            }
        }
    }

    public List<Attendee> getAttendeesByEvent(String file_attendees, int eventId) {
        List<Attendee> attendees = new ArrayList<>();
        File file = new File(file_attendees);
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNext()) {
            String[] lines = sc.nextLine().split(",");
            for (String line : lines) {
                String[] content = line.split(":");
                int eId = Integer.parseInt(content[4]);
                if (eId == eventId)
                    attendees.add(new Attendee(
                                    Integer.parseInt(content[0]),
                                    content[1],
                                    content[2],
                                    content[3],
                                    Integer.parseInt(content[4])
                            )
                    );
            }
        }
        return attendees;
    }

    private Attendee searchByMobileNo() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("ENTER MOBILE NUMBER OF ATTENDEE - ");
        String mobileNo = br.readLine();
        List<Attendee> attendees = getAttendees(file_attendees);
        for (Attendee attendee : attendees) {
            if (attendee.getAttendeeMobileNo().equals(mobileNo))
                return attendee;
        }
        return null;
    }

    private Attendee searchByEmail() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("ENTER EMAIL ID OF ATTENDEE - ");
        String email = br.readLine();
        List<Attendee> attendees = getAttendees(file_attendees);
        for (Attendee attendee : attendees) {
            if (attendee.getAttendeeEmail().equals(email))
                return attendee;
        }
        return null;
    }

    private Attendee searchByName() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("ENTER NAME ID OF ATTENDEE - ");
        String attendeeName = br.readLine();
        List<Attendee> attendees = getAttendees(file_attendees);
        for (Attendee attendee : attendees) {
            if (attendee.getAttendeeName().equals(attendeeName))
                return attendee;
        }
        return null;
    }

    public void deleteAttendee() throws IOException {
        eventOperations.getAllEvents();
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER THE EVENT ID TO DELETE THE ATTENDEE FROM - ");
        int eventId = sc.nextInt();
        List<Attendee> attendees = getAttendeesByEvent(file_attendees, eventId);

        if (attendees.size() > 0) {
            displayAttendees(attendees);
            System.out.print("ENTER THE ATTENDEE'S ID TO DELETE - ");
            int attendeeId = sc.nextInt();
            int index = getAttendeeById(attendees, attendeeId);
            if (index == -1) {
                System.out.println("INVALID ID.");
            } else {
                List<Attendee> all_attendees = getAttendees(file_attendees);
//                System.out.println(index);
                all_attendees.remove(index);
                FileWriter fileWriter = new FileWriter(file_attendees);
                String data = "";
                for (Attendee a : all_attendees) {
                    System.out.println(a);
                    data += a;
                }
                fileWriter.write(data);
                fileWriter.close();
                System.out.println("ATTENDEE DELETED SUCCESSFULLY.");
            }
        }else System.out.println("ERROR : NO DATA PRESENT AT THIS MOMENT.");

    }

    private int getAttendeeById(List<Attendee> attendees, int attendeeId) {
        for (int i=0;i<attendees.size();i++) {
            if (attendees.get(i).getAttendeeId() == attendeeId)
                return i;
        }
        return -1;
    }

    public List<Attendee> getAttendees(String file_attendees) {
        List<Attendee> attendees = new ArrayList<>();
        File file = new File(file_attendees);
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNext()) {
            String[] lines = sc.nextLine().split(",");
            for (String line : lines) {
                String[] content = line.split(":");
                attendees.add(new Attendee(
                                Integer.parseInt(content[0]),
                                content[1],
                                content[2],
                                content[3],
                                Integer.parseInt(content[4])
                        )
                );
            }
        }
        return attendees;
    }

    private void displayAttendees(List<Attendee> attendees) {
        if (attendees.size() > 0) {
            System.out.println("================================================================================");
            System.out.printf("%5s %15s %15s %15s", "ID", "ATTENDEE NAME", "EMAIL", "MOBILE NUMBER");
            System.out.println();
            System.out.println("================================================================================");
            for (Attendee attendee : attendees) {
                System.out.printf("%5s %15s %15s %15s", attendee.getAttendeeId(), attendee.getAttendeeName(), attendee.getAttendeeEmail(), attendee.getAttendeeMobileNo());
                System.out.println();
            }
            System.out.println("================================================================================");
        } else {
            System.out.println("ERROR : NO DATA PRESENT AT THIS MOMENT.");
        }
    }
}
