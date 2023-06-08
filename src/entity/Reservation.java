package entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class Reservation {
    private String id; // UUID
    private Room room;
    private String customerName;
    private String customerPhoneNumber;
    private String confirmationDate; // 예약 확정한 날짜
    private LocalDate startDate; // 체크인 날짜
    private LocalDate endDate; // 체크아웃 날짜

    private int period; // 숙박 기간

    public Reservation(Room room, String customerName, String customerPhoneNumber, String confirmationDate, LocalDate startDate, LocalDate endDate, int period) {
        this.id = UUID.randomUUID().toString();
        this.room = room;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.confirmationDate = confirmationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
    }

    public String getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
     public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(String date) {
        this.confirmationDate = date;
    }


    public int getPeriod() {
        return period;
    }


    @Override
    public String toString() {
        return "예약 id : " + id +
                "\n객실 번호 : " + room.getRoomNumber() +
                "\n고객 성함 : " + customerName +
                "\n고객 전화번호 : " + customerPhoneNumber +
                "\n예약 확정 시간 : " + confirmationDate +
                "\n체크인 날짜 : " + startDate +
                "\n체크아웃 날짜 : " + endDate +
                "\n숙박 기간 : " + period + "박 " + (period+1) + "일"
                ;
    }
}
