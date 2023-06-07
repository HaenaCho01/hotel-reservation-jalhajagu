package service;

import entity.Customer;
import entity.Reservation;
import entity.Room;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class ReservationService {
    private Map<String, Reservation> reservationMap;
    private HotelService hotelService;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
        this.hotelService = new HotelService();
    }

    public void addReservation(int roomNumber, Customer customer, LocalDate startDate, LocalDate endDate) {
        //매개변수 LocalDate 타입인지 확실하지 않음.
        Room room = hotelService.findRoom(roomNumber);

        // 아래 4개 줄은 임의로 작성했습니다. 변경해주세요!!!!!!!
        String confirmationDate = ""; // 현재 시간을 UTC 포맷으로
        Period period = Period.between(startDate, endDate);
        Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), confirmationDate, period);
        reservationMap.put(reservation.getId(), reservation);
        System.out.println("객실 예약이 성공적으로 완료되었습니다.");

        int roomPrice = room.getPrice();
        makePayment(customer, roomPrice);
        System.out.println("숙박 금액이 결제되었습니다.");
    }

    private void makePayment(Customer customer, int roomPrice) {
        // 고객 소지금에서 숙박비 제하고...
        int updatedMoney = customer.getMoney() - roomPrice;
        customer.setMoney(updatedMoney);

        // 호텔 매출에 반영
        hotelService.addToTotalSales(roomPrice);
    }

    public void cancelReservation(Customer customer, String id) {
        Reservation reservation = reservationMap.get(id);
        int roomPrice = reservation.getRoom().getPrice();
        reservationMap.remove(id);
        System.out.println("해당 예약이 성공적으로 취소되었습니다.");

        refund(customer, roomPrice);
        System.out.println("숙박 금액이 환불되었습니다.");
    }

    private void refund(Customer customer, int roomPrice) {
        // 고객 소지금에서 숙박비 더하고...
        int updatedMoney = customer.getMoney() + roomPrice;
        customer.setMoney(updatedMoney);

        // 호텔 매출에 반영
        hotelService.subtractFromTotalSales(roomPrice);
    }

    public void checkAllReservations() {

    }
}
