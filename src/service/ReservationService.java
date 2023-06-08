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
    private HotelService hotelService;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
        this.hotelService = new HotelService();
    }

    public void addReservation(int roomNumber, Customer customer, String startDate, String endDate) {
        // 룸넘버로 객실 체크하기
        Room room = hotelService.findRoom(roomNumber);
        int roomPrice = room.getPrice();

        if(customer.canAfford(roomPrice)) {
            // 기간 계산
            String[] startSplit = startDate.split("-");
            LocalDate start = LocalDate.of(Integer.parseInt(startSplit[0]), Integer.parseInt(startSplit[1]), Integer.parseInt(startSplit[2]));
            String[] endSplit = endDate.split("-");
            LocalDate end = LocalDate.of(Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]), Integer.parseInt(endSplit[2]));
            int days = (int) ChronoUnit.DAYS.between(start, end);

            // UTC 시간
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            String nowAsISO = df.format(new Date());

            // 예약 객체 생성
            Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), nowAsISO, days);
            String id = reservation.getId();
            reservationMap.put(id, reservation);
            System.out.println("예약번호: " + id);
            System.out.println("객실 예약이 성공적으로 완료되었습니다.");

            // 정산
            customer.makePayment(roomPrice);
            hotelService.addToTotalSales(roomPrice);
            System.out.println("숙박 금액이 결제되었습니다.");
        } else {
            System.out.println("숙박 금액이 소지금보다 많아 예약이 불가합니다.");
        }
    }

    public void cancelReservation(Customer customer, String id) { // 예약 취소하기
        Reservation reservation = reservationMap.get(id);
        int roomPrice = reservation.getRoom().getPrice();
        reservationMap.remove(id);
        System.out.println("해당 예약이 성공적으로 취소되었습니다.");

        customer.getRefund(roomPrice);
        hotelService.subtractFromTotalSales(roomPrice);
        System.out.println("숙박 금액이 환불되었습니다.");
    }

    public void checkAllReservations() {

    }

    public void checkReservation(Customer customer, String id) { // 예약번호로 예약 조회하기
        if (reservationMap.containsKey(id)) { // 예약번호가 있을 시 예약내용 반환
            System.out.println(reservationMap.get(id));
        } else { // 해당 예약변호가 없을 시 출력
            System.out.println("조회되지 않는 예약 번호입니다. 확인 후 다시 입력해주세요!");
        }
    }
}
