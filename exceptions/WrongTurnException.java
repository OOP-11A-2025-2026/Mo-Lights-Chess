package exceptions;

/**
 * Exception thrown when a player attempts to move a piece out of turn.
 */
public class WrongTurnException extends InvalidMoveException {
    
    public WrongTurnException(String currentTurn, String attemptedColor) {
        super(String.format("It's %s's turn, but %s attempted to move.", currentTurn, attemptedColor));
    }
    
    public WrongTurnException(String message) {
        super(message);
    }
}

