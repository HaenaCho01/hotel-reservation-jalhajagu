package exception;

public class DuplicatedCustomerException extends RuntimeException {
    public DuplicatedCustomerException() {}

    public DuplicatedCustomerException(String message) {
        super(message);
    }
}
