package service;

import entity.Customer;
import entity.Reservation;
import entity.Room;
import util.ConsoleUtil;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class ReservationService {
    private Map<String, Reservation> reservationMap;
    private HotelService hotelService;
    private ConsoleUtil consoleUtil;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
        this.hotelService = new HotelService();
        this.consoleUtil = new ConsoleUtil();
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

    public void cancelReservation(Customer customer) { // 예약 취소하기
        String id = consoleUtil.getValueOf("취소할 예약번호를 입력해주세요"); // 예약번호 입력

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

    public void checkMoney(Customer customer) { // 소지금 조회하기
        if (customer.getMoney() != 0) { // 고객 소지금 출력
            System.out.println("고객님의 소지금은 " + customer.getMoney() + "원 입니다.");
        } else if (customer.getMoney() == 0){ // 소지금이 없을 시 출력
            System.out.println("고객님의 소지금이 없습니다.");
        }
    }

    public void checkReservation() { // 예약번호로 예약 조회하기
        String id = consoleUtil.getValueOf("조회할 예약번호를 입력해주세요"); // 예약번호 입력

        if (reservationMap.containsKey(id)) { // 예약번호가 있을 시 예약내용 반환
            String checkReservation = reservationMap.get(id).toString();
            System.out.println(checkReservation);
        } else { // 해당 예약변호가 없을 시 출력
            System.out.println("조회되지 않는 예약 번호입니다. 확인 후 다시 입력해주세요!");
        }
    }
}
