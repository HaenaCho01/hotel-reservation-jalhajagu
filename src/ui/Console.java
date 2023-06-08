package ui;

import controller.MainController;
import entity.Customer;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import util.ConsoleUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
    private ConsoleUtil consoleUtil;
    private MainController controller;

    public Console() {
        this.consoleUtil = new ConsoleUtil();
        this.controller = new MainController();
    }

    public Hotel adminLogin() {
        int inputCode = Integer.parseInt(consoleUtil.getValueOf("코드를 입력해주세요"));
        return controller.adminLogin(inputCode);
    }

    public void checkTotalSales(Hotel hotel) {
       int totalSales = hotel.getTotalSales();
        System.out.printf("호텔 총 매출은 %d원 입니다.\n", totalSales);
    }

    public void checkAllReservations() {
        ArrayList<Reservation> reservations = controller.checkAllReservations();
        if(reservations.isEmpty()) {
            System.out.println("예약 내역이 없습니다.");
        } else {
            for(Reservation reservation : reservations) {
                System.out.printf("예약 목록은 총 %d건입니다.\n", reservations.size());
                System.out.println(reservation);
            }
        }
    }

    public Customer customerLogin() {
        String phoneNumber = consoleUtil.getValueOf("전화번호를 입력해주세요");
        String password = consoleUtil.getValueOf("비밀번호를 입력해주세요");
        return controller.customerLogin(phoneNumber, password);
    }

    public void resisterCustomer() {
        String name = consoleUtil.getValueOf("이름을 입력해주세요");
        String phoneNumber = consoleUtil.getValueOf("전화번호를 입력해주세요(010-0000-0000)");
        while(!isValid(phoneNumber)) {
            phoneNumber = consoleUtil.getValueOf("전화번호를 올바른 형식으로 입력해주세요");
        }
        String password = consoleUtil.getValueOf("비밀번호를 입력해주세요");
        int money = Integer.parseInt(consoleUtil.getValueOf("소지금을 입력해주세요"));
        controller.resisterCustomer(name, phoneNumber, password, money);
    }

    private boolean isValid(String phoneNumber) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public void checkMoney(Customer customer) {
        System.out.printf("%s 고객님의 현재 소지금은 %d원 입니다.\n", customer.getName(), customer.getMoney());
    }

    public void makeReservation(Customer customer) {
        LocalDate startDate = LocalDate.parse(consoleUtil.getValueOf("체크인 날짜를 입력해주세요"));
        LocalDate endDate = LocalDate.parse(consoleUtil.getValueOf("체크아웃 날짜를 입력해주세요"));
        System.out.println("선택하신 날짜에 예약가능한 객실목록은 아래와 같습니다.");
        ArrayList<LocalDate> dates = controller.convertToDateList(startDate, endDate);
        ArrayList<Room> availableRooms = controller.checkAvailableRooms(dates);
        for (Room room : availableRooms) {
            System.out.println(room);
        }
        int roomNumber = Integer.parseInt(consoleUtil.getValueOf("원하시는 객실 번호를 입력해주세요"));
        Room room = controller.selectRoom(roomNumber, dates);
        controller.makeReservation(room, customer, startDate, endDate);
    }

    public void cancelReservation(Customer customer) {
        String id = consoleUtil.getValueOf("취소할 예약번호를 입력해주세요");
        controller.cancelReservation(customer, id);
        System.out.println("예약이 정상적으로 취소되어 객실 금액이 환불되었습니다.");
    }

    public void checkCustomerReservations(Customer customer) {
        ArrayList<Reservation> reservations = controller.checkCustomerReservations(customer);
        System.out.printf("%s 고객님의 총 예약 내역입니다.\n", customer.getName());
        for(int i = 0; i < reservations.size(); i++) {
            System.out.printf("%d. %s번 예약\n", (i + 1), reservations.get(i).getId());
        }
        String id = consoleUtil.getValueOf("조회할 예약번호를 입력해주세요");
        System.out.printf("%s번 예약 상세 내역은 다음과 같습니다.\n", id);
        Reservation reservation = controller.checkReservation(id);
        System.out.println(reservation);
    }
}
