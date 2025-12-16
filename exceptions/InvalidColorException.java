package exceptions;

/**
 * Exception thrown when an invalid piece color is specified.
 * Valid colors are "white" and "black".
 */
public class InvalidColorException extends Exception {
    
    public InvalidColorException(String providedColor) {
        super(String.format("Invalid color '%s'. Color must be 'white' or 'black'.", providedColor));
    }
    
    public InvalidColorException(String message, Throwable cause) {
        super(message, cause);
    }
}

