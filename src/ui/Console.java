package ui;

import entity.Customer;
import entity.Room;
import service.CustomerService;
import service.HotelService;
import service.ReservationService;
import util.ConsoleUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        Customer customer = new Customer(name, phoneNumber, password, 1000000);
        customerService.customers.put(phoneNumber, customer);
    }

    public void checkMoney(Customer customer) {
        customerService.checkMoney(customer);
    }

    public void reserve(Customer customer) {
        LocalDate startDate = LocalDate.parse(consoleUtil.getValueOf("체크인 날짜를 입력해주세요"));
        LocalDate endDate = LocalDate.parse(consoleUtil.getValueOf("체크아웃 날짜를 입력해주세요"));
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        ArrayList<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < days; i++){
            dates.add(startDate.plusDays(i));
        }
        Room room = selectRoom(dates);

        // 가격이 초과되지 않을 경우에만 addReservedDate
        int daysForCheck = (int) ChronoUnit.DAYS.between(startDate, endDate);
        if (customer.canAfford(room.getPrice() * daysForCheck)) {
            room.addReservedDate(dates);
        }

        // 예약 시작
        reservationService.addReservation(room, customer, startDate, endDate);

    }

    private Room selectRoom(ArrayList<LocalDate> dates) {
        List<Room> rooms = hotelService.getRooms();
        System.out.println("선택하신 날짜에 예약가능한 객실목록은 아래와 같습니다.");

        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean isAvailable = true;
            for (LocalDate date : dates) {
                if (room.getReservedDate().contains(date)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableRooms.add(room);
            }
        }
        for (Room room : availableRooms) {
            System.out.println(room);
        }
        System.out.print("원하시는 객실을 선택해주세요: ");
        int inputNum = scanner.nextInt();
        scanner.nextLine();
        return rooms.get(inputNum);
    }

    public void cancel(Customer customer) {
        String id = consoleUtil.getValueOf("취소할 예약번호를 입력해주세요"); // 예약번호 입력
        reservationService.cancelReservation(hotelService, customer, id);
    }

    public void checkReservation(Customer customer) {
        String id = consoleUtil.getValueOf("조회할 예약번호를 입력해주세요"); // 예약번호 입력
        reservationService.checkReservation(customer, id);
    }
}
