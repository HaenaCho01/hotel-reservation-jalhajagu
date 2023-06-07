package entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    String name;
    List<Room> rooms;
    int totalSales;
    int loginCode;

    public Hotel() {
        this.name = "";
        this.rooms = new ArrayList<>();
        this.loginCode = 1234;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
