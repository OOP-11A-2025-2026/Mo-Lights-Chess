public class Square {
    private int row;
    private int col;
    private Piece piece;
    
    /**
     * Constructor for a chess board square.
     * 
     * @param row Row position (0-7, where 0 is row 8 and 7 is row 1)
     * @param col Column position (0-7, where 0 is 'a' and 7 is 'h')
     */
    public Square(int row, int col) {
        this(row, col, null);
    }
    
    /**
     * Constructor for a chess board square with a piece.
     * 
     * @param row Row position (0-7)
     * @param col Column position (0-7)
     * @param piece Piece on this square (can be null)
     */
    public Square(int row, int col, Piece piece) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Row and column must be between 0 and 7");
        }
        this.row = row;
        this.col = col;
        this.piece = piece;
    }
    
    /**
     * Get the row position of this square.
     * 
     * @return Row position (0-7)
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Get the column position of this square.
     * 
     * @return Column position (0-7)
     */
    public int getCol() {
        return col;
    }
    
    /**
     * Get the piece on this square.
     * 
     * @return The piece, or null if the square is empty
     */
    public Piece getPiece() {
        return piece;
    }
    
    /**
     * Set the piece on this square.
     * 
     * @param piece The piece to place on this square
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * Check if this square has a piece.
     * 
     * @return true if there is a piece on this square, false otherwise
     */
    public boolean hasPiece() {
        return piece != null;
    }
    
    /**
     * Remove and return the piece from this square.
     * 
     * @return The piece that was on this square, or null if empty
     */
    public Piece removePiece() {
        Piece removedPiece = piece;
        piece = null;
        return removedPiece;
    }
    
    /**
     * Get the algebraic notation for this square (e.g., "e4").
     * 
     * @return Algebraic notation string
     */
    public String getAlgebraicNotation() {
        char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
        return "" + files[col] + ranks[row];
    }
    
    /**
     * Check if this is a light-colored square.
     * 
     * @return true if light square, false if dark square
     */
    public boolean isLightSquare() {
        return (row + col) % 2 == 1;
    }
    
    @Override
    public String toString() {
        if (piece != null) {
            return piece.toString();
        }
        return " ";
    }
    
    public String toStringDetailed() {
        String pieceStr = (piece != null) ? piece.getType() + "(" + piece.getColor() + ")" : "Empty";
        return "Square[" + getAlgebraicNotation() + ", " + pieceStr + "]";
    }
}

