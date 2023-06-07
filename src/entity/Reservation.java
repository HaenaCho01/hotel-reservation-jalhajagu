package entity;

import java.time.Duration;
import java.util.UUID;

public class Reservation {
    UUID id;
    Room room;
    String customerName;
    String customerPhoneNumber;
    String date;
    Duration duration;
}
