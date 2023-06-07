package service;

import entity.Reservation;

import java.util.HashMap;
import java.util.Map;

public class ReservationService {
    private Map<String, Reservation> reservationMap;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
    }
}
