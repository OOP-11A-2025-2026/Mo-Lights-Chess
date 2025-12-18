/**
 * Represents the result and status of a chess game
 */
public class GameResult {
    public enum ResultType {
        ONGOING,      // Game is still in progress
        WHITE_WIN,    // White won (by checkmate or resignation)
        BLACK_WIN,    // Black won (by checkmate or resignation)
        DRAW,         // Game ended in a draw (agreed or stalemate)
        WHITE_RESIGNED,  // White resigned
        BLACK_RESIGNED   // Black resigned
    }

    private ResultType resultType;
    private String reason;

    public GameResult() {
        this.resultType = ResultType.ONGOING;
        this.reason = "Game in progress";
    }

    public GameResult(ResultType resultType, String reason) {
        this.resultType = resultType;
        this.reason = reason;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public String getReason() {
        return this.reason;
    }

    public void setResult(ResultType resultType, String reason) {
        this.resultType = resultType;
        this.reason = reason;
    }

    public boolean isGameOver() {
        return this.resultType != ResultType.ONGOING;
    }

    public String getResultMessage() {
        switch (this.resultType) {
            case ONGOING:
                return "Game in progress";
            case WHITE_WIN:
                return "White wins!";
            case BLACK_WIN:
                return "Black wins!";
            case DRAW:
                return "Game ends in a draw";
            case WHITE_RESIGNED:
                return "White resigned - Black wins!";
            case BLACK_RESIGNED:
                return "Black resigned - White wins!";
            default:
                return "Unknown result";
        }
    }
}
