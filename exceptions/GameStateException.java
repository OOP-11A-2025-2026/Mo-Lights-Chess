package exceptions;

/**
 * Exception thrown when an invalid game state operation is attempted.
 * For example, trying to undo a move when no moves have been made.
 */
public class GameStateException extends Exception {
    
    public GameStateException(String message) {
        super(message);
    }
    
    public GameStateException(String message, Throwable cause) {
        super(message, cause);
    }
}

