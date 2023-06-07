package service;

import entity.Hotel;

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
}
