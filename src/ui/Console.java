package ui;

import entity.Room;
import entity.Customer;
import service.CustomerService;
import service.HotelService;
import service.ReservationService;
import util.ConsoleUtil;

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

    public boolean loginCustomer() {
        return true;
    }

    public void resisterCustomer() {
    }

    public void checkMoney() {
    }

    public void reserve() {
    }

    public void cancel() {
    }

    public void checkReservation() {
    }
}
