package service;

import entity.Hotel;
import entity.Room;

public class HotelService {
    Hotel hotel;

    public HotelService() {
        this.hotel = new Hotel();
    }

    public boolean loginSuccess(int inputCode) {
        return inputCode == hotel.getLoginCode();
    }

    public void checkTotalSales() {
        System.out.printf("현재 %s의 총 매출은 %d원 입니다.\n", hotel.getName(), hotel.getTotalSales());
    }

    public void addToTotalSales(int roomPrice) {
        int updatedSales = hotel.getTotalSales() + roomPrice;
        hotel.setTotalSales(updatedSales);
    }

    public Room findRoom(int roomNumber) {
        return hotel.getRooms().get(roomNumber);
    }
}
