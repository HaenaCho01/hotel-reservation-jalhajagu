package entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private ArrayList<Room> rooms;
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

    public ArrayList<Room> getRooms() {
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
