public class Board 
{
    private Square[][] squares; 

    public Board() {
        this.squares = new Square[8][8];
        this.initEmptyBoard();
        this.setStartingPosition();
    }
    private void initEmptyBoard()
    {
        for(int row = 0; row < 8; row++) 
        {
            for(int col = 0; col < 8; col++) 
            {
                this.squares[row][col] = new Square(row, col);
            }
        }
    }

    private void setStartingPosition()
    {
         for(int col = 0; col < 8; col++) {
            this.squares[6][col].setPiece(new Pawn("white"));
            this.squares[1][col].setPiece(new Pawn("black"));
            if(col == 0 || col == 7)
            {
                this.squares[7][col].setPiece(new Rook("white"));
                this.squares[0][col].setPiece(new Rook("black"));
            }
            if(col == 1 || col == 6)
            {
                this.squares[7][col].setPiece(new Knight("white"));
                this.squares[0][col].setPiece(new Knight("black"));
            }
            if(col == 2 || col == 5)
            {
                this.squares[7][col].setPiece(new Bishop("white"));
                this.squares[0][col].setPiece(new Bishop("black"));
            }
            if(col == 3)
            {
                this.squares[7][col].setPiece(new Queen("white"));
                this.squares[0][col].setPiece(new Queen("black"));
            }
            if(col == 4)
            {
                this.squares[7][col].setPiece(new King("white"));
                this.squares[0][col].setPiece(new King("black"));
            }
        }

    }

    //get square by row and col
    public Square getSquare(int row, int col) {
        if(row < 0 || row > 7 || col < 0 || col > 7)
            throw new IllegalArgumentException("Row and col must be between 0 and 7");
        return this.squares[row][col];
    }
    //get square by file and rank(e.g., d4)
    public Square getSquare(String square)
    {
        String files = "abcdefgh";
        String ranks = "87654321";
        int col = files.indexOf(square.charAt(0));
        int row = ranks.indexOf(square.charAt(1));
        return this.squares[row][col];
    }
    public Square[][] getSquares()
    {
        return this.squares;
    }
    
    public void printBoard() {
        for(int row = 0; row < 8; row++) 
        {
            System.out.print((8 - row) + " ");
            for(int col = 0; col < 8; col++) {
                System.out.print(this.squares[row][col].toString() + " ");
            }
            System.out.println();
        }
        System.out.println("  a  b  c  d  e  f  g  h");
    }
}
