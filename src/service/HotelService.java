package service;

import entity.Hotel;
import entity.Room;
import exception.PasswordNotMatchedException;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelService {
    private Hotel hotel;

    public HotelService() {
        this.hotel = new Hotel("잘하자구 호텔", 1234);
        hotel.addRoom(new Room(0, "Standard", 50000));
        hotel.addRoom(new Room(1, "Standard", 50000));
        hotel.addRoom(new Room(2, "Standard", 50000));
        hotel.addRoom(new Room(3, "Superior", 80000));
        hotel.addRoom(new Room(4, "Superior", 80000));
        hotel.addRoom(new Room(5, "Deluxe", 100000));
        hotel.addRoom(new Room(6, "Deluxe", 100000));
        hotel.addRoom(new Room(7, "Suite", 150000));
    }

    public Hotel adminLogin(int inputCode) {
        if(inputCode != hotel.getLoginCode()) {
            throw new PasswordNotMatchedException("로그인 코드가 올바르지 않습니다.");
        }
        return hotel;
    }

    public Room findRoom(int roomNumber) {
        return hotel.getRooms().get(roomNumber);
    }

    public ArrayList<Room> getAvailableRooms(ArrayList<LocalDate> dates) {
        ArrayList<Room> rooms = hotel.getRooms();
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean isAvailable = true;
            for (LocalDate date : dates) {
                if (room.getReservedDate().contains(date)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void addToTotalSales(int roomPrice) {
        int updatedSales = hotel.getTotalSales() + roomPrice;
        hotel.setTotalSales(updatedSales);
    }

    public void subtractFromTotalSales(int roomPrice) {
        int updatedSales = hotel.getTotalSales() - roomPrice;
        hotel.setTotalSales(updatedSales);
    }
}
