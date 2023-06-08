package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import entity.Customer;
import entity.Reservation;
import entity.Room;
import util.ConsoleUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReservationService {
    private Map<String, Reservation> reservationMap;
    private ConsoleUtil consoleUtil;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
        this.consoleUtil = new ConsoleUtil();
    }

    public void addReservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        // 룸넘버로 객실 체크하기
        int roomPrice = room.getPrice();
        // 기간 계산
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        if(customer.canAfford(roomPrice * days)) {
            // UTC 시간
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());
            // 예약 객체 생성
            Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), nowAsISO, days);
            reservationMap.put(reservation.getId(), reservation);
            // 정산 및 확인 출력
            customer.makePayment(roomPrice * days);
            System.out.println("숙박 금액이 결제되었습니다.");
            System.out.println("객실 예약이 성공적으로 완료되었습니다.");
            System.out.println("고객님의 예약번호는 " + reservation.getId() + "입니다.");
            consoleUtil.printLine();
        } else {
            System.out.println("숙박 금액이 소지금보다 많아 예약이 불가합니다.");
            consoleUtil.printLine();
        }
    }

    public void cancelReservation(Customer customer, String id) { // 예약 취소하기
        Reservation reservation = reservationMap.get(id);
        int roomPrice = reservation.getRoom().getPrice() * reservation.getPeriod();
        reservationMap.remove(id);
        System.out.println("해당 예약이 성공적으로 취소되었습니다.");

        customer.getRefund(roomPrice);
        System.out.println("숙박 금액이 환불되었습니다.");
    }

    public void checkAllReservations() {

    }

    public void checkMoney(Customer customer) { // 소지금 조회하기
        if (customer.getMoney() != 0) { // 고객 소지금 출력
            System.out.println("고객님의 소지금은 " + customer.getMoney() + "원 입니다.");
        } else if (customer.getMoney() == 0){ // 소지금이 없을 시 출력
            System.out.println("고객님의 소지금이 없습니다.");
        }
    }

    public void checkReservation(Customer customer, String id) { // 예약번호로 예약 조회하기
        if (reservationMap.containsKey(id)) { // 예약번호가 있을 시 예약내용 반환
            String checkReservation = reservationMap.get(id).toString();
            System.out.println(checkReservation);
        } else { // 해당 예약변호가 없을 시 출력
            System.out.println("조회되지 않는 예약 번호입니다. 확인 후 다시 입력해주세요!");
        }
    }
}
