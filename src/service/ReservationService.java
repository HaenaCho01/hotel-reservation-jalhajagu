package service;

import entity.Customer;
import entity.Reservation;
import entity.Room;
import exception.InsuffcientMoneyException;
import exception.ReservationNotFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReservationService {
    private Map<String, Reservation> reservationMap;

    public ReservationService() {
        this.reservationMap = new HashMap<>();
    }

    public Reservation addReservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        int roomPrice = room.getPrice();
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        if (customer.canAfford(roomPrice * days)) {
            // UTC 시간
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());

            // 예약 객체 생성
            Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), nowAsISO, startDate, endDate);
            reservationMap.put(reservation.getId(), reservation);

            // 숙박비 결제
            customer.makePayment(reservation.getTotalPrice());
            return reservation;
        } else {
            throw new InsuffcientMoneyException("숙박 금액이 소지금보다 많아 예약이 불가합니다.");
        }
    }

    public Reservation cancelReservation(Customer customer, String id) {
        if (reservationMap.containsKey(id)) {
            Reservation reservation = reservationMap.get(id);
            int totalPrice = reservation.getTotalPrice();
            LocalDate startDate = reservation.getStartDate();
            int days = reservation.getDays();

            // 예약 취소 처리
            reservationMap.remove(id);

            // 방 예약가능 상태로 바꿈
            ArrayList<LocalDate> dates = new ArrayList<>();
            for (int i = 0; i < days; i++) {
                dates.add(startDate.plusDays(i));
            }
            Room room = reservation.getRoom();
            room.substractReservedDate(dates);

            // 고객 소지금 환불
            customer.getRefund(totalPrice);
            return reservation;
        } else { // 해당 예약변호가 없을 시 출력
            throw new ReservationNotFoundException("해당하는 예약이 없습니다.");
        }
    }

    public ArrayList<Reservation> getAllReservations() {
        return new ArrayList<>(reservationMap.values());
    }

    public ArrayList<Reservation> getCustomerReservations(Customer customer) {
        ArrayList<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservationMap.values()) {
            if (reservation.belongsTo(customer.getPhoneNumber())) {
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

    public void reserveRoom(Room room, ArrayList<LocalDate> dates) {
        room.addReservedDate(dates);
    }

    public ArrayList<LocalDate> getDateList(LocalDate startDate, LocalDate endDate) {
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        ArrayList<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }
}