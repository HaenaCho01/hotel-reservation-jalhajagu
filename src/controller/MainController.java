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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Room> checkAvailableRooms(LocalDate startDate, LocalDate endDate) {
        ArrayList<LocalDate> dates = reservationService.getDateList(startDate, endDate);
        return hotelService.getAvailableRooms(dates);
    }

    public Reservation addReservation(int roomNumber, Customer customer, LocalDate startDate, LocalDate endDate) {
        Room room = hotelService.findRoom(roomNumber);
        ArrayList<LocalDate> dates = reservationService.getDateList(startDate, endDate);
        reservationService.reserveRoom(room, dates);
        try {
            Reservation reservation = reservationService.addReservation(room, customer, startDate, endDate);
            hotelService.addToTotalSales(reservation.getTotalPrice());
            return reservation;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Reservation cancelReservation(Customer customer, String id) {
        try {
            Reservation reservation = reservationService.cancelReservation(customer, id);
            hotelService.subtractFromTotalSales(reservation.getTotalPrice());
            return reservation;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Reservation> checkCustomerReservations(Customer customer) {
        return reservationService.getCustomerReservations(customer);
    }

    public Reservation checkReservation(String id) {
        try {
            return reservationService.getReservation(id);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
