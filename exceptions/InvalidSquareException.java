package exceptions;

/**
 * Exception thrown when an invalid square position is accessed or specified.
 */
public class InvalidSquareException extends Exception {
    
    public InvalidSquareException(String message) {
        super(message);
    }
    
    public InvalidSquareException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidSquareException(int row, int col) {
        super(String.format("Invalid square position: row=%d, col=%d. Must be between 0 and 7.", row, col));
    }
}

