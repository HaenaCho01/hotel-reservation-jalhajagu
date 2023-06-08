package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
            Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), nowAsISO, startDate, endDate, days);
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

    public void cancelReservation(HotelService hotelService, Customer customer, String id) { // 예약 취소하기
        if (reservationMap.containsKey(id)) { // 예약번호가 있을 시 예약취소 처리
            Reservation reservation = reservationMap.get(id);
            int roomPrice = reservation.getRoom().getPrice();
            int days = reservation.getPeriod();

            reservationMap.remove(id);
            System.out.println("해당 예약이 성공적으로 취소되었습니다.");

            // 방 예약가능상태로 바꿈
            LocalDate startDate = reservation.getStartDate();
            ArrayList<LocalDate> dates = new ArrayList<>();
            for (int i = 0; i < days; i++){
                dates.add(startDate.plusDays(i));
            }
            Room room = reservation.getRoom();
            room.substractReservedDate(dates);

            // 고객 소지금 환불 및 호텔 매출 감소
            customer.getRefund(roomPrice * days);
            hotelService.subtractFromTotalSales(roomPrice * days);
            System.out.println("숙박 금액이 환불되었습니다.");
            consoleUtil.printLine();
        } else { // 해당 예약변호가 없을 시 출력
            consoleUtil.printLine();
            System.out.println("조회되지 않는 예약 번호입니다. 확인 후 다시 입력해주세요! 이전 화면으로 돌아갑니다!");
            consoleUtil.printLine();
        }

    }

    public void checkAllReservations() {

    }


    public void checkReservation(Customer customer, String id) { // 예약번호로 예약 조회하기
        if (reservationMap.containsKey(id)) { // 예약번호가 있을 시 예약내용 반환
            consoleUtil.printLine();
            System.out.println(reservationMap.get(id));
            consoleUtil.printLine();
        } else { // 해당 예약변호가 없을 시 출력
            consoleUtil.printLine();
            System.out.println("조회되지 않는 예약 번호입니다. 확인 후 다시 입력해주세요! 이전 화면으로 돌아갑니다!");
            consoleUtil.printLine();
        }
    }
}
