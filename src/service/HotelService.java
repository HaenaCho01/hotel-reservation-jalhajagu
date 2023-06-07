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
}
