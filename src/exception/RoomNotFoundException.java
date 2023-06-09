package exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException() {
    }

    public RoomNotFoundException(String message) {
        super(message);
    }
}
