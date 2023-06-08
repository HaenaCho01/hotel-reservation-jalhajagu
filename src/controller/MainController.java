package controller;

import entity.Customer;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import exception.CustomerNotFoundException;
import exception.PasswordNotMatchedException;
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
        hotelService.registerHotel();
    }

    public Hotel adminLogin(int inputCode) {
        return hotelService.adminLogin(inputCode);
    }

    public ArrayList<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public Customer customerLogin(String phoneNumber, String password) {
        Customer customer = customerService.findOne(phoneNumber);
        if (!customer.getPassword().equals(password)){
            throw new PasswordNotMatchedException("비밀번호가 올바르지 않습니다. 다시 시도해주세요.");
        }
        return customer;
    }

    public void resisterCustomer(String name, String phoneNumber, String password, int money) {
        Customer customer = new Customer(name, phoneNumber, password, money);
        customerService.customers.put(phoneNumber, customer);
    }

    public void addReservation(int roomNumber, Customer customer, LocalDate startDate, LocalDate endDate) {
        Room room = hotelService.findRoom(roomNumber);
        reservationService.addReservation(room, customer, startDate, endDate);
    }

    public void cancelReservation(Customer customer, String id) {
        reservationService.cancelReservation(customer, id);
    }

    public ArrayList<String> getCustomerReservationIds(Customer customer) {
        return reservationService.getCustomerReservationIds(customer);
    }

    public Reservation getReservation(String id) {
        return reservationService.getReservation(id);
    }

    public ArrayList<Reservation> checkAllReservations() {
        return reservationService.getAllReservations();
    }
}
