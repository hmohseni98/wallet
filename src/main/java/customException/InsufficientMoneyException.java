package customException;

public class InsufficientMoneyException extends RuntimeException{
    public InsufficientMoneyException() {
    }

    public InsufficientMoneyException(String message) {
        super(message);
    }
}
