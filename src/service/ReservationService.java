package service;

import entity.Customer;
import entity.Reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReservationService {
    private Map<String, Reservation> reservationMap;

    public ReservationService(){
        this.reservationMap = new HashMap<>();
    }

    public void checkMoney(Customer customer) { // 소지금 조회하기
        if (customer.getMoney() != 0) { // 고객 소지금 출력
            System.out.println("고객님의 소지금은 " + customer.getMoney() + "원 입니다.");
        } else if (customer.getMoney() == 0){ // 소지금이 없을 시 출력
            System.out.println("소지금이 없습니다.");
        }
    }

    public void checkReservation(Customer customer) { // 예약번호로 예약 조회하기
        System.out.println("예약 번호를 입력해주세요!");
        Scanner sc = new Scanner(System.in);
        String reservationNum = sc.nextLine(); // 예약번호 입력
        if (reservationMap.containsKey(reservationNum)) { // 예약번호가 있을 시 예약내용 반환
            String reservationCheck = reservationMap.get(reservationNum).toString();
            System.out.println(reservationCheck);
        } else { // 해당 예약변호가 없을 시 출력
            System.out.println("조회되지 않는 예약 번호입니다. 확인 후 다시 입력해주세요!");
        }
    }
}
