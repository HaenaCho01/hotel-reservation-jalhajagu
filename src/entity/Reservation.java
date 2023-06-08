package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class Reservation {
    private String id; // UUID
    private Room room;
    private String customerName;
    private String customerPhoneNumber;
    private String confirmationDate; // 예약 확정한 날짜
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalPrice; // 고객이 결제한 금액
//    private int period; // 숙박 기간

    public Reservation(Room room, String customerName, String customerPhoneNumber, String confirmationDate, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID().toString();
        this.room = room;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.confirmationDate = confirmationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = room.getPrice() * getDays();
    }

    public String getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public boolean belongsTo(String phoneNumber) {
        return this.customerPhoneNumber.equals(phoneNumber);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTotalPrice() { return totalPrice; }

    public int getDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public String toString() {
        return "예약 id : " + id +
                ", 예약 확정 시간 : " + confirmationDate +
                ", 객실 번호 : " + room.getRoomNumber() +
                "\n고객 성함 : " + customerName +
                ", 고객 전화번호 : " + customerPhoneNumber +
                "\n숙박 기간 : " + startDate + " ~ " + endDate
                ;
    }
}
