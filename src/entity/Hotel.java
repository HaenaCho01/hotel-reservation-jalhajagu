package entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms;
    private int totalSales;
    private int loginCode;

    public Hotel(String name, int loginCode) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.totalSales = 0;
        this.loginCode = loginCode;
    }

    public void addRoom(Room room) {
        rooms.add(room);
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

    public void addToTotalSales(int price) {
        this.totalSales += price;
    }

    public void subtractFromTotalSales(int price) {
        this.totalSales -= price;
    }

    public int getLoginCode() {
        return loginCode;
    }
}
