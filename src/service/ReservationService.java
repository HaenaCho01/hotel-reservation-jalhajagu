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
        int roomPrice = room.getPrice();

        if(customer.canAfford(roomPrice)) {
            // 아래 4개 줄은 임의로 작성했습니다. 변경해주세요!!!!!!!
            String confirmationDate = ""; // 현재 시간을 UTC 포맷으로
            Period period = Period.between(startDate, endDate);
            Reservation reservation = new Reservation(room, customer.getName(), customer.getPhoneNumber(), confirmationDate, period);
            reservationMap.put(reservation.getId(), reservation);
            System.out.println("객실 예약이 성공적으로 완료되었습니다.");

            customer.makePayment(roomPrice);
            hotelService.addToTotalSales(roomPrice);
            System.out.println("숙박 금액이 결제되었습니다.");
        } else {
            System.out.println("숙박 금액이 소지금보다 많아 예약이 불가합니다.");
        }
    }

    public void cancelReservation(Customer customer, String id) {
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
}
