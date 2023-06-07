package entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms;
    private int totalSales;
    private int loginCode;

    public Hotel() {
        this.name = "잘하자구 호텔";
        this.rooms = new ArrayList<>();
        this.totalSales = 0;
        this.loginCode = 1234;
        addRooms();
    }

    private void addRooms() {
        rooms.add(new Room(0, "Standard", 50000));
        rooms.add(new Room(1, "Standard", 50000));
        rooms.add(new Room(2, "Standard", 50000));
        rooms.add(new Room(3, "Superior", 80000));
        rooms.add(new Room(4, "Superior", 80000));
        rooms.add(new Room(5, "Deluxe", 100000));
        rooms.add(new Room(6, "Deluxe", 100000));
        rooms.add(new Room(7, "Suite", 150000));
    }

    public String getName() {
        return name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getLoginCode() {
        return loginCode;
    }
}
