package exceptions;

/**
 * Exception thrown when an error occurs during chess game file I/O operations.
 * This includes reading and writing PGN files.
 */
public class ChessFileException extends Exception {
    
    public ChessFileException(String message) {
        super(message);
    }
    
    public ChessFileException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ChessFileException(String filename, String operation, Throwable cause) {
        super(String.format("Failed to %s file '%s': %s", operation, filename, cause.getMessage()), cause);
    }
}

