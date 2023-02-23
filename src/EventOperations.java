import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EventOperations {
    private final String file_bookings = "src/booking.txt";
    private final String file_attendees = "src/attendees.txt";

    public void registerEvent() throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER THE DATE OF EVENT IN DD-MM-YYYY FORMAT - ");
        String eventDate = sc.next();
        Events bookedEvent = getBookedEvent(eventDate);
        if (bookedEvent == null) {
            /*BOOKING LOGIC*/
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("ENTER EVENT NAME - ");
            String eventName = br.readLine();
            System.out.println("ENTER EVENT ORGANIZER'S NAME - ");
            String eventOrganizer = br.readLine();
            List<Events> events = getEvents(file_bookings);
            Events event;
            int id = 0;
            if (events.size() > 0)
                id = events.get(events.size() - 1).getEventId();
            event = new Events(id + 1, eventName, eventDate, eventOrganizer, new SimpleDateFormat("dd-MM-yyyy").format(Date.from(Instant.now())), "ACTIVE");
            events.add(event);

            File file = new File(file_bookings);
            FileWriter fw;
            try {
                fw = new FileWriter(file, true);
                fw.write(event.toString());
                fw.close();
                System.out.println("EVENT HAS BEEN BOOKED SUCCESSFULLY.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (bookedEvent.getEventStatus().equals("CANCELLED")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("ENTER EVENT NAME - ");
            String eventName = br.readLine();
            System.out.println("ENTER EVENT ORGANIZER'S NAME - ");
            String eventOrganizer = br.readLine();
            List<Events> events = getEvents(file_bookings);
            int index = getEvent(bookedEvent.getEventId());
            events.get(index).setEventName(eventName);
            events.get(index).setEventOrganizer(eventOrganizer);
            events.get(index).setEventDate(eventDate);
            events.get(index).setBookingDate(new SimpleDateFormat("dd-MM-YYYY").format(Date.from(Instant.now())));
            events.get(index).setEventStatus("ACTIVE");

            String data = "";
            for (Events e : events) {
                data += e;
            }
            File file = new File(file_bookings);
            FileWriter fw;
            try {
                fw = new FileWriter(file);
                fw.write(data);
                fw.close();
                System.out.println("EVENT HAS BEEN BOOKED SUCCESSFULLY.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("ERROR : ANOTHER EVENT IS ALREADY BOOKED FOR THE SAME DATE.");
        }
    }

    private List<Events> getEvents(String file_bookings) {
        List<Events> events = new ArrayList<>();
        File file = new File(file_bookings);
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
                events.add(new Events(
                        Integer.parseInt(content[0]),
                        content[1],
                        content[2],
                        content[3],
                        content[4],
                        content[5])
                );
            }
        }
        return events;
    }

    public Events getBookedEvent(String eventDate) {
        List<Events> events = getEvents(file_bookings);
        if (events.size() != 0)
            for (Events event : events) {
                if (event.getEventDate().equals(eventDate))
                    return event;
            }
        return null;
    }

    public void deleteEvent() throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Events> events = getEvents(file_bookings);
        if (events.size() == 0) {
            System.out.println("ERROR : NO EVENTS BOOKED AT THIS MOMENT.");
            return;
        }
        getAllEvents();
        System.out.print("ENTER AN EVENT ID YOU WANT TO DELETE - ");
        int eventId = sc.nextInt();
        int index = getEvent(eventId);
        if (index != -1) {
            events.remove(index);
            FileWriter fileWriter = new FileWriter(file_bookings);
            String data = "";
            for (Events event : events) {
                data += event;
            }
            fileWriter.write(data);
            fileWriter.close();
            deleteAttendeesByEvent(eventId);

            System.out.println("EVENT DELETED SUCCESSFULLY.");
        } else
            System.out.println("ERROR : EVENT NOT PRESENT FOR THE GIVEN ID.");
    }

    private void deleteAttendeesByEvent(int eventId) throws IOException {
        AttendeeOperations operations = new AttendeeOperations();
        List<Attendee> event_attendees = operations.getAttendeesByEvent(file_attendees, eventId);

        List<Attendee> all_attendees = operations.getAttendees(file_attendees);
        all_attendees.removeAll(event_attendees);

        FileWriter fileWriter1 = new FileWriter(file_attendees);
        String data = "";
        for (Attendee a : all_attendees) {
            data += a;
        }
        fileWriter1.write(data);
        fileWriter1.close();
    }

    private int getEvent(int eventId) {
        List<Events> events = getEvents(file_bookings);
        for (Events e : events) {
            if (e.getEventId() == eventId) {
                return events.indexOf(e);
            }
        }
        return -1;
    }

    public void getAllEvents() {
        displayEvents(getEvents(file_bookings));
    }

    private void displayEvents(List<Events> events) {
        if (events.size() >= 0) {
            System.out.println("================================================================================");
            System.out.printf("%5s %15s %15s %15s %15s %9s", "ID", "EVENT NAME", "ORGANIZER", "BOOKING DATE", "EVENT DATE", "STATUS");
            System.out.println();
            System.out.println("================================================================================");
            for (Events event : events) {
                System.out.printf("%5s %15s %15s %15s %15s %9s", event.getEventId(), event.getEventName(), event.getEventOrganizer(), event.getBookingDate(), event.getEventDate(), event.getEventStatus());
                System.out.println();
            }
            System.out.println("================================================================================");
        } else {
            System.out.println("ERROR : NO DATA PRESENT AT THIS MOMENT.");
        }
    }

    public void cancelEvent() throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Events> events = getEvents(file_bookings);
        if (events.size() == 0) {
            System.out.println("ERROR : NO EVENTS BOOKED AT THIS MOMENT.");
            return;
        }
        getAllEvents();
        System.out.print("ENTER AN EVENT ID YOU WANT TO CANCEL - ");
        int eventId = sc.nextInt();
        int index = getEvent(eventId);
        if (index != -1) {
            events.get(index).setEventStatus("CANCELLED");
            FileWriter fileWriter = new FileWriter(file_bookings);
            String data = "";
            for (Events event : events) {
                data += event;
            }
            fileWriter.write(data);
            fileWriter.close();
            deleteAttendeesByEvent(eventId);
            System.out.println("EVENT CANCELLED SUCCESSFULLY.");
        } else {
            System.out.println("INVALID EVENT ID.");
        }
    }

    public void resetData() {
        try {
            FileWriter fileWriter = new FileWriter(file_bookings);
            fileWriter.write("");
            fileWriter.close();
            System.out.println("EVENTS DATA RESET SUCCESSFUL.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

