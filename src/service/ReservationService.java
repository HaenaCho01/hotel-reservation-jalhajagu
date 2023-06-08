package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

import entity.Customer;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import exception.InsuffcientMoneyException;
import exception.ReservationNotFoundException;
import util.ConsoleUtil;

import java.time.LocalDate;

public class ReservationService {
    private Map<String, Reservation> reservationMap;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
    }

    public int addReservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        int roomPrice = room.getPrice();
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        if(customer.canAfford(roomPrice * days)) {
            // UTC 시간
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());

            // 예약 객체 생성
            Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), nowAsISO, days);
            reservationMap.put(customer.getPhoneNumber(), reservation);

            // 정산
            int price = roomPrice * days;
            customer.makePayment(price);
            return price;
        } else {
            throw new InsuffcientMoneyException("숙박 금액이 소지금보다 많아 예약이 불가합니다.");
        }
    }

    public int cancelReservation(Customer customer, String id) {
        Reservation reservation = getReservation(id);
        int roomPrice = reservation.getRoom().getPrice();
        int period = reservation.getPeriod();
        reservationMap.remove(id);

        // 정산
        int price = roomPrice * period;
        customer.getRefund(price);
        return price;
    }

    public ArrayList<Reservation> getAllReservations() {
        return new ArrayList<>(reservationMap.values());
    }

    public ArrayList<Reservation> getCustomerReservations(Customer customer) {
        ArrayList<Reservation> customerReservations = new ArrayList<>();
        for(Reservation reservation : reservationMap.values()) {
            if(reservation.belongsTo(customer.getPhoneNumber())) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public Reservation getReservation(String id) {
        if (!reservationMap.containsKey(id)) {
            throw new ReservationNotFoundException(id + "번 예약이 존재하지 않습니다.");
        }
        return reservationMap.get(id);
    }

    public ArrayList<LocalDate> getDateList(LocalDate startDate, LocalDate endDate) {
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        ArrayList<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < days; i++){
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }

    public Room reserveRoom(Room room, ArrayList<LocalDate> dates) {
        room.addReservedDate(dates);
        return room;
    }
}