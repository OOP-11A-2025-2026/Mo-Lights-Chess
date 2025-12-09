package pieces;

import java.util.List;

public abstract class Piece {
    protected String color;
    protected boolean hasMoved;
    
    /**
     * Constructor for a chess piece.
     * 
     * @param color The color of the piece ("white" or "black")
     */
    public Piece(String color) {
        if (!color.equals("white") && !color.equals("black")) {
            throw new IllegalArgumentException("Color must be 'white' or 'black'");
        }
        this.color = color;
        this.hasMoved = false;
    }
    
    /**
     * Get all possible move directions/patterns for this piece from a given position.
     * This returns the theoretical moves without considering board state.
     * 
     * @param fromRow Current row position (0-7)
     * @param fromCol Current column position (0-7)
     * @return List of possible move positions as int arrays [row, col]
     */
    public abstract List<int[]> getPossibleMoves(int fromRow, int fromCol);
    
    /**
     * Get the unicode symbol for this piece.
     * 
     * @return Unicode character representing the piece
     */
    public abstract String getSymbol();
    
    /**
     * Get the color of this piece.
     * 
     * @return "white" or "black"
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Check if this piece has moved.
     * 
     * @return true if the piece has moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }
    
    /**
     * Mark this piece as having moved.
     */
    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }
    
    /**
     * Get the name of the piece type.
     * 
     * @return The class name of the piece
     */
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    public String getPieceLetter() {
        switch (this.getType()) 
        {
            case "Pieces.King": return "K";
            case "Pieces.Queen": return "Q";
            case "Pieces.Rook": return "R";
            case "Pieces.Bishop": return "B";
            case "Pieces.Knight": return "N";
            default: return ""; 
        }
    }
    
    //get piece as string(e.g., bR, wQ)
    public String getPieceStr()
    {
        String color = this.color.equals("white") ? "w" : "b";
        String pieceLetter = this.getPieceLetter().equals("") ? "P" : this.getPieceLetter();
        return color + pieceLetter;
    }

    @Override
    public String toString() {
        return getSymbol();
    }
}

