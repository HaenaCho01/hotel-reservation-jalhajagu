package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import entity.Customer;
import entity.Reservation;
import entity.Room;

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

        Room room = hotelService.findRoom(roomNumber);
        // 룸넘버 체크하기

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
        reservationMap.put(customer.getPhoneNumber(), reservation);

        int roomPrice = room.getPrice();
        makePayment(customer, roomPrice);
//        for (Reservation re : reservationMap.values()) {
//            System.out.println(re.getRoom());
//            System.out.println(re.getConfirmationDate());
//            System.out.println(re.getCustomerName());
//            System.out.println(re.getPeriod());
//            System.out.println(re.getId());
//        }
    }

    private void makePayment(Customer customer, int roomPrice) {
        // 고객 소지금에서 숙박비 제하고...
        int updatedMoney = customer.getMoney() - roomPrice;
        customer.setMoney(updatedMoney);

        // 호텔 매출에 반영
        hotelService.addToTotalSales(roomPrice);
    }

    public void checkAllReservations() {

    }
}
