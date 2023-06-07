package entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms;
    private int totalSales;
    private int loginCode;

    public Hotel() {
        this.name = "";
        this.rooms = addRoom();
        this.totalSales = 0;
        this.loginCode = 1234;
    }

    private List<Room> addRoom() {
        List<Room> roomsList = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        roomsList.add(new Room(0, "21.74", 50000));
        roomsList.add(new Room(1, "21.74", 50000));
        roomsList.add(new Room(2, "21.74", 50000));
        roomsList.add(new Room(3, "35.48", 80000));
        roomsList.add(new Room(4, "35.48", 80000));
        roomsList.add(new Room(5, "35.48", 80000));
        roomsList.add(new Room(6, "50.85", 150000));
        roomsList.add(new Room(7, "50.85", 150000));
        return roomsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
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

    public void setLoginCode(int loginCode) {
        this.loginCode = loginCode;
    }
}
