package entity;

import java.time.Duration;
import java.util.UUID;

public class Reservation {
    private String id; // UUID
    private Room room;
    private String customerName;
    private String customerPhoneNumber;
    private String date;
    private Duration duration;

    private Reservation(){
        this.id = UUID.randomUUID().toString();
    }
    public Reservation(Room room, String customerName, String customerPhoneNumber, String date, Duration duration) {
        this();
        this.room = room;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.date = date;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
