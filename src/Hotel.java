import java.util.ArrayList;
import java.util.List;

public class Hotel {
    String name;
    List<Room> rooms;
    int totalSales;
    int loginCode;

    public Hotel(String name, int loginCode) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.loginCode = loginCode;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
