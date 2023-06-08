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

    public void makeReservation(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        int price = reservationService.addReservation(room, customer, startDate, endDate);
        hotelService.addToTotalSales(price);
    }

    public void cancelReservation(Customer customer, String id) {
        int price = reservationService.cancelReservation(customer, id);
        hotelService.subtractFromTotalSales(price);
    }

    public ArrayList<Reservation> checkCustomerReservations(Customer customer) {
        return reservationService.getCustomerReservations(customer);
    }

    public Reservation checkReservation(String id) {
        return reservationService.getReservation(id);
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