package controller;

import entity.Customer;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import service.CustomerService;
import service.HotelService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainController {
    private HotelService hotelService;
    private CustomerService customerService;
    private ReservationService reservationService;

    public MainController() {
        this.hotelService = new HotelService();
        this.customerService = new CustomerService();
        this.reservationService = new ReservationService();
    }

    public Hotel adminLogin(int inputCode) {
        try {
            return hotelService.adminLogin(inputCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Reservation> checkAllReservations() {
        return reservationService.getAllReservations();
    }

    public Customer customerLogin(String phoneNumber, String password) {
        try {
            Customer customer = customerService.findCustomer(phoneNumber);
            customerService.checkPassword(customer, password);
            return customer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void resisterCustomer(String name, String phoneNumber, String password, int money) {
        Customer customer = new Customer(name, phoneNumber, password, money);
        try {
            customerService.addCustomer(customer);
            System.out.println("회원가입이 완료되었습니다");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeReservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        try {
            int price = reservationService.addReservation(room, customer, startDate, endDate);
            hotelService.addToTotalSales(price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelReservation(Customer customer, String id) {
        int price = reservationService.cancelReservation(hotelService, customer, id);
        hotelService.subtractFromTotalSales(price);
    }

    public ArrayList<Reservation> checkCustomerReservations(Customer customer) {
        return reservationService.getCustomerReservations(customer);
    }

    public Reservation checkReservation(String id) {
        try {
            return reservationService.getReservation(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<LocalDate> convertToDateList(LocalDate startDate, LocalDate endDate) {
        return reservationService.getDateList(startDate, endDate);
    }

    public ArrayList<Room> checkAvailableRooms(ArrayList<LocalDate> dates) {
        return hotelService.getAvailableRooms(dates);
    }

    public Room selectRoom(int roomNumber, ArrayList<LocalDate> dates) {
        Room room = hotelService.findRoom(roomNumber);
        return reservationService.reserveRoom(room, dates);
    }
}
