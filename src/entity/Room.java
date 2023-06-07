package entity;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private int roomNumber;
    private String size;
    private int price;
    private Set reservedDate;

    public Room(int roomNumber, String size, int price) {
        this.roomNumber = roomNumber;
        this.size = size;
        this.price = price;
        this.reservedDate = new HashSet();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Set reservedDate) {
        this.reservedDate = reservedDate;
    }
}