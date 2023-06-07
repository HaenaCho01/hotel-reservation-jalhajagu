package ui;

import entity.Reservation;
import entity.Room;
import service.CustomerService;
import service.HotelService;
import service.ReservationService;
import util.ConsoleUtil;
import entity.Customer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Console {
    private Scanner scanner;
    private ConsoleUtil consoleUtil;
    private HotelService hotelService;
    private CustomerService customerService;
    private ReservationService reservationService;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.consoleUtil = new ConsoleUtil();
        this.hotelService = new HotelService();
        this.customerService = new CustomerService();
        this.reservationService = new ReservationService();
    }


    public boolean loginManager() {
        int inputCode = Integer.parseInt(consoleUtil.getValueOf("코드를 입력해주세요"));
        return hotelService.loginSuccess(inputCode);
    }

    public void checkTotalSales() {
        hotelService.checkTotalSales();
    }

    public void checkAllReservations() {
    }

    public Customer loginCustomer() {
        String phoneNumber = consoleUtil.getValueOf("전화번호를 입력해주세요");
        if (customerService.customers.containsKey(phoneNumber)) {
            String password = consoleUtil.getValueOf("비밀번호를 입력해주세요");
            if (customerService.customers.get(phoneNumber).getPassword().equals(password)) {
                return customerService.customers.get(phoneNumber);

            }
            else {
                System.out.println("비밀번호가 틀립니다.");
                return null;
            }
        }
        else {
            System.out.println("등록된 전화번호가 없습니다");
            return null;
        }

    }

    public void resisterCustomer() {
        String name = consoleUtil.getValueOf("이름을 입력해주세요");
        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        String phoneNumber;
        while (true) {
            phoneNumber = consoleUtil.getValueOf("전화번호를 입력해주세요");
            Matcher matcher = pattern.matcher(phoneNumber);
            if (matcher.matches()) {
                break;
            }
        }
        String password = consoleUtil.getValueOf("비밀번호를 입력해주세요");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setPassword(password);
        customerService.customers.put(phoneNumber, customer);
    }

    public void checkMoney() {
    }

    public void reserve(Customer customer) {
        int roomNumber = Integer.parseInt(consoleUtil.getValueOf("객실 번호를 입력해주세요"));
        String startDate = consoleUtil.getValueOf("체크인 날짜를 입력해주세요");
        String endDate = consoleUtil.getValueOf("체크아웃 날짜를 입력해주세요");
        ReservationService reservation = new ReservationService();
        reservation.addReservation(roomNumber, customer, startDate, endDate);

    }

    public void cancel() {
    }

    public void checkReservation() {
    }
}
