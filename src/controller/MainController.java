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
        return hotelService.adminLogin(inputCode);
    }

    public ArrayList<Reservation> checkAllReservations() {
        return reservationService.getAllReservations();
    }

    public Customer customerLogin(String phoneNumber, String password) {
        Customer customer = customerService.findCustomer(phoneNumber);
        customerService.checkPassword(customer, password);
        return customer;
    }

    public void resisterCustomer(String name, String phoneNumber, String password, int money) {
        Customer customer = new Customer(name, phoneNumber, password, money);
        customerService.addCustomer(customer);
    }

    public ArrayList<Room> checkAvailableRooms(LocalDate startDate, LocalDate endDate) {
        ArrayList<LocalDate> dates = reservationService.getDateList(startDate, endDate);
        return hotelService.getAvailableRooms(dates);
    }

    public Reservation addReservation(int roomNumber, Customer customer, LocalDate startDate, LocalDate endDate) {
        Room room = hotelService.findRoom(roomNumber);
        ArrayList<LocalDate> dates = reservationService.getDateList(startDate, endDate);
        reservationService.reserveRoom(room, dates);
        Reservation reservation = reservationService.addReservation(room, customer, startDate, endDate);
        hotelService.addToTotalSales(reservation.getTotalPrice());
        return reservation;
    }

    public Reservation cancelReservation(Customer customer, String id) {
        Reservation reservation = reservationService.cancelReservation(customer, id);
        hotelService.subtractFromTotalSales(reservation.getTotalPrice());
        return reservation;
    }

    public ArrayList<Reservation> checkCustomerReservations(Customer customer) {
        return reservationService.getCustomerReservations(customer);
    }

    public Reservation checkReservation(String id) {
        return reservationService.findReservation(id);
    }
}
