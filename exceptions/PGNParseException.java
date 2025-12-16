package exceptions;

/**
 * Exception thrown when an error occurs while parsing PGN (Portable Game Notation) files.
 */
public class PGNParseException extends Exception {
    
    public PGNParseException(String message) {
        super(message);
    }
    
    public PGNParseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PGNParseException(String san, String reason) {
        super(String.format("Failed to parse PGN move '%s': %s", san, reason));
    }
}

