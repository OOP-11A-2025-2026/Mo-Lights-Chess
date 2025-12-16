package exceptions;

/**
 * Exception thrown when an invalid or illegal chess move is attempted.
 */
public class InvalidMoveException extends Exception {
    
    public InvalidMoveException(String message) {
        super(message);
    }
    
    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}

