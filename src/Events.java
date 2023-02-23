public class Events {
    private int eventId;
    private String eventName;
    private String eventDate;
    private String eventOrganizer;
    private String bookingDate;
    private String eventStatus;

    public Events() {
    }

    public Events(int eventId, String eventName, String eventDate, String eventOrganizer, String bookingDate, String eventStatus) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventOrganizer = eventOrganizer;
        this.bookingDate = bookingDate;
        this.eventStatus = eventStatus;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    @Override
    public String toString() {
        return eventId + ":" + eventName + ":" + eventDate + ":" + eventOrganizer + ":"+ bookingDate + ":" + eventStatus + ",";
    }
}
