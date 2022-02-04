package customException;

public class InvalidWalletException extends RuntimeException{

    public InvalidWalletException(String message) {
        super(message);
    }

    public InvalidWalletException() {
    }
}
