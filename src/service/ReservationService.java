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

    public int addReservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
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
            System.out.println("숙박 금액이 결제되었습니다.");
            System.out.println("객실 예약이 성공적으로 완료되었습니다.");
            System.out.println("고객님의 예약번호는 " + reservation.getId() + "입니다.");
            printLine();
            // 정산
            int price = roomPrice * days;
            customer.makePayment(price);
            return price;
        } else {
            throw new InsuffcientMoneyException("숙박 금액이 소지금보다 많아 예약이 불가합니다.");
        }
    }

    public int cancelReservation(HotelService hotelService, Customer customer, String id) { // 예약 취소하기
        if (reservationMap.containsKey(id)) { // 예약번호가 있을 시 예약취소 처리
            Reservation reservation = reservationMap.get(id);
            int roomPrice = reservation.getRoom().getPrice();
            LocalDate startDate = reservation.getStartDate();
            LocalDate endDate = reservation.getEndDate();

            int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

            reservationMap.remove(id);
            System.out.println("해당 예약이 성공적으로 취소되었습니다.");

            // 방 예약가능상태로 바꿈
            ArrayList<LocalDate> dates = new ArrayList<>();
            for (int i = 0; i < days; i++) {
                dates.add(startDate.plusDays(i));
            }
            Room room = reservation.getRoom();
            room.substractReservedDate(dates);

            // 고객 소지금 환불 및 호텔 매출 감소
            customer.getRefund(roomPrice * days);
            System.out.println("숙박 금액이 환불되었습니다.");
            printLine();
            return roomPrice * days;
        } else { // 해당 예약변호가 없을 시 출력
            throw new ReservationNotFoundException();
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

    public ArrayList<LocalDate> getDateList(LocalDate startDate, LocalDate endDate) {
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        ArrayList<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }

    public void reserveRoom(Room room, ArrayList<LocalDate> dates) {
        room.addReservedDate(dates);
    }


    private void printLine() {
        System.out.println("-----------------------------------");
    }
}