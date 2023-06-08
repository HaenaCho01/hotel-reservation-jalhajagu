package exception;

public class InsuffcientMoneyException extends RuntimeException {
    public InsuffcientMoneyException() {
    }

    public InsuffcientMoneyException(String message) {
        super(message);
    }
}
